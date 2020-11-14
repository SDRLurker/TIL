출처 : [https://stackoverflow.com/questions/3241086/how-to-schedule-to-run-first-sunday-of-every-month](https://stackoverflow.com/questions/3241086/how-to-schedule-to-run-first-sunday-of-every-month)

# 매달 첫 번째 일요일에 crontab 스케쥴링 하는 방법

저는 Redhat에서 Bash를 사용하고 있습니다. 저는 매달 첫 번째 일요일 오전 9시에 cron job을 스케쥴링 하고 싶습니다. 이를 어떻게 할 수 있을까요?

---

## 10개의 답변 중 1개를 추려냄

`crontab` 파일에서 이를 다음처럼 할 수 있습니다.

```
00 09 * * 7 [ $(date +\%d) -le 07 ] && /run/your/script
```

`date +%d` 는 오늘의 날짜 중 몇일인지 제공하며, 몇일이 7보다 작거나 같은지 검사할 수 있습니다. 그렇다면 당신의 명령을 실행합니다.

만약 이 스크립트가 일요일에만 실행되기를 원하면, 그 달의 첫번째 일요일에만 실행된다는 뜻이 됩니다.

`crontab` 파일에서 `date` 명령의 포멧팅 옵션은 escape 처리가 되어야 합니다.
