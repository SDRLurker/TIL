출처 : [http://www.linuxproblem.org/art_9.html](http://www.linuxproblem.org/art_9.html)

# 비밀번호 없이 SSH 로그인

## 목표

당신이 리눅스를 사용하고 당신의 작업을 자동화하기 위해 OpenSSH를 사용하기 원합니다. 그래서 당신은 host A / 사용자 a에서 호스트 B / 사용자 b로 **자동** 로그인이 필요합니다. 당신은 쉘 스크립트(shell script)로 ssh를 호출하고 싶기 때문에 비밀번호를 입력하길 원하지 않을것입니다.

## 방법

우선 호스트 A에서 a 사용자로 로그인하여 인증 키 쌍을 생성합니다. passphrase는 입력하지 않습니다.

```shell
a@A:~> ssh-keygen -t rsa
Generating public/private rsa key pair.
Enter file in which to save the key (/home/a/.ssh/id_rsa): 
Created directory '/home/a/.ssh'.
Enter passphrase (empty for no passphrase): 
Enter same passphrase again: 
Your identification has been saved in /home/a/.ssh/id_rsa.
Your public key has been saved in /home/a/.ssh/id_rsa.pub.
The key fingerprint is:
3e:4f:05:79:3a:9f:96:7c:3b:ad:e9:58:37:bc:37:e4 a@A
```

이제 호스트 B에서 b 사용자로 ssh 프로그램을 사용하여 `~/.ssh` 디렉터리를 만듭니다. (디렉터리가 이미 있을지 모르지만 괜찮습니다.)

```ssh
a@A:~> ssh b@B mkdir -p .ssh
b@B's password: 
```

마지막으로 a 사용자의 새로운 공개키를 `b@B:.ssh/authorized_keys`에 추가하고 마지막으로 b 사용자의 비밀번호를 입력합니다.

```shell
a@A:~> cat .ssh/id_rsa.pub | ssh b@B 'cat >> .ssh/authorized_keys'
b@B's password: 
```


이제부터 비밀번호 없이 호스트 B의 b 사용자로 로그인 할 수 있습니다.

```shell
a@A:~> ssh b@B
```


독자중 한명이 남긴 메모: SSH 버전에 따라 당신은 다음 작업을 해야할 수 있습니다. 

* `.ssh/authorized_keys2`에 public key를 넣습니다.
* `.ssh` 디렉터리 권한을 `700`으로 변경(chmod)합니다.
* `.ssh/authorized_keys`의 권한을 `640`으로 변경합니다.

---

다음 출처 : [https://opentutorials.org/module/432/3742](https://opentutorials.org/module/432/3742)

역자 추가내용 : 호스트 B의 b 사용자는 .ssh 디렉터리에 다음 권한처럼 설정이 필요합니다.

```shell
chmod 700 ~/.ssh
chmod 600 ~/.ssh/id_rsa
chmod 644 ~/.ssh/id_rsa.pub  
chmod 644 ~/.ssh/authorized_keys
chmod 644 ~/.ssh/known_hosts
```


출처: https://sdr1982.tistory.com/204
