출처 : [https://www.tecmint.com/rsync-local-remote-file-synchronization-commands/](https://www.tecmint.com/rsync-local-remote-file-synchronization-commands/)

# Rsync (원격 동기화): 리눅스에서 Rsync 명령의 10가지 실용적인 예시

**Rsync (원격 동기화)** 는 **리눅스/유닉스** 시스템에서 **로컬** 뿐만이 아니라 [**원격**으로 파일과 디렉터리를 **복사**하고 **동기화**](https://www.tecmint.com/sync-new-changed-modified-files-rsync-linux/)하는데 가장 보편적으로 사용되는 명령입니다. 

**rsync** 명령의 도움으로 당신은 당신의 데이터를 로컬과 원격으로 디렉터리, 디스크, 네트워크 상에서 복사하고 동기화할 수 있고 [두 리눅스 머신 간에 미러링](https://www.tecmint.com/clone-centos-server/)과 백업을 할 수 있습니다.

이 글은 파일을 **리눅스** 기반의 머신에서 로컬과 원격으로 파일을 전송하는 **rsync** 명령의 **10**개의 기본적이고 고급 사용법을 설명합니다. **rsync** 명령을 실행하는 데 **root** 사용자일 필요는 없습니다.

## Rsync 명령의 장점과 특징

* 원격 시스템부터 또는 원격으로 파일을 효율적으로 복사하고 동기화 합니다.
* 링크, 장치, 소유자, 그룹, 권한 복사를 지원합니다.
* **rsync**는 파일의 두 집합 사이에 차이점만 전송하는 원격 갱신 프로토콜을 사용하기 때문에 [**scp (Secure Copy)**](https://www.tecmint.com/scp-commands-examples/) 보다 빠릅니다. 처음에는 파일 또는 디렉토리의 전체 내용을 소스에서 대상으로 복사하지만 다음부터는 변경된 블록과 바이트만 대상으로 복사합니다.
* Rsync는 압축 및 압축 해제 방식을 사용하여 데이터를 송수신하면서 [**대역폭**](https://www.tecmint.com/linux-network-bandwidth-monitoring-tools/)을 덜 소모합니다.

## rsync 명령의 기본 문법

```shell
# rsync 옵션 원본 대상
# rsync options source destination
```

## 명령에서 사용되는 몇 가지 공통 옵션

* -v : 자세한 설명 출력
* -r : 데이터를 재귀적으로 복사합니다(그러나 데이터를 전송하는 동안 타임스탬프와 권한을 유지하지 않습니다.)
* -a : 아카이브 모드, 아카이브 모드는 파일을 재귀적으로 복사할 수 있으며 심볼릭 링크, 파일 권한, 사용자 및 그룹 소유권 및 타임스탬프도 보존합니다.
* -z : 파일 데이터 압축
* -h : 사람이 읽을 수 있는 형식의 출력 숫자

> [추천 읽기: 비표준 SSH 포트와 함께 Rsync를 사용하여 파일/디렉토리를 동기화하는 방법](https://www.tecmint.com/sync-files-using-rsync-with-non-standard-ssh-port/)

## 리눅스 머신에서 rsync 설치하기

다음 명령으로 **rsync** 패키지를 설치할 수 있습니다.

```shell
# yum install rsync (Red Hat 기반 시스템에서)
# apt-get install rsync (On Debian 기반 시스템에서)
```

# 1. 로컬에서 파일과 디렉터리 복사/동기화 하기

## 로컬 컴퓨터에서 파일 복사/동기화 하기

다음 명령어는 로컬에서 한 위치에서 다른 위치로 하나의 파일을 동기화할 것입니다. 여기 이 예시에서 파일 이름 **backup.tar**는 **/tmp/backups** 폴더로 복사되거나 동기화될 것입니다.

```shell
[root@tecmint]# rsync -zvh backup.tar /tmp/backups/

created directory /tmp/backups

backup.tar
```

위 예시에서 목적지(/tmp/backups)가 아직 없다면 rsync는 자동으로 목적지에 디렉터리를 만들 것입니다.

## 로컬 컴퓨터에서 디렉터리 복사/동기화 하기

다음 명령어는 같은 머신에서 한 디렉터리에서 다른 디렉터리로 모든 파일을 동기화하거나 전송할 것입니다. 여기 이 예시에서 **/root/rpmpkgs**는 몇 개의 rpm 패키지 파일을 포함하며 **/tmp/backups** 폴더로 복사될 것입니다.

```shell
[root@tecmint]# rsync -avzh /root/rpmpkgs /tmp/backups/

sending incremental file list

rpmpkgs/

rpmpkgs/httpd-2.2.3-82.el5.centos.i386.rpm

rpmpkgs/mod_ssl-2.2.3-82.el5.centos.i386.rpm

rpmpkgs/nagios-3.5.0.tar.gz

rpmpkgs/nagios-plugins-1.4.16.tar.gz

sent 4.99M bytes  received 92 bytes  3.33M bytes/sec

total size is 4.99M  speedup is 1.00
```

# 2. 서버에서 또는 서버로 파일과 디렉터리 복사/동기화 하기

## 로컬 서버에서 원격 서버로 디렉터리 복사하기

이 명령은 로컬 머신에서 원격 머신으로 디렉터리를 동기화 합니다. **예시:** 로컬 컴퓨터에 몇개의 **RPM** 패키지를 포함하는 **"rpmpkg"** 폴더가 있습니다. 로컬 디렉터리의 내용을 원격서버로 보내고 싶으시면, 다음 명령을 사용할 수 있습니다.

```shell
[root@tecmint]$ rsync -avz rpmpkgs/ root@192.168.0.101:/home/

root@192.168.0.101's password:

sending incremental file list

./

httpd-2.2.3-82.el5.centos.i386.rpm

mod_ssl-2.2.3-82.el5.centos.i386.rpm

nagios-3.5.0.tar.gz

nagios-plugins-1.4.16.tar.gz

sent 4993369 bytes  received 91 bytes  399476.80 bytes/sec
```

## 로컬 서버에서 원격 서버로 디렉터리의 특정 파일만 복사하기(역자추가)

여기 예시로 로컬 컴퓨터에 몇개의 **RPM** 패키지를 포함하는 **"rpmpkg"** 폴더가 있습니다. 로컬 디렉터리의 확장자가 rpm인 파일만을 원격서버로 보내고 싶으시면, 다음 명령을 사용할 수 있습니다.

```
[root@tecmint]$ rsync -avz rpmpkgs/*.rpm root@192.168.0.101:/home/

root@192.168.0.101's password:

sending incremental file list

./

httpd-2.2.3-82.el5.centos.i386.rpm

mod_ssl-2.2.3-82.el5.centos.i386.rpm

sent ? bytes  received ? bytes  ? bytes/sec
```

## 원격 디렉터리를 로컬 머신으로 복사/동기화 하기

이 명령은 당신이 원격 디렉터리를 로컬 디렉터리로 동기화 하도록 도움을 줄 것입니다. 여기 예시로 원격서버에 있는 디렉터리 **/home/tarunika/rmpkgs**는 당신의 로컬 컴퓨터의 **/tmp/myrpms**로 복사될 것입니다.

```shell
[root@tecmint]# rsync -avzh root@192.168.0.100:/home/tarunika/rpmpkgs /tmp/myrpms

root@192.168.0.100's password:

receiving incremental file list

created directory /tmp/myrpms

rpmpkgs/

rpmpkgs/httpd-2.2.3-82.el5.centos.i386.rpm

rpmpkgs/mod_ssl-2.2.3-82.el5.centos.i386.rpm

rpmpkgs/nagios-3.5.0.tar.gz

rpmpkgs/nagios-plugins-1.4.16.tar.gz

sent 91 bytes  received 4.99M bytes  322.16K bytes/sec

total size is 4.99M  speedup is 1.00
```

# 3. SSH를 통한 RSync

rsync로 우리는 데이터 전송을 위해 **SSH (Secure Shell)** 을 사용할 수 있습니다. SSH 프로토콜을 사용하면 데이터가 전송되는 동안 아무도 읽을 수 없도록 암호화된 보안 연결로 데이터가 인터넷의 유선을 통해 전송되고 있음을 확인할 수 있습니다.

또한 우리가 rsync를 사용할 때 특정한 작업을 성공하기 위해 **user/root** 비밀번호를 제공할 필요가 있습니다. **SSH** 옵션을 사용하여 암호화된 방법으로 로그인하여 전송할 수 있고 당신의 **비밀번호**는 안전할 것입니다.

## SSH로 원격 서버에서 로컬 서버로 파일 복사하기

**rsync**로 프로토콜을 지정하기 위해 **"-e"** 옵션을 당신이 사용하기 원하는 프로토콜 이름을 함께 제공할 필요가 있습니다. 여기 예시에서 **"-e"** 옵션으로 **"ssh"** 를 사용하여 데이터 전송을 수행할 것입니다.

```shell
[root@tecmint]# rsync -avzhe ssh root@192.168.0.100:/root/install.log /tmp/

root@192.168.0.100's password:

receiving incremental file list

install.log

sent 30 bytes  received 8.12K bytes  1.48K bytes/sec

total size is 30.74K  speedup is 3.77
```

## SSH로 로컬 서버에서 원격 서버로 파일 복사하기

```shell
[root@tecmint]# rsync -avzhe ssh backup.tar root@192.168.0.100:/backups/

root@192.168.0.100's password:

sending incremental file list

backup.tar

sent 14.71M bytes  received 31 bytes  1.28M bytes/sec

total size is 16.18M  speedup is 1.10
```

> [추천 읽기: 리눅스에서 새로운 또는 변경/수정된 파일을 동기화하기 위해 Rsync 사용하기](https://www.tecmint.com/sync-new-changed-modified-files-rsync-linux/)

# 4. rsync로 데이터 전송하는 동안 진행상태 보기

하나의 머신에서 다른 머신으로 데이터가 전송되는 동안 진행상태를 보기 위해 우리는 **'--progress'** 옵션을 사용할 수 있습니다. 이것은 파일과 전송을 완료하는 데 남은 시간을 표시합니다.

```shell
[root@tecmint]# rsync -avzhe ssh --progress /home/rpmpkgs root@192.168.0.100:/root/rpmpkgs

root@192.168.0.100's password:

sending incremental file list

created directory /root/rpmpkgs

rpmpkgs/

rpmpkgs/httpd-2.2.3-82.el5.centos.i386.rpm

           1.02M 100%        2.72MB/s        0:00:00 (xfer#1, to-check=3/5)

rpmpkgs/mod_ssl-2.2.3-82.el5.centos.i386.rpm

          99.04K 100%  241.19kB/s        0:00:00 (xfer#2, to-check=2/5)

rpmpkgs/nagios-3.5.0.tar.gz

           1.79M 100%        1.56MB/s        0:00:01 (xfer#3, to-check=1/5)

rpmpkgs/nagios-plugins-1.4.16.tar.gz

           2.09M 100%        1.47MB/s        0:00:01 (xfer#4, to-check=0/5)

sent 4.99M bytes  received 92 bytes  475.56K bytes/sec

total size is 4.99M  speedup is 1.00
```

# 5. --include 와 --exclude 옵션 사용하기

이 두 가지 옵션을 사용하면 매개변수를 지정하여 파일을 **포함** 및 **제외**할 수 있습니다. 이 옵션은 동기화에 포함할 파일 또는 디렉터리를 지정하고 전송을 원하지 않는 파일 및 폴더를 제외하는 데 도움이 됩니다.

여기 예시로 rsync 명령은 **'R'** 로 시작하는 파일이나 디렉터리를 포함하며 모든 다른 파일과 디렉터리는 제외될 것입니다.

```shell
[root@tecmint]# rsync -avze ssh --include 'R*' --exclude '*' root@192.168.0.101:/var/lib/rpm/ /root/rpm

root@192.168.0.101's password:

receiving incremental file list

created directory /root/rpm

./

Requirename

Requireversion

sent 67 bytes  received 167289 bytes  7438.04 bytes/sec

total size is 434176  speedup is 2.59
```

# 6. --delete 옵션 사용하기

파일 또는 디렉토리가 원본에 없지만 대상에는 이미 존재하는 경우 동기화하는 동안 대상에서 기존 파일/디렉토리를 삭제할 수 있습니다.

**'-delete'** 옵션을 사용하여 원본 디렉토리에 없는 파일을 삭제할 수 있습니다.

원본와 대상은 동기화 됩니다. 이제 대상에서 새 파일 **test.txt**가 만들어 집니다.

```shell
[root@tecmint]# touch test.txt
[root@tecmint]# rsync -avz --delete root@192.168.0.100:/var/lib/rpm/ .
Password:
receiving file list ... done
deleting test.txt
./
sent 26 bytes  received 390 bytes  48.94 bytes/sec
total size is 45305958  speedup is 108908.55
```

대상에는 **test.txt((라는 새 파일이 있으며 **'–delete'** 옵션으로 소스와 동기화하면 **test.txt** 파일이 제거됩니다.

# 7. 전송될 파일의 최대 크기 설정

전송되거나 동기화될 파일의 **(최대)** 크기를 지정할 수 있습니다. **"--max-size"** 옵션과 함께 최대 크기를 지정할 수 있습니다. 여기 예시로 최대 파일 크기는 **200k** 이며, 이 명령은 **200k** 보다 같거나 작은 파일만 전송할 것입니다.

```shell
[root@tecmint]# rsync -avzhe ssh --max-size='200k' /var/lib/rpm/ root@192.168.0.100:/root/tmprpm

root@192.168.0.100's password:

sending incremental file list

created directory /root/tmprpm

./

Conflictname

Group

Installtid

Name

Provideversion

Pubkeys

Requireversion

Sha1header

Sigmd5

Triggername

__db.001

sent 189.79K bytes  received 224 bytes  13.10K bytes/sec

total size is 38.08M  speedup is 200.43
```

# 8. 성공적인 전송 후에 원본 파일 자동으로 지우기

메인 웹 서버와 데이터 백업 서버가 있다고 가정하면, 매일 백업을 생성하고 당신의 백업서버로 동기화 하면 이제 당신의 웹 서버에 있는 백업의 로컬 복제본은 유지하고 싶지 않을 것입니다.

그럼, 파일 전송 완료될 때까지 기다렸다가 수동으로 이 로컬 백업을 삭제해야 할까요? 당연히 아닙니다. 이 자동 삭제는 **'--remove-source-files'** 옵션을 사용하여 할 수 있습니다.

```shell
[root@tecmint]# rsync --remove-source-files -zvh backup.tar /tmp/backups/

backup.tar

sent 14.71M bytes  received 31 bytes  4.20M bytes/sec

total size is 16.18M  speedup is 1.10

[root@tecmint]# ll backup.tar

ls: backup.tar: No such file or directory
```

# 9. rsync로 모의테스트 하기

당신이 초보자이고 rsync를 사용하고 있고 당신의 명령이 정확히 무엇을 하는지 모르는 경우 Rsync는 대상 폴더의 항목을 실제로 엉망으로 만들 수 있으며 실행 취소를 수행하는 것은 지루한 작업이 될 수 있습니다.

> [추천 읽기: Rsync 사용하여 두 웹 서버/웹 사이트를 동기화 하는 방법](https://www.tecmint.com/sync-two-apache-websites-using-rsync/)

이 옵션을 사용하면 아무 것도 변경되지 않고 명령의 테스트 실행만 수행하고 명령의 출력이 표시됩니다. 출력이 원하는 것과 정확히 일치하면 명령에서 **'-dry-run'** 옵션을 제거하고 명령의 출력을 표시할 수 있습니다. 터미널에서 실행합니다.

```shell
root@tecmint]# rsync --dry-run --remove-source-files -zvh backup.tar /tmp/backups/

backup.tar

sent 35 bytes  received 15 bytes  100.00 bytes/sec

total size is 16.18M  speedup is 323584.00 (DRY RUN)
```

# 10. 대역폭 제한을 설정하고 파일 전송하기

당신은 **'–bwlimit'** 옵션의 도움으로 한 머신에서 다른 머신으로 데이터를 전송하는 동안 대역폭을 설정할 수 있습니다. 이 옵션은 **입출력(I/O)** 대역폭을 제한하도록 우리에게 도움을 줍니다.

```shell
[root@tecmint]# rsync --bwlimit=100 -avzhe ssh  /var/lib/rpm/  root@192.168.0.100:/root/tmprpm/
root@192.168.0.100's password:
sending incremental file list
sent 324 bytes  received 12 bytes  61.09 bytes/sec
total size is 38.08M  speedup is 113347.05
```

또한 기본적으로 rsync는 변경된 블록과 바이트만 동기화합니다. 명시적으로 전체 파일을 동기화하려면 **'-W'** 옵션을 사용합니다.

```shell
[root@tecmint]# rsync -zvhW backup.tar /tmp/backups/backup.tar
backup.tar
sent 14.71M bytes  received 31 bytes  3.27M bytes/sec
total size is 16.18M  speedup is 1.10
```

이제 rsync에 알아 보았고 더 많은 옵션에 대한 **매뉴얼 페이지**에서 볼 수 있습니다. 앞으로 더 흥미롭고 흥미로운 튜토리얼을 위해 **Tecmint**와 계속 연결하십시오. 귀하의 의견과 제안을 남겨주세요.
