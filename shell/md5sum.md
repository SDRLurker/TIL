**출처**

[https://stackoverflow.com/questions/39604202/listing-md5sum-for-all-files](https://stackoverflow.com/questions/39604202/listing-md5sum-for-all-files)

## 모든 파일 md5sum 목록 보여주기

저는 파일의 목록과 이들의 md5sum를 생성하는 아래 명령어를 사용하려 합니다. 문제는 이름에 몇개 파일이나 폴더에서 공백이 있습니다. 어떻게 이를 다룰 수 있을까요?

```shell
find -type f -name \* | xargs md5sum
```

---

### 2개의 답변 중 1개의 답변

다음을 수행해 보세요.

```shell
find . -type f -exec md5sum {} +
```

이 명령어로 `find`는 찾은 파일로 `md5sum`을 실행할 것입니다.

**MacOS:** [MacOS find man page](https://stackoverflow.com/questions/39604202/listing-md5sum-for-all-files)에 따르면, `find`는 `+`옵션을 지원하지 않습니다. 대신에 덜 효율적인 (아래 3을 봐주세요.) 형식이 요구됩니다.

```shell
find . -type f -exec md5sum {} \;
```

노트:

1. `-name \`는 모든 파일을 `find`로 찾으라고 합니다. 이는 기본값이기 때문에 그것을 지정할 필요는 없습니다.
2. 이름에 공백이 있는 것은 현대적인 파일 이름에 보편적인 현상입니다. 사실, 파일 이름에 개행이 있을 수도 있습니다. 결국, `xargs`는 `-0` 또는 `--null` 옵션으로 NULL로 구분된 입력을 사용하지 않는다면 일반적으로 안전하지 않습니다. 이는 find의 `-print0`으로 NULL로 구분된 출력을 생성한다고 `find`를 실행하기 위해 결합되어 사용될 수 있습니다. 하지만 `-exec`는 어려운 파일 이름에도 안전하게 `xargs`를 실행할 수 있도록 최선의 것으로 실행하며, `-exec` 형식이 주로 선호됩니다.
3. 만약 형식 `-exec md5sum {} \;`를 사용하였다면, `find`는 각 파일이 찾아질 때마다 `md5sum`을 실행할 것입니다. 형식 `-exec md5sum {} +`는 반면 명령어 줄에서 많은 파일 이름으로 사용될 수 있습니다. 이는 시작해야 될 프로세스의 수를 줄일 수 있습니다.

### 예시

위의 명령어의 샘플 출력입니다.

```shell
$ find . -type f -exec md5sum {} +
e75632e8a11db7513c2a9f25cb6c9627  ./file1
004dedba9b67f3a93924db548fd4d6ef  ./file2
48645402a2cf6ada3548ad69d8d906db  ./dir1/file1
6a182d8fe659c067897be7fde72903ea  ./dir1/file2
```

**추가 참고자료**

* [리눅스]] md5sum: 무결성 확인 명령어
  * [https://bio-info.tistory.com/47](https://bio-info.tistory.com/47)
