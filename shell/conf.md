출처 : [https://stackoverflow.com/questions/22683269/how-to-parse-a-config-file-conf-in-shell-script](https://stackoverflow.com/questions/22683269/how-to-parse-a-config-file-conf-in-shell-script)

# shell 스크립트에서 설정 파일 (\*.conf) 파싱하는 방법

저는 shell 스크립트 신입입니다. `app.conf` 파일이 다음처럼 있습니다.

```shell
[MySql]
user = root
password = root123
domain = localhost
database = db_name
port = 3306

[Logs]
level = logging.DEBUG

[Server]
port = 8080
```

저는 shell 스크립트에서 이 파일을 파싱하여 mysql 설정 정보를 추출하고 싶습니다. 어떻게 할 수 있을까요?

## 4개의 답변 중 1개의 답변

저는 다음처럼 하였습니다.

```shell
pw=$(awk '/^password/{print $3}' app.conf)
user=$(awk '/^user/{print $3}' app.conf)

echo $pw
root123

echo $user
root
```

`$()`는 변수 `pw`를 내부 명령의 출력으로 설정합니다. 안쪽의 명령은 app.conf 파일에서 `password` 로 시작하는 줄(line)에서 세 번째 필드를 출력합니다.

**편집됨**

설정 파일에서 많은 값을 파싱하려면 설정 파일 이름에 대한 변수를 만듭니다.

```shell
CONFIG=app.conf
pw=$(awk '/^password/{print $3}' "${CONFIG}")
user=$(awk '/^user/{print $3}' "${CONFIG}")
```

2개의 다른 포트가 있다면... 올바른 section이 나왔을 때 flag를 1로 설정하고 당신이 찾는 포트가 나왔을 때 종료(exit) 합니다.

```shell
mport=$(awk '/^\[MySQL\]/{f=1} f==1&&/^port/{print $3;exit}' "${CONFIG}")
sport=$(awk '/^\[Server\]/{f=1} f==1&&/^port/{print $3;exit}' "${CONFIG}")
```

**역자 추가내용**

저 같은 경우에는 다음처럼 파싱처리 하였습니다.

```shell
pw=$(awk -F "=" '/^\[MySQL\]/{f=1} f==1&&/^password/{print $2;exit}' "${CONFIG}")
```
