## 어떻게 접속이 끊어졌을 때 자동으로 재접속할 수 있나요?

-   출처 : [https://websockets.readthedocs.io/en/stable/faq.html#client-side](https://websockets.readthedocs.io/en/stable/faq.html#client-side)

[issue 414](https://websockets.readthedocs.io/en/stable/faq.html#client-side)를 보세요.

---

## 재접속을 위한 공통 패턴

-   출처 : [https://github.com/aaugustin/websockets/issues/414](https://github.com/aaugustin/websockets/issues/414)

### pgrandinetti님 질문

몇 개의 제 프로젝트에서 접속 오류나 재접속 시도가 발생할 수 있는 시나리오를 다루기 위해 다음처럼 진행합니다.

```python
async def listen_forever(self):
        while True:
        # 접속을 실패할 때마다 밖의 루프는 재실행됨
            try:
                async with websockets.connect(self.url) as ws:
                    while True:
                    # listener loop
                        try:
                            reply = await asyncio.wait_for(ws.recv(), timeout=***)
                        except (asyncio.TimeoutError, websockets.exceptions.ConnectionClosed):
                            try:
                                pong = await ws.ping()
                                await asyncio.wait_for(pong, timeout=self.ping_timeout)
                                logger.debug('Ping OK, keeping connection alive...')
                                continue
                            except:
                                await asyncio.sleep(self.sleep_time)
                                break  # 안쪽 loop
                        # dreply 객체로 작업을 진행합니다.
            except socket.gaierror:
                # 뭔가 로그를 남깁니다.
                continue
            except ConnectionRefusedError:
                # 뭔가 다른 것을 로그로 남깁니다.
                continue
```

저는 (1)이것이 괜찮은지 궁금하고요. (아마 맞을거 같습니다만) (2) 반복적인 행위같은 것을 처리하기 위해 제공되는 지름길 같은 방법이 있는지도 궁금합니다.

### aaugustin님 답변

이를 구현한 웹소켓에서 지름길 같은 방법은 없습니다.

이 사용 사례에 관해 이전에 들었고 저는 유효하다고 갱각합니다.

저는 좋은 API를 제공할 수 있을 지 확실하지는 않습니다. -- 저는 사용자 코드가 접속할 때 혹은 접속이 끊어졌을 때 이 작업(재접속)을 해야될 것으로 예상되지만 어떻게 이를 처리해야할지는 확실하지 않습니다.

### heckad님 질문

이 특징을 추가하기 위해 필요한 것은 무멋이 있을까요?

### aaugustin님 추가 제안

이 특징을 위해 저는 다음을 제안합니다.

-   첫째, 이 특징이 정확히 반응하는지 묘사하는 문서가 필요합니다.
-   우리가 그것에 동의하면 테스트와 함께 구현하겠습니다.

### pgrandinetti 님 답변

@heckad 저는 최소한의 재생산 가능한 예시를 여기에 작성하였습니다.  
[https://gist.github.com/pgrandinetti/964747a9f2464e576b8c6725da12c1eb](https://gist.github.com/pgrandinetti/964747a9f2464e576b8c6725da12c1eb)
