출처 : [https://serverfault.com/questions/59140/how-do-diff-over-ssh](https://serverfault.com/questions/59140/how-do-diff-over-ssh)

# ssh로 원격 diff 하는 방법

ssh로 접속만이 가능한 원격 머신에 있는 파일이나 폴더를 diff를 어떻게 할 수 있을까요?

------

## 14개의 답변 중 1 개의 답변만 추려냄.

Bash의 [process substitution](https://tldp.org/LDP/abs/html/process-sub.html)으로 당신은 원격 diff를 하실 수 있습니다.

```shell
diff foo <(ssh myServer 'cat foo')
```

또는 둘다 원격 서버라면 다음처럼 가능합니다.

```shell
diff <(ssh myServer1 'cat foo') <(ssh myServer2 'cat foo')
```
