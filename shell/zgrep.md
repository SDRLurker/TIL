출처 : [https://stackoverflow.com/questions/13983365/grep-from-tar-gz-without-extracting-faster-one](https://stackoverflow.com/questions/13983365/grep-from-tar-gz-without-extracting-faster-one)

# 압축해제 없이 tar.gz 파일을 grep 하기 [빠른 방법]

10여개의 .tar.gz 파일로부터 grep 패턴을 시도하였지만 매우 느립니다.
제가 사용한 내용입니다.

```shell
tar -ztf file.tar.gz | while read FILENAME
do
        if tar -zxf file.tar.gz "$FILENAME" -O | grep "string" > /dev/null
        then
                echo "$FILENAME contains string"
        fi
done
```

---

## 11개의 답변 중 1개

`zgrep`이 있다면 다음처럼 사용할 수 있습니다.

```shell
zgrep -a string file.tar.gz
```
