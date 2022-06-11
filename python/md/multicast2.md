출처 : [https://huichen-cs.github.io/course/CISC7334X/20FA/lecture/pymcast/](https://huichen-cs.github.io/course/CISC7334X/20FA/lecture/pymcast/)

# Python에서 멀티캐스트 프로그래밍

## 차례

* 소개
* IPv4 멀티캐스트 주소
* UDP 데이터그램 멀티캐스트
* 프로그램 예시와 실험 환경
  - 전송 프로그램 (mcastsend.py)
  - 수신 프로그램 (mcastrecv.py)
  - 시연
    + 가정
    + 시연 실행하기
    + 출력

## 소개

통신 패턴을 설명하는 몇 가지 용어가 있습니다. 이러한 용어는 모두 "-cast"로 끝나고 유니캐스트, 브로드캐스트, 멀티캐스트, 애니캐스트 및 지오캐스트를 포함합니다. 여기서는 단일 패킷의 복사본을 가능한 모든 대상의 선택된 하위 집합으로 전달하는 기술인 멀티캐스트에 특히 관심이 있습니다. TCP/IP는 멀티캐스트를 지원합니다. 여기서 우리의 목표는 TCP/IP 프로토콜 스택에서 멀티캐스트의 개념을 설명하기 위해 기초적인 Python 프로그램을 작성하는 것입니다.

## IPv4 멀티캐스트 주소

IP 주소에는 몇 가지 범주가 있습니다. 멀티캐스트 주소는 범주입니다. 224.0.0.0/4 범위의 모든 IPv4 주소는 그룹, 즉 우리가 패킷을 전달할 모든 가능한 호스트의 선택된 하위 집합을 정의하는 멀티캐스트 주소입니다. 이 "224.0.0.0/4"와 같은 IP 주소 범위는 소위 CIDR 표기법에 있습니다. 이 특별한 경우 기본적으로 최상위 4비트(또는 4비트 접두사)가 224.0.0.0과 동일한 모든 IPv4 주소가 이 범위에 있음을 의미합니다. (수학적 경사?의 경우 224.0.0.0/4는 {a|∀a,a∧f0000000<sub>h</sub> ≡ f0000000<sub>h</sub>} 다음의 IPv4 주소 집합을 정의합니다.

## UDP 데이터그램 멀티캐스트

TCP/IP 프로토콜 스택에서 UDP는 데이터그램 멀티캐스트 서비스를 실현합니다.

## 프로그램 예시와 실험 환경

여기 UDP 데이터그램 멀티캐스트 예시는 두개의 파이썬 프로그램으로 구성됩니다. *mcastsend.py*는 전송 프로그램이며 *mcastrecv.py*는 수신 프로그램 입니다. 우리는 4개의 가상 머신을 사용하여 멀티캐스트 개념을 시연하기 위해 프로그램을 실행합니다.

### 전송 프로그램 (mcastsend.py)

```python
import socket
import sys

def help_and_exit(prog):
    print('Usage: ' + prog + ' host_ip mcast_group_ip mcast_port_num message',
        file=sys.stderr)
    sys.exit(1)

def mc_send(hostip, mcgrpip, mcport, msgbuf):
    # UDP 소켓을 생성한다
    sender = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM, \
            proto=socket.IPPROTO_UDP, fileno=None)
    # 멀티캐스트 (멀티캐스트 그룹 IP 주소, 전송할 포트 번호) 짝인 엔드 포인트를 정의한다.
    mcgrp = (mcgrpip, mcport)

    # 멀티캐스트 데이터그램이 얼마나 많은 홉(hop)을 여행할 수 있는지 정의한다.
    # IP_MULTICAST_TTL를 따로 정의하지 않는다면 기본 값은 1이다. 
    sender.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 1)

    # 멀티캐스트 데이터그램 전송을 담당하는 네트워크 인터페이스(NIC)를 정의합니다.
    # 그렇지 않으면 소켓은 기본 인터페이스를 사용합니다. (루프백이 0이면 ifindex = 1)
    # 데이터그램을 여러 NIC로 전송하려면 각 NIC에 대한 소켓을 만들어야 합니다.
    sender.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_IF, \
         socket.inet_aton(hostip))

    # 버퍼에서 데이터그램을 전송합니다
    sender.sendto(msgbuf, mcgrp)

    # 소켓 자원을 해제합니다 
    sender.close()


def main(argv):
    if len(argv) < 5:
        help_and_exit(argv[0])

    hostipaddr = argv[1]
    mcgrpipaddr = argv[2]
    mcport = int(argv[3])
    msg = argv[4]

    mc_send(hostipaddr, mcgrpipaddr, mcport, msg.encode())

if __name__=='__main__':
    main(sys.argv)
```

### 수신 프로그램 (mcastrecv.py)

```python
import sys
import socket
import struct


def help_and_exit(prog):
    print('Usage: ' + prog + ' from_nic_by_host_ip mcast_group_ip mcast_port')
    sys.exit(1)

def mc_recv(fromnicip, mcgrpip, mcport):
    bufsize = 1024

    # UDP 소켓을 생성한다
    receiver = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM, \
            proto=socket.IPPROTO_UDP, fileno=None)

    # 멀티캐스트 (멀티캐스트 그룹 IP 주소, 전송할 포트 번호) 짝인 엔드 포인트로부터 
    # 전송된 데이터그램을 수신하기 위한 소켓을 설정한다.
    # 이 소켓은 전송 프로그램의 짝과 매칭 되어야 한다.
    bindaddr = (mcgrpip, mcport)
    receiver.bind(bindaddr)

    # 소켓을 의도한 멀티캐스트 그룹에 조인합니다. 그 의미는 두 가지입니다.
    # 멀티캐스트 IP 주소에 의해 식별되는 의도된 멀티캐스트 그룹을 지정합니다.
    # 또한 어떤 네트워크 인터페이스에서 (NIC) 소켓은 의도한 멀티캐스트 그룹에 대한 데이터그램을 수신합니다.
    # socket.INADDR_ANY는 시스템의 인터페이스(ifindex = 루프백 인터페이스가 있는 경우 1)에서 
    # 기본 네트워크를 의미한다는 점에 유의하는 것이 중요합니다.
    # 여러 NIC에서 멀티캐스트 데이터그램을 수신하려면
    # 각 NIC용 소켓을 생성해야 합니다. 또한 할당된 IP 주소로 NIC를 식별합니다.
    if fromnicip == '0.0.0.0':
        mreq = struct.pack("=4sl", socket.inet_aton(mcgrpip), socket.INADDR_ANY)
    else:
        mreq = struct.pack("=4s4s", \
            socket.inet_aton(mcgrpip), socket.inet_aton(fromnicip))
    receiver.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)

    # 메세지를 수신한다
    buf, senderaddr = receiver.recvfrom(1024)
    msg = buf.decode()

		# 자원을 해제한다
		receiver.close()

    return msg

def main(argv):
    if len(argv) < 4:
        help_and_exit(argv[0])

    fromnicip = argv[1] 
    mcgrpip = argv[2]
    mcport = int(argv[3])


    msg = mc_recv(fromnicip, mcgrpip, mcport)
    print(msg)
    
if __name__=='__main__':
    main(sys.argv)
```

### 시연

#### 가정

편의를 위해 4개의 리눅스 가상 머신을 다음처럼 이름을 만들고 다음 표에 주어진 IPv4 주소가 있다고 가정합니다.

|**호스트 이름**|**IPv4 주소**|
|-|-|
|eastny|192.168.56.1|
|midwood|192.168.56.3|
|flatbush|192.168.56.4|
|bushwick|192.168.56.5|

두 개의 멀티캐스트 그룹이 있다고 가정합니다.

|**IPv4 멀티캐스트 그룹 1**|**IPv4 멀티캐스트 그룹 2**|
|-|-|
|224.1.1.5|234.3.2.1|

전송자는 엔드 포인트를 식별하고 UDP를 사용하기로 결정하였고 4개의 호스트로 다음처럼 두 개의 프로그램을 배포합니다.

|**호스트**|**프로그램**|
|-|-|
|midwood|mccastsend.py|
|eastny|mccastrecv.py|
|flatbush|mccastrecv.py|
|bushwick|mccastrecv.py|

#### 시연 실행하기

우리는 다음처럼 프로그램을 실행합니다.

1. 첫째, 우리는 다음처럼 *eastny*와 *flatbush*에서 *mcastrecv.py*를 실행합니다.

```shell
brooklyn@flatbush:~$ python mcastrecv.py 192.168.56.104 224.1.1.5 50001
```

```shell
brooklyn@eastny:~$ python mcastrecv.py 192.168.56.101 224.1.1.5 50001
```

2. 우리는 *bushwick*에서 *mcastrecv.py*와 *scapy3*를 두 개의 터미널에서 둘 다 실행합니다.

```shell
brooklyn@bushwick:~$ python mcastrecv.py 192.168.56.105 234.3.2.1 50001
```

```shell
brooklyn@bushwick:~$ sudo scapy3
>>> packets = sniff(prn=lambda p: p.summary(), filter='udp port 50001')
```

3. 마지막으로 우리는 *mcastsend.py*를 *midwood*에서 실행합니다.

```shell
brooklyn@midwood:~$ python mcastsend.py 192.168.56.103 224.1.1.5 50001 "Hello, World!"
```

#### 출력

출력은 다음과 같습니다.

*flatbush*에서 우리는 다음을 관찰합니다.

```shell
brooklyn@flatbush:~$ python mcastrecv.py 192.168.56.104 224.1.1.5 50001
Hello, World!
brooklyn@flatbush:~$
```

*eastny*에서 우리는 다음도 관찰합니다.

```shell
brooklyn@eastny:~$ python mcastrecv.py 192.168.56.101 224.1.1.5 50001
Hello, World!
brooklyn@eastny:~$
```

하지만, *bushwick*에서 *mcastrecv.py*는 IPv4 주소 192.168.56.101에 의해 식별되는 네트워크 인터페이스로부터 멀티캐스트 그룹 234.3.2.1로 데이터그램을 받지 못했기에 데이터를 아직 기다립니다. (글쎄, 우리는 이 데모에서 우리는 절대 그 그룹에 데이터그램을 보내지 않는다는 점에 주의해야 합니다.)

```shell
`brooklyn@bushwick:~$ python mcastrecv.py 192.168.56.105 234.3.2.1 50001


```

하지만, *scrapy3*은 *midwood*에서 *mcastsend.py*에 의해 보내진 패킷을 캡쳐했습니다.

```python
>>> packets = sniff(prn=lambda p: p.summary(), filter='udp port 50001')
Ether / IP / UDP 192.168.56.103:52582 > 224.1.1.5:50001 / Raw / Padding

^C>>> hexdump(packets[0])
0000  01005E010105080027A133B408004500 ..^.....'.3...E.
0010  00290DC74000011191E7C0A83867E001 .)..@.......8g..
0020  0105CD66C351001553C948656C6C6F2C ...f.Q..S.Hello,
0030  20576F726C64210000000000          World!.....
>>>
```
