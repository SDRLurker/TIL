출처 : [http://stackoverflow.com/questions/60208/replacements-for-switch-statement-in-python](http://stackoverflow.com/questions/60208/replacements-for-switch-statement-in-python)

# 파이썬에서 switch 구문을 대체하여 사용하는 방법

입력 index의 값을 기본으로 하여 다른 고정된 값을 리턴하는 파이썬의 함수를 작성하고 싶습니다.

다른 언어에서 저는 switch 또는 case 구문을 사용하고 싶지만 파이썬은 구문을 가지고 있는 것처럼 보이지 않습니다. 이러한 경우 파이썬에서 추천할만한 해결책은 무엇이 있습니까?

---

## 30 개의 답변 중 2개의 답변만 추려냄.

원래 아래 답변은 2008년에 작성했습니다. 그 이후로 Python 3.10 (2021)은 Python에서 "switch"의 일류 구현을 제공하는 `match`-`case` 구문을 소개하였습니다. 예를 들면 다음처럼 작성가능합니다.

```python
def f(x):
    match x:
        case 'a':
            return 1
        case 'b':
            return 2
```


`match`-`case` 문은 이 간단한 예제보다 훨씬 더 강력합니다.

당신은 dictionary를 사용할 수 있습니다. 

```python
def f(x):
    return {
        'a': 1,
        'b': 2,
    }[x]
```

---

만약 당신이 default를 사용하고 싶다면, dictionary의 get(key[, default]) 메소드를 사용할 수 있습니다.

```python
def f(x):
    return {
        'a': 1,
        'b': 2,
    }.get(x, 9)    # 9 is default if x not found
```

---

위의 답변과 관련하여 

http://www.tutorialspoint.com/python/dictionary_get.htm

위 사이트에서 dictionary get 메소드에 대한 내용을 확인하실 수 있습니다.
