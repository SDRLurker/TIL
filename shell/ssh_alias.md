출처 : [https://askubuntu.com/questions/810098/why-doesnt-my-alias-work-over-ssh](https://askubuntu.com/questions/810098/why-doesnt-my-alias-work-over-ssh)

# alias가 ssh에서 왜 작동하지 않을까요?

저는 `.bashrc`에서 다음과 같이 alias를 가지고 있습니다.

```shell
alias l.='ls -d .* --color=auto'
```

이는 매우 유용합니다 :) 하지만 `ssh`를 통해 작동하지 않습니다.

```shell
$ ssh localhost l.
bash: l.: command not found
```

왜 그럴까요?

---

## 1개의 답변

다음을 시도해 보세요.

```shell
ssh localhost -t bash -lci l.
```

* alias는 당신의 로컬 컴퓨터가 아닌 원격 서버에서 `~/.bashrc`에 있어야 됩니다.
* `-i` 옵션은 `bash`에게 interactive 쉘이라고 알려줍니다. alias는 interactive 쉘에서만 가능합니다.
* `-t` 옵션은 `ssh`에 슈도-터미널(pseudo-tty)을 할당하도록 합니다. 이것이 없으면 `bash`는 interactive 모드를 시작할 때 주의(warning) 메세지를 방출합니다. 이는 `ls`에 칼라를 가능하게 합니다. 이것이 없으면 당신은 `--color=always`를 사용해야 합니다. `man ls`를 확인해주세요.
* interactive flag를 설정하지 않고 alias를 가능하게 하는 다른 방법은 `shopt -s expand_aliases`를 사용하는 것입니다. 다음처럼 시도할 수 있습니다.
* 역자추가 : `-l` 옵션은 bash가 로그인 쉘로 호출된 것처럼 작동하게 합니다.

```shell
ssh localhost 'bash -c "shopt -s expand_aliases; l."'
```

하지만
* `.bashrc`는 쉘 source이 interactive인 경우에만 alias를 정의할 수 있습니다. 이 예시에서 쉘은 interactive 형이 아닙니다.
* 만약 같은 줄에 alias를 정의하려는 경우 [이 항목](https://stackoverflow.com/questions/2501056/cant-get-expand-aliases-to-take-effect)을 참조하세요.
