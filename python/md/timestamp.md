출처 : [https://stackoverflow.com/questions/19231871/convert-unix-time-to-readable-date-in-pandas-dataframe](https://stackoverflow.com/questions/19231871/convert-unix-time-to-readable-date-in-pandas-dataframe)

# Unix 시간을 pandas dataframe에서 읽을 수 있는 날짜로 변환하기

저는 Unix 시간으로 가격이 포함된 dataframe이 있습니다. 사람이 읽을 수 있는 날짜로 표시되도록 index 열을 변환하고 싶습니다.

예를 들어 index 열에서 `date`는 `1349633705`가 있지만 그것이 `10/07/2012`(또는 적어도 `10/07/2012 18:15`)로 보여지길 원합니다.

다음 구문에서 내가 작업 중인 코드와 이미 시도한 코드는 다음과 같습니다.

```
import json
import urllib2
from datetime import datetime
response = urllib2.urlopen('http://blockchain.info/charts/market-price?&format=json')
data = json.load(response)   
df = DataFrame(data['values'])
df.columns = ["date","price"]
#convert dates 
df.date = df.date.apply(lambda d: datetime.strptime(d, "%Y-%m-%d"))
df.index = df.date
```

보시다시피 `df.date = df.date.apply(lambda d: datetime.strptime (d, "%Y-%m-%d"))` 여기에서 문자열이 아닌 정수로 작동하지 않습니다. 나는 `datetime.date.fromtimestamp`를 사용해야 한다고 생각하지만 이것을 `df.date` 전체에 적용하는 방법을 잘 모르겠습니다.

감사합니다.

---

## 4 개의 답변 중 1 개의 답변만 추려냄.

다음은 epoch 이후 초단위로 보일 것입니다.

```
In [20]: df = DataFrame(data['values'])

In [21]: df.columns = ["date","price"]

In [22]: df
Out[22]: 
<class 'pandas.core.frame.DataFrame'>
Int64Index: 358 entries, 0 to 357
Data columns (total 2 columns):
date     358  non-null values
price    358  non-null values
dtypes: float64(1), int64(1)

In [23]: df.head()
Out[23]: 
         date  price
0  1349720105  12.08
1  1349806505  12.35
2  1349892905  12.15
3  1349979305  12.19
4  1350065705  12.15
In [25]: df['date'] = pd.to_datetime(df['date'],unit='s')

In [26]: df.head()
Out[26]: 
                 date  price
0 2012-10-08 18:15:05  12.08
1 2012-10-09 18:15:05  12.35
2 2012-10-10 18:15:05  12.15
3 2012-10-11 18:15:05  12.19
4 2012-10-12 18:15:05  12.15

In [27]: df.dtypes
Out[27]: 
date     datetime64[ns]
price           float64
dtype: object
```
