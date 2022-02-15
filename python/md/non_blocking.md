출처  
[https://stackoverflow.com/questions/375427/non-blocking-read-on-a-subprocess-pipe-in-python](https://stackoverflow.com/questions/375427/non-blocking-read-on-a-subprocess-pipe-in-python)

## python에서 subprocess.PIPE로 non-blocking 읽기

저는 subprocess를 시작하기 위해 을 출력 스트림(표준출력)으로 접속하기 위해 [subprocess 모듈](https://stackoverflow.com/questions/375427/non-blocking-read-on-a-subprocess-pipe-in-python)을 사용하고 있습니다. 저는 그 출력을 non-blocking으로 읽도록 실행하고 싶습니다. .readline을 non-blocking으로 만들거나 `.readline`를 실행하기 전에 스트림에 데이터가 있는지 검사하는 방법이 있습니까? 저는 이를 Windows나 Linux에서 최소한의 작업으로 이식하고 싶습니다.  
여기는 현재 제가 한 방법입니다. (데이터가 없을 때 `.readline`는 blocking됩니다.)

```python
p = subprocess.Popen('myprogram.exe', stdout = subprocess.PIPE)
output_str = p.stdout.readline()
```

---

### 30개의 답변 중 1개의 답변

[`fcntl`](https://stackoverflow.com/questions/375427/a-non-blocking-read-on-a-subprocess-pipe-in-python/4025909#4025909), [`select`](https://stackoverflow.com/questions/375427/a-non-blocking-read-on-a-subprocess-pipe-in-python/375511#375511), [`asyncproc`](https://stackoverflow.com/questions/375427/a-non-blocking-read-on-a-subprocess-pipe-in-python/437888#437888)는 이 경우 도움이 되지 않을 것입니다.  
운영체제에 관계없이 blocking 없이 스트림을 읽는 신뢰성 있는 방법은 [`Queue.get_nowait()`](https://docs.python.org/ko/3/library/queue.html#queue.Queue.get_nowait)를 사용하는 것입니다.

```python
import sys
from subprocess import PIPE, Popen
from threading  import Thread

try:
    from queue import Queue, Empty
except ImportError:
    from Queue import Queue, Empty  # python 2.x

ON_POSIX = 'posix' in sys.builtin_module_names

def enqueue_output(out, queue):
    for line in iter(out.readline, b''):
        queue.put(line)
    out.close()

p = Popen(['myprogram.exe'], stdout=PIPE, bufsize=1, close_fds=ON_POSIX)
q = Queue()
t = Thread(target=enqueue_output, args=(p.stdout, q))
t.daemon = True # 쓰레드가 프로그램과 함께 죽습니다.
t.start()

# ... 여기서 다른 것을 합니다

# blocking 없이 한 줄을 읽습니다.
try:  line = q.get_nowait() # or q.get(timeout=.1)
except Empty:
    print('no output yet')
else: # 한 줄을 얻었습니다.
    # ... 그 한 줄로 뭔가를 합니다.
```
