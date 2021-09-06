출처 : [https://www.linuxquestions.org/questions/linux-newbie-8/ssh-login-without-password-not-working-4175561052/](https://www.linuxquestions.org/questions/linux-newbie-8/ssh-login-without-password-not-working-4175561052/) 

# 비밀번호가 없는 SSH 로그인이 잘 안됩니다

안녕하세요.

여기에 첫번째 위협입니다만 제가 규칙을 어긴거라면 친절하게 대해주세요... :)

저는 NAS 장치가 있고 2개 장치 모두에 root로 접근합니다.

저는 그들 사이에 rsync를 하고 싶습니다.

rsync 명령은 잘 됩니다만... cron job으로 자동화가 불가능 하도록 만드는 password 구문이 프롬프트로 나타납니다.

저는 이미 rsa key를 생성했고 다른 NAS로 인증 키를 얻었지만, 그 이후에도 password 구문이 있습니다.

다음을 시도하였습니다.

```shell
"cat /root/.ssh/id_rsa.pub | ssh root@destinationip 'cat >> .ssh/authorized_keys'
```

다음처럼 결과가 나왔습니다.

```shell
"The authenticity of host 'dest ip (destip)' can't be established.
RSA key fingerprint is 35:60:b9:53:a4:08:fb:1a:be:7a:7d:b5:4f:ee:cc:2c.
Are you sure you want to continue connecting (yes/no)? yes
Warning: Permanently added 'destip' (RSA) to the list of known hosts.
root@destip's password:
```

누군가 범인이 어디에 있는지 힌트를 줄 수 있습니까?

## 1개의 답변

부적절한 권한이 주로 이에 대한 원인입니다.

```shell
chmod 750 ~
chmod 700 ~/.ssh
chmod 600 ~/.ssh/authorized_keys
```

대상 머신에서 위의 권한 작업을 해야합니다.
