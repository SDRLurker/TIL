출처 : [https://superuser.com/questions/98974/how-to-use-crontab-to-run-a-script-as-nobody](https://superuser.com/questions/98974/how-to-use-crontab-to-run-a-script-as-nobody)

# nobody로 스크립트 실행하기 위해 crontab 사용하는 방법

이는 CentOS 머신입니다. 저는 매일 특정 시간에 (최소 권한으로 사용자로써) 사용자 nobody로 스크립트를 실행하려 합니다. 여기에 nobody가 있습니다.

```shell
[root@CentOS % ~] grep "^nobody" /etc/passwd  
nobody:x:99:99:Nobody:/:/sbin/nologin  
```

루트의 crontab에서 제가 실행하려 한 것입니다.

환경변수 SUDO_USER=nobody로 설정하고

15 17 * * * sudo -u nobody /bin/bash /usr/local/bin/bashscript.sh

15 17 * * * su -c /usr/local/bin/bashscript.sh nobody

저는 모두 가능한 루트의 crontab에 엔트리를 유지하고 싶습니다. 나는 또한 해당 설정에 의존할 수 있는 다른 어떤 것도 깨뜨리고 싶지 않기 때문에 아무도 nobody의 계정을 속이지 않는 것을 선호합니다. 나는 권한이 없는 다른 계정을 만들고 그것이 문제가 되는 경우 실제 쉘을 제공하는 것에 반대하지 않습니다.

저도 약간 당혹스러웠던 점은 인정합니다. 도움이 되지 않는다는 점을 제외하고는 이것이 일상적인 문제라고 가정합니다.

## 5개의 답변 중 1개의 답변

저는 `crontab -e` 또는 `crontab -l` 내용에서 당신이 작성했다고 가정합니다?

이것은 사용자 "루트"에 속한 crontab 파일이며 해당 파일은 (일반적으로 *개인* 작업을 예약하는 데 사용되는 파일) 명령을 실행할 사용자 지정을 지원하지 않습니다 .
시스템 전체의 crontab이고 추가 필드인 **사용자** 필드가 있는 `/etc/crontab`을 보십시오. `/etc/crontab`에 다음과 같은 줄을 추가해 보십시오.

```shell
15 17 * * * nobody /usr/local/bin/bashscript.sh
```
