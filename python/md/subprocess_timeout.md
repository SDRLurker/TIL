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

## 31 개의 답변 중 2개의 답변

Python 3.3+ 에서

```python
from subprocess import STDOUT, check_output

output = check_output(cmd, stderr=STDOUT, timeout=seconds)
```

`output`은 명령어의 표준 출력, 표준 에러 데이터가 합쳐진 것을 포함하는 바이트 문자열입니다.

[`check_output`](https://docs.python.org/ko/3/library/subprocess.html#subprocess.check_output)은 `proc.communicate()` 메소드와는 다르게 문제 텍스트에 지정된 대로 0이 아닌 종료 상태에서 `CalledProcessError`를 발생시킵니다.

저는 불필요하게 자주 사용되기 때문에 `shell=True`를 제거하였습니다. cmd에서 실제로 필요한 경우 언제든지 다시 추가할 수 있습니다. `shell=True`를 추가하면 즉, 자식 프로세스가 자체 하위 항목을 생성하는 경우; check_output()은 시간 초과가 나타내는 것보다 훨씬 늦게 반환 할 수 있습니다. [Subprocess timeout 실패](https://stackoverflow.com/questions/36952245/subprocess-timeout-failure)를 참조하세요.

타임 아웃 기능은 3.2+ subprocess 모듈의 [`subprocess32`](https://pypi.org/project/subprocess32/) 백 포트를 통해 Python 2.x에서 사용할 수 있습니다.

---

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
    print "헉, 너무 오래 걸립니다!"
    # 그 밖에 무엇이든
```
