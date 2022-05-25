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
