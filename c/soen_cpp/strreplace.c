#include<string.h>
#include<stdio.h>

void insertstr(char *str, const char *insert)
{
	int len;

	len = strlen(insert);
	memmove(str+len, str, strlen(str)+1);
	memcpy(str, insert, len);
}

void deletestr(char *str, int count)
{
	memmove(str,str+count,strlen(str+count)+1);
}

void strreplace(char *str, const char *a, const char *b)
{
	char *p = str;

	for(;;) {
		p = strstr(p,a);
		if (p == NULL) return;
		deletestr(p, strlen(a));
		insertstr(p,b);
		p += strlen(b);
	}
}

int main()
{
	char str[128] = "welcome to korea";
	char str2[512] = "내가 그린 기린 그림은 암 기린을 그린 기린 그림이고 "
		"네가 그린 기린 그림은 숫 기린을 그린 기린 그림이다.";
	char *p;

	puts(str);
	p = strstr(str, "korea");
	insertstr(p, "beautiful ");
	puts(str);
	p = strstr(str, "to");
	deletestr(p, 3);
	puts(str);
	
	puts(str2);
	strreplace(str2, "기린", "기린아들");
	puts(str2);

	return 0;
}
