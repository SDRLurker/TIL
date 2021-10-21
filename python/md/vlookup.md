출처 : [https://stackoverflow.com/questions/39993238/vlookup-between-2-pandas-dataframes](https://stackoverflow.com/questions/39993238/vlookup-between-2-pandas-dataframes)

# 2개의 Pandas 데이터프레임 간에 vlookup

다음처럼 2개의 Pandas 데이터 프레임이 있습니다.

DF1:

```
Security     ISIN
ABC           I1 
DEF           I2
JHK           I3
LMN           I4
OPQ           I5
```

DF2:

```
ISIN      Value
 I2        100
 I3        200
 I5        300
```

저는 다음처럼 보이는 결국 세번째 데이터프레임을 얻고 싶습니다.

DF3:

```
Security   Value
 DEF       100
 JHK       200
 OPQ       300
```

---

## 2개의 답변 중 1개

당신은 기본으로 inner join에 의해 [`merge`](https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.merge.html)를 사용할 수 있습니다. `how=inner`는 제외되고 두 DataFrames에서 공통 열만 있다면, 당신은 파라미터 `on=ISIN`을 제외할 수 있습니다.

```python
df3 = pd.merge(df1, df2)
#ISIN 열 제거
df3.drop('ISIN', axis=1, inplace=True)
print (df3)
  Security  Value
0      DEF    100
1      JHK    200
2      OPQ    300
```

또는 `df1`로부터 `Series`에 의한 `ISIN` 열을 [`map`](https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.Series.map.html)을 호출할 수 있습니다.

```python
print (df1.set_index('ISIN')['Security'])
ISIN
I1    ABC
I2    DEF
I3    JHK
I4    LMN
I5    OPQ
Name: Security, dtype: object

#df2를 복사하여 새로운 df를 생성
df3 = df2.copy()
df3['Security'] = df3.ISIN.map(df1.set_index('ISIN')['Security'])
#ISIN열을 없앰
df3.drop('ISIN', axis=1, inplace=True)
#열의 순서를 변경
df3 = df3[['Security','Value']]
print (df3)
  Security  Value
0      DEF    100
1      JHK    200
2      OPQ    30
```
