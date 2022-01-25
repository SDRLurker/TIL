출처 : [https://stackoverflow.com/questions/11873714/how-do-i-get-new-york-city-time](https://stackoverflow.com/questions/11873714/how-do-i-get-new-york-city-time)

# 뉴욕 도시 시간을 어떻게 얻을 수 있습니까?

저는 자주 여행하지만 뉴욕 도시에 살고 있고 제가 어디 있던지 상관없이 뉴욕 시간을 표시하려고 하고 있습니다. 저는 이를 Python에서 어떻게 할 수 있을까요? 저는 다음 코드가 있습니다만 작동하지 않고 다음 오류가 발생하였습니다.

```shell
`'module' object is not callable` 
```

또한, 저는 아래 저의 메소드가 일광절약시간(서머타임)으로 정확하게 갱신될 것인지 확실하지 않습니다.

```python
import pytz
utc = pytz.utc
utc_dt = datetime(2002, 10, 27, 6, 0, 0, tzinfo=utc)
eastern = pytz.timezone('US/Eastern')
loc_dt = utc_dt.astimezone(eastern)
fmt = '%Y-%m-%d %H:%M:%S %Z%z'
loc_dt.strftime(fmt)
```

## 4개의 답변 중 1개의 답변

`datetime` 대신에 `datetime.datetime`으로 작성하세요:

```python
import datetime
import pytz

utc = pytz.utc
utc_dt = datetime.datetime(2002, 10, 27, 6, 0, 0, tzinfo=utc)
eastern = pytz.timezone('US/Eastern')
loc_dt = utc_dt.astimezone(eastern)
fmt = '%Y-%m-%d %H:%M:%S %Z%z'
loc_dt.strftime(fmt)
```

모듈 [datetime](https://docs.python.org/3/library/datetime.html)은 클래스 [`datetime.datetime`](https://docs.python.org/3/library/datetime.html#datetime.datetime)을 포함하고 있기 때문입니다.
