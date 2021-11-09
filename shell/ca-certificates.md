# CentOS 6 ca-certificates 패키지 수동 설치

**수동 설치 shell**

-   [https://github.com/SDRLurker/TIL/blob/master/shell/ca-certificates.sh](https://github.com/SDRLurker/TIL/blob/master/shell/ca-certificates.sh)

**문제점**

-   scp가 원격서버로 복사가 안 되어서 찾아본 결과 openssh-clients 패키지를 yum으로 설치해야 됨을 확인하였습니다.
-   yum으로 openssh-clients 등의 CentOS 기본(Base) 패키지를 설치하려면 base, updates, extras 섹션에서 다음 baseurl 주소를 변경해야 합니다.
    -   참고주소 : [https://woongwoong.tistory.com/entry/CentOS-6%EB%A6%AC%EB%88%85%EC%8A%A4-yum-%EC%95%88%EB%90%A8%E3%85%A0%E3%85%A0-Baserepo-%EB%B3%80%EA%B2%BD%ED%95%98%EC%97%AC-yum-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0](https://woongwoong.tistory.com/entry/CentOS-6%EB%A6%AC%EB%88%85%EC%8A%A4-yum-%EC%95%88%EB%90%A8%E3%85%A0%E3%85%A0-Baserepo-%EB%B3%80%EA%B2%BD%ED%95%98%EC%97%AC-yum-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0)

```
# vi CentOS-Base.repo
[base]
...
baseurl=http://vault.centos.org/centos/$releasever/os/$basearch/
[updates]
...
baseurl=http://vault.centos.org/centos/$releasever/updates/$basearch/
[extras]
...
baseurl=http://vault.centos.org/centos/$releasever/extras/$basearch/
```

-   위에처럼 변경하였지만 다음과 같이 SSL 접속에 문제가 발생하였습니다.

```
# yum install openssh-clients
http://vault.centos.org/centos/6/os/x86_64/repodata/repomd.xml: [Errno 14] problem making ssl connection
```

**해결과정**

-   yum으로 패키지를 설치할 수가 없어서 rpm 파일을 [https://vault.centos.org](https://vault.centos.org) 에서 직접 다운로드 받아 설치하는 방법을 사용하였습니다.
-   CentOS 6.10 기준으로 찾아 보았고 다음 주소에서 rpm 파일들을 찾기 시작하였습니다.
    -   [https://vault.centos.org/6.0/os/x86\_64/Packages/](https://vault.centos.org/6.0/os/x86_64/Packages/)
-   SSL 문제로 wget 프로그램을 통해 --no-check-certificate 옵션을 추가하여 rpm 파일을 다운로드 받았습니다.
-   rpm 프로그램을 실행하면서 패키지를 다운로드 받았습니다. 그 결과 위에처럼 수동 설치 shell을 만들 수 있었습니다.

```
#!/bin/bash
wget https://vault.centos.org/6.10/os/x86_64/Packages/ca-certificates-2018.2.22-65.1.el6.noarch.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/p11-kit-0.18.5-2.el6_5.2.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/p11-kit-trust-0.18.5-2.el6_5.2.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/libtasn1-2.3-6.el6_5.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-3.36.0-8.el6.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-sysinit-3.36.0-8.el6.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-tools-3.36.0-8.el6.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nspr-4.19.0-1.el6.x86_64.rpm  --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-softokn-3.14.3-23.3.el6_8.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-util-3.36.0-1.el6.x86_64.rpm --no-check-certificate
wget https://vault.centos.org/6.10/os/x86_64/Packages/nss-softokn-freebl-3.14.3-23.3.el6_8.x86_64.rpm --no-check-certificate

rpm -Uvh ca-certificates-2018.2.22-65.1.el6.noarch.rpm p11-kit-0.18.5-2.el6_5.2.x86_64.rpm p11-kit-trust-0.18.5-2.el6_5.2.x86_64.rpm libtasn1-2.3-6.el6_5.x86_64.rpm nss-3.36.0-8.el6.x86_64.rpm nss-sysinit-3.36.0-8.el6.x86_64.rpm nss-tools-3.36.0-8.el6.x86_64.rpm nspr-4.19.0-1.el6.x86_64.rpm nss-softokn-3.14.3-23.3.el6_8.x86_64.rpm nss-util-3.36.0-1.el6.x86_64.rpm nss-softokn-freebl-3.14.3-23.3.el6_8.x86_64.rpm
```

**Discussion**

Windows XP 처럼 업데이트를 더 이상 지원하지 않는 CentOS 6을 사용하는 것의 어려움을 느꼈습니다. 최신 버전의 운영체제를 사용하는 것이 필요하다고 뼈저리게 느꼈습니다.  
업데이트를 더 이상 지원하지 않는 CentOS의 패키지를 [https://vault.centos.org](https://vault.centos.org) 에서 다운로드 받을 수 있었습니다. CentOS 6보다 더 낮은 버전의 패키지도 있다는 점이 놀라웠고 좋았습니다. 위 홈페이지도 오래동안 유지되면 좋겠습니다.
