## 출처

[https://stackoverflow.com/questions/47415796/multicast-from-tcp-replay-seen-by-wireshark-but-not-by-application](https://stackoverflow.com/questions/47415796/multicast-from-tcp-replay-seen-by-wireshark-but-not-by-application)

---

# [](https://github.com/SDRLurker/TIL/new/master/python#python-%EC%93%B0%EB%A0%88%EB%93%9C-%ED%92%80)Wireshark에서 tcpreplay의 멀티캐스트가 보이지만 응용프로그램에서는 안 보입니다.

저는 tcpreplay로 진행할 멀티캐스트 패킷 캡쳐를 얻었습니다.

```
sysctl net.ipv4.conf.all.rp_filter=0
sysctl net.ipv4.conf.eth0.rp_filter=0
tcpreplay -i eth0 --loop=100 new.pcap
```

Wireshark로 eth0의 트래픽을 관찰하고 제가 관심있는 패킷(224.0.23.60:4937입니다.) 을 볼 수 있었습니다.

하지만 다음 Python 응용프로그램은 패킷을 찾을 수 없습니다.

```python
import socket
import struct

MCAST_GRP = '224.0.23.60'
MCAST_PORT = 4937

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind((MCAST_GRP, MCAST_PORT))  # use MCAST_GRP instead of '' to listen only
                         # to MCAST_GRP, not all groups on MCAST_PORT
mreq = struct.pack("4sl", socket.inet_aton(MCAST_GRP), socket.INADDR_ANY)

sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)

while True:
   print '#'
   print sock.recv(64)
```

netstat -g를 실행하면 다음 출력이 나옵니다.

```
lo              1      all-systems.mcast.net
eth0            1      224.0.23.60
```

여기에 뭔가 놓친 것이 있습니까?

\[편집\] 정확히 내 패킷 캡처에서 IP src가 동일한 네트워크 하위 도메인 (ip src : 192.168.1.10)에 없는 반면 내 ip는 146.186.197.164와 비슷합니다.

## 1개의 답변만 발췌

이 문서([http://tcpreplay.synfin.net/wiki/FAQ](http://tcpreplay.synfin.net/wiki/FAQ))를 주의깊게 읽어 보시면, tcpreplay는 TCP/IP stack과 이더넷 장치 드라이버 사이에 패킷을 보내므로, 호스트 시스템의 TCP/IP 스택에서는 패킷을 볼 수 없습니다.

저는 VirtualBox로 '호스트 전용 어댑터'로 설정된 Debian OS를 사용하여 그 머신에서 tcp를 사용하여 해결하였습니다.

## [http://tcpreplay.synfin.net/wiki/FAQ](http://tcpreplay.synfin.net/wiki/FAQ) 일부 발췌

**tcpreplay를 같은 컴퓨터에서 실행하여 패킷을 보낼 수 있습니까?**일반적으로 안 됩니다. tcpreplay가 패킷을 보낼 때, 이 프로그램은 

TCP/IP stack과 네트워크 카드의 장치 드라이버 사이에 패킷을 주입합니다. 그 결과, tcpreplay를 실행하는 TCP/IP 시스템은 패킷을 볼 수 없습니다.

한 가지 제안할 수 있는 것은[​VMWare](http://www.vmware.com/), [​Parallels](http://www.parallels.com/) 또는 [​Xen](http://www.xensource.com/) 같은 것을 사용하는 것입니다. 가상 머신(guest)에서 tcpreplay를 실행하는 것은 호스트 운영체제에서 패킷을 볼 수 있게 합니다.
