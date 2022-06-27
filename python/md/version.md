**출처**

[https://stackoverflow.com/questions/20180543/how-to-check-the-version-of-python-modules](https://stackoverflow.com/questions/20180543/how-to-check-the-version-of-python-modules)

# 파이썬 모듈의 버전 확인하는 방법

저는 다음처럼 파이썬 모듈을 설치하였습니다. `construct`, `statlib`과 `setuptools` 입니다.

```shell
# 다음처럼 다운로드 가능하기 위한 setuptools 설치
sudo apt-get install python-setuptools

# 가벼운 통계 도구를 위한 statlib 설치
sudo easy_install statlib

# 바이너리 데이터를 packing/unpacking하기 위한 construct 설치
sudo easy_install construct
```

저는 그들의 버전을 검사하기 위해 (프로그래밍으로) 가능한지 알고 싶습니다. 명령어로부터 제가 실행할 수 있는 `python --version`과 같은 것이 있을까요?

---

## 31 개의 답변 중 1 개의 답변

저는 [easy install 대신에 pip](https://stackoverflow.com/questions/3220404/why-use-pip-over-easy-install/3220572#3220572)를 사용하는 것을 제안합니다. pip로 당신은 모든 설치된 패키지를 알 수 있고 버전은 다음 명령으로 알 수 있습니다.

```shell
pip freeze
```

리눅스 시스템 대부분에서, 당신은 관심있는 특정 패키지를 행 단위로 찾기 위해 `grep`으로 (또는 윈도우즈에서는 `findstr`으로) 파이프를 사용할 수 있습니다.

### Linux:

```shell
pip freeze | grep lxml
```

> lxml==2.3

### Windows:

```shell
pip freeze | findstr lxml
```

> lxml==2.3

개별 모듈에서, 당신은 [`__version__` 속성](https://peps.python.org/pep-0396/) 을 시도할 수 있습니다. 하지만, 그것이 없는 모듈도 있습니다.

```shell
python -c "import requests; print(requests.__version__)"
2.14.2

python -c "import lxml; print(lxml.__version__)"
```

> Traceback (most recent call last):
> 
> File "<string>", line 1, in <module>
>
> AttributeError: 'module' object has no attribute 'version'
  

마지막으로 질문의 명령에 `sudo` 접두사가 붙기 때문에 전역 Python 환경에 설치하는 것으로 보입니다. 예를 들어 [virtualenvwrapper](https://virtualenvwrapper.readthedocs.io/en/latest/)와 같은 Python [가상 환경](https://pypi.org/project/virtualenv/) 관리자를 살펴보는 것이 좋습니다.

