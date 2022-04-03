출처 : [https://stackoverflow.com/questions/4770297/python-convert-utc-datetime-string-to-local-datetime](https://stackoverflow.com/questions/4770297/python-convert-utc-datetime-string-to-local-datetime)

# 파이썬 - UTC 날짜시간 문자열을 local 날짜시간으로 변환하기

저는 시간과 utc간에 변환 할 필요가 없었습니다. 최근에 제 앱이 시간대를 인식하도록 요청이 있었으며 스스로 알아보고 있었습니다. 현지 시간을 utc로 변환하는 방법에 대한 많은 정보를 얻었는데 초보자 (어쩌면 그렇게 잘못하고 있는 것 같습니다)라 최종 사용자 시간대로 UTC 시간을 쉽게 변환하는 방법에 대한 정보는 찾을 수 없습니다.

간단히 말해서 android app은 나를 (appengine app) 데이터로 보내고 그 데이터는 타임 스탬프입니다. 해당 타임 스탬프를 utc 시간으로 저장하기 위해 다음을 사용하였습니다.

```python
datetime.utcfromtimestamp(timestamp)
```

이는 효과가 있는 것 같습니다. 내 앱이 데이터를 저장하면 5시간 이전 시간으로 저장됩니다. (저는 EST-5 시간대에 있습니다.)

데이터가 appengine의 BigTable에 저장되고 검색되면 다음과 같은 문자열로 나타납니다.

```
"2011-01-21 02:37:21"
```

이 문자열을 사용자의 올바른 시간대에있는 DateTime으로 변환하려면 어떻게 해야 합니까?

또한, 사용자 시간대 정보가 주로 저장됩니까? tz(시간대) 정보를 일반적으로 어떻게 저장합니까? (예시: "-5:00" 또는 "EST" 등등?) 첫 번째 질문에 대한 대답에는 두 번째 대답에 포함될 수 있습니다.

---

## 16 개의 답변 중 1 개의 답변

당신이 `tzinfo` 객체를 제공하기 원하지 않으면 [python-dateutil](http://niemeyer.net/python-dateutil) 라이브러리를 확인하시면 됩니다. 시간대에 대한 표준명으로 시간대 규칙을 참조할 수 있도록 [zoneinfo (Olson) 데이터베이스](https://en.wikipedia.org/wiki/Tz_database) 위에 `tzinfo` 구현을 제공합니다.

```python
from datetime import datetime
from dateutil import tz

# 방법 1: 시간대 하드코딩:
from_zone = tz.gettz('UTC')
to_zone = tz.gettz('America/New_York')

# 방법 2: 시간대 자동감지:
from_zone = tz.tzutc()
to_zone = tz.tzlocal()

# utc = datetime.utcnow()
utc = datetime.strptime('2011-01-21 02:37:21', '%Y-%m-%d %H:%M:%S')

# 날짜시간 객체가 기본으로 '순수'하기 때문에  
# UTC 시간대에 있다고 날짜시간 객체에게 알려줍니다.
utc = utc.replace(tzinfo=from_zone)

# 시간대를 변환합니다.
central = utc.astimezone(to_zone)
```

**편집** `strptime` 사용법을 보여주기 위해 예제를 확장함

**편집2** 더 좋은 entry point 메소드를 보여주기 위해 API 사용법을 수정

**편집3** 시간대 (Yarin)를 자동 감지하기 위한 메소드 포함
