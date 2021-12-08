**출처**

[https://stackoverflow.com/questions/44882063/replace-all-values-with-nan-in-the-dataframe-in-pandas](https://stackoverflow.com/questions/44882063/replace-all-values-with-nan-in-the-dataframe-in-pandas)

# pandas의 데이터프레임에서 NaN으로 모든 값 대체하기

다음과 같은 작은 데이터프레임(df)이 있습니다.

```
unique  a     b     c     d 
  0    None  None  None  None
  1    None  None  None  None
  2    None  0132  None  None
  3    None  None  None  0231
  4    None  None  None  None
  5    None  None  0143  None
  6    0121  None  None  None
  7    None  None  None  0432
```

저는 모든 값을 NaN으로 대체해야 합니다. 저는 `df.fillna(np.NAN)`의 적용을 시도해 보았지만, 셀에서 숫자가 있는 곳의 값이 변하지 않았습니다. 어떻게 모든 값이 교체되도록 할 수 있을까요? 데이터프레임은 다음처럼 보여야 합니다.

```
unique  a     b     c     d 
  0    NaN   NaN   NaN   NaN
  1    NaN   NaN   NaN   NaN
  2    NaN   NaN   NaN   NaN
  3    NaN   NaN   NaN   NaN
  4    NaN   NaN   NaN   NaN
  5    NaN   NaN   NaN   NaN
  6    NaN   NaN   NaN   NaN
  7    NaN   NaN   NaN   NaN
```

---

## 3 개의 답변 중 1 개의 답변

`np.nan`을 대입하기 위해 `loc`를 사용합니다.

```python
df.loc[:] = np.nan
```

`iloc`도 잘 작동합니다.

```python
df.iloc[:] = np.nan
```
