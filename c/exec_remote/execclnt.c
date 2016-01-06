#include<sys/select.h>
#include<sys/socket.h>
#include<sys/types.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<sys/time.h>
#include<unistd.h>
#include<fcntl.h>
#include<stdio.h>
#include<errno.h>
#include<string.h>
#include<stdlib.h>
#include<stdlib.h>
#include<string.h>

void error_handling(char *message);

int connectWithTimeout(int fd,struct sockaddr *remote, int len, int secs, int *err) 
{ 
    int saveflags,ret,back_err; 
    fd_set fd_w; 
    struct timeval timeout; 
	socklen_t	sock_len = (socklen_t)len;

    saveflags = fcntl(fd,F_GETFL,0); 
    if(saveflags<0)  
    { 
        perror("fcntl 1st error"); 
        *err=errno; 
        return -1; 
    } 
 
    /* Set non blocking */ 
    if( fcntl(fd,F_SETFL,saveflags|O_NONBLOCK)<0 )  
    { 
        perror("fcntl 2nd error"); 
        *err=errno; 
        return -1; 
    } 
 
    /* This will return immediately */ 
    *err = connect(fd,remote,len); 
    back_err=errno; 
 
    /* restore flags */ 
    if( fcntl(fd,F_SETFL,saveflags)<0 )  
    { 
        perror("fcntl 3rd error"); 
        *err=errno; 
        return -1; 
    } 
 
    /* return unless the connection was successful or the connect is 
    still in progress. */ 
    if(*err < 0 && back_err != EINPROGRESS)  
    { 
        perror("connect error"); 
        *err=errno; 
        return -1; 
    } 
 
    timeout.tv_sec = (long)secs; 
    timeout.tv_usec = 0L; 
 
    FD_ZERO(&fd_w); 
    FD_SET(fd,&fd_w); 
 
    *err= select(FD_SETSIZE,NULL,&fd_w,NULL,&timeout); 
    if(*err<0)  
    { 
        perror("select function error..."); 
        *err=errno; 
        return -1; 
    } 
 
    /* 0 means it timeout out & no fds changed */ 
    if(*err==0)  
    { 
        perror("connect timeout..."); 
        *err=ETIMEDOUT; 
        return -1; 
    } 
 
    /* Get the return code from the connect */ 
    len= sizeof(ret); 
    *err= getsockopt(fd,SOL_SOCKET,SO_ERROR,&ret,&sock_len); 
    if(*err<0)  
    { 
        perror("getsockopt error"); 
        *err=errno; 
        return -1; 
    } 
 
    /* ret=0 means success, otherwise it contains the errno */ 
    if(ret)  
    { 
        *err=ret; 
        return -1; 
    } 
    *err=0; 
    return 0; 
}
  
int main(int argc, char **argv)
{
	int sock;
	char *message;
	int total=0, rcv_len=0, accu=0, str_len,e;

	struct sockaddr_in remote_addr;
	struct timeval tv;

	if(argc!=4 && argc!=5)
	{
		printf("Usage : %s <IP> <port> <cmd> [sun]\n", argv[0]);
		return 1;
	}

	sock = socket(PF_INET, SOCK_STREAM, 0);
	if(sock == -1)
	{
		error_handling("TCP Socket Creation error");			
	}
	
	tv.tv_sec = 1;
	tv.tv_usec = 0;
	if(setsockopt(sock, SOL_SOCKET, SO_RCVTIMEO, (char*)&tv, sizeof(tv)))
	{
		error_handling("TCP Socket Creation error");			
	}

	memset(&remote_addr, 0, sizeof(remote_addr));
	remote_addr.sin_family = AF_INET;
	remote_addr.sin_addr.s_addr = inet_addr(argv[1]);
	remote_addr.sin_port = htons(atoi(argv[2]));

	if(connectWithTimeout(sock, (struct sockaddr*)&remote_addr, 
		sizeof(remote_addr), 1, &e)==-1)
	{
		printf("%s :\n",argv[1]);
		printf("connect error!\n");
		return 1;
	}
	/*( printf("%s : \n",argv[1]); */

	str_len = strlen(argv[3]) + 1;
	/*printf("cmd : %s, str_len : %d\n", argv[3], str_len);	*/
	if(argc==5 && strcmp(argv[4],"sun")==0)
		str_len = htonl(str_len);
	write(sock, (char*)&str_len, sizeof(int));
	write(sock, argv[3], strlen(argv[3]) + 1);

	rcv_len = read(sock,(char*)&total,4);
	if(rcv_len<=0)
	{
		printf("%s :\n",argv[1]);
		printf("connect error!\n");
		close(sock);	
		return 1;
	}
        if(argc==5 && strcmp(argv[4],"sun")==0)
                total = htonl(total);
	message = (char*)malloc(sizeof(char)*(total));
	memset(message,0,total);
	do
	{
		rcv_len = read(sock, message+accu, total);
		if(rcv_len < 0 && errno == EINTR)
			continue;
		if(rcv_len <= 0)
		{
			printf("%s :\n",argv[1]);
			printf("connect error!\n");
			close(sock);
			exit(1);
		}
		total -= rcv_len;
		accu += rcv_len;
	} while(total > 0);
	printf("%s\n",message);
	close(sock);
		
	return 0;
}

void error_handling(char *message)
{
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}
