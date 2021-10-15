출처 : [https://stackoverflow.com/questions/603852/how-do-you-udp-multicast-in-python](https://stackoverflow.com/questions/603852/how-do-you-udp-multicast-in-python)

# Python에서 UDP 멀티캐스트 하는 방법?

어떻게 Python에서 UDP 멀티캐스트를 송신하고 수신하나요? 이를 하기 위해 표준 라이브러리가 있을까요?

---

## 9개의 답변 중 1개

이 코드는 잘 작동합니다.

수신

```python
import socket
import struct

MCAST_GRP = '224.1.1.1'
MCAST_PORT = 5007
IS_ALL_GROUPS = True

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
if IS_ALL_GROUPS:
    # 이 포트로는 모든 멀티캐스트 그룹을 수신합니다.
    sock.bind(('', MCAST_PORT))
else:
    # 이 포트로는 MCAST_GRP IP의 멀티캐스트 그룹만 listen 합니다.
    sock.bind((MCAST_GRP, MCAST_PORT))
mreq = struct.pack("4sl", socket.inet_aton(MCAST_GRP), socket.INADDR_ANY)

sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)

while True:
  # Python 3의 경우 다음 줄을 "print(sock.recv(10240))"로 변경합니다.
  print sock.recv(10240)
```

송신

```python
import socket

MCAST_GRP = '224.1.1.1'
MCAST_PORT = 5007
# regarding socket.IP_MULTICAST_TTL
# ---------------------------------
# 모든 송신된 패킷에 대해 네트워크에서 2홉을 초과하는 패킷은 재전송/broadcast되지 않을 것입니다.
# (https://www.tldp.org/HOWTO/Multicast-HOWTO-6.html 를 참조하세요.)
MULTICAST_TTL = 2

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, MULTICAST_TTL)

# Python 3의 경우 다음 줄을 'sock.sendto(b"robot", ...'로 변경합니다.
# msg 파라미터는 "바이트 류 객체가 필요합니다" (https://stackoverflow.com/a/42612820)
sock.sendto("robot", (MCAST_GRP, MCAST_PORT))
```

작동하지 않는 [https://wiki.python.org/moin/UdpCommunication](http://wiki.python.org/moin/UdpCommunication)의 예제를 기반으로합니다.

내 시스템은 ... Linux 2.6.31-15-generic # 50-Ubuntu SMP Tue Nov 10 14:54:29 UTC 2009 i686 GNU / Linux Python 2.6.4 입니다.
