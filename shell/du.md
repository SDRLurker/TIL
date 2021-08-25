출처 : [http://serverfault.com/questions/62411/how-can-i-sort-du-h-output-by-size](http://serverfault.com/questions/62411/how-can-i-sort-du-h-output-by-size)

# 크기로 du -h의 출력을 정렬할 수 있나요?

저는 du 출력을 사람이 읽을 수 있는 목록으로 얻고 싶습니다.

하지만, `du`는 "크기로 정렬"이란 옵션이 없기 때문에 `sort`로 pipe하는 것은 사람이 읽을 수 있도록 하는 플래그(human readable flag)가 작동하지 않습니다. 

예를 들어 다음 명령을 실행하면 

```shell
du | sort -n -r 
```

(내림차순으로) 크기로 정렬된 디스크 사용량이 출력됩니다.

```shell
du |sort -n -r
65108   .
61508   ./dir3
2056    ./dir4
1032    ./dir1
508     ./dir2
```

하지만, 사람이 읽을 수 있도록 하는 플래그(human readable flag)를 사용하여 실행하면 적절하게 정렬되지 않습니다. 

```shell
du -h | sort -n -r
508K    ./dir2
64M     .
61M     ./dir3
2.1M    ./dir4
1.1M    ./dir1
```

크기로 `du -h`를 정렬하는 방법을 아시는 분이 계신가요?

----

## 39 개의 답변 중 1개의 답변

2009년 8월에 [GNU coreutils 7.5](http://article.gmane.org/gmane.comp.gnu.core-utils.announce/52) 가 나오고 부터, `du -h`와 같은 종류의 접미사를 사용하는 `-h` 파라미터를 `sort`에서 쓸 수 있습니다.

```shell
du -hs * | sort -h
```

만약 `-h`를 지원하지 않는 sort를 사용하신다면, GNU Coreutils를 설치할 수 있습니다. 다음은 오래된 Mac OS X 입니다.

```shell
brew install coreutils
du -hs * | gsort -h
```

다음은 `sort` [매뉴얼](https://linux.die.net/man/1/sort) 내용입니다.

`-h, --human-numeric-sort 사람이 읽을 수 있는 숫자를 비교한다 (예시, 2K 1G)`
