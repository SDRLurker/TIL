**출처**

[https://www.baeldung.com/linux/redirect-output-of-running-process](https://www.baeldung.com/linux/redirect-output-of-running-process)

# 이미 실행중인 프로세스의 출력을 redirecting 하기

## 1. 소개

Linux에서 작업할 때 [redirections](https://www.baeldung.com/linux/pipes-redirection)을 사용하는 것은 매우 일반적입니다. 예를 들어, 프로그램을 실행할 수 있고 실행시 [생성되는 출력을 침묵](https://www.baeldung.com/linux/silencing-bash-output)시킬 수 있습니다. 그러나 **bash는 프로세스가 실행되면 출력을 redirection 하는 직접적인 방법을 제공하지 않습니다.** 따라서 이 글에서는 실행중인 프로세스의 출력을 리디렉션하거나 복사하는 다양한 방법에 대해 알아 봅니다. 예를 들어 프로그램을 실행할 때 출력을 리디렉션하는 것을 잊은 경우 유용 할 수 있습니다.

이를 위해 첫 번째는 [gdb](https://man7.org/linux/man-pages/man1/gdb.1.html)를 사용하고 다른 하나는 [strace](https://man7.org/linux/man-pages/man1/strace.1.html)를 사용하며 마지막으로 세 번째는 [screen](https://www.baeldung.com/linux/screen-command)을 사용하는 세 가지 방법을 살펴 보겠습니다. 세 번째 접근 방식은 약간 다릅니다.
