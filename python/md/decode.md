출처 : [http://stackoverflow.com/questions/2184181/decoding-tcp-packets-using-python](http://stackoverflow.com/questions/2184181/decoding-tcp-packets-using-python)

# 파이썬을 사용하여 TCP 패킷 decode하기

저는 TCP 접속을 통해 데이터를 decode하려고 합니다. 그 패킷은 작고 100바이트를 넘지 않습니다. 하지만 제가 받은 그 패킷들이 많을 때 그 패킷 중 일부는 결합 됩니다. 이를 방지할 수 있는 방법이 있습니까? 저는 파이썬을 사용합니다.

저는 그 패킷들을 분리하고 싶소 제 소스는 밑에 있습니다. 패킷은 STX 바이트로 시작하며 ETX 바이트로 끝납니다.  STX 뒤의 바이트는 패킷 길이이며, (패킷 길이가 5 미만이면 유효하지 않습니다.) checksum은 ETX 이전 바이트입니다.

```python
def decode(data):
  while True:
    start = data.find(STX)
    if start == -1: #no stx in message
        pkt = ''
        data = ''
        break
    #stx found , next byte is the length
    pktlen = ord(data[1])
    #check message ends in ETX (pktken -1) or checksum invalid
    if pktlen < 5 or data[pktlen-1] != ETX or checksum_valid(data[start:pktlen]) == False:
        print "Invalid Pkt"
        data = data[start+1:]
        continue
    else:
        pkt = data[start:pktlen]
        data = data[pktlen:]
        break

return data , pkt
```

저는 이것을 다음처럼 사용했습니다.

데이터 스트림에서 여러 개의 패킷이 있다면 리스트의 모음으로 패킷을 리턴하거나 첫 번째 패킷만 리턴하는 것이 최선입니다.

저는 C는 익숙하지만 파이썬에 익숙하지 않습니다. 이 방법이 괜찮나요? 어떠한 충고도 감사합니다.

## 5 개의 답변 중 1 개의 답변만 추려냄.

저는 이것처럼 스트림에서부터 패킷을 decoding에 대응하는 클래스를 만들었습니다.

```python
class PacketDecoder(object):

    STX = ...
    ETX = ...

    def __init__(self):
        self._stream = ''

    def feed(self, buffer):
        self._stream += buffer

    def decode(self):
        '''
        Yields packets from the current stream.
        '''
        while len(self._stream) > 2:
            end = self._stream.find(self.ETX)
            if end == -1:
                break

            packet_len = ord(self._stream[1])
            packet = self._stream[:end]
            if packet_len >= 5 and check_sum_valid(packet):
                yield packet
            self._stream = self._stream[end+1:]
```

그리고나서 이를 다음처럼 사용합니다.

```python
decoder = PacketDecoder()
while True:
    data = sock.recv(256) 
    if not data:
        # handle lost connection... 접속이 끊어졌을 때 다룰 코드
    decoder.feed(data)
    for packet in decoder.decode():
        process(packet)
```
