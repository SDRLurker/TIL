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

### 댓글에 대한 응답

> Guido가 말한 것에도 불구하고 `dict(x, **y)`는 btw인 dict 사양과 일치합니다. Python 2와 3 모두에서 작동합니다. 이것이 문자열 키에만 작동한다는 사실은 dict의 단점이 아니라 키워드 매개변수가 작동하는 방식의 직접적인 결과입니다. 여기에서 ** 연산자를 사용하는 것도 메커니즘을 남용하는 것이 아닙니다. 실제로 **는 딕셔너리를 키워드로 정확하게 전달하도록 설계되었습니다.

다시 말하지만 키가 문자열이 아닌 경우 3에서는 작동하지 않습니다. 암시적 호출 규약은 네임스페이스가 일반 사전을 사용하는 반면 사용자는 문자열인 키워드 인수만 전달해야 한다는 것입니다. 다른 모든 호출 가능 항목은 이를 적용했습니다. `dict`는 Python 2에서 이 일관성을 깨뜨렸습니다.

```python
>>> foo(**{('a', 'b'): None})
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: foo() keywords must be strings
>>> dict(**{('a', 'b'): None})
{('a', 'b'): None}
```

이 불일치는 Python의 다른 구현(PyPy, Jython, IronPython)을 고려할 때 좋지 않았습니다. 따라서 이 사용법은 주요 변경 사항이 될 수 있으므로 Python 3에서 수정되었습니다.

한 가지 버전의 언어에서만 작동하거나 임의의 특정 제약 조건이 주어졌을 때만 작동하는 코드를 의도적으로 작성하는 것은 악의적인 무능력자라는 점을 알려드립니다.

다른 댓글:

> `dict(x.items() + y.items())`는 여전히 Python 2에서 가장 읽기 쉬운 해결책입니다. 가독성이 중요합니다.

내 응답: `merge_two_dicts(x, y)` 실제로 가독성에 대해 우려한다면 실제로 훨씬 더 명확해 보입니다. 그리고 Python 2가 점점 더 사용되지 않으므로 앞으로 호환되지 않습니다.

> `{**x, **y}`는 중첩된 딕셔너리를 처리하지 않는 것 같습니다. 중첩된 키의 내용은 병합되지 않고 단순히 덮어쓰여집니다. [...] 나는 재귀적으로 병합되지 않는 이러한 답변으로 인해 화상을 입었고 아무도 언급하지 않았다는 사실에 놀랐습니다. "병합"이라는 단어에 대한 내 해석에서 이러한 답변은 병합이 아닌 "한 딕셔너리을 다른 딕셔너리로 업데이트"를 설명합니다.

예. 단일 표현식에서 첫 번째 값이 두 번째 값으로 덮어쓰여진 상태에서 **두개의** 딕셔너리를 얕은 병합을 요구하는 질문으로 다시 안내해 드리겠습니다.

두 개의 딕셔너리을 가정하면 하나는 단일 함수에서 재귀적으로 병합할 수 있지만 어느 소스에서 딕셔너리를 수정하지 않도록 주의해야 하며 이를 방지하는 가장 확실한 방법은 값을 할당할 때 복사본을 만드는 것입니다. 키는 해시 가능해야 하고 일반적으로 변경할 수 없으므로 복사하는 것은 무의미합니다.

```python
from copy import deepcopy

def dict_of_dicts_merge(x, y):
    z = {}
    overlapping_keys = x.keys() & y.keys()
    for key in overlapping_keys:
        z[key] = dict_of_dicts_merge(x[key], y[key])
    for key in x.keys() - overlapping_keys:
        z[key] = deepcopy(x[key])
    for key in y.keys() - overlapping_keys:
        z[key] = deepcopy(y[key])
    return z
```

사용법:

```python
>>> x = {'a':{1:{}}, 'b': {2:{}}}
>>> y = {'b':{10:{}}, 'c': {11:{}}}
>>> dict_of_dicts_merge(x, y)
{'b': {2: {}, 10: {}}, 'a': {1: {}}, 'c': {11: {}}}
```

다른 값 유형에 대한 우연성을 생각해 내는 것은 이 질문의 범위를 훨씬 넘어서므로 ["딕셔너리 병합"에 대한 표준 질문에 대한 답변](https://stackoverflow.com/questions/7204805/how-to-merge-dictionaries-of-dictionaries/24088493#24088493)을 알려드리겠습니다.

### 성능은 떨어지지만 올바른 Ad-hocs

이러한 접근 방식은 성능이 떨어지지만 올바른 동작을 제공합니다. 더 높은 수준의 추상화에서 각 키-값 쌍을 반복하기 때문에 `copy` 및 `update` 또는 새로운 unpacking보다 성능이 *훨씬 떨어지지만* 우선 순위를 존중합니다(후자의 딕셔너리가 우선합니다).

또한 [딕셔너리 comprehension](https://stackoverflow.com/questions/38987/how-do-i-merge-two-dictionaries-in-a-single-expression-take-union-of-dictionari) 내에서 딕셔너리를 수동으로 연결할 수도 있습니다.

```python
{k: v for d in dicts for k, v in d.items()} # iteritems in Python 2.7
```

또는 Python 2.6(과 generator 표현식이 소개된 2.4 이전 버젼)은 다음처럼 작성합니다.

```python
dict((k, v) for d in dicts for k, v in d.items()) # iteritems in Python 2
```

`itertools.chain`은 올바른 순서로 키-값 쌍에 대한 이터레이터를 연결합니다.

```python
from itertools import chain
z = dict(chain(x.items(), y.items())) # iteritems in Python 2
```

### 성능 분석

저는 정확하게 작동하는 지 알려진 사용법의 성능 분석을 진행할 것입니다. (자체 포함되어 있으므로 직접 복사하여 붙여넣을 수 있습니다.)

```python
from timeit import repeat
from itertools import chain

x = dict.fromkeys('abcdefg')
y = dict.fromkeys('efghijk')

def merge_two_dicts(x, y):
    z = x.copy()
    z.update(y)
    return z

min(repeat(lambda: {**x, **y}))
min(repeat(lambda: merge_two_dicts(x, y)))
min(repeat(lambda: {k: v for d in (x, y) for k, v in d.items()}))
min(repeat(lambda: dict(chain(x.items(), y.items()))))
min(repeat(lambda: dict(item for d in (x, y) for item in d.items())))
```

Python 3.8.1 NixOS에서

```python
>>> min(repeat(lambda: {**x, **y}))
1.0804965235292912
>>> min(repeat(lambda: merge_two_dicts(x, y)))
1.636518670246005
>>> min(repeat(lambda: {k: v for d in (x, y) for k, v in d.items()}))
3.1779992282390594
>>> min(repeat(lambda: dict(chain(x.items(), y.items()))))
2.740647904574871
>>> min(repeat(lambda: dict(item for d in (x, y) for item in d.items())))
4.266070580109954
```

```shell
$ uname -a
Linux nixos 4.19.113 #1-NixOS SMP Wed Mar 25 07:06:15 UTC 2020 x86_64 GNU/Linux
```

### 딕셔너리에 관한 리소스

* [Python 3.6으로 업데이트된 **딕셔너리 구현**에 관한 설명](https://stackoverflow.com/questions/327311/how-are-pythons-built-in-dictionaries-implemented/44509302#44509302)
* [새로운 키를 딕셔너리로 추가하는 방법에 대한 답변](https://stackoverflow.com/questions/1024847/how-can-i-add-new-keys-to-a-dictionary-in-python/27208535#27208535)
* [2 개의 리스트를 딕셔너리로 맵핑](https://stackoverflow.com/questions/209840/how-do-i-convert-two-lists-into-a-dictionary/33737067#33737067)
* [딕셔너리에 대한 파이썬 공식 문서](https://docs.python.org/3/tutorial/datastructures.html#dictionaries)
* [더 강력한 딕셔너리](https://www.youtube.com/watch?v=66P5FMkWoVU) - Pycon 2017에서 Brandon Rhode의 강연
* [현대의 파이썬 딕셔너리, A Confluence of Great Ideas](https://www.youtube.com/watch?v=npw4s1QTmPg) - Pycon 2017에서 Raymond Hettinger의 강연
