#python

### 목록(list)

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


### 배운 내용

##### shebang

mv.py 파일 내용

\#!/usr/bin/python

위에처럼 shebang을 사용하면 

mv.py A B

python 명령을 없이 바로 스크립트를 실행할 수 있습니다.
