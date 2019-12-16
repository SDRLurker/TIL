출처 : https://www.shellscript.sh/tips/getopts/

# Shell Script 튜토리얼 - 팁 Getopts

## 명령줄 인자 파싱하기

대부분의 Unix, Linux 명령은 "마이너스" 심볼을 앞에 붙여 옵션을 사용합니다. 예를 들어 파일 목록을 긴 포멧으로, (역순으로) 시간순으로 정렬할 때 당신은 `ls -l -r -t`를 사용하거나 `ls -lrt`를 사용할 수 있습니다..
몇가지 명령은 인자를 가지는데, 당신은 `"myfiles"` 디렉터리를 `"mytarfile..tar"`로 압축할 수 있습니다. `tar -c`의 경우, 압축 하기 위해 파일 목록을 취하는 옵션이 오게 됩니다.

## The tl;dr lowdown
여기에 "너무 길어 읽지 않았다"처럼 빠른 개요가 있습니다. 다음처럼 `getopts`를 사용할 수 있습니다.

```shell
while getopts 'srd:f:' c
do
  case $c in
    s) ACTION=SAVE ;;
    r) ACTION=RESTORE ;;
    d) DB_DUMP=$OPTARG ;;
    f) TARBALL=$OPTARG ;;
  esac
done
```

그렇습니다. 바쁜 사람들은 위 예시에 만족할 것이며 `getopts`가 무엇인지 어떻게 작동하는지와 당신의 스크립트에서 어떻게 유용하게 사용할 수 있는지 알아보겠습니다.

## getopts 소개
당신을 위해 이 옵션들을 파싱하는 편리한 유틸리티를 `getops`라 부릅니다. 그 사용법은 조금 이상하게 느껴지지만, 이 기술은 당신의 스크립트가 옵션을 표준화되고 친근하게 느끼는 방법으로 처리하도록 합니다..

`getopts`를 통해 당신이 전달한 첫번째 인자는 그것이 받아들일 어떤 문자(혹은 숫자, 또는 다른 한개의 글자))의 목록입니다.  각 글자 뒤에 colon(:)는 `tar -f mytarfile` 처럼 인자가 따라온다는 의미입니다. `tar -f`는 tar 파일의 이름이 항상 따라와야 합니다. 이 옵션 인자는 `$OPTARG` 변수에서 당신의 스크립트로 보내집니다.

`getopts`는 `$OPTIND` 변수도 세팅하는데 [나중](https://www.shellscript.sh/tips/getopts/#optind)에 다룰 것입니다.

당신이 `getopts`로 전달하는 두번째 인자는 현재 switch의 문자가 위치하게 될 변수의 이름입니다. 주로 이는 `opt` 또는 `c`로 불리며, 아무 이름으로 올 수 있습니다.

이 예시 스크립트는 tarball로 파일을 저장하고 복구할 수 있습니다. 당신은 그것에 -s(저장) 또는 -r(복구)로 그것에 전달해야 합니다. 만약 당신이 `-d databasefile`을 전달하면 데이터베이스를 덤프(또는 복구)하는 이름으로 사용될 것입니다. 만약 `-f tarball`을 전달하면 파일을 생성(또는 해제하는) tarball의 이름으로 사용될 것입니다.

이 첫 번째 스크립트 초안에서 다루지 않는 몇 가지 사항이 있습니다. `-s`와 `-r`을 모두 전달하는 것은 유효하지 않습니다. 그렇게 하면 이 스크립트는 마지막으로 전달한 것을 취하므로 `dbdump.sh -s -r -d` 또는 `dbdump.bin -s -r -s`는 마지막으로 처리 한 것이 저장 명령이므로 (복원하지 않고) 저장합니다.

마찬가지로 `-d` 및 `-f` 중 하나 이상을 전달하지 않으면 아무 일도 일어나지 않습니다.

`DB_DUMP TARBALL ACTION` 설정이 안되는 이유는 스크립트가 이미 설정된 환경 변수의 영향을 받지 않기 때문입니다. 이는 실행중인 스크립트의 범위에만 영향을 미칩니다. 호출 쉘은 변수가 변경되지 않습니다.

간결하게하기 위해 여기서 `save_database()`, `save_files()`, `restore_database()` 및 `restore_files()` 함수를 정의하지 않았습니다. 다운로드 가능한 스크립트에는 더미 기능이 있어 스크립트가 실제로 실행됩니다. 수행할 작업만 표시하지만 실제로는 파일에 아무런 작업을 수행하지 않습니다.

[이 스크립트 다운로드(getops1.sh)](https://www.shellscript.sh/tips/getopts/getopts1.sh.txt)
```shell
#!/bin/bash

unset DB_DUMP TARBALL ACTION

while getopts 'srd:f:' c
do
  case $c in
    s) ACTION=SAVE ;;
    r) ACTION=RESTORE ;;
    d) DB_DUMP=$OPTARG ;;
    f) TARBALL=$OPTARG ;;
  esac
done

if [ -n "$DB_DUMP" ]; then
  case $ACTION in
    SAVE)    save_database $DB_DUMP    ;;
    RESTORE) restore_database $DB_DUMP ;;
  esac
fi

if [ -n "$TARBALL" ]; then
  case $ACTION in
    SAVE)    save_files $TARBALL    ;;
    RESTORE) restore_files $TARBALL ;;
  esac
fi
```

`getopts` 명령은 `while` 루프에 대한 인수입니다. 루프를 통해 매번 switch를 처리하고 `$c` 변수를 switch의 문자로 설정합니다. 기본 튜토리얼에서 [루프](https://www.shellscript.sh/loops.html) 및 [case](https://www.shellscript.sh/case.html)에 대해 자세히 읽을 수 있습니다.

이 스크립트를 `dbdump.sh -s -r -d /tmp/dbdump.bin -f /tmp/files.tar -s`로 호출하면 `-s`를 처리하고 `$c=s`를 설정하고 처음으로 `case`` 구문을 실행합니다. `$c=s`가 `$ACTION=SAVE` 를 설정하고 그 줄의 끝에는 처리를 중지하라고 알려주고 while 루프 주위의 다음 실행을 위해 getopts로 돌아갑니다. 논리적으로 의미가 없는 -r을 읽지만 (백업을 저장하고 동시에 백업을 복원 할 수는 없지만) 스크립트는 이를 알지 못하므로 `$c=r`을 설정합니다. case 문은 `$ACTION = RESTORE`를 설정하고 다음 인수를 처리하기 위해 `getopts`로 돌아갑니다.

이제 getopts는 `$c=d`를 설정하고 `$OPTARG = /tmp/dbdump.bin` 도 설정합니다. getopts 호출에서 'd:는 -d 다음에 인수 (데이터베이스 덤프 파일의 이름)가 옵니다. 실행은 case 문으로 진행하여 `$DBDUMP=/tmp/dbdump.bin`을 설정합니다. 스크립트의 본문에 들어갈 때 $ DBDUMP 변수에 값이 있으면 데이터베이스를 해당 파일에 저장하거나 해당 파일에서 복원합니다.

다음 옵션은 `-f /tmp/files.tar` 이며 동일한 프로세스가 수행됩니다. getopts는 `$c=f` 를 설정하고 `$OPTARG=/tmp/files.tar`도 설정합니다. `case` 문은 이것을 읽고 `$TARBALL=/tmp/files.tar`를 설정합니다.

마지막으로 또 다른 `-s` 스위치를 전달하여 `$ACTION` 변수를 다시 `SAVE`로 변경합니다.

기본 스크립트가 시작되면 `$DB_DUMP`가 설정되어 있는지 확인한 다음 `$ACTION` 값을 확인하고 `$ACTION` 값에 따라 `$DB_DUMP`를 사용하여 데이터베이스를 저장하거나 복원합니다.

마찬가지로 `$TARBALL`이 설정되어 있는지 확인하고 `$TARBALL`을 인수로 사용하여 파일을 저장하거나 복원합니다.
















