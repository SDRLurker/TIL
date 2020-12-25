출처 : [https://stackoverflow.com/questions/19447603/how-to-kill-a-python-child-process-created-with-subprocess-check-output-when-t](https://stackoverflow.com/questions/19447603/how-to-kill-a-python-child-process-created-with-subprocess-check-output-when-t)

# 부모 프로세스가 죽을 때 subprocess.check_output()로 생성된 Python 자식 프로세스를 죽이는 방법?

저는 subprocess를 시작하고 표준 출력을 확인하는 간단한 python script를 작성하려고 합니다.

여기에 코드의 스니펫이 있습니다.

```python
subprocess.check_output(["ls", "-l"], stderr=subprocess.STDOUT)
```

문제는 부모 프로세스가 죽었을 때 조차 자식 프로세스가 실행중이라는 것입니다. 부모 프로세스가 죽었을 때 자식 프로세스도 함께 죽일 수 있는 방법이 있습니까?

------

## 4개의 답변 중 1 개의 답변만 추려냄.

당신의 문제는 `subprocess.check_output`을 사용하는 것입니다. - 당신은 맞게 작성 했습니다만 check_ouput 인터페이스로는 자식 프로세스의 PID를 얻을 수 없습니다. 대신에 Popen을 사용하세요.

```python
proc = subprocess.Popen(["ls", "-l"], stdout=PIPE, stderr=PIPE)

# 여기에서 자식 프로세스의 PID를 얻을 수 있습니다.
global child_pid
child_pid = proc.pid

# 여기서 자식 프로세스가 완료될 때까지 기다릴 수 있습니다.
(output, error) = proc.communicate()

if error:
    print "error:", error

print "output:", output
```

종료할 때 자식 프로세스를 죽이는 것을 분명하게 만드세요.

```python
import os
import signal
def kill_child():
    if child_pid is None:
        pass
    else:
        os.kill(child_pid, signal.SIGTERM)

import atexit
atexit.register(kill_child)
```
