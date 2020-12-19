출처 : [https://medium.com/back-to-basics-project/how-to-install-pip-on-centos-6-8-b7453fef63e3](https://medium.com/back-to-basics-project/how-to-install-pip-on-centos-6-8-b7453fef63e3)

# CentOS 6.8에서 pip 설치하는 방법

이는 "... 하는 방법" 이야기의 부분입니다.

안녕하세요. 저는 Python으로 프로그래밍을 시작하기 위해 Python을 몇일간 배웠고 먼저 pip를 설치하는 방법을 배워야 했습니다. 

저는 CentOS 6.8에서 .env를 사용하는 이유는 OS 버전이 이렇게 다루기 때문이지만 다른 Linux OS 버전도 같은 방식일 거라 생각합니다.

## 왜 pip인가?

Pip는 정확히! Python 패키지를 설치하는 프로그램입니다. 

pip를 설치하는 2가지 방법이 있습니다. 첫 번째는 Yum을 사용하는 것, 두 번째는 CURL과 python을 사용하는 것입니다.

## 방법 1. Yum을 사용

Pip는 EPEL의 부분이기 때문에 우리는 EPEL 저장소를 먼저 설치해야 합니다. 그리고 나서 python-pip를 설치합시다.

```shell
# EPEL 저장소를 먼저 설치합니다.
$ sudo yum install epel-release
```

```shell
# python-pip를 설치합니다.
$ sudo yum -y install python-pip
```

## 방법 2. CURL과 python을 사용

우리는 pip를 설치하는 방법을 사용할 수 있습니다. curl을 사용하여 먼저 다운로드 받고 python을 사용하여 pip를 설치합니다.

```shell
# 파일을 다운로드 합니다.
$ curl "https://bootstrap.pypa.io/get-pip.py" -o "get-pip.py"
```

```shell
# pip를 설치하기 위해 get-pip.py 파이썬 실행 파일을 사용합니다.
$ python get-pip.py
```


## pip 설치 테스트

```shell
$ pip — help
$ pip -v
```

shell 창에서 뭔가를 얻을 수 있어야 합니다.

행복한 코딩 되세요!
