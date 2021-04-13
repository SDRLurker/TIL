**출처**

[https://www.baeldung.com/linux/redirect-output-of-running-process](https://www.baeldung.com/linux/redirect-output-of-running-process)

# 이미 실행중인 프로세스의 출력을 redirecting 하기

## 1. 소개

Linux에서 작업할 때 [redirections](https://www.baeldung.com/linux/pipes-redirection)을 사용하는 것은 매우 일반적입니다. 예를 들어, 프로그램을 실행할 수 있고 실행시 [생성되는 출력을 침묵](https://www.baeldung.com/linux/silencing-bash-output)시킬 수 있습니다. 그러나 **bash는 프로세스가 실행되면 출력을 redirection 하는 직접적인 방법을 제공하지 않습니다.** 따라서 이 글에서는 실행중인 프로세스의 출력을 리디렉션하거나 복사하는 다양한 방법에 대해 알아 봅니다. 예를 들어 프로그램을 실행할 때 출력을 리디렉션하는 것을 잊은 경우 유용 할 수 있습니다.

이를 위해 첫 번째는 [gdb](https://man7.org/linux/man-pages/man1/gdb.1.html)를 사용하고 다른 하나는 [strace](https://man7.org/linux/man-pages/man1/strace.1.html)를 사용하며 마지막으로 세 번째는 [screen](https://www.baeldung.com/linux/screen-command)을 사용하는 세 가지 방법을 살펴 보겠습니다. 세 번째 접근 방식은 약간 다릅니다.

## 2. 출력을 redirect하기 위해 *gdb* 사용하기

*gdb*는 리눅스에서 코드 디버깅을 위한 강력한 도구입니다. 그리고 우리는 실행 중인 프로세스에서 코드를 실행하기 위해 그것을 사용할 수 있습니다. 이 경우, **우리는 실행 중인 프로세스로 *gdb*에 접근(attach)하여 출력을 redirect하기 위해 *gdb* 사용할 것이며 작업을 마쳤을 때 실행중인 프로세서로부터 분리(detach)할 것입니다.**

출력을 redirect하기 위해 우리가 할 수 있는 방법은 현재 파일 descriptor를 닫고 그것을 새로운 출력을 가리키도록 다시 여는 것입니다. 우리는 [open](https://www.man7.org/linux/man-pages/man2/open.2.html)과 [dup2](https://www.man7.org/linux/man-pages/man2/dup.2.html) 함수를 사용하여 이를 할 것입니다.

유닉스 시스템에는 2개의 기본 출력이 있는데, *표준출력*과 *표준에러* 입니다. *표준출력*은 파일 descriptor 1과 관련되어 있고 *표준에러*는 파일 descriptor 2와 관련되어 있습니다. 예를 들어, 표준 출력을 redirect하기 원하면 우리는 파일 descriptor 1에서 활동할 필요가 있습니다.

**우리는 PID 14560인 프로세스의 출력을 redirect하기 원한다고 가정합시다.** 우리는 그 PID로 *gdb*를 접근함으로써 시작해야 합니다.

```shell
$ gdb -p 14560
```

**프로세스로 접근했을 때 우리는 파일 descriptor 1을** /tmp/process_stdout로 **redirect할 수 있습니다.**

```shell
(gdb) p dup2(open("/tmp/process_stdout", 1089, 0777), 1)
```

**우리는 숫자 1089를 사용했는데 이는 *O_WRONY | O_CREAT | O_APPEND*를 표현하기 때문이라는 것을 참고하세요.**

우리는 표준 출력도 같은 방법으로  파일 descriptor 2를 사용함으로써 정확하게 redirect할 수 있습니다.

```shell
(gdb) p dup2(open("/tmp/process_stderr", 1089, 0777), 2)
```

이 시점에서 출력이 redirect 되었습니다. 그러나 프로세스가 중지되고 *gdb*가 그 프로세스에 접근(attach)합니다.

더 이상 gdb가 필요하지 않으므로 프로세스에서 분리하고 *gdb*를 종료할 수 있습니다. 이렇게하면 프로세스가 계속 실행됩니다.

```shell
(gdb) q
```

또는 /dev/null로 redirect 하여 출력을 침묵시킬 수 있습니다.

```shell
(gdb) p dup2(open("/dev/null"), 1)
```

**우리는 모든 *gdb* 명령을 "명령 파일"로 작성할 수 있고 파라미터 -x 를 사용하여 그것을 실행할 수 있습니다.**
*gdb_redirect* 라 불리는 파일을 생성합시다.

```shell
p dup2(open("/tmp/process_stdout", 1089, 0777), 1)
p dup2(open("/tmp/process_stderr", 1089, 0777), 2)
q
```

다음 우리의 파일 *gdb_redirect*를 사용하여 *gdb*를 실행하면 됩니다.

```shell
$ gdb -p 14560 -x gdb_redirect
```

## 3. 모든 쓰기 호출을 검사하는 *strace* 사용하기

우리는 시스템 콜(호출)을 검사하는 *strace*를 사용할 수도 있습니다. 이 방법은 원래 출력을 중단하지 않습니다. 대신 다른 위치로 복사합니다.
PID 14560의 표준 오류로 쓰는 모든 쓰기를 검사합시다.

```shell
$ strace -etrace=write -s 100000 -p 14560 2>&1 | grep --line-buffered '^write(2,'
write(2, "\r", 1)                       = 1
write(2, "17243001856 bytes (17 GB, 16 GiB) copied, 1185 s, 14.6 MB/s", 59) = 59
write(2, "\r", 1)                       = 1
write(2, "17249965568 bytes (17 GB, 16 GiB) copied, 1186 s, 14.5 MB/s", 59) = 59
```

이 파라미터로 *strace*는 *쓰기* 호출과 최대 문자열 크기를 10만으로 설정하여 PID 14560에 접근합니다. 그리고 우리는 파일 descriptor 2로 쓰는 것만 출력하기 위해 [grep](https://www.baeldung.com/linux/common-text-search#the-grep-command) 으로 사용합니다.
우리는 /tmp/process_stderr라 불리는 파일로 이를 redirect할 수 있습니다.

```shell
$ strace -etrace=write -s 100000 -p 14560 2>&1 | grep --line-buffered '^write(2,' > /tmp/process_stderr
```

만약 *strace* 출력 포멧을 좋아하지 않는다면 문자열만 출력하기 위해 [sed](https://www.baeldung.com/linux/redirect-output-of-running-process)를 사용할 수 있습니다.

```shell
$ strace -etrace=write -s 100000 -p 14560 2>&1 | sed -n -r 's/^write\(2,\s*"(.+)",\s*[[:digit:]]+\)\s*=\s*[[:digit:]]+$/\1/p'
\r
25754656256 bytes (26 GB, 24 GiB) copied, 1710 s, 15.1 MB/s
\r
25761747456 bytes (26 GB, 24 GiB) copied, 1711 s, 15.1 MB/s
```

또한 ** *strace*는 16진수와 파일 descriptor로 쓰여진 아스키코드로 출력할 수 있습니다.** 우리는 이를 -ewrite=fd 옵션을 사용하여 할 수 있습니다.

```shell
$ strace -ewrite=2 -etrace=write -p 14560 2>&1 | grep  --line-buffered '^ |'
 | 00000  0d                                                .                |
 | 00000  33 33 30 31 32 30 32 37  39 30 34 20 62 79 74 65  33012027904 byte |
 | 00010  73 20 28 33 33 20 47 42  2c 20 33 31 20 47 69 42  s (33 GB, 31 GiB |
 | 00020  29 20 63 6f 70 69 65 64  2c 20 32 31 36 32 20 73  ) copied, 2162 s |
 | 00030  2c 20 31 35 2e 33 20 4d  42 2f 73                 , 15.3 MB/s      |
 | 00000  0d                                                .                |
 | 00000  33 33 30 31 39 36 32 31  38 38 38 20 62 79 74 65  33019621888 byte |
 | 00010  73 20 28 33 33 20 47 42  2c 20 33 31 20 47 69 42  s (33 GB, 31 GiB |
 | 00020  29 20 63 6f 70 69 65 64  2c 20 32 31 36 33 20 73  ) copied, 2163 s |
 | 00030  2c 20 31 35 2e 33 20 4d  42 2f 73                 , 15.3 MB/s      |
```
