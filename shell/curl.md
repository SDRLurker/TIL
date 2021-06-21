**출처**

[https://stackoverflow.com/questions/40852784/how-can-i-use-a-variable-in-curl-call-within-bash-script](https://stackoverflow.com/questions/40852784/how-can-i-use-a-variable-in-curl-call-within-bash-script)

# bash 스크립트에서 curl 호출시 변수를 어떻게 사용할 수 있을까요?

간단한 작업이 있고 제 bash 스크립트 안에 curl 호출시 변수를 어떻게 사용할 수 있을지 알아내기 위해 이미 여러 시간을 소비하였습니다.

```shell
message="Hello there"
curl -X POST -H 'Content-type: application/json' --data '{"text": "${message}"}'
```

이는 문법적으로 작은 따옴표 안에 있기 때문에 ${message}을 출력합니다. 만약 따옴표를 바깥쪽은 큰 따옴표 안쪽은 작은 따옴표로 변경하였을 때 Hello와 there 명령어가 없다고(command not found: Hello 다음에 command not found: there) 나옵니다. 

어떻게 해야 합니까?

---

## 2개 답변 중 1개만 추림

변수는 작음 따옴표 안에서 확장될 수 없습니다. 큰 따옴표로 다음처럼 다시 작성할 수 있습니다.

```shell
curl -X POST -H 'Content-type: application/json' --data "{\"text\": \"${message}\"}"
```

큰 따옴표 안에 큰 따옴표는 escape 문자(`\`)가 있어야 합니다.

다른 방법은 다음처럼 할 수 있습니다.

```shell
curl -X POST -H 'Content-type: application/json' --data '{"text": "'"${message}"'"}'
```


이것은 작은 따옴표에서 밖에서 단어 분할을 방지하기 위해 `${message}` 를 큰 따옴표로 묶은 다음 다른 작은 따옴표 문자열로 끝납니다. 다음처럼 처리 됩니다.

```
... '{"text": "'"${message}"'"}'
    ^^^^^^^^^^^^
    작은 따옴표 문자열


... '{"text": "'"${message}"'"}'
                ^^^^^^^^^^^^
                큰 따옴표 문자열


... '{"text": "'"${message}"'"}'
                            ^^^^
                            작은 따옴표 문자열
```         
