출처 : [https://stackoverflow.com/questions/32456881/getting-values-from-functions-that-run-as-asyncio-tasks](https://stackoverflow.com/questions/32456881/getting-values-from-functions-that-run-as-asyncio-tasks)

# asyncio 작업(task)으로 실행한 함수에서 값 얻어오기

저는 다음 코드를 작성하였습니다.

```python
import asyncio

@asyncio.coroutine
def func_normal():
        print("A")
        yield from asyncio.sleep(5)
        print("B")
        return 'saad'

@asyncio.coroutine
def func_infinite():
    i = 0
    while i<10:
        print("--"+str(i))
        i = i+1
    return('saad2')

loop = asyncio.get_event_loop()

tasks = [
    asyncio.async(func_normal()),
    asyncio.async(func_infinite())]

loop.run_until_complete(asyncio.wait(tasks))
loop.close()
```

저는 이 함수의 변수에서 값들을 어떻게 얻는지 모르겠습니다. 다음처럼 했는데 안 됩니다.

```python
asyncio.async(a = func_infinite())
```

위처럼 keyword 인자로 값을 넘겼습니다. 함수 리턴값을 얻으려면 어떻게 해야 할까요?

---

## 3개의 답변 중 1개

코루틴은 기존처럼 작동합니다. `loop.run_until_complete()`로부터 리턴 값을 바로 사용하시면 되고 [여러개의 결과를 모으기 위해 `asyncio.gather()`를 호출](https://docs.python.org/ko/3/library/asyncio-task.html#asyncio.gather) 합니다.

```python
#!/usr/bin/env python3
import asyncio

@asyncio.coroutine
def func_normal():
    print('A')
    yield from asyncio.sleep(5)
    print('B')
    return 'saad'

@asyncio.coroutine
def func_infinite():
    for i in range(10):
        print("--%d" % i)
    return 'saad2'

loop = asyncio.get_event_loop()
tasks = func_normal(), func_infinite()
a, b = loop.run_until_complete(asyncio.gather(*tasks))
print("func_normal()={a}, func_infinite()={b}".format(**vars()))
loop.close()
```

### 출력

```
--0
--1
--2
--3
--4
--5
--6
--7
--8
--9
A
B
func_normal()=saad, func_infinite()=saad2
```
