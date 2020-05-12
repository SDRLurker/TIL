출처 : [https://stackoverflow.com/questions/54101923/1006-connection-closed-abnormally-error-with-python-3-7-websockets](https://stackoverflow.com/questions/54101923/1006-connection-closed-abnormally-error-with-python-3-7-websockets)

# python 3.7 websockets에서 비정상적으로 1006 접속 종료 오류

저는 python websockets에서 이러한 github 이슈와 같은 문제가 있습니다.
[https://github.com/aaugustin/websockets/issues/367](https://github.com/aaugustin/websockets/issues/367)

제안된 해결책은 저는 작동하지 않습니다. 제가 본 오류는

websockets.exceptions.ConnectionClosed: WebSocket connection is closed: code = 1006 (connection closed abnormally [internal]), no reason

이는 제 코드입니다.

```python
async def get_order_book(symbol):
    with open('test.csv', 'a+') as csvfile:
        csvw = csv.writer(csvfile, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)
        DT = Data(data=data, last_OB_id=ob_id, last_TR_id=tr_id, sz=10, csvw=csvw)

        while True:
            if not websocket.open:
                print('Reconnecting')
                websocket = await websockets.connect(ws_url)
            else:
                resp = await websocket.recv()
                update = ujson.loads(resp)
                DT.update_data(update)

async def get_order_books():
    r = requests.get(url='https://api.binance.com/api/v1/ticker/24hr')
    await asyncio.gather(*[get_order_book(data['symbol']) for data in r.json()])

if __name__ == '__main__':
    asyncio.run(get_order_books())
```


내가 테스트 한 방법은 인터넷 연결을 종료하는 것이지만 10초 후에도 여전히 1006 오류가 반환됩니다.

Python 3.7 및 Websockets 7.0을 실행하고 있습니다.

당신의 생각이 무엇인지 알려주십시오. 감사합니다!

## 2개의 답변 중 1개의 답변만 추려냄

저도 같은 문제에 직면했습니다. 잠시 동안 파고들자 다시 연결하라는 여러 버전의 답변을 찾았지만 합리적인 방법이라고 생각하지 않았으므로 더 파고 들었습니다.

DEBUG 레벨 로깅을 활성화하면 파이썬 웹소켓이 기본적으로 핑 패킷을 보내고 응답을 받지 못하면 연결 시간이 초과된다는 것을 알았습니다. 이것이 표준과 일치하는지 확실하지 않지만 적어도 파이썬 스크립트 시간이 초과되는 서버에는 적어도 자바 스크립트의 웹 소켓은 완전히 좋았습니다.

수정은 간단합니다. `연결`하기 위한 다른 kw 인수를 추가합니다.

```python
websockets.connect(uri, ping_interval=None)
```

같은 인자는 서버쪽 함수 `serve`로 작동해야 합니다.
[https://websockets.readthedocs.io/en/stable/api.html](https://websockets.readthedocs.io/en/stable/api.html)에서 정보를 더 얻을 수 있습니다.
