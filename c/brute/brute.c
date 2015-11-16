#include<stdio.h>
#include<stdlib.h>

typedef struct _ELEMENT{
	char name[80+1];
	int from;
	int to;
	int curidx;	
} ELEMENT;

void print_all_curidx(const ELEMENT* elements, int size)
{
	int i;

	printf("( ");
	for(i=0;i<size;i++)
	{
		if(i<size-1)
			printf("%d,", elements[i].curidx);
		else
			printf("%d", elements[i].curidx);

	}
	printf(" )\n");
}

void swap(int *a, int *b)
{
	int temp;
	temp = *a;
	*a = *b;
	*b = temp;
}

int main()
{
	ELEMENT *elements = NULL;
	int size = 0;
	int fidx = 0;
	int bidx = 0;
	int i;
	char tmp[10+1];

	do
	{
		printf("변수의 개수를 입력하세요...");	
		fgets(tmp, sizeof(tmp), stdin);
		size = atoi(tmp);
	} while(size < 0);

	elements = (ELEMENT*)malloc(sizeof(ELEMENT) * size);
	if(elements==NULL)
	{
		printf("메모리 할당 실패!!");
		return -1;
	}

	for(i=0;i<size;i++)
	{
		printf("%d 번째 변수명을 입력하세요...", i+1);	
		fgets(elements[i].name, 
			sizeof(elements[i].name),	
			stdin);

		printf("%d 번째 시작값을 입력하세요...", i+1);	
		fgets(tmp, sizeof(tmp), stdin);
		elements[i].from = atoi(tmp);

		printf("%d 번째 끝값을 입력하세요...", i+1);	
		fgets(tmp, sizeof(tmp), stdin);
		elements[i].to = atoi(tmp);

		if(elements[i].from > elements[i].to)
			swap(&elements[i].from, &elements[i].to);
	}	

	/* 알고리즘 시작 */
	for(i=0;i<size;i++)
		elements[i].curidx = elements[i].from;

	while(1)
	{
		print_all_curidx(elements, size);

		for(i=0;i<size;i++)
		{
			if(elements[i].curidx < elements[i].to)
				break;
		}
		if(i==size)
			break;

		if(elements[0].curidx<elements[0].to)
			elements[0].curidx++;
		else
		{
			while(elements[fidx].curidx == elements[fidx].to)
				if(fidx < size)		fidx++;

			if(elements[fidx].curidx<elements[fidx].to)
				elements[fidx].curidx++;

			for(bidx=--fidx;bidx>=0;bidx--)
				elements[bidx].curidx = elements[bidx].from;
			fidx = 0;
		}
	} 
	/* 알고리즘 끝 */

	if(elements)	free(elements);

	return 0;
}
