참고주소 : [https://stackoverflow.com/questions/6473679/transpose-list-of-lists](https://stackoverflow.com/questions/6473679/transpose-list-of-lists)

# 리스트의 리스트를 transpose(전치행렬)

다음과 같은 행렬이 있습니다.

```python
l = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
```

제가 찾고 있는 결과는 다음과 같습니다.

```python
r = [[1, 4, 7], [2, 5, 8], [3, 6, 9]]
```

다음 결과는 아닙니다.

```python
r = [(1, 4, 7), (2, 5, 8), (3, 6, 9)]
```

매우 감사합니다.

## 13개 답변 중 1개

Python 3 :

```python
# 테이블(행렬)이 들쭉날쭉한 경우 가장 짧은 내부 리스트 개수만큼 단락:
list(map(list, zip(*l)))

# 들쭉날쭉한 경우 데이터를 버리지 않고 내부 리스트를 None으로 채웁니다
list(map(list, itertools.zip_longest(*l, fillvalue=None)))
```

Python 2 :

```python
map(list, zip(*l))
```

```python
[[1, 4, 7], [2, 5, 8], [3, 6, 9]]
```

설명:

진행 상황을 이해하기 위해 알아야 할 두 가지 사항이 있습니다.

1.  [zip](https://docs.python.org/3/library/functions.html#zip)의 시그너쳐: `zip(*iterables)` 이것은 zip이 각각 반복 가능한 임의의 개수의 인수를 예상한다는 것을 의미합니다. 예를 들어 `zip([1, 2], [3, 4], [5, 6])`.
2.  [unpack 된 인수 목록](https://docs.python.org/2/tutorial/controlflow.html#unpacking-argument-lists): 인수 `args`의 시퀀스가 주어지면 `f(*args)`는 `args`의 각 요소가 `f`의 개별 위치 인수가 되도록 f를 호출합니다.
3.  `itertools.zip_longest`는 내부 리스트의 요소 수가 동일하지 않은 경우(동종) 데이터를 버리지 않고 대신 더 짧은 중첩 목록을 채운 _다음_ zip 처리를 합니다.

질문 `l = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]`의 입력으로 돌아가면 `zip(*l)`은 `zip([1, 2, 3], [4, 5, 6], [7, 8, 9])`와 같습니다. 나머지는 결과가 튜플의 리스트 대신 리스트의 리스트인지 확인하는 것입니다.
