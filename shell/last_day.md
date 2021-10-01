출처 : [https://unix.stackexchange.com/questions/223543/get-the-date-of-last-months-last-day-in-a-shell-script](https://unix.stackexchange.com/questions/223543/get-the-date-of-last-months-last-day-in-a-shell-script)

# shell 스크립트에서 지난 달의 마지막 날짜 구하기

어떻게 처리할 날짜에 기반하여 지난 달의 날짜를 구할 수 있습니까?

예시:

* 처리할 날짜 = 15jan2015, 2015년 1월 15일
* 예상 날짜 = 31dec2014, 2014년 12월 31일
* 처리할 날짜 = 10feb2015, 2015년 2월 10일
* 예상 날짜 = 31Jan2015, 2015년 1월 31일

------

## 4개의 답변 중 1개의 답변

GNU의 `date`로 

몇 가지 shell은 날짜 조작 지원이 내장되어 있습니다.

```shell
$ date +%d%b%Y
16Aug2015
$ date -d "$(date +%Y-%m-01) -1 day" +%d%b%Y
31Jul2015
```

`ksh93`으로 

```shell
$ printf "%(%d%b%Y)T\n" "1st day, yesterday"
31Jul2015
```

`zsh`로 

```shell
$ zmodload zsh/datetime
$ strftime -s d %Y-%m-01-12 $EPOCHSECONDS
$ strftime -rs d %Y-%m-%d-%H $d
$ strftime %d%b%Y $((d-86400))
31Jul2015
```
