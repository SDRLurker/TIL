출처 : [https://stackoverflow.com/questions/31166594/socket-python-recvfrom](https://stackoverflow.com/questions/31166594/socket-python-recvfrom)

# 소켓 파이썬 : recvfrom

저는 파이썬의 socket.recvfrom이 blocking 함수인지 알고 싶습니다. 그렇지 않다면 [문서](https://docs.python.org/ko/3/library/socket.html#socket.socket.recvfrom)에서 답변을 찾을 수 없었습니다. 수신 받은 게 없다면 무엇이 리턴되나요? 빈 문자열 '' 인가요? 다른 경우, 사실 blocking이라면 이를 unblocking 함수로 어떻게 변경할 수 있나요? 저는 [settimeout](https://docs.python.org/ko/3/library/socket.html#socket.socket.settimeout)에 관해 들었지만 이것이 올바른 해결책인지는 모르겠습니다.

## 1개의 답변

기본적으로 그것은 blocking입니다. `socket.setblocking(0)` 또는 (같은 의미인) `socket.settimeout(0)`을 통해 non-blocking으로 바뀔 수 있습니다. 이 경우 받은 패킷이 없다면 `socket.error` 예외가 발생합니다. 
다음 문서를 확인하세요.

[https://docs.python.org/ko/3/library/socket.html#socket.socket.setblocking](https://docs.python.org/ko/3/library/socket.html#socket.socket.setblocking)
