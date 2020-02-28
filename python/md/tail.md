출처 :[https://stackoverflow.com/questions/12523044/how-can-i-tail-a-log-file-in-python](https://stackoverflow.com/questions/12523044/how-can-i-tail-a-log-file-in-python) 

# Python에서 로그 파일 tail 하는 방법?

저는 blocking이나 locking 없이 Python에서 tail -F 또는 뭔가 비슷한 방법으로 출력을 하고 싶습니다. 저는 [여기](http://code.activestate.com/recipes/436477-filetailpy/)에서 이를 할 수 있는 오래된 코드를 찾았지만 같은 것을 할 수 있는 라이브러리나 더 좋은 방법이 있을거라 생각합니다. 

저는 더 많은 데이터를 원할때마다 호출할 수 있는 `tail.getNewData()`처럼 뭔가 있으면 합니다.

## 12개의 답변 중 1개의 답변만 추려냄

### Non Blocking

리눅스에 있다면 (윈도우가 파일에서 select 호출을 지원하지 않기 때문에) select 모듈과 함께 서브 프로세스 모듈을 사용할 수 있습니다.

```python
import time 
import subprocess 
import select 

f = subprocess.Popen(['tail','-F',filename],\ 
        stdout=subprocess.PIPE,stderr=subprocess.PIPE) 
p = select.poll() 
p.register(f.stdout) 
while True: 
    if p.poll(1): 
        print f.stdout.readline() 
    time.sleep(1)
```

이는 새로운 데이터를 위한 출력 pipe를 poll하고 가능할 때 print 합니다. 일반적으로 `time.sleep(1)과 print `f.stdout.readline()` 은 유용한 코드로 대체될 수 있습니다.

### Blocking

추가적은 select 모듈 호출 없이 subprocess 모듈을 사용할 수 있습니다.

```python
import subprocess
f = subprocess.Popen(['tail','-F',filename],\
        stdout=subprocess.PIPE,stderr=subprocess.PIPE)
while True:
    line = f.stdout.readline()
    print line
```

이는 추가될 때 새로운 line이 출력될 것이지만, tail program이 `f.kill`에 의해 닫힐 때까지 blocking 될 것입니다.
