출처 : [https://stackoverflow.com/questions/4585929/how-to-use-cp-command-to-exclude-a-specific-directory](https://stackoverflow.com/questions/4585929/how-to-use-cp-command-to-exclude-a-specific-directory)

# 특정 디렉터리를 제외하고 'cp' 명령 사용하는 방법

저는 특정 하위 디렉터리(sub-directory)에서 몇 개의 파일을 제외하고 그 디렉터리의 모든 파일을 복사하고 싶습니다. 저는 `cp` 명령어는 `--exclude` 옵션이 없는 것을 알게 되었습니다. 이를 어떻게 할 수 있을까요?

## 19개 답변 중 1개

`rsync`는 빠르고 쉽습니다.

```shell
rsync -av --progress sourcefolder /destinationfolder --exclude thefoldertoexclude
rsync -av --progress 원본폴더 /대상폴더 --exclude 제외할폴더
```

당신은 `--exclude`를 여러번 사용할 수 있습니다.

```shell
rsync -av --progress sourcefolder /destinationfolder --exclude thefoldertoexclude --exclude anotherfoldertoexclude
rsync -av --progress 원본폴더 /대상폴더 --exclude 제외할폴더 --exclude 또다른제외할폴더
```

**`--exclude` 뒤의 폴더에 `thefoldertoexclude` 디렉터리는 `sourcefolder`, 즉 `sourcefolder / thefoldertoexclude`와 관련이 있습니다.**

또한 실제 명령을 실행하기 전에 무엇이 복사되는 지 보기만 하려면 `-n`을 추가할 수 있고 괜찮다면 명령줄에서 `-n`을 제거하면 됩니다.

