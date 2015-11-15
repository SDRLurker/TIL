# Ryan Shell Collection(쉘 모음)
## mrsh.sh
**Usage - 사용법**

mrsh.sh hosts "cmd"

mrsh.sh 실행할서버 "명령"

**Description - 설명**

This shell execute "cmd" among hosts.

여러 서버에 rsh로 접속하여 원하는 명령을 실행합니다.

each host is delimited by comma(,).

"실행할서버"는 쉼표(,)로 구분됩니다.

Example) If the env GROUP="GROUP" in the shell and you input 1,2 as hosts, this shell execute "cmd" by connecting GROUP1 and GROUP2 via rsh.

예시) mrsh.sh의 환경변수가 GROUP="GROUP"이고 실행할서버로 1,2를 입력하였으면 GROUP1, GROUP2에 rsh로 접속하여 원격실행을 하게 됩니다.

## mssh.sh
**Usage - 사용법**

mssh.sh hosts SSHport "cmd"

mssh.sh 실행할서버 SSH포트 "명령"

**Description - 설명**

This shell execute "cmd" among hosts with SSH port.

여러 서버에 SSH포트로 SSH를 접속하여 원하는 명령을 실행합니다.

each host is delimited by comma(,).

"실행할서버"는 쉼표(,)로 구분됩니다.

Example) If the env GROUP="GROUP", you input 1,2 as hosts, and the env PASSWD="PASSWORD", this shell execute "cmd" by connecting GROUP1 and GROUP2 via ssh with the password "PASSWORD".

예시) mssh.sh의 환경변수가 GROUP="GROUP", PASSWD="PASSWORD" 실행할서버로 1,2를 입력하였으면 GROUP1, GROUP2에 ssh로 접속하며 비밀번호는 "PASSWORD"를 입력하면서 원격실행을 하게 됩니다.

## rsend.sh
**Usage - 사용법**

rsend.sh hosts file [path]

rsend.sh 넘길서버 넘길파일 [넘길위치]

**Description - 설명**

rcp files on the current directory to other hosts.

현재 디렉터리의 파일을 여러 서버로 rcp로 원격복사 합니다.

each host is delimited by comma(,).

"넘길서버"는 쉼표(,)로 구분됩니다.

Example) rsend.sh 1,2 rsend.sh

If the env GROUP="GROUP" in the rsend.sh, rsend.sh file on the current directory will be copied to GROUP1 and GROUP2.

rsend.sh의 환경변수 GROUP="GROUP"이라면 현재 디렉터리의 rsend.sh 파일을 GROUP1, GROUP2로 원격복사합니다.

GROUP1, GROUP2에 rsh로 접속하여 원격실행을 하게 됩니다.

## ssend.sh
**Usage - 사용법**

ssend.sh hosts file [path]

ssend.sh 넘길서버 넘길파일 [넘길위치]

**Description - 설명**

scp files on the current directory to other hosts.

현재 디렉터리의 파일을 여러 서버로 scp로 원격복사 합니다.

each host is delimited by comma(,).

"넘길서버"는 쉼표(,)로 구분됩니다.

Example) ssend.sh 1,2 ssend.sh

If the env GROUP="GROUP" and GROUPPORT="22" in the ssend.sh, ssend.sh file on the current directory will be copied to GROUP1 and GROUP2 with 22 SSH port.

ssend.sh의 환경변수 GROUP="GROUP"이고 GROUPPORT="22"라면 현재 디렉터리의 ssend.sh 파일을 GROUP1, GROUP2로 원격복사합니다.
GROUP1, GROUP2에 22번 포트로 ssh를 접속하여 원격실행을 하게 됩니다.

## automv.sh
**Usage - 사용법**

automv.sh file

automv.sh 파일

**Description - 설명**

If the file size is equal or greater than LIMIT bytes, mv file file.HHMM

파일이 LIMIT 바이트 이상이 되면 그 파일을 파일.시분로 이동(move) 합니다.