출처 : [https://stackoverflow.com/questions/4260280/if-else-in-pythons-list-comprehension](https://stackoverflow.com/questions/4260280/if-else-in-pythons-list-comprehension)

# 파이썬 list comprehension에서 if/else 구문 사용법?

파이썬에서 어떻게 다음 구문을 할 수 있습니까?

```python
row = [unicode(x.strip()) for x in row if x is not None else '']
```

특별히:

1. 빈 문자열을 None으로 대체하고

2. 함수를 실행하고 싶습니다.

---

## 12 개의 답변 중 1 개의 답변

이렇게 하실 수 있고 순서의 문제입니다.

```python
[ unicode(x.strip()) if x is not None else '' for x in row ]
```

일반적으로,

```python
[f(x) if condition else g(x) for x in sequence]
```

`if` 조건만으로 for list comprehensions을 사용한다면

```python
[f(x) for x in sequence if condition]
```

이는 실제로 다른 언어 구문인 [조건부 표현식](https://docs.python.org/ko/3/reference/expressions.html#conditional-expressions)을 사용하는 데, 그 자체는 list [comprehension 문법](https://docs.python.org/ko/3/reference/expressions.html#displays-for-lists-sets-and-dictionaries)의 일부가 아니며 `if` 뒤에 `for…in` 이 list comprehension이며 원래 데이터(source)를 순회(iterable)하면서 각 요소를 *필터링하는데* (조건부 표현식이) 사용됩니다.

조건식은 2가지 값 중 선택하려는 모든 종류의 상황에서 사용할 수 있습니다. 이 조건식은 [다른 언어에도 존재하는 `?:` 삼항 연산자](https://docs.python.org/ko/3/faq/programming.html#is-there-an-equivalent-of-c-s-ternary-operator) 와 같습니다. 예를 들면

```python
value = 123
print(value, 'is', 'even' if value % 2 == 0 else 'odd')
```

이렇게 사용하실 수 있습니다.
