# CentOS6 Docker설치

## Docker 1.7.1 Download

* 참고주소 
  - [https://web.archive.org/web/20210117022836/https://linoxide.com/linux-how-to/install-upgrade-docker-1-7/](https://web.archive.org/web/20210117022836/https://linoxide.com/linux-how-to/install-upgrade-docker-1-7/)

```shell
centos ~]$ curl -O -sSL https://get.docker.com/rpm/1.7.1/centos-6/RPMS/x86_64/docker-engine-1.7.1-1.el6.x86_64.rpm
centos ~]$ sudo yum -y localinstall --nogpgcheck docker-engine-1.7.1-1.el6.x86_64.rpm
centos ~]$ sudo service docker start
```

## 일반 계정(centos) 유저로 실행

- [http://pyrasis.com/book/DockerForTheReallyImpatient/Chapter03](http://pyrasis.com/book/DockerForTheReallyImpatient/Chapter03)
    - 'sudo 입력하지 않기'를 참고 하였습니다.
- 루트 계정에서 다음 내용을 실행하였습니다.

```shell
centos ~]$ sudo usermod -aG docker centos
centos ~]$ sudo service docker restart
```
