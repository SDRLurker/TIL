출처 : [http://stackoverflow.com/questions/4960048/python-3-and-mysql](http://stackoverflow.com/questions/4960048/python-3-and-mysql)

# Python 3과 MySQL

저는 Windows에서 ActiveState Python 3를 사용하고 있고 MySQL 데이터베이스에 접속 하기를 원했습니다. 저는 [`mysqldb`](https://pypi.org/project/MySQL-python/1.2.5/) 가 사용할 수 있는 모듈이라 들었습니다. 저는 Python 3 용 `mysqldb`을 찾을 수 없었습니다.

`mysqldb`용 바이너리 파일이 있는 가능한 저장소가 있습니까? 윈도우즈에서 Python 3로 MySQL에 어떻게 접속할 수 있습니까?

---

## 14 개의 답변 중 1 개의 답변

mysql과 함께 Python 3를 사용할 수 있는 몇 가지 옵션이 있습니다.

[https://pypi.python.org/pypi/mysql-connector-python](https://pypi.python.org/pypi/mysql-connector-python)

* Oracle에 의해 공식적으로 지원됨
* 순수 파이썬
* 약간 느림
* MySQLdb와 호환되지 않음

[https://pypi.python.org/pypi/pymysql](https://pypi.python.org/pypi/pymysql)

* 순수 파이썬
* mysql-connector 보다 빠름
* `pymysql.install_as_MySQLdb()`를 호출한 후, `MySQLdb`와 거의 대부분 호환됨.

[https://pypi.python.org/pypi/cymysql](https://pypi.python.org/pypi/cymysql)

* C 속도 향상을 위한 pymysql의 복제버전

[https://pypi.python.org/pypi/mysqlclient](https://pypi.python.org/pypi/mysqlclient)

* Django에서 추천하는 라이브러리
* 원래 MySQLdb의 복제버전, 언젠가 다시 합쳐지길 희망함. 
* 가장 빠른 실행을 보이며 C 기반이기 때문
* 가장 MySQLdb와 호환성이 좋고, 이 라이브러리가 복제버전이기 때문
* `python-mysqldb`패키지와 `python3-mysqldb` 패키지 둘 다 제공하기 위해 Debian과 Ubuntu가 이를 사용

참고할 벤치마크: [https://github.com/methane/mysql-driver-benchmarks](https://github.com/methane/mysql-driver-benchmarks)
