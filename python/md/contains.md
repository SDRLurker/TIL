출처 : [https://stackoverflow.com/questions/17097643/search-for-does-not-contain-on-a-dataframe-in-pandas](https://stackoverflow.com/questions/17097643/search-for-does-not-contain-on-a-dataframe-in-pandas)

# Pandas 데이터프레임에서 "포함되지 않은 것" 찾기

저는 몇가지 검색을 수행했지만 `df["col"].str.contains(word)`로 데이터프레임을 제외하는 방법을 알 수 없습니다. 하지만, 저는 contains를 반대로 수행할 방법, 즉 데이터프레임의 여집합을 구하는 방법이 있는지 궁금합니다. 예: `!(df["col"].str.contains(word))` 의 효과

`DataFrame` 방법을 통해 할 수 있는 방법이 있을까요?

---

## 6개의 답변

(불리언 데이터에서 not처럼 실행하는) 반전(invert, ~) 연산자를 사용할 수 있습니다.

```python
new_df = df[~df["col"].str.contains(word)]
```

`new_df`는 RHS에 의해 복사되는 곳입니다.

*contains는 정규 표현식도 받아 들입니다.*

---

만약 위에서 ValueError가 발생하면 그 이유는 여러 타입이 섞인 데이터 타입이라 그렇고 `na=False`를 사용합니다.

```python
new_df = df[~df["col"].str.contains(word, na=False)]
```

또는

```python
new_df = df[df["col"].str.contains(word) == False]
```

를 사용합니다.
