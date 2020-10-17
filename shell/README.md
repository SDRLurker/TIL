# MarkDown 문서

## 21.md

"crontab에 관한 글에서 '>/dev/null 2>&1'의 뜻이 무엇인가요?"를 번역한 내용입니다.

http://unix.stackexchange.com/questions/163352/what-does-dev-null-21-mean-in-this-article-of-crontab-basics

※ 2>&1.md 파일을 윈도우즈에서는 생성할 수 없어서 파일명을 변경하였습니다.

## all_arguments.md

"bash 쉘 스크립트에서 모든 인수를 전달"를 번역한 내용입니다.

https://stackoverflow.com/questions/4824590/propagate-all-arguments-in-a-bash-shell-script

## boolean.md

"Bash에서 Boolean 연산자( &&, -a, ||, -o )"를 번역한 내용입니다.

https://stackoverflow.com/questions/20449680/boolean-operators-a-o-in-bash

## conf.md

"shell 스크립트에서 설정 파일 (*.conf) 파싱하는 방법"를 번역한 내용입니다.

https://stackoverflow.com/questions/22683269/how-to-parse-a-config-file-conf-in-shell-script

## conf2.md

"shell 스크립트에서 INI 파일 설정값을 얻는 방법은?"를 번역한 내용입니다.

https://stackoverflow.com/questions/6318809/how-do-i-grab-an-ini-value-within-a-shell-script

## contain.md

"Bash에서 문자열이 부분 문자열을 포함하는지 확인하는 방법"를 번역한 내용입니다.

https://stackoverflow.com/questions/229551/how-to-check-if-a-string-contains-a-substring-in-bash

## cronjob.md

"cronjob 문법에서 backtick(`)을 사용하려는데 무엇이 잘못되었습니까?"를 번역한 내용입니다.

https://serverfault.com/questions/84430/whats-wrong-with-my-cronjob-syntax-im-trying-to-use-a-backtick

## getops.md

"Shell Script 튜토리얼 - 팁 Getopts"를 번역한 내용입니다.

https://www.shellscript.sh/tips/getopts/

## not_work.md

"비밀번호가 없는 SSH 로그인이 잘 안됩니다"를 번역한 내용입니다.

https://www.linuxquestions.org/questions/linux-newbie-8/ssh-login-without-password-not-working-4175561052/

## rsync.md

"특정 디렉터리를 제외하고 'cp' 명령 사용하는 방법"를 번역한 내용입니다.

https://stackoverflow.com/questions/4585929/how-to-use-cp-command-to-exclude-a-specific-directory

## sed_even.md

"sed에서 파일의 짝수 줄만 찾아 치환하는 명령"를 번역한 내용입니다.

https://stackoverflow.com/questions/10856447/sed-command-find-and-replace-in-even-lines-of-a-file

## ssh.md

"script에서 ssh로 timeout을 주는 방법?"를 번역한 내용입니다.
https://serverfault.com/questions/59140/how-do-diff-over-ssh

## ssh_diff.md

"ssh로 원격 diff 하는 방법"를 번역한 내용입니다.

https://stackoverflow.com/questions/4936807/how-to-do-ssh-with-a-timeout-in-a-script

## ssh_login.md

"비밀번호가 없는 SSH 로그인이 잘 안됩니다"를 번역한 내용입니다.

https://www.linuxquestions.org/questions/linux-newbie-8/ssh-login-without-password-not-working-4175561052/

## tr.md

"tr을 사용하여 줄바꿈을 공백으로 바꾸기"를 번역한 내용입니다.

https://stackoverflow.com/questions/25826752/using-tr-to-replace-newline-with-space/25826920

## zgrep.md

"압축해제 없이 tar.gz 파일을 grep 하기 [빠른 방법]"를 번역한 내용입니다.

https://stackoverflow.com/questions/13983365/grep-from-tar-gz-without-extracting-faster-one

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

## ssend2.sh
**Usage - 사용법**

ssend2.sh hosts file [path]

ssend2.sh 넘길서버 넘길파일 [넘길위치]

**Description - 설명**

When you can log into B as b from A as a without password, you can use this script for scp files.

A 서버에서 비밀번호 입력 없이 B 서버로 접속할 수 있을 때, 이 스크립트를 scp로 원격복사에 사용할 수 있습니다.


## automv.sh
**Usage - 사용법**

automv.sh file

automv.sh 파일

**Description - 설명**

If the file size is equal or greater than LIMIT bytes, mv file file.HHMM

파일이 LIMIT 바이트 이상이 되면 그 파일을 파일.시분로 이동(move) 합니다.

## sdiff.sh
**Usage - 사용법**

sdiff.sh host file 

sdiff.sh 원격서버 비교할파일

**Description - 설명**

When you can log into B as b from A as a without password, you can diff a local file from a remote(host) file.

A 서버에서 비밀번호 입력 없이 B 서버로 접속할 수 있을 때, 원격서버의 파일과 비교할 수 있습니다.

## install_gradle_centos.sh

CentOS6에서 Gradle 3.4.1을 설치할 수 있는 shell 스크립트입니다.

출처 : https://gist.github.com/parzonka/9371885

## bring.sh
**Usage - 사용법**

bring.sh hosts file [path]

bring.sh 원격서버 원격파일 [가져올위치]

**Description - 설명**

When you can log into B as b from A as a without password, you can use this script for bringing file via scp.

A 서버에서 비밀번호 입력 없이 B 서버로 접속할 수 있을 때, 이 스크립트를 scp로 원격파일을 가져오는 데 사용할 수 있습니다.
