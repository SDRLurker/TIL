출처  
[https://stackoverflow.com/questions/59371973/quickfix-python-plugin-use-case-for-testing-segmentation-fault-error](https://stackoverflow.com/questions/59371973/quickfix-python-plugin-use-case-for-testing-segmentation-fault-error)

## QuickFix 파이썬 플러그인 - 테스트 케이스 용도 - segmentation fault 오류

우리는 Fix 서버로부터 메세지를 주고 받기 위해 QuickFix를 사용하는 곳에서 테스트 자동화 프레임워크 (PyTest+Quickfix)를 만들었습니다. 우리가 (임의의 양으로) 많은 양의 데이터를 보낼 때 우리는 Segmentation fault 오류가 발생하였습니다. 일부의 작은 양의 테스트 케이스는 잘 작동하였습니다. QuickFix의 내부 상태가 잘못된 거라 생각합니다. (유지하는 실제 세션에 대해 알고 있음)

다음은 코드의 일부분입니다.

```python
class MyFixApp(quickfix.Application):
    class State(Enum):
        NONE = 0
        LOGGED_IN = 1
        LOGGED_OUT = 2

    def __init__(self, config_file, timeout_seconds=1):
        super().__init__()
        self.out_messages_queue = queue.Queue()  # blocking queue of quickfix outbound messages
        self.in_messages_queue = queue.Queue()  # blocking queue of quickfix inbound messages
        self.all_messages_list = list()  # list of all quickfix messages
        self.parser = simplefix.FixParser()
        self.settings = quickfix.SessionSettings(config_file)
        self.storeFactory = quickfix.FileStoreFactory(self.settings)
        self.logFactory = quickfix.FileLogFactory(self.settings)
        self.timeout_seconds = timeout_seconds
        self.sessionID = None  # This is just a stateless collection of sender comp, target comp, etc...
        self.state = FixApp.State.NONE
        # initiate the socket last, because this may call into the
        # callbacks below and we need all the members constructed
        self.initiator = quickfix.SocketInitiator(self, self.storeFactory, self.settings, self.logFactory)

    def wait_for_next_in(self, timeout=1):
        return self.in_messages_queue.get(block=True, timeout=timeout)

    def wait_for_next_in_matching(self, subset, skipped=None, timeout=None):
        if timeout is None:
            timeout = self.timeout_seconds
        msg = self.wait_for_next_in(timeout=timeout)
        matches = FixApp.message_contains(msg, subset)
        if matches:
            return msg
        assert skipped is not None, f"expected={subset} msg={msg}"
        skipped.append(msg)
        return self.wait_for_next_in_matching(subset=subset, skipped=skipped, timeout=timeout)

    def login(self, timeout=30):
        print(f"Logging in {threading.get_ident()}")
        self.initiator.start()
        FixApp.wait_until(lambda: self.state is FixApp.State.LOGGED_IN, timeout)
        time.sleep(self.timeout_seconds)
        print(f"Logged in")

    # 30 seconds is needed for removing the session from QuickFix
    def logout(self, timeout=30):
        if not self.sessionID:
            print(f"Logging out without session")
            return
        print(f"Logging out from {self.sessionID} {threading.get_ident()}")
        self.initiator.getSession(self.sessionID).disconnect()
        FixApp.wait_until(lambda: self.state is FixApp.State.LOGGED_OUT, timeout)
        time.sleep(self.timeout_seconds)  # somehow, this needs more time. Remove this, and the following stop fails
        print(f"Logged out")
        self.initiator.stop()
        FixApp.wait_until(lambda: self.initiator.isStopped(), timeout)
        print(f"Initiator stopped")

    @staticmethod
    def wait_until(condition, timeout_sec):
        start = time.time()
        while not condition() and (time.time() - start) < timeout_sec:
            time.sleep(0.1)
        assert condition(), condition()
```

Stack trace:

```
#0  0x00007ffff7665e5b in raise () from /lib64/libpthread.so.0
#1  <signal handler called>
#2  0x00007fffee803d14 in FIX::Initiator::connect() () at C++/Initiator.cpp:139
#3  0x00007fffee7fce74 in FIX::SocketInitiator::onStart (this=0x555556a3c5d0) at C++/SocketInitiator.cpp:89
#4  0x00007fffee801cee in FIX::Initiator::startThread (p=<optimized out>) at C++/Initiator.cpp:292
#5  0x00007ffff765b594 in start_thread () from /lib64/libpthread.so.0
#6  0x00007ffff6bf400f in clone () from /lib64/libc.so.6
```

파이썬에서는 이 라인이 실패합니다. (줄 번호가 첨부된 코드와 일치하지 않습니다.)

```
Thread 0x00007ffff7fde540 (most recent call first):
  line 187 in wait_until
  line 153 in login
```

다음은 일치하는 라인입니다.

```python
FixApp.wait_until(lambda: self.state is FixApp.State.LOGGED_IN, timeout)
```

---

### 2개의 답변 중 1개의 답변

해결 방법을 수행했습니다. 세션을 중지(stop)하는 부분을 변경했습니다. self.initiator.stop(True) + 결국 모든 세션 및 로그 파일을 제거했습니다. 이 중 하나만 하면 작동하지 않습니다.
