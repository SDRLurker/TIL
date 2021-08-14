출처 : [https://stackoverflow.com/questions/18924792/sort-and-remove-duplicates-from-int-array-in-c](https://stackoverflow.com/questions/18924792/sort-and-remove-duplicates-from-int-array-in-c)

# C의 int 배열을 정렬하고 중복 제거하기

저는 C를 배우고 있고 정렬 부분가지 왔습니다. 저는 `comp()` 함수를 작성하고 `int` 의 배열로 정렬하는 데 `qsort`를 사용하였습니다. 이제 다음에 할 일은 배열로부터 중복을 제거하는 것입니다. 한 번에 정렬과 중복제거가 가능합니까?

```c
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>    
int indexes[10] = { 0, 98, 45, 65, 45, 98, 78, 56, 65, 45 };

int comp(const void * elem1, const void * elem2) {

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

void printIndexArray() {    
    int i = 0;    
    for (i = 0; i < 10; i++) {    
        printf("i is %d\n", indexes[i]);    
    }
}

int main() {    
    qsort(indexes, sizeof(indexes) / sizeof(int), sizeof(int), comp);    
    printIndexArray();    
    return 0;
}
```

---

## 5 개의 답변 중 1 개의 답변

당신이 만든 숫자가 이미 정렬되었기 때문에 중복을 제거하는 것은 쉽습니다. C++에서는 `std::unique`로 만들어져 있습니다.

[http://en.cppreference.com/w/cpp/algorithm/unique](http://en.cppreference.com/w/cpp/algorithm/unique)

당신 스스로 하고 싶기를 원하시면 `unique`가 하는 것과 똑같이 당신도 다음처럼 하실 수 있습니다.

```c
int* unique (int* first, int* last)
{
  if (first==last) return last;

  int* result = first;
  while (++first != last)
  {
    if (!(*result == *first)) 
      *(++result)=*first;
  }
  return ++result;
}
```


