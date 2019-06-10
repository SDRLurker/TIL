#include <string.h> 
#include <stdio.h> 
#include <stdlib.h> 
#include <ctype.h> 

int indexes[10] = { 0, 98, 45, 65, 45, 98, 78, 56, 65, 45 }; 

int comp(const void * elem1, const void * elem2) 
{ 
	int f = *((int*) elem1); 
	int s = *((int*) elem2); 
	if (f > s) { 
		return 1; 
	} 
	if (f < s) { 
		return -1; 
	} 
	return 0; 
} 

void printIndexArray(int size) 
{ 
	int i = 0; 
	for (i = 0; i < size; i++) { 
		printf("i is %d\n", indexes[i]); 
	} 
} 

int* unique (int* first, int* last) { 
	if (first==last) 
		return last; 
	int* result = first; 
	while (++first != last) { 
		if (!(*result == *first)) 
			*(++result)=*first; 
  	} 
	return ++result;
}

int main() 
{ 
	int *size, length;
	qsort(indexes, sizeof(indexes) / sizeof(int), sizeof(int), comp); 
        printf("before unique length=10\n");
	printIndexArray(10); 
	size = unique(indexes, indexes+10);
        length = size - indexes;
        printf("after unique length=%d\n", length);
	printIndexArray(length); 
	return 0; 
}
