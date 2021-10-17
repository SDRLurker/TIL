출처 : [https://serverfault.com/questions/914476/cant-access-mariadb-from-google-cloud-compute-instance](https://serverfault.com/questions/914476/cant-access-mariadb-from-google-cloud-compute-instance)

# 구글 클라우드 / Compute 인스턴스로부터 MariaDB에 접근할 수 없습니다.

저는 구글 클라우드 / Compute 인스턴스에서 실행하고 있는 Mysql에 원격 접근을 할 수 없는 거 같습니다. 저는 기본 설치를 시도하였습니다.

1. 저는 인스턴스를 기본값인 Debian을 사용하여 만들었습니다.

2. mysql을 apt-get install로 설치했고 설명하기 어렵지만 대신 MariaDB를 설치했습니다. 잘됩니다.

3. 저는 sudo mysql mysql_secure_installation을 prompt에 따라서 를 실행하였습니다. 설명하기 어렵지만 sudo를 사용하여 local 접근만 허용하기 위해 "root"를 위한 mysql.users 행을 "unix_socket"의 플러그인 열 값으로 나두었습니다.  잘 되었고 새로운 사용자를 추가하려고 합니다.

4. 저는 local에서 로그인하고 다음을 실행하였습니다.

```SQL
create user 'myname'@'%' identified by 'mypass';
grant all privileges on * . * to 'mypass'@'%';
flush privileges;
```

저는 localhost에서 'myname'을 사용하여 접속할 수 있었습니다.

5. 저는 구글 클라우드 방화벽에서 8088 포트와 똑같은 규칙으로 만들었고 그 포트가 원격에서 잘 접속을 할 수 있었기 때문에 0.0.0.0/0으로 3306 포트를 열었습니다. 

6. 저는 원격에서 mysql을 접속했을 때 다음과 같은 내용을 얻었습니다.

이는 네트워크 문제일 수도 mysql 문제일 수도 있을 거 같은데 어느쪽인지 저는 모르겠습니다. 좋은 아이디어 있으신가요?

------

## 1개의 답변(자문자답)

알아내었습니다. /etc/mysql/maria.conf.d/50-server.cnf 설정 파일 깊은 곳에 뭍여 있었습니다. 그 파일에 놀라운 부분이 있었습니다.

```
# Instead of skip-networking the default is now to listen only on
# localhost which is more compatible and is not less secure.
bind-address        = 127.0.0.1
```

bind-address 부분을 주석처리 하니 잘 작동하였습니다.

*이것*은 MySql을 요청할 때 Debian으로 MariaDB를 설치하는 것은 예외적으로 나쁜 결정의 결과입니다. mysql과 mariadb라는 이름으로 복잡한 설정 파일들이 있는데 아무도 그 파일들이 하고자 하는 일을 하지 않습니다.
