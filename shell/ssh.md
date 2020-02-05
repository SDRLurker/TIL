출처 : [https://stackoverflow.com/questions/4936807/how-to-do-ssh-with-a-timeout-in-a-script](https://stackoverflow.com/questions/4936807/how-to-do-ssh-with-a-timeout-in-a-script)

# script에서 ssh로 timeout을 주는 방법?

저는 원격 호스트로 비밀번호 없는 SSH로 접속하는ㄴ script를 실행하고 있습니다. 만약 원격호스트가 실행하는데 무한의 시간이 걸린다면 저는 timeout을 설정하여 그 ssh 세션을 빠져나와 저의 sh script에서 다음 행을 진행하고 싶습니다.

아이디어나 그것을 어떻게 할 수 있을까요?

### 6개의 답변 중 1개의 답변만 추려냄

```shell
ssh -o ConnectTimeout=10  <hostName>
```

10은 초로 된 시간을 의미합니다.
