출처 : [https://note.nkmk.me/en/python-for-enumerate-zip/](https://note.nkmk.me/en/python-for-enumerate-zip/)

# Python에서 enumerate()와 zip() 함께 사용하기

Python에서 `enumerate()`와 `zip()`은 `for` 루프에서 iterable(`list`, `tuple` 등)의 요소들을 iterate하는 데 유용합니다.

* [Python에서 range, enumerate, zip 등과 for 루프](https://note.nkmk.me/en/python-for-usage/)

당신은 `enumerate()`로 인덱스를 얻을 수 있으며, `zip()`으로 여러개의 요소들을 얻을 수 있습니다.

* [Python에서 enumerate(): 리스트에서 요소와 인덱스 얻기](https://note.nkmk.me/en/python-enumerate-start/)
* [Python에서 zip(): 여러개의 리스트에서 요소들 얻기](https://note.nkmk.me/en/python-zip-usage-for/)

이 글은 `enumerate()`외 `zip()`을 함께 사용할 때 참고 사항을 설명합니다.

## enumerate()와 zip() 함께 사용할 때 참고 사항

여러개의 리스트와 인덱스의 요소를 얻고 싶을 때 `enumerate()`와 `zip()`를 함께 사용할 수 있습니다.

이 경우, `for i, (a, b, ...) in enumerate(zip( ... ))`처럼 괄호로 `zip()`의 요소들을 둘러쌓을 필요가 있습니다.

```python
names = ['Alice', 'Bob', 'Charlie']
ages = [24, 50, 18]

for i, (name, age) in enumerate(zip(names, ages)):
    print(i, name, age)
# 0 Alice 24
# 1 Bob 50
# 2 Charlie 18
```

튜플로 `zip()`의 요소들을 받을 수도 있습니다.

```python
for i, t in enumerate(zip(names, ages)):
    print(i, t)
# 0 ('Alice', 24)
# 1 ('Bob', 50)
# 2 ('Charlie', 18)
```

```python
for i, t in enumerate(zip(names, ages)):
    print(i, t[0], t[1])
# 0 Alice 24
# 1 Bob 50
# 2 Charlie 18
```

표준 라이브러리의 itertools 모듈의 `count()`와 `zip()` 함수는 `(i, a, b)` 처럼 nested되지 않은 형태를 생성하는 데 사용될 수 있습니다.

* [Python에서 무한 이터레이터(itertools.count, cycle, repeat)](https://note.nkmk.me/en/python-itertools-count-cycle-repeat/)
