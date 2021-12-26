출처 : [http://unix.stackexchange.com/questions/16640/how-can-i-get-the-size-of-a-file-in-a-bash-script](http://unix.stackexchange.com/questions/16640/how-can-i-get-the-size-of-a-file-in-a-bash-script)

# bash script 에서 파일의 크기를 어떻게 구할 수 있을까요?

bash script 에서 파일의 크기를 어떻게 구할 수 있을까요?

이를 bash 변수에 할당하여 나중에 사용하려면 어떻게 해야 할까요?

---

## 13개의 답변 중 1개의 답변

GNU 시스템에서 최선의 선택:

```shell
stat --printf="%s" file.any
```

[man stat](https://linux.die.net/man/1/stat) 에서는

> %s 바이트로 전체 크기

bash script에서는

```shell
#!/bin/bash
FILENAME=/home/heiko/dummy/packages.txt
FILESIZE=$(stat -c%s "$FILENAME")
echo "Size of $FILENAME = $FILESIZE bytes."
```

Mac OS X에서 터미널의 stat 사용하는 방법은 [@chbrown의 답변](https://unix.stackexchange.com/questions/16640/how-can-i-get-the-size-of-a-file-in-a-bash-script/185039#185039)을 보세요.
