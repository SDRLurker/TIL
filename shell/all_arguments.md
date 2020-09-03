출처  
[https://stackoverflow.com/questions/4824590/propagate-all-arguments-in-a-bash-shell-script](https://stackoverflow.com/questions/4824590/propagate-all-arguments-in-a-bash-shell-script)

## bash 쉘 스크립트에서 모든 인수를 전달

저는 다른 스크립트를 호출하는 애무 간단한 스크립트를 작성하였고 현재 스크립트로부터 실행할 스크립트로 파라미터를 전달할 필요가 있습니다.  
예를 들어 제 스크립트는 `foo.sh`이고 `bar.sh`를 호출합니다.  
foo.sh:

```
bar $1 $2 $3 $4
```

각 파라미터를 명시적으로 지정하지 않고 이를 어떻게 할 수 있을까요?

---

### 8개의 답변 중 1개의 답변만 추려냄

같은 것을 파라미터로 사용하길 원하신다면 순수한 `$@`대신에 `"$@"`를 사용하세요.  
확인:

```
$ cat foo.sh
#!/bin/bash
baz.sh $@

$ cat bar.sh
#!/bin/bash
baz.sh "$@"

$ cat baz.sh
#!/bin/bash
echo Received: $1
echo Received: $2
echo Received: $3
echo Received: $4

$ ./foo.sh first second
Received: first
Received: second
Received:
Received:

$ ./foo.sh "one quoted arg"
Received: one
Received: quoted
Received: arg
Received:

$ ./bar.sh first second
Received: first
Received: second
Received:
Received:

$ ./bar.sh "one quoted arg"
Received: one quoted arg
Received:
Received:
Received:
```
