출처 : [http://blog.tanelpoder.com/2009/01/22/identifying-shared-memory-segment-users-using-lsof/](http://blog.tanelpoder.com/2009/01/22/identifying-shared-memory-segment-users-using-lsof/)

# lsof를 사용하여 공유메모리 사용자를 식별하기

lsof(열린 파일의 목록을 보여주는 프로그램)은 지워진 파일의 해제 또는 공유메모리의 삭제를 방해하는 열려있는 파일 디스크립터의 문제를 해결하는데 매우 유용한 도구입니다.

이 예시는 리눅스에서 Oracle 사용자의 공유메모리가 해제되지 않고 아직 사용중이라고 표시되고 있는 상황입니다. 

```shell
$ ipcs -ma

------ Shared Memory Segments --------
key        shmid      owner      perms      bytes      nattch     status
0x00000000 393216     oracle    640        289406976  1          dest
0xbfb94e30 425985     oracle    640        289406976  18
0x3cf13430 557058     oracle    660        423624704  22

------ Semaphore Arrays --------
key        semid      owner      perms      nsems
0xe2260ff0 1409024    oracle    640        154
0x9df96b74 1671169    oracle    660        154

------ Message Queues --------
key        msqid      owner      perms      used-bytes   messages
```

**0x00000000 393216     oracle    640        289406976  1          dest**

진하게 표시된 줄은 인스턴스가 닫힌 후에 사라져야 하지만 그렇지 못했습니다. "nattch"(공유메모리에 접근한 프로세스들의 수)로부터 저는 공유메모리를 사용하는 몇개의 프로세스가 아직 있다는 것을 알았습니다. 그래서 이 조각은 해제되지 않았고 **ipcrm** 명령어조차 그것(누군가 파일들을 열었다면 정상 파일인것처럼)을 삭제할 수 없었습니다.

그래서 이 공유메모리를 사용하는 프로세스를 식별할 필요가 있었습니다. 만약 보통 존재하는 파일이었다면 어떤 프로세스가 그 파일을 열었는지 **/sbin/fuser** 명령을 사용했겠지만 이 명령은 디렉터리에서 현재 존재하는 파일에 대해서만 작동합니다.

하지만 지워진 파일, 소켓이나 공유 메모리를 위해서는 lsof 명령을 사용할 수 있습니다. (보통 리눅스에 기본으로 설치되어 있지만 Unix에서는 별도로 다운로드 받아 설치해야 합니다.)

ipcs -ma가 보여주듯이 사용중인 SHM ID는 393216이며 저는 SHM ID를 grep하여 모든 열여 있는 파일 디스크립터를 보여주도록 lsof를 다음처럼 실행하였습니다.

```shell
$ lsof | egrep "393216|COMMAND"
COMMAND     PID      USER   FD      TYPE     DEVICE       SIZE       NODE NAME
python    18811    oracle  DEL       REG        0,8                393216 /SYSVbfb94e30
```

NODE 열에 대응되는 ipcs의 출력된 SHM ID와 대응되는지 알 수 있습니다.
그래서 저는 아직 SHM 에 접근 중인 PID 18811을 kill 하였습니다.

```shell
$ kill 18811

$ ipcs -ma

------ Shared Memory Segments --------
key        shmid      owner      perms      bytes      nattch     status
0xbfb94e30 425985     oracle    640        289406976  18
0x3cf13430 557058     oracle    660        423624704  25

------ Semaphore Arrays --------
key        semid      owner      perms      nsems
0xe2260ff0 1409024    oracle    640        154
0x9df96b74 1671169    oracle    660        154

------ Message Queues --------
key        msqid      owner      perms      used-bytes   messages
```

해당 공유메모리가 사라지고 해제되었습니다.

명령은 다른 작업을 하는데도 매우 유용합니다. 예를 들어, 네트워크 프로토콜, IP, port 등 열여 있는 소켓을 보여주기도 합니다. 예를 들어 OS level에서 어떤 클라이언트가 서버 프로세스와 통신하는 지 확인 할 수 있습니다.

```shell
$ lsof -i:1521
COMMAND   PID   USER   FD   TYPE DEVICE SIZE NODE NAME
tnslsnr  6212 oracle   11u  IPv4  49486       TCP *:1521 (LISTEN)
tnslsnr  6212 oracle   13u  IPv4 276708       TCP linux03:1521->linux03:37277 (ESTABLISHED)
tnslsnr  6212 oracle   14u  IPv4 264894       TCP linux03:1521->linux03:41122 (ESTABLISHED)
oracle  22687 oracle   20u  IPv4 264893       TCP linux03:41122->linux03:1521 (ESTABLISHED)
oracle  25250 oracle   15u  IPv4 276707       TCP linux03:37277->linux03:1521 (ESTABLISHED)
oracle  25530 oracle   15u  IPv4 279910       TCP linux03:1521->192.168.247.1:nimsh (ESTABLISHED)
```

불행히도 lsof는 클래식 유닉스에서 기본적으로 설치되지 않지만 일부에서는 시스템 관리자가 설치하기로 선택했습니다. 그러나 lsof는 /dev/kmem 또는 이와 유사한 것을 통해 커널 메모리 구조에 액세스해야 하므로 일반 사용자에게는 작동하지 않을 수 있습니다. lsof에 액세스할 수 없다면 lsof가 할 수 있는 몇 가지 트릭을 수행할 수 있는 다른 도구가 있을 수 있습니다. 예를 들어 Solaris에는 프로세스의 열린 파일을 표시할 수 있는 유용한 명령 **pfiles**가 있으며 (내 생각에는) Solaris 9 부터 네트워크 소켓의 TCP 연결 endpoint도 보고할 수 있습니다…
