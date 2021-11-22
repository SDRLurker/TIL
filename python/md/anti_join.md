**출처**

* [https://stackoverflow.com/questions/38242368/anti-merge-in-pandas-python](https://stackoverflow.com/questions/38242368/anti-merge-in-pandas-python)
* [https://gist.github.com/sainathadapa/eb3303975196d15c73bac5b92d8a210f](https://gist.github.com/sainathadapa/eb3303975196d15c73bac5b92d8a210f)

# Pandas에서 "안티조인, Anti-merge" (Python)

두 개의 데이터프레임에서 같은 이름의 컬럼들 사이에 차이를 선택할 수 있을까요? 제 의미는 X라는 컬럼으로 데이터프레임 A가 있고 X라는 컬럼으로 데이터프레임 B가 있을 때 다음 `pd.merge(A, B, on=['X'])`을 하면 A와 B의 공통된 X 값을 얻을 수 있습니다만 "공통적이지 않은" 것을 얻을 수 있을까요?

---

## 2 개의 답변 중 1 개의 답변

당신은 merge type을 `how='outer'` 및 `indicator=True`로 변경할 수 있고 이는 왼쪽/양쪽/오른쪽에만 값들이 있다는 것을 말해주는 컬럼을 추가할 것입니다.

```python
In [2]:
A = pd.DataFrame({'x':np.arange(5)})
B = pd.DataFrame({'x':np.arange(3,8)})
print(A)
print(B)
   x
0  0
1  1
2  2
3  3
4  4
   x
0  3
1  4
2  5
3  6
4  7

In [3]:
pd.merge(A,B, how='outer', indicator=True)

Out[3]:
     x      _merge
0  0.0   left_only
1  1.0   left_only
2  2.0   left_only
3  3.0        both
4  4.0        both
5  5.0  right_only
6  6.0  right_only
7  7.0  right_only
```

`_merge` 컬럼에서 머지된 df 결과를 필터처리 할 수 있습니다.

```python
In [4]:
merged = pd.merge(A,B, how='outer', indicator=True)
merged[merged['_merge'] == 'left_only']

Out[4]:
     x     _merge
0  0.0  left_only
1  1.0  left_only
2  2.0  left_only
```

또한 `isin`을 사용하여 not 연산으로 B에 있지 않은 값들을 찾을 수 있습니다.

```python
In [5]:
A[~A['x'].isin(B['x'])]

Out[5]:
   x
0  0
1  1
2  2
```
