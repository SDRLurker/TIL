#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<arpa/inet.h>
#include<sys/socket.h>
#include<sys/types.h>
#include<unistd.h>
#include<limits.h>

#define IP_CONF     "/conf/mon/ip.dat"
#define CMD_CONF    "/conf/mon/cmd.dat"
#define CMD_DIR     "/cmd/"
#define RESULT_SIZE		1024*1024
void error_handling(char *message);

#ifdef _DAEMON
int daemon_init(void)
{
	pid_t	pid;
	if((pid = fork()) < 0)
		return -1;
	else if(pid!=0)
		exit(0);
	
	setsid();
	chdir("/");
	umask(0);

	return 0;		
}
#endif

int main(int argc, char **argv)
{
	int serv_sock, clnt_sock;
	struct sockaddr_in serv_addr;
	struct sockaddr_in clnt_addr;
	socklen_t clnt_addr_size;
    int socket_option = 1;

    char home[PATH_MAX];			/* 홈디렉터리 저장 */
	/* 클라이언트로 부터 받은 명령을 실행한 표준 결과를 출력한다. */
#ifdef	_MALLOC
	char *result=NULL;
#else
	char result[RESULT_SIZE];		
#endif

	if(argc!=2)
	{
		fprintf(stderr,"Usage : %s <port>\n", argv[0]);
		return 1; 	
	}
    strcpy(home,getenv("HOME"));

#ifdef	_DAEMON
	daemon_init();
#endif

	serv_sock = socket(PF_INET,SOCK_STREAM, 0);
	if(serv_sock == -1)
	{
		error_handling("TCP socket error\n");
	}
    setsockopt(serv_sock, SOL_SOCKET, SO_REUSEADDR, 
		&socket_option, sizeof(socket_option));

	memset(&serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	serv_addr.sin_port = htons(atoi(argv[1]));

	if(bind(serv_sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr))==-1)
	{
		error_handling("bind() error\n");
	}

	if(listen(serv_sock,1)==-1)
	{
		error_handling("listen() error\n");
	}

	while(1)
	{
		int read_leng;
		char read_cmd[255];
		char shell_cmd[255];

		int result_len=0;
#ifdef	_MALLOC
		char *result=NULL;
#endif
		char line[1024];

		FILE *fp;
		int num;	/* 파일에서 몇 라인을 읽었는지 확인하는 변수 */
		int ipNone = 0;
		int cmdNone = 0;
		int i;

		clnt_addr_size = sizeof(struct sockaddr);
		clnt_sock = accept(serv_sock, (struct sockaddr*)&clnt_addr,
			&clnt_addr_size);

		/* ip필터링(화이트 리스트) */
        strcpy(shell_cmd,home);
        strcat(shell_cmd,IP_CONF);
		fp = fopen(shell_cmd,"rt");
		if(!fp)
		{
			close(clnt_sock);
			continue;	
		}
		fgets(line,1024,fp);

		num = atoi(line);
		for(i=0;i<num;i++)
		{
			char *lsAddr = inet_ntoa(clnt_addr.sin_addr);
			fgets(line,1024,fp);
			if(strncmp(line,lsAddr,strlen(lsAddr))==0)
			{
				ipNone = 1;
				break;
			}
		}
		if(ipNone==0)
		{
			fclose(fp);
			close(clnt_sock);
			continue;
		}
		fclose(fp);

		if(read(clnt_sock, &read_leng, sizeof(int)) <=0)
		{
			close(clnt_sock);
			continue;
		}
		if(read(clnt_sock, read_cmd, read_leng) <=0)
		{
			close(clnt_sock);
			continue;
		}
		/* ip필터링끝 */

		/* 명령어 필터링(화이트 리스트) */
        strcpy(shell_cmd,home);
        strcat(shell_cmd,CMD_CONF);
		fp = fopen(shell_cmd,"rt");
        fgets(line,1024,fp);
        num = atoi(line);
        for(i=0;i<num;i++)
        {
            fgets(line,1024,fp);
            if(strncmp(line,read_cmd,strlen(read_cmd))==0)
            {
                cmdNone = 1;
				break;
            }
        }
        if(cmdNone==0)
        {
            fclose(fp);
            close(clnt_sock);
            continue;
        }
        fclose(fp);
		/* 명령어 필터링끝 */

		/* popen결과를 그대로 보낼거임 */
		strcpy(shell_cmd, read_cmd);
		fp = popen(shell_cmd,"r");
		if(!fp)
		{
			printf("file pointer error\n");	
			close(clnt_sock);
			continue;
		}
		while(fgets(line,1024,fp)!=NULL)
		{
			result_len += strlen(line);
#ifdef	_MALLOC 	
			if(result==NULL)
			{
				result = (char*)malloc(sizeof(char)*result_len);
			}	
			else
			{
				result = (char*)realloc(result, sizeof(char)*result_len);
			}
#endif
			strncpy(result+result_len-strlen(line),line,strlen(line));
		}
#ifdef	_MALLOC
		result = (char*)realloc(result,sizeof(char)*(result_len+1));
#endif
		result[result_len] = 0;
		write(clnt_sock,(char*)&result_len,sizeof(int));
		write(clnt_sock,result,result_len);
#ifdef	_MALLOC
		free(result);
		result = NULL;
#endif
		pclose(fp);
		close(clnt_sock);
        sleep(1);
	}
	close(serv_sock);
	return 0;
}	

void error_handling(char *message)
{
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}
