출처 : [https://stackoverflow.com/questions/5537876/get-utc-offset-from-time-zone-name-in-python](https://stackoverflow.com/questions/5537876/get-utc-offset-from-time-zone-name-in-python)

# Python에서 시간대(timezone) 이름으로 UTC 시차(offset) 구하기

How can I get UTC offset from time zone name in python?  
Python에서 시간대(timezone) 이름으로 UTC 시차(offset)을 어떻게 구합니까?  
예시: 저는 Asia/Jerusalem을 통해 +0200을 얻고 싶습니다.  
For example: I have Asia/Jerusalem and I want to get +0200

---

## 미국동부시간으로 한국 시간 구하기 예시

```
import datetime
import pytz

est = datetime.datetime.now(pytz.timezone('America/New_York'))
diff_min = (est.utcoffset().seconds - 86400) // 60 - 540
# ...중략...
# 02:38:00 28.02.20
svr_time = datetime.datetime.strptime(svr_str, "%H:%M:%S %d.%m.%y") - datetime.timedelta(minutes=diff_min)
```

---

## 3개의 답변 중 2개의 답변만 추려냄

pytz 프로젝트와 `utcoffset` 메소드 사용을 시도하신 적 있으신가요?

예시

```
>>> import datetime
>>> import pytz
>>> pacific_now = datetime.datetime.now(pytz.timezone('US/Pacific'))
>>> pacific_now.utcoffset().total_seconds()/60/60
-7.0
```

---

DST(일광절약시간, 서머타임) 때문에 결과는 그 해의 시간에 따라 다릅니다.

```
import datetime, pytz

datetime.datetime.now(pytz.timezone('Asia/Jerusalem')).strftime('%z')

# returns '+0300' (because 'now' they have DST)


pytz.timezone('Asia/Jerusalem').localize(datetime.datetime(2011,1,1)).strftime('%z')

# returns '+0200' (1월에는 DST(일광정약시간, 서머타임)이 아니기 때문입니다)
```
