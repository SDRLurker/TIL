출처

* [https://blog.tinned-software.net/generate-public-ssh-key-from-private-ssh-key/](https://blog.tinned-software.net/generate-public-ssh-key-from-private-ssh-key/)

# 비밀 SSH 키로부터 공개 SSH 키를 생성하기

잃어버린 공개 키나 웹서비스는 SSH키를 생성하지만 당신에게 공개키를 제공하지는 않습니다. 이 때 지금할 것은 무엇일까요? 이 상황에 대한 해결책이 있습니다.

SSH key로부터 SSH 비밀번호 없이 로그인을 설정하기 위해 당신은 공개키가 필요합니다. 그러나 공개키를 잃어버려도 비밀키를 가지고 있다면 그 키를 재생성하는 방법이 있습니다.

잃어버린 공개키로, 다음 명령은 이 SSH 키로 공개키가 없다는 것을 보여줍니다.

```shell
$ ssh-keygen -l -f ~/.ssh/id_rsa
test is not a public key file.
```

-l 옵션은 공개키의 지문(fingerprint)을 알려주며 -f 옵션은 지문(fingerprint)을 나열하는 키의 파일을 지정합니다.

비밀키로부터 잃어버린 공개키를 다시 생성하기 위해 다음 명령어는 -f 옵션으로 제공된 비밀키에 대한 공개키를 생성할 것입니다.

```shell
$ ssh-keygen -y -f ~/.ssh/id_rsa > ~/.ssh/id_rsa.pub
Enter passphrase:
```

-y 옵션은 비밀 SSH 키를 읽어 SSH 공개키를 stdout으로 출력할 것입니다. 공개키 부분은 비밀 키와 같은 이름의 파일이지만 확장자는 .pub 파일로 redirect됩니다. 키가 비밀번호 set이면 그 비밀번호는 공개키를 생성하는데 필요합니다.

생성된 공개키의 세부사항을 확인하려면 다음 명령어를 실행합니다.

```shell
$ ssh-keygen -l -f ~/.ssh/id_rsa
4096 d6:7b:c7:7a:4f:3c:4d:29:54:62:5f:2c:58:b2:cb:86 ~/.ssh/id_rsa (RSA)
```

이 명령어의 출력은 첫 번째 열에 키의 크기를 보여주며, 두 번째 열에는 지문(fingerprint)이 다음 열에는 파일명, type은 괄호로 둘러쌓여 보입니다. 위의 예제에서는 4096 비트의 RSA key입니다.
