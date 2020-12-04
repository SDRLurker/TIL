출처 : [http://stackoverflow.com/questions/1191374/using-module-subprocess-with-timeout](http://stackoverflow.com/questions/1191374/using-module-subprocess-with-timeout)

# timeout과 함께 'subprocess' 모듈 사용하기

여기에 stdout 데이터를 리턴하는 임의의 명령어를 실행하거나 0이 아닌 종료 코드에서 예외를 발생시키는 파이썬 코드가 있습니다.

```python
proc = subprocess.Popen(
    cmd,
    stderr=subprocess.STDOUT,  # Merge stdout and stderr
    stdout=subprocess.PIPE,
    shell=True)
```

`communicate`는 프로세스가 종료하기를 기다리는 데 사용합니다.
```python
stdoutdata, stderrdata = proc.communicate()
```
`subprocess` 모듈은 몇 초 이상 실행하고 있는 프로세스를 없애는(kill) timtout 능력을 지원하지 않습니다. 그래서 `communicate`는 영원히 실행될 수 있습니다.
윈도우즈나 리눅스에서 실행하는 데 파이썬 프로그램에서 timeout을 구현할 수 있는 **간단한** 방법이 있을까요?

----

## 20 개의 답변 중 1개의 답변만 추려냄.

유닉스를 사용하신다면,

```python
import signal
  ...
class Alarm(Exception):
    pass

def alarm_handler(signum, frame):
    raise Alarm

signal.signal(signal.SIGALRM, alarm_handler)
signal.alarm(5*60)  # 5분
try:
    stdoutdata, stderrdata = proc.communicate()
    signal.alarm(0)  # 알람을 리셋한다.
except Alarm:
    print "Oops, taking too long!"
    # whatever else
```
