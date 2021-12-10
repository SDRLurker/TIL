출처 : [https://superuser.com/questions/403800/how-can-i-make-the-bash-backtick-operator-keep-newlines-in-output](https://superuser.com/questions/403800/how-can-i-make-the-bash-backtick-operator-keep-newlines-in-output)

# bash backtick(\`\`) 연산자로 출력의 개행을 어떻게 유지할 수 있을까요?

backtick(\`\`) 치환에 bash가 개행 문자를 먹지 않도록 하는 방법이 있습니까?

예를 들면

```shell
var=`echo line one && echo line two`
echo $var

line one line two
```

하지만 제가 원하는 것은 다음과 같습니다.

```shell
var=`echo line one && echo line two` # plus some magic
echo $var

line one
line two
```

---

## 3개의 답변 중 1개

이는 backtick() 치환 문제가 아닌 `echo`; 의 문제입니다. 제어 문자가 작동하려면 변수에 따옴표를 사용해야 합니다.

```
$ var=`echo line one && echo line two`
$ echo "$var"
line one
line two
```
