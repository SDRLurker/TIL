출처 : [https://stackoverflow.com/questions/2719017/how-to-set-timeout-on-pythons-socket-recv-method](https://stackoverflow.com/questions/2719017/how-to-set-timeout-on-pythons-socket-recv-method)

# 파이썬 소켓 수신 메소드에서 timeout을 정하는 방법?

저는 파이썬의 소켓 수신 메소드에서 timeout을 정하고 싶습니다. 어떻게 할 수 있을까요?

## 11개의 답변 중 1개의 답변만 추려냄

일반적인 접근은 timeout이 발생할 때까지 데이터가 접근 가능할 때까지 [select()](https://docs.python.org/3/library/select.html#select.select)를 사용하는 것입니다. 데이터가 실제로 가능할 때만 `recv()`를 호출합니다. 안전을 위해, 우리는 `recv`가 무한으로 block하지 않는 것을 보장하기 위해 non-blocking 모드로 소켓을 설정할 수 있습니다. `select()`는 한번에 하나 이상의 소켓을 기다리도록 하는데 사용될 수 있습니다.

```python
import select

mysocket.setblocking(0)

ready = select.select([mysocket], [], [], timeout_in_seconds)
if ready[0]:
    data = mysocket.recv(4096)
```

만약 많은 열려있는 파일 디스크립터가 있다면, [poll()](https://docs.python.org/3/library/select.html#select.poll)이 `select()`보다 더 효율적인 대안입니다.

다른 옵션은 `socket.settimeout()`을 사용하여 소켓의 모든 연산에 timeout을 설정하는 것입니다. 하지만 당신은 다른 답변에서 해당 솔루션을 명시적으로 거부했던 것을 확인하였습니다.
