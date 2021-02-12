**출처**

[http://stackoverflow.com/questions/4466655/how-do-i-specify-what-function-to-export-from-so-library-when-compiling-the-c](http://stackoverflow.com/questions/4466655/how-do-i-specify-what-function-to-export-from-so-library-when-compiling-the-c)

# C 코드를 컴파일할 때 .so 라이브러리로 부터 특정함수만 export하는 방법?

저의 "C" 코드에는 많은 함수들이 있습니다. .so를 컴파일할 때 .so 파일에 모든 이름을 볼 수 있습니다. 어떻게 export될 함수들만 지정할 수 있습니까? (내부적으로 사용되는 거는 공개되지 말아야 합니다.)

-------------

## 2개의 답변 중 1개의 답변만 추려냄.

.so 파일을 언급하셨기 때문에, gcc나 gcc같은 컴파일러를 사용하신다고 가정합니다.

기본적으로 모든 extern 함수들은 연결된 object에서 보실 수 있습니다. 당신은 함수와 (전역변수를) `hidden` 속성을 사용하여 각 경우에 대해 (같은 라이브러리의 다른 소스 파일로부터 사용되는 함수를 `extern`으로 나둔 채로) 숨기실 수 있습니다.

```c
int __attribute__((visibility("hidden"))) foo(void)
{
    return 10;
}
```

다른 방법으로 당신은 gcc로 `-fvisibility=hidden` 옵션을 보냄으로서 default를 `hidden`으로 바꿀 수 있습니다. 다음을 사용하여 export할 특별한 표시를 할 수 있습니다.

```c
__attribute__((visibility("default")))
```
