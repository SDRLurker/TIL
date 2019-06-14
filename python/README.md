# python

### MarkDown 문서

##### thread_pool.md

"Python 쓰레드 풀"을 번역한 내용입니다.

https://www.metachris.com/2016/04/python-threadpool/

##### ctypes.md

"ctypes로 Python에서 C++ 클래스 호출하기"을 번역한 내용입니다.

http://www.auctoris.co.uk/2017/04/29/calling-c-classes-from-python-with-ctypes/

##### non_blocking.md

"python에서 subprocess.PIPE로 non-blocking 읽기"를 번역한 내용입니다.

https://stackoverflow.com/questions/375427/non-blocking-read-on-a-subprocess-pipe-in-python

### Jupyter Notebook 문서

##### python basic.ipynb

* 파이썬의 기초 내용에 대한 실습

##### tensorflow.ipynb

##### 머신러닝_스터디_lab3_숙제.ipynb

* H(X) = XW + b 에 대한 미분식을 사용하여 경사하강법에 계산식을 코딩하여 적용. 

### 프로그램 목록(program list)

##### mv.py {src} {desc}

mv only files from src pattern to desc pattern

src 패턴의 파일을 desc 패턴의 파일로 이동(rename) 합니다.

예시) mv.py A B

A라는 글자가 나오는 모든 패턴의 파일을 B라는 패턴의 파일로 이동 합니다.

##### ping.py

ping several hosts in $HOME/conf/ping.conf.

$HOME/ping.conf에 설정된 몇 개의 host의 ping을 검사합니다.

##### slice.py {rfile} {wfile} {n}

slice {rfile} every {n}th character. all results will be saved to {wfile}.

{rfile} 파일을 {n}번째 문자마다 자릅니다. 모든 결과는 {wfile}에 저장 됩니다.

##### top.py

execute top program in Linux, and then show total CPU usage percentage and top 3 program of using CPU.

Linux에서 top을 실행하여 CPU 전체 사용률 및 CPU 사용률이 높은 상위 3개 프로그램을 보여줍니다.


##### tree.py

GRAPHICALLY DISPLAYS THE DIRECTORY STRUCTURE OF A SPECIFIED PATH

This refers to http://code.activestate.com/recipes/217212-treepy-graphically-displays-the-directory-structure/

and then I modified this code.

특정 경로의 디렉터리 구조를 그래픽으로 보여준다.

이는 다음 http://code.activestate.com/recipes/217212-treepy-graphically-displays-the-directory-structure/ 을 참조했습니다.

그리고 나서 수정하였습니다.

##### zip.py

Zip every file whose size is more than 2G(default) in the specified directory and then remove it.

지정한 디렉터리에서 2기가(디폴트)가 넘는 모든 파일을 압축한 뒤 삭제합니다.

This program is used to get more free hard disk space.

이 프로그램은 하드 디스크의 여유 공간을 확보하는 데 사용됩니다.

##### mul_conn.py IP PORT (NUM)

NUM client threads connect with IP:PORT.

NUM개의 클라이언트 쓰레드는 IP:PORT로 접속합니다.

##### df.py

Check(find) the disk space on remote hosts.

원격 호스트의 디스크 공간을 확인합니다.

##### multicast.py

Print multicast packets from IP:PORT.

IP:PORT로부터 멀티캐스트 패킷을 출력합니다.

##### Lotto.py

Lotto Class with the summation of six random numbers between start and end.

임의로 뽑은 6개의 난수의 총합이 start~end 값을 가지는 로또 클래스를 만듭니다.

##### crawl.py

Get the title of an tistory(blog) article ID.

티스토리 블로그 글 id의 제목을 얻습니다.

* pool = multiprocessing.Pool(processes=2)

|변수명|작업|배열1|배열2|
|-|-|-|-|
|pool|작업을 위한 CPU를 할당|Core-1|Core-2|

* res = pool.map(get_tistory_title, range(s,e+1))
  - 20, 21을 먼저 Core 2개에 할당 한뒤 먼저 끝난 Core에 22, 23 등 남은 값을 할당합니다.

|변수명|작업|배열1|배열2|...|
|-|-|-|-|-|
|range(20,30+1)|배열 할당|20|21|...|
|pool.map|프로세스를 fork(복제)하여 함수를 실행|get_tistory_title(20)|get_tistory_title(21)|...|
|...|10개의 작업이 모두 마칠 때까지 blocking(대기)|...|
|res|함수를 실행한 결과를 리스트로 가져옴|20에 대한 블로그 글 제목|21에 대한 블로그 글 제목|...|

* 참고주소 
  - https://beomi.github.io/2017/07/05/HowToMakeWebCrawler-with-Multiprocess/

### 배운 내용

##### shebang

mv.py 파일 내용

\#!/usr/bin/python

위에처럼 제일 첫 번째 줄에 shebang을 사용한 구문을 추가한 뒤 다음처럼 실행권한을 추가합니다.

```shell
$ chmod +x mv.py
```

그렇게 하면 shell에서 python 명령을 없이 바로 스크립트를 실행할 수 있습니다.

```shell
$ mv.py A B
```


