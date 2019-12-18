출처 : [https://serverfault.com/questions/84430/whats-wrong-with-my-cronjob-syntax-im-trying-to-use-a-backtick](https://serverfault.com/questions/84430/whats-wrong-with-my-cronjob-syntax-im-trying-to-use-a-backtick)

# cronjob 문법에서 backtick(\`)을 사용하려는데 무엇이 잘못되었습니까?

제가 자동화 하고 싶은 것이 여기 있습니다.

```shell
00 08 * * * psql -Uuser database < query.sql | mail somone@null.com -s "query for `date +%Y-%m-%dZ%I:%M`"
```

하지만 다음처럼 오류 메세지가 나옵니다.

```shell
/bin/sh: -c: line 0: unexpected EOF while looking for matching ``'
/bin/sh: -c: line 1: syntax error: unexpected end of file
```

### 4개의 답변 중 1개의 답변만 추려냄

[cronjob(5)](https://linux.die.net/man/5/crontab)에서

> "여섯"번째 필드 (라인의 마지막)은 실행할 명령어입니다. 그 행의 전체 명령 부분 개행문자 혹은 %까지 /bin/sh에 의해 실행되거나 crontab 파일의 SHELL 변수에 정의된 shell에 의해 실행됩니다. 명령어에서 퍼센트 표시(%)가 backslash()에 의해 escape되지 않으면 이는 개행문자로 변경되고 첫번째 % 이후 모든 데이터는 표준 입력의 명령어로 보내집니다. 하나의 명령 행을 여러 행으로 나누지 않으려면 shell ""를 붙여야 합니다.

% 표시 앞에 backslash를 추가하면 됩니다.

```
00 08 * * * psql -Uuser database < query.sql | mail somone@null.com -s "query for `date +\%Y-\%m-\%dZ\%I:\%M`"
```
