출처 : [https://www.shellscript.sh/trap.html](https://www.shellscript.sh/trap.html)

# Trap

Trap은 간단하지만 매우 유용한 유틸리티입니다. 스크립트로 현재 디렉터리에서 모든 파일의 내용을 FOO를 BAR로 바꾼 결과 파일을 만든다면, 스크립트가 종료할 때 /tmp를 정리할 수 있습니다. 하지만 도중에 중단되면 /tmp에 파일이 있을 수 있습니다.

```
#!/bin/sh

trap cleanup 1 2 3 6

cleanup()
{
  echo "Caught Signal ... cleaning up."
  rm -rf /tmp/temp_*.$$
  echo "Done cleanup ... quitting."
  exit 1
}

### main script
for i in *
do
  sed s/FOO/BAR/g $i > /tmp/temp_${i}.$$ && mv /tmp/temp_${i}.$$ $i
done
```

`trap` 구문은 시그널 1,2,3, 또는 6을 받았을 때 `cleanup()`을 실행하라고 스크립트에 말합니다. 가장 보편적인 시그널(CTRL-C)은 시그널 2(SIGINT)입니다. 이는 아주 흥미로운 목적으로도 사용될 수 있습니다.

```
#!/bin/sh

trap 'increment' 2

increment()
{
  echo "Caught SIGINT ..."
  X=`expr ${X} + 500`
  if [ "${X}" -gt "2000" ]
  then
    echo "Okay, I'll quit ..."
    exit 1
  fi
}

### main script
X=0
while :
do
  echo "X=$X"
  X=`expr ${X} + 1`
  sleep 1
done
```

위의 스크립트는 CTRL-C를 캐치하여 종료하지 않고 실행하면서 변수 값을 변경합니다. 이것이 유용성에 있어 어떤 긍정적이고 부정적인 효과를 미치는지는 독자의 연습으로 남겨 둡니다. 이 예시는 4번 인터럽트 (혹은 2000초) 이후에 종료합니다. 모든 쉘은 처리할 기회 없이 `kill -9 <PID>`에 의해 강제종료될 수 있습니다.

다음은 공통 인터럽트에 대한 표입니다.

| 번호 | 시그널 | 의미 |
| --- | --- | --- |
| 0 | 0 | 쉘에서 종료했을 때 |
| 1 | SIGHUP | 깔끔한 정리 |
| 2 | SIGINT | 인터럽트 |
| 3 | SIGQUIT | 종료 |
| 6 | SIGABRT | 중단 |
| 9 | SIGKILL | 강제종료(trap 인식 안됨) |
| 14 | SIGALRM | 알람 시계 |
| 15 | SIGTERM | 프로그램 종료 |

스크립트가 자체적으로 신호를 무시하는 환경 (예 : nohup 제어)에서 시작된 경우 스크립트는 해당 신호도 무시합니다.
