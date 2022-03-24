출처 : [https://stackoverflow.com/questions/2921847/what-does-the-star-and-doublestar-operator-mean-in-a-function-call](https://stackoverflow.com/questions/2921847/what-does-the-star-and-doublestar-operator-mean-in-a-function-call)

# 함수 호출에서 별(\*)과 쌍별(\*\*) 연산자의 의미는 무엇입니까?

다음 코드 같은 `zip(*x)` 또는 `f(**k)`처럼 파이썬에서 `*` 연산자의 의미는 무엇입니까?

1. 그것은 인터프리터로 내부적으로 어떻게 다루어집니까?
2. 그것은 성능에 영향을 줍니까? 빠릅니까 또는 느립니까?
3. 언제 유용하며 언제 그렇지 않습니까?
4. 그것은 함수 선언에서 사용되어야 합니까? 호출할 때 사용되어야 합니까?

---

## 5개 중 1개의 답변

하나의 별 `*`은 시퀀스/컬렉션을 위치 인자로 unpack합니다. 다음처럼 사용할 수 있습니다.

```python
def sum(a, b):
    return a + b

values = (1, 2)

s = sum(*values)
```

이는 튜플을 unpack하여 실제로 다음처럼 실행할 것입니다.

```python
s = sum(1, 2)
```

쌍별 `**`은 같지만 dictionary와 이름있는 인자를 사용합니다.

```python
values = { 'a': 1, 'b': 2 }
s = sum(**values)
```

다음처럼 결합하여 사용도 가능합니다.

```python
def sum(a, b, c, d):
    return a + b + c + d

values1 = (1, 2)
values2 = { 'c': 10, 'd': 15 }
s = sum(*values1, **values2)
```

이는 다음처럼 실행할 것입니다.

```python
s = sum(1, 2, c=10, d=15)
```

또한, 파이썬 문서에서 [4.7.4 인자 목록 언 패킹](https://docs.python.org/ko/3/tutorial/controlflow.html#unpacking-argument-lists) 섹션도 확인하세요.

---

또한 `*x` 및 `**y` 인수를 사용하도록 함수를 정의할 수 있습니다. 이렇게 하면 함수가 선언에 구체적으로 이름이 지정되지 않아도 위치 및/또는 명명된 인수를 원하는 만큼 허용할 수 있습니다.

예시:

```python
def sum(*values):
    s = 0
    for v in values:
        s = s + v
    return s

s = sum(1, 2, 3, 4, 5)
```

`**` 예시입니다.

```python
def get_a(**values):
    return values['a']

s = get_a(a=1, b=2)      # 1을 리턴
```

이는 그들을 선언할 필요 없이 많은 수의 인자를 지정할 수 있도록 합니다.

그리고  결합할 수 있습니다.

```python
def sum(*values, **options):
    s = 0
    for i in values:
        s = s + i
    if "neg" in options:
        if options["neg"]:
            s = -s
    return s

s = sum(1, 2, 3, 4, 5)            # 15를 리턴
s = sum(1, 2, 3, 4, 5, neg=True)  # -15를 리턴
s = sum(1, 2, 3, 4, 5, neg=False) # 15를 리턴
```
