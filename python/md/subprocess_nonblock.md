출처 : [https://stackoverflow.com/questions/36476841/python-how-to-read-stdout-of-subprocess-in-a-nonblocking-way/36477512](https://stackoverflow.com/questions/36476841/python-how-to-read-stdout-of-subprocess-in-a-nonblocking-way/36477512)

# nonblocking 방법으로 subprocess의 출력을 읽는 방법

저는 subprocess를 시작하고 표준 출력을 확인하는 간단한 python script를 작성하려고 합니다. 여기에 코드의 스니펫이 있습니다.

```python
process = subprocess.Popen([path_to_exe, os.path.join(temp_dir,temp_file)], stdout=subprocess.PIPE)
while True:   
    output=process.stdout.readline()
    print "test"
```

문제는 script가 `output=process.stdout.readline()`에서 대기하고 subprocess가 끝난 후에만 `print "test"`를 실행하는 것입니다.

subprocess가 종료되는 것을 기다리지 않고 표준 출력을 읽어 그것을 출력하는 방법이 있을까요?

제가 시작한 subprocess는 제가 소스 코드를 가지고 있지 않은 윈도우즈 바이너리입니다.

비슷한 질문을 몇 개 찾았지만 그 답변은 리눅스에서만 적용할 수 있거나 제가 시작한 subprocess의 소스를 가지고 있을 경우에만 적용할 수 있었습니다.

---

2개의 답변 중 1 개의 답변

[select](https://docs.python.org/2/library/select.html) 모듈을 확인하세요.

```python
import subprocess
import select
import time

x=subprocess.Popen(['/bin/bash','-c',"while true; do sleep 5; echo yes; done"],stdout=subprocess.PIPE)

y=select.poll()
y.register(x.stdout,select.POLLIN)

while True:
  if y.poll(1):
     print x.stdout.readline()
  else:
     print "nothing here"
     time.sleep(1)
```

---

# 편집:

POSIX가 아닌 시스템을 위한 쓰레드 처리된 해결책입니다.

```python
import subprocess
from threading import Thread 
import time

linebuffer=[]
x=subprocess.Popen(['/bin/bash','-c',"while true; do sleep 5; echo yes; done"],stdout=subprocess.PIPE)

def reader(f,buffer):
   while True:
     line=f.readline()
     if line:
        buffer.append(line)
     else:
        break

t=Thread(target=reader,args=(x.stdout,linebuffer))
t.daemon=True
t.start()

while True:
  if linebuffer:
     print linebuffer.pop(0)
  else:
     print "nothing here"
     time.sleep(1)
```
