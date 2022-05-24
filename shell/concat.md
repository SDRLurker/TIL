출처 : [https://stackoverflow.com/questions/4181703/how-to-concatenate-string-variables-in-bash](https://stackoverflow.com/questions/4181703/how-to-concatenate-string-variables-in-bash)

# Bash에서 문자열 변수 합치는 방법

PHP에서 문자열들은 다음처럼 함께 합쳐질 수 있습니다.

```PHP
$foo = "Hello";
$foo .= " World";
```

여기 `$foo`는 "Hello World"가 됩니다.

Bash로 이를 어떻게 이룰 수 있을까요?

---

### 30개의 답변 중 2개의 답변

```shell
foo="Hello"
foo="${foo} World"
echo "${foo}"
> Hello World
```

일반적으로 두 변수를 연결하려면 다음과 같이 하나씩 작성하면 됩니다.

```shell
a='Hello'
b='World'
c="${a} ${b}"
echo "${c}"
> Hello World
```

---

Bash도 이 코드에서 보듯이 `+=` 연산자를 지원합니다.

```shell
A="X Y"
A+=" Z"
echo "$A"
```

출력

> X Y Z

