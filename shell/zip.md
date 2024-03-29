출처 : [https://www.educba.com/zip-command-in-linux/](https://www.educba.com/zip-command-in-linux/)

# Linux에서 Zip 명령 소개

ZIP은 Unix에서 파일 압축 기술 패키징 유틸리티 입니다. 파일은 확장자 .zip으로 하나의 파일에 저장됩니다. Linux에서 Zip 명령은 MSDOS, OS/2, Window NT, Minix, 매킨토시 등과 같은 거의 모든 운영 체제에 의해 주로 지원됩니다. 압축과 tar는 명령어로 사용되며 PKZIP(MSDOS 시스템을 위한 Phil Katz의 ZIP)와 호환됩니다.

**문법:**

```shell
zip [몇가지 옵션]  zip파일 파일디렉터리/파일목록
```

zip파일은 새로운 혹은 이미 존재하는 zip 압축이며 파일디렉터리/파일목록은 [와일드카드](https://www.educba.com/oracle-wildcards/)를 포함하는 경로입니다. zip 압축과 같은 이름이 발견되면 그것을 갱신할 것입니다.

**예시:** 만약 폴더/파일_1과 폴더/파일_2가 folder.zip에 포함되어 있고 디렉터리 폴더는 폴더/파일_1과 폴더/파일_3을 포함 합니다. 그렇다면 명령을 실행하기 전에 folder.zip은 다음 파일이 있습니다.

* 폴더/파일_1
* 폴더/파일_2

디렉터리 폴더는 다음 파일이 있습니다.

* 파일_1
* 파일_3

명령을 실행하면 folder.zip은 다음 파일을 가지고 있습니다.

* 폴더/파일_1
* 폴더/파일_2
* 폴더/파일_3

폴더/파일_1은 교체되며 폴더/파일_3은 새로운 파일입니다. 그래서, folder.zip은 폴더/파일_1, 폴더/파일_2, 폴더/파일_3을 포함하며 폴더/파일_2는 이전과 변화되지 않습니다.

## Linux에서 Zip 파일 압축풀기 문법

명령은 [Unix 시스템](https://www.educba.com/what-is-unix/)에서 압축으로부터 파일의 압축을 풉니다. 아무런 옵션이 없을 때 특정 ZIP 압축파일로부터 현재 작업 디렉터리(와 그 안에 하위 디렉터리)로 그것의 압축을 풉니다.

문법:

```shell
$unzip fold.zip
```

## Linux에서 Zip 명령 옵션

이제 아래의 Zip 명령 옵션에 집중해 봅시다.

### 1) -u 옵션

파일을 갱신합니다. 이는 압축에서 기존 엔트리도 갱신하며 이미 있는 버전보다 더 많이 수정된 경우에만 갱신됩니다.

**명령:**

```shell
$zip -u 파일명.zip 파일.txt
```

현재 디렉터리에 아래와 같은 파일이 있다 가정합니다.

* 파일1.txt
* 파일2.txt
* 파일3.txt
* 파일4.txt

**문법:**

```shell
$zip –u 파일명.zip 파일5.txt
```

파일명.zip으로부터 파일5.txt를 갱신한 후, unzip 명령으로 파일을 복구할 것입니다.

**명령:**

```shell
$unzip file_name.zip
$ls command
```

**출력:**

* 파일1.txt
* 파일2.txt
* 파일3.txt
* 파일4.txt
* 파일5.txt

파일5.txt는 zip로 갱신되었습니다.

### 2) -d 옵션

zip 압축으로부터 파일을 삭제합니다. 이 옵션은 생성된 파일을 삭제합니다. 현재 디렉터리에 다음 파일이 있습니다.

**문법:**

```shell
$zip –u 파일명.zip 파일.txt
```

**명령:**

```shell
$zip –d file_name.zip file5.txt
```

파일명.zip으로부터 파일5.txt를 삭제한 후 unzip 명령으로 파일을 복구할 것입니다.

**명령:**

```shell
$unzip file_name.zip
$ls command
```


**출력:**

* 파일1.txt
* 파일2.txt
* 파일3.txt
* 파일4.txt
* 파일5.txt

파일5.txt는 zip 파일로부터 삭제되었습니다.

### 3) -m 옵션

zip 압축 후에 original/main 파일을 삭제할 것입니다. zip을 만들고 원래 파일/폴더를 삭제함으로써 파일을 옮길 것입니다.

디렉터리가 파일을 삭제한 후 비어 있으면 해당 디렉토리도 함께 삭제됩니다. zip이 오류 없이 압축을 생성할 때까지 삭제가 완료되지 않습니다. 따라서 이것은 디스크 공간을 유지하는 데 유용하지만 모든 입력 파일을 제거하는 동안 궁극적으로 안전하지 않습니다.

**문법:**

```shell
$zip –m 파일명.zip 파일.txt
```

아래처럼 현재 디렉터리에 다음 파일이 있다고 가정합니다.

파일_1.txt,파일_2.txt,파일_3.txt,파일_4.txt


**명령:**

```shell
$zip -m 파일명.zip *.txt
```

터미널의 이 명령을 실행한 후에 결과는 다음과 같습니다.

**명령:**

```shell
$ls command
```

**출력:**

* 파일명.zip
* // txt(확장자)인 다른 파일은 발견되지 않습니다.

### 4) -x 옵션

zip을 만들 때 파일을 제외합니다. 현재 디렉토리에 있는 모든 파일을 압축하고 필요하지 않은 몇 개의 파일을 제외하려고 한다고 가정해 보겠습니다. 따라서 -x 옵션을 사용하여 필요하지 않은 이러한 파일을 제외할 수 있습니다.

**문법:**

```shell
$zip –m 파일명.zip 제외될파일.txt
```

아래처럼 현재 디렉터리에 다음 파일이 있다고 가정합니다.

파일_1.txt,파일_2.txt,파일_3.txt,파일_4.txt

**명령:**

```shell
$zip –x 파일명.zip 파일_3.txt
```

이 명령은 파일_3.txt 제외하고 모든 파일을 압축할 것입니다.

**명령:**

```shell
$ls command
```

**출력:**

* 파일명.zip // 압축된 파일
* 파일_3.txt // 압축하는 동안 제외된 파일

### 5) -r 옵션

재귀적으로 zip을 그 안에 폴더까지 만들 것입니다.

**문법:**

```shell
$zip –r 파일명.zip 디렉터리명
```

아래처럼 현재 디렉터리 (doc)에 다음 파일이 있다고 가정합니다.

* a.pdf
* b.pdf
* c.pdf

**명령:**

```shell
$zip –r filedir.zip doc
```

이 명령은 파일_3.txt 제외하고 모든 파일을 압축할 것입니다.


**출력:**

* adding: doc/                  // 디렉터리 압축
* adding: doc/a.pdf    // 첫 번째 파일 압축
* adding: doc/b.pdf // 두 번째 파일 압축
* adding: doc/c.pdf  // 번째 파일 압축

### 6) -v 옵션

자세히(Verbose) 모드 옵션을 사용하여 우리는 분석 정보를 출력할 것입니다. 이 옵션은 압축 도중에 진행상황 표시를 보여주며 zip 구조에 관한 자세한 정보를 요청합니다.

**문법:**

```shell
$zip –v 파일명.zip 파일.txt
```

아래처럼 현재 디렉터리에 다음 파일이 있다고 가정합니다.

파일_1.txt,파일_2.txt,파일_3.txt,파일_4.txt

**명령:**

```shell
$zip -v file1.zip *.txt
```

**출력:**

* adding: file_1.txt     (in=0) (out=0) (stored 0%)
* adding: file_2.txt    (in=0) (out=0) (stored 0%)
* adding: file_3.txt    (in=0) (out=0) (stored 0%)
* adding: file_4.txt    (in=0) (out=0) (stored 0%)

* total bytes=0, compressed=0 -> 0% savings

