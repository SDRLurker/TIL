**출처**

[https://stackoverflow.com/questions/38987/how-do-i-merge-two-dictionaries-in-a-single-expression-take-union-of-dictionari](https://stackoverflow.com/questions/38987/how-do-i-merge-two-dictionaries-in-a-single-expression-take-union-of-dictionari)

# 하나의 표현식으로 (딕셔너리의 합집합을 취하도록) 두 개의 딕셔너리를 어떻게 합치나요?

2개의 파이썬 딕셔너리가 있고 합쳐진 (합집합을 취함) 두 개의 딕셔너리를 리턴하는 하나의 표현식으로 작성하고 싶습니다. `update()` 메소드는 제가 필요한 것을 할 수 있지만, 제 자리에서(in-place) 딕셔너리가 수정되는 결과를 리턴합니다.

```python
>>> x = {'a': 1, 'b': 2}
>>> y = {'b': 10, 'c': 11}
>>> z = x.update(y)
>>> print(z)
None
>>> x
{'a': 1, 'b': 10, 'c': 11}
```

저는 마지막으로 합쳐진 딕셔너리를 `x`가 아닌 `z`에서 얻을 수 있을까요?

(분명하게 하기 위해 `dict.update()`의 최후의 승자 충돌 처리도 제가 찾고 있는 것입니다.)

---

## 55 개의 답변 중 1 개의 답변

`x`와 `y`, `z`는 `y`의 값들이 `x`의 값으로 대체된 얕게 합쳐진 딕셔너리가 됩니다.

* Python 3.9.0(2020년 1월 17일에 발표) 이나 그 이상: [PEP-584](https://www.python.org/dev/peps/pep-0584/), [여기에서 토론됨](https://bugs.python.org/issue36144)은 구현되었고, 가장 간단한 방법을 제공합니다.

```python
z = x | y          # NOTE: 3.9+ ONLY
```

* Python 3.5 이상

```python
z = {**x, **y}
```

* Python 2 또는 (3.4 이하)에서는 함수를 작성합니다

```python
def merge_two_dicts(x, y):
    z = x.copy()   # start with keys and values of x
    z.update(y)    # modifies z with keys and values of y
    return z
```

이제 다음처럼 사용합니다.

```python
z = merge_two_dicts(x, y)
```

### 설명

두 개의 딕셔너리가 있고 원래 딕셔너리를 변경하지 않고 새로운 딕셔너리를 합치기를 원합니다.

```python
x = {'a': 1, 'b': 2}
y = {'b': 3, 'c': 4}
```

원하는 결과는 합쳐진 값을 가진 새로운 딕셔너리(`z`)를 얻는 것이고 두 번째 딕셔너리의 값을 첫 번째로부터 덮어쓰는 것입니다.

```python
>>> z
{'a': 1, 'b': 3, 'c': 4}
```

이것을 위한 새로운 문법은 [PEP 448](https://www.python.org/dev/peps/pep-0448/)에서 제안되었고 [Python 3.5부터 가능합니다](https://mail.python.org/pipermail/python-dev/2015-February/138564.html)

```python
z = {**x, **y}
```

이는 실제로 하나의 표현식입니다.

우리는 리터럴 표기법으로도 합칠 수 있습니다.

```python
z = {**x, 'foo': 1, 'bar': 2, **y}
```

결과는 이제 이렇게 나옵니다.

```python
>>> z
{'a': 1, 'b': 3, 'foo': 1, 'bar': 2, 'c': 4}
```

이제 [3.5, PEP 478의 릴리스 일정](https://www.python.org/dev/peps/pep-0478/#features-for-3-5)에 구현된 것으로 표시되며 이제 [Python 3.5의 새로운 기능](https://docs.python.org/dev/whatsnew/3.5.html#pep-448-additional-unpacking-generalizations) 문서에 포함되었습니다.

그러나 많은 조직이 여전히 Python 2를 사용하고 있으므로 이전 버전과 호환되는 방식으로 이 작업을 수행할 수 있습니다. Python 2 및 Python 3.0-3.4에서 사용할 수 있는 고전적인 Python 방식은 이 작업을 2단계 프로세스로 수행하는 것입니다.

```python
z = x.copy()
z.update(y) # z를 변경하기 때문에 None을 반환합니다.
```

두 접근 방식 모두에서 `y`가 두 번째로 오고 그 값이 `x`의 값을 대체하므로 `b`는 최종 결과에서 `3`을 가리킵니다.

### Python 3.5가 아니자만 *하나의 표현식*을 원할 때

아직 Python 3.5를 사용하지 않거나 이전 버전과 호환되는 코드를 작성해야 하고 이를 *하나의 표현식*으로 원할 경우 가장 성능이 좋고 올바른 접근 방식은 이를 함수에 넣는 것입니다.

```python
def merge_two_dicts(x, y):
    """Given two dictionaries, merge them into a new dict as a shallow copy."""
    z = x.copy()
    z.update(y)
    return z
```

다음과 같은 하나의 표현식이 있습니다.

```python
z = merge_two_dicts(x, y)
```

0에서 매우 큰 숫자까지 임의의 수의 딕셔너리를 합치는 함수를 만들 수도 있습니다.

```python
def merge_dicts(*dict_args):
    """
    Given any number of dictionaries, shallow copy and merge into a new dict,
    precedence goes to key-value pairs in latter dictionaries.
    """
    result = {}
    for dictionary in dict_args:
        result.update(dictionary)
    return result
```

이 함수는 모든 딕셔너리에 대해 Python 2 및 3에서 작동합니다. 예를 들어 `a`부터 `g`까지 딕셔너리가 있습니다.

```python
z = merge_dicts(a, b, c, d, e, f, g) 
```

g의 키-값 쌍은 딕셔너리에서 `a`부터 f까지 우선 적용됩니다.

### 다른 답변에 대한 비판

이전에 채택된 답변에 표시된 내용을 사용하지 마세요.

```python
z = dict(x.items() + y.items())
```

Python 2에서는 각 딕셔너리에 대해 메모리에 두 개의 list 만들고 처음 두 개를 합친 길이와 동일한 길이로 메모리에 세 번째 list를 만든 다음 세 개의 list를 모두 버려 딕셔너리을 만듭니다. **Python 3에서는** 두 개의 list가 아닌 두 개의 `dict_items` 객체를 함께 추가하기 때문에 **실패합니다.**

```python
>>> c = dict(a.items() + b.items())
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: unsupported operand type(s) for +: 'dict_items' and 'dict_items'
```

예를 들어, list로 명시적으로 생성해야 합니다. `z = dict(list(x.items()) + list(y.items()))`. 이것은 자원과 계산 능력의 낭비입니다.

마찬가지로, 값이 해시할 수 없는 객체(예: list)인 경우 Python 3(Python 2.7의 `viewitems()`)에서 `items()`의 합집합을 취하는 것도 실패합니다. 값이 해시 가능하더라도 **집합은 의미적으로 순서가 지정되지 않으므로 우선 순위와 관련하여 동작이 정의되지 않습니다. 따라서 다음과 같이 하지 마십시오.**

```python
>>> c = dict(a.items() | b.items())
```

이 예는 값이 해시 불가능할 때 어떤 일이 발생하는지 보여줍니다.

```python
>>> x = {'a': []}
>>> y = {'b': []}
>>> dict(x.items() | y.items())
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: unhashable type: 'list'
```

다음은 `y`가 우선 순위를 가져야 하는 예입니다. 대신 임의의 집합 순서로 인해 `x`의 값이 유지됩니다.

```python
>>> x = {'a': 2}
>>> y = {'a': 1}
>>> dict(x.items() | y.items())
{'a': 2}
```

당신이 사용하지 말아야 할 다른 예시입니다.

```python
z = dict(x, **y)
```

이것은 `dict` 생성자를 사용하며 매우 빠르고 메모리 효율적입니다(2단계 프로세스보다 약간 더 높음). 그러나 여기서 무슨 일이 일어나고 있는지 정확히 알지 못한다면(즉, 두 번째 dict는 dict에 키워드 인수로 전달됩니다) 생성자), 읽기 어렵고 의도한 사용법이 아니므로 Pythonic하지 않습니다.

다음은 [django에서 수정되는](https://code.djangoproject.com/attachment/ticket/13357/django-pypy.2.diff) 사용법의 예입니다.

딕셔너리는 해시 가능한 키(예: `frozensets` 또는 tuples)를 사용하기 위한 것이지만 **키가 문자열이 아닌 경우 Python 3에서는 이 방법이 실패합니다.**

```python
>>> c = dict(a, **b)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: keyword arguments must be strings
```

[메일링 리스트](https://mail.python.org/pipermail/python-dev/2010-April/099459.html)에서 파이썬 언어의 창시자인 Guido van Rossum은 다음과 같이 썼습니다.

> 결국 ** 메커니즘을 남용하기 때문에 dict({}, **{1:3})를 불법으로 선언한 것입니다.

그리고

> 분명히 dict(x, **y)는 "x.update(y) 호출 및 x 반환"에 대한 "멋진 해킹"으로 돌아갑니다. 개인적인 생각으로는 멋있다기 보다는 촌스럽습니다.

`dict(**y)` 의 의도된 사용법은 가독성을 위해 딕셔너리을 만드는 것입니다. 예를 들어, 

`{'a': 1, 'b': 10, 'c': 11}`

대신에

`dict(a=1, b=10, c=11)`로 이해가 됩니다.



