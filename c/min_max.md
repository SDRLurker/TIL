출처 : [http://stackoverflow.com/questions/3437404/min-and-max-in-c](http://stackoverflow.com/questions/3437404/min-and-max-in-c)

# C언어에서 MIN과 MAX

C에서 정의된 `MIN`과 `MAX`가 어디에 있습니까?

가능한 일반적이고 안전하게 이를 구현하는 최고의 방법은 무엇입니까? (주요 컴파일러에서 사용하는 컴파일러 확장/빌트인을 선호합니다.)

---

## 15 개의 답변 중 1 개의 답변

> C에서 정의된 `MIN` 과 `MAX` 가 어디에 있습니까?

없습니다.

> 가능한 일반적이고 안전하게 이를 구현하는 최고의 방법은 무엇입니까? (주요 컴파일러에서 사용하는 컴파일러 확장/빌트인을 선호합니다.)

함수로써 저는 `#define MIN(X, Y) (((X) < (Y)) ? (X) : (Y))`같은 매크로를 사용하지 않을 것입니다. 특별히, 당신의 코드에 배포할 계획이 있다면요. [GCC 구문 표현](https://gcc.gnu.org/onlinedocs/gcc/Statement-Exprs.html#Statement-Exprs)에서 당신 스스로 표준인 [`fmax`](https://en.cppreference.com/w/c/numeric/math/fmax)또는 [`fmin`](https://en.cppreference.com/w/c/numeric/math/fmin)같은 것을 사용하거나 [GCC 구문 표현식](https://gcc.gnu.org/onlinedocs/gcc/Statement-Exprs.html#Statement-Exprs)에서 [GCC의 typeof](http://gcc.gnu.org/onlinedocs/gcc-4.9.2/gcc/Typeof.html#Typeof)를 사용한 매크로를 고칠것입니다. (타입에 안전함도 보너스로 얻을 것입니다.)

```c
#define max(a,b) \
   ({ __typeof__ (a) _a = (a); \
       __typeof__ (b) _b = (b); \
     _a > _b ? _a : _b; })
```

모두가 "아, 이중 평가에 대해 알고 있습니다. 문제가 없습니다."라고 말하고 몇 달 후에는 몇 시간 동안 가장 어리석은 문제를 디버깅하게 될 것입니다.

`typeof` 대신에 `__typeof__`를 사용하세요.

> 만약 ISO C 프로그램에서 include될 때 당신이 헤더파일을 작성한다면 `typeof` 대신에 `__typeof__`를 써야 작동될 것입니다. 
