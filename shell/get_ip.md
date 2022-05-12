출처 : [https://stackoverflow.com/questions/13322485/how-to-get-the-primary-ip-address-of-the-local-machine-on-linux-and-os-x](https://stackoverflow.com/questions/13322485/how-to-get-the-primary-ip-address-of-the-local-machine-on-linux-and-os-x)

# 리눅스와 OS X에서 현재 컴퓨터의 중요 외부 IP를 알아내는 방법

저는 127.0.0.1보다 현재 컴퓨터(localhost)의 중요(첫 번째) IP 주소를 리턴하는 command를 찾고 있습니다.

이 해결책은 적어도 리눅스(Debian과 Redhat)과 OS X 10.7+에서 작동해야 합니다.

저는 둘다 `ifconfig`로 가능하지만 이 플랫폼 사이에 출력이 일관성이 없다는 게 문제입니다.

------

## 31개의 답변 중 2개의 답변

`ifconfig`로 부터 `grep`을 사용하여 IP 주소를 걸러낼 수 있습니다.

```shell
ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1'
```

아니면 `sed`로

```shell
ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p'
```

만약 특정 인터페이스인 wlan0, eth0, 등등에만 관심이 있다면 다음처럼 사용가능합니다.

```shell
ifconfig wlan0 | ...
```


예를 들어 `myip`라 불리는 당신이 명령어를 만들어 `.bashrc`에 명령어를 별명(alias)으로 사용할 수 있습니다.

```shell
alias myip="ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p'"
```

더 간단한 방법은 `hostname -I`(`hostname -i`는 `hostname`의 옛날 버전에서 되므로 댓글을 보세요.) 입니다. 하지만 리눅스에서만 됩니다.

---

리눅스에서(OS X 아님) 다음 명령어로 가능합니다.

```shell
hostname --ip-address
```
