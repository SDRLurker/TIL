출처 : [https://stackabuse.com/unpacking-in-python-beyond-parallel-assignment/](https://stackabuse.com/unpacking-in-python-beyond-parallel-assignment/)

# 파이썬으로 unpacking : 병렬 대입을 넘어서

## 소개

파이썬에서 unpacking은 하나의 대입 구문에서 변수들의 [튜플(tuple)](https://stackabuse.com/lists-vs-tuples-in-python/) (또는 `리스트(list)`)로 iterable한 할당을 포함하는 연산입니다. 반대로 packing 이라는 용어는 iterable unpacking 연산자 `*` 를 사용하여 단일 변수에서 여러 값을 수집할 때 사용할 수 있습니다.

역사적으로, 파이썬 개발자는 *튜플(tuple) unpacking* 같은 연산 종류를 일반적으로 참조해 왔습니다. 하지만, 파이썬 기능은 매우 유용하고 인기있는 것으로 밝여졌기 때문에 모든 종류의 iterable 들로 일반화되었습니다. 요즘에는 더 현대적이고 정확한 용어는 *iterable unpacking* 입니다.

이 튜토리얼에서 우리는 iterable unpacking이 무엇이고 우리의 코드를 가독성 있고 유지보수에 좋고 pythonic 하도록 하는 파이썬 특징에 어떻게 이득을 주는지 학습 하겠습니다.

추가적으로, 우리는 대입 연산, `for` 루프, 함수 정의와 함수 호출에서 iterable unpacking 특징을 사용하는 방법에 대한 몇 가지 실용적인 예시를 다룰 것입니다.

## 파이썬에서 packing과 unpacking

파이썬은 변수들의 `튜플(tuple)` ( 또는 `리스트(list)` ) 대입 연산 왼쪽에 나오는 것을 허용합니다. `튜플(tuple)`의 각 변수는 오른쪽 부분에 iterable로부터 하나의 값 ( 또는 만약 `*` 연산자를 사용할 경우 그 이상)을 받을 수 있습니다.

역사적인 이유로 파이썬 개발자는 이를 _tuple unpacking_ 이라 부르고는 했습니다. 하지만 이 특징은 모든 종류의 iterable로 일반화되고 부터 더 정확한 용어는 _iterable unpacking_이 되었고 이 튜토리얼에서 그렇게 부르는 것입니다.

Unpacking 연산은 우리의 코드를 더 가독성있고 우아하게 해주기 때문에 파이썬 개발자 사이에서 아주 유명해졌습니다. 파이썬에서 unpacking에 대해 더 살펴보고 이 특징이 우리의 코드를 어떻게 증진시키는지 살펴봅시다.

## Unpacking Tuples

파이썬에서 우리는 대입 연산 왼쪽에 변수들의 `튜플(tuple)` 을 놓고 오른쪽 부분에 값들의 `튜플(tuple)` 을 놓습니다. 오른쪽의 값은 자동적으로 `튜플(tuple)`에서 그 위치에 따라서 왼쪽의 변수로 대입됩니다. 이는 파이썬에서 *tuple unpacking*으로 널리 알려져 있습니다. 다음 예제를 확인하세요.

```python
>>> (a, b, c) = (1, 2, 3)
>>> a
1
>>> b
2
>>> c
3
```

우리가 대입 연산자 양쪽에 튜플을 놓았을 때, 튜플 unpacking 연산이 발생합니다. 오른쪽의 값은 각 `튜플(tuple)`에 상대적인 위치에 따라 왼쪽에 변수로 대입됩니다. 위에 예제에서 볼 수 있듯이, `a`는 `1`, `b`는 `2`, `c`는 3이 될 것입니다.

`튜플(tuple)` 객체를 생성하기 위해 우리는 괄호 `()` 의 쌍을 구분자로 사용할 필요가 없습니다. 이는 또한 튜플 unpacking으로 작동하며 다음 문법들은 똑같습니다.

```python
>>> (a, b, c) = 1, 2, 3
>>> a, b, c = (1, 2, 3)
>>> a, b, c = 1, 2, 3
```

모든 변이는 파이썬 문법에서 유효하기 때문에 우리는 위에 것 중 상황에 맞춰 아무거나 사용할 수 있습니다. 논쟁의 여지가 있지만 마지막 문법은 파이썬에서 unpacking할 때 더 일반적으로 사용됩니다.

튜플 unpacking을 사용하여 변수로 값을 unpacking할 때, `튜플(tuple)` 왼쪽에 변수의 수는 오른쪽 `튜플(tuple)`의 값의 수와 같아야 합니다. 그렇지 않으면 `ValueError`가 나오게 됩니다.

예를 들어 다음 코드에서 우리는 왼쪽에 2개 변수와 오른쪽에 3개의 값을 사용합니다. 이는 `ValueError` 예외를 발생하는데 우리에게 너무 많은 값을 unpacking 한다고 우리에게 이야기 합니다.

```python
>>> a, b = 1, 2, 3
Traceback (most recent call last):
  ...
ValueError: too many values to unpack (expected 2)
```


참고 : 이에 대한 유일한 예외는 나중에 살펴 보겠지만 `*` 연산자를 사용하여 하나의 변수에 여러 값을 pack 하는 경우입니다.

반면에 값보다 더 많은 변수를 사용하면 ValueError가 발생하지만 이번에는 압축을 풀 값이 충분하지 않다는 메시지가 표시됩니다.

```python
>>> a, b, c = 1, 2
Traceback (most recent call last):
  ...
ValueError: not enough values to unpack (expected 3, got 2)
```

튜플 unpacking 연산에서 다른 수의 변수와 값을 사용하면 `ValueError`가 발생합니다. 파이썬은 어떤 값이 어떤 변수에 들어가는 지 모호하지 않게 알아야 하기 때문에 그에 따라 할당을 수행할 수 있습니다.

## Unpacking Iterables

튜플 unpacking 특징은 매우 인기가 많아서 iterable 객체와 함께 작동하도록 구문이 확장되었습니다. 유일한 요구 사항은 iterable이 수신 튜플 (또는 목록)의 변수당 정확히 하나의 item을 생성한다는 것입니다.

Python에서 iterable unpacking 작동 방식에 대한 다음 예제를 확인하십시오.

```python
>>> # Unpacking strings
>>> a, b, c = '123'
>>> a
'1'
>>> b
'2'
>>> c
'3'
>>> # Unpacking lists
>>> a, b, c = [1, 2, 3]
>>> a
1
>>> b
2
>>> c
3
>>> # Unpacking generators
>>> gen = (i ** 2 for i in range(3))
>>> a, b, c = gen
>>> a
0
>>> b
1
>>> c
4
>>> # Unpacking dictionaries (keys, values, and items)
>>> my_dict = {'one': 1, 'two':2, 'three': 3}
>>> a, b, c = my_dict  # Unpack keys
>>> a
'one'
>>> b
'two'
>>> c
'three'
>>> a, b, c = my_dict.values()  # Unpack values
>>> a
1
>>> b
2
>>> c
3
>>> a, b, c = my_dict.items()  # Unpacking key-value pairs
>>> a
('one', 1)
>>> b
('two', 2)
>>> c
('three', 3)
```

파이썬에서 unpacking할 때 할당 연산자의 오른쪽에 모든 iterable을 사용할 수 있습니다. 왼쪽은 `튜플(tuple)`이나 변수 `리스트(list)`으로 채울 수 있습니다. 대입문의 오른쪽에 `튜플(tuple)`을 사용하는 다음 예제를 확인하십시오.

```python
>>> [a, b, c] = 1, 2, 3
>>> a
1
>>> b
2
>>> c
3
```

`range()` iterator를 사용한다면 같은 방법으로 작동합니다.

```python
>>> x, y, z = range(3)
>>> x
0
>>> y
1
>>> z
2
```

유효한 파이썬 구문일지라도 실제 코드에서는 일반적으로 사용되지 않으며 초보 파이썬 개발자에게는 약간 혼란스러울 수 있습니다.

마지막으로, unpacking 작업에서 `set` 객체를 사용할 수도 있습니다. 그러나 `set`는 순서가 지정되지 않은 컬렉션이므로 할당 순서가 일관성이 없고 미묘한 버그가 발생할 수 있습니다. 다음 예를 확인하십시오.

```python
>>> a, b, c = {'a', 'b', 'c'}
>>> a
'c'
>>> b
'b'
>>> c
'a'
```

set을 unpacking 연산에서 사용한다면 대입문의 마지막 순서는 우리가 원하거나 기대했던 거와는 많이 다릅니다. 따라서 할당 순서가 중요하지 않은 경우가 아니면 unpacking 작업에서 set을 사용하지 않는 것이 가장 좋습니다.

## * 연산자로 packing 하기

여기에서 `*` 연산자는 튜플 (또는 iterable) unpacking 연산으로 알려져 있습니다. 이 연산자는 단일 변수로 여러 값을 수집하거나 packing 할 수 있도록 unpacking 기능을 확장합니다. 다음 예제에서는 `*` 연산자를 사용하여 값의 `튜플(tuple)` 을 단일 변수로 pack 합니다.

```python
>>> *a, = 1, 2
>>> a
[1, 2]
```

이 코드가 작동하려면 대입문에 왼쪽은 `튜플(tuple)` 또는 `리스트(list)`가 되어야 합니다. 그래서 마지막에 쉼표(콤마)를 사용하는 이유입니다. 이 `튜플(tuple)`은 우리가 필요한 만큼 많은 변수들을 담습니다. 하지만, 이는 하나의 별로 표시된 표현식만 포함할 수 있습니다.

위 코드의 `*a` 와 같이 유효한 파이썬 식별자와 함께 unpacking 연산자 `*`를 사용하여 별표 표현식을 만들 수 있습니다. 왼쪽 `튜플(tuple)`의 나머지 변수는 구체적인 값으로 채워야하기 때문에 *필수* 변수라고 합니다. 그렇지 않으면 오류가 발생합니다. 이것이 실제로 작동하는 방법은 다음과 같습니다.

마지막의 값들로 `b`를 packing 합니다.

```python
>>> a, *b = 1, 2, 3
>>> a
1
>>> b
[2, 3]
```

시작 값들로 `a`를 packing 합니다.

```python
>>> *a, b = 1, 2, 3
>>> a
[1, 2]
>>> b
3
```

`b`와`c`가 필수 변수이기 때문에 하나의 값 `a`를 packing 합니다.

```python
>>> *a, b, c = 1, 2, 3
>>> a
[1]
>>> b
2
>>> c
3
```python

`b`와`c`,`d`가 필수 변수이기 때문에 빈 값 `a`(`a`는 `[]`가 기본값)를 packing 합니다.

```python
>>> *a, b, c, d = 1, 2, 3
>>> a
[]
>>> b
1
>>> c
2
>>> d
3
```

필수 변수 (`e`)로 공급할 값이 없기 때문에, 오류가 발생합니다.

```python
>>> *a, b, c, d, e = 1, 2, 3
 ...
ValueError: not enough values to unpack (expected at least 4, got 3)
```

`*` 연산자를 사용하여 변수에 값을 packing하면 `list()` 함수를 사용하지 않고 단일 변수에서 generator의 요소를 수집해야 할 때 편리합니다. 다음 예제에서는 `*` 연산자를 사용하여 [generator 표현식](https://docs.python.org/3/glossary.html#term-generator)의 요소와 [range](https://docs.python.org/3/library/stdtypes.html#range) 객체를 개별 변수로 압축합니다.

```python
>>> gen = (2 ** x for x in range(10))
>>> gen
<generator object <genexpr> at 0x7f44613ebcf0>
>>> *g, = gen
>>> g
[1, 2, 4, 8, 16, 32, 64, 128, 256, 512]
>>> ran = range(10)
>>> *r, = ran
>>> r
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
```

이 예에서 `*` 연산자는 `gen`에 요소를 packing 하고 각각 `g`와 `r`을 만났습니다. 그 구문을 사용하면 `range` 객체, generator 표현식 또는 generator 함수에서 값 `목록(list)`을 만들기 위해 `list()`를 호출 할 필요가 없습니다.

할당의 왼쪽에 있는 변수 마지막에 쉼표를 추가하지 않고는 unpacking 연산자 `*`를 사용하여 여러 값을 하나의 변수로 packing 할 수 없습니다. 따라서 다음 코드는 작동하지 않습니다.

```python
>>> *r = range(10)
  File "<input>", line 1
SyntaxError: starred assignment target must be in a list or tuple
```

`*` 연산자를 사용하여 여러 값을 단일 변수로 packing 경우 단일 `튜플(tuple)` 구문을 사용해야 합니다. 예를 들어, 위의 예제가 작동하도록 하려면 `*r, = range(10)` 에서와 같이 변수 `r` 뒤에 쉼표를 추가하면 됩니다.

# 실제로 Packing 과 Unpacking 사용하기

실제로 Packing 과 Unpacking 연산은 매우 유용합니다. 이는 코드를 깔끔하고 가독성있고 pythonic하게 만듧니다. 파이썬에서 packing과 unpacking의 몇가지 공통 사용 예시를 살펴봅시다.

## 병렬로 대입

파이썬에서 unpacking의 가장 일반적인 사용 사례 중 하나는 병렬 할당 입니다. 병렬 할당을 사용하면 반복 가능한 값을 하나의 우아한 문장으로 변수의 `튜플(tuple)` (또는 `목록(list)`)에 할당 할 수 있습니다.

예를 들어 회사의 직원에 대한 데이터베이스가 있고 목록의 각 항목을 변수에 할당해야 한다고 가정해 보겠습니다. 파이썬에서 반복 가능한 unpacking이 작동하는 방식을 무시하면 다음과 같은 코드를 직접 작성할 수 있습니다.

```python
>>> employee = ["John Doe", "40", "Software Engineer"]
>>> name = employee[0]
>>> age = employee[1]
>>> job = employee[2]
>>> name
'John Doe'
>>> age
'40'
>>> job
'Software Engineer'
```

이 코드는 작동하지만 index 처리는 서투르고 입력하기 어렵고 혼란스러울 수 있습니다. 더 깨끗하고 읽기 쉽고 pythonic한 해결책은 다음과 같이 코딩할 수 있습니다.

```python
>>> name, age, job = ["John Doe", "40", "Software Engineer"]
>>> name
'John Doe'
>>> age
40
>>> job
'Software Engineer'
```

Python에서 unpacking를 사용하면 하나의 간단하고 우아한 문장으로 이전 예제의 문제를 해결할 수 있습니다. 이 작은 변경으로 인해 신규 개발자가 코드를 더 쉽게 읽고 이해할 수 있습니다.

## 변수간 값 교환

파이썬에서 unpacking의 또 다른 우아한 응용 프로그램은 임시 또는 보조 변수를 사용하지 않고 변수간에 값을 교환하는 것입니다. 예를 들어 두 변수 a와 b의 값을 바꿔야한다고 가정 해 보겠습니다. 이를 위해 기존 솔루션을 고수하고 임시 변수를 사용하여 다음과 같이 교환할 값을 저장할 수 있습니다.

```python
>>> a = 100
>>> b = 200
>>> temp = a
>>> a = b
>>> b = temp
>>> a
200
>>> b
100
```

이 절차에는 세 단계와 새 임시 변수가 필요합니다. 파이썬에서 unpacking을 사용하면, 한 번의 간결한 단계로 동일한 결과를 얻을 수 있습니다.

```python
>>> a = 100
>>> b = 200
>>> a, b = b, a
>>> a
200
>>> b
100
```

문 `a, b = b, a`에서 우리는 한 줄의 코드에서 `a`를 `b`에, `b`를 `a`에 재할당합니다. 이것은 훨씬 더 읽기 쉽고 간단합니다. 또한 이 기술을 사용하면 새 임시 변수가 필요하지 않습니다.

## `*`로 여러개의 값 모으기

일부 알고리즘으로 작업할 때 추가 처리를 위해 반복 가능한 값 또는 시퀀스 값을 값 청크로 분할해야 하는 상황이 있을 수 있습니다. 다음 예는 이를 위해 `리스트(list)` 및 [slicing 연산](https://docs.python.org/3/library/stdtypes.html#common-sequence-operations)을 사용하는 방법을 보여줍니다.

```python
>>> seq = [1, 2, 3, 4]
>>> first, body, last = seq[0], seq[1:3], seq[-1]
>>> first, body, last
(1, [2, 3], 4)
>>> first
1
>>> body
[2, 3]
>>> last
4
```

이 코드는 우리가 예상 한대로 작동 하지만, index와 slice를 다루는 것은 초보자에게 약간 귀찮고 읽기 어렵고 혼란 스러울 수 있습니다. 또한 코드를 엄격하고 유지 관리하기 어렵게 만드는 단점도 있습니다. 이 상황에서 반복 가능한 unpacking 연산자 `*` 및 단일 변수에 여러 값을 pack하는 기능은 훌륭한 도구가 될 수 있습니다. 위 코드의 리팩토링한 내용을 확인하세요.

```python
>>> seq = [1, 2, 3, 4]
>>> first, *body, last = seq
>>> first, body, last
(1, [2, 3], 4)
>>> first
1
>>> body
[2, 3]
>>> last
4
```

첫 번째 라인, `*body, last = seq`는 여기서 마법을 만듭니다. 반복 가능한 unpacking 연산자 `*`는 `body`의 `seq` 중간에 있는 요소들을 수집합니다. 이것은 우리의 코드를 더 읽기 쉽고, 유지 관리하고, 유연하게 만듭니다. 왜 더 유연할까요? 음, `seq`가 리스트의 길이를 변경하였고 여전히 `body`의 중간 요소를 수집해야 한다고 가정합니다. 이 경우 Python에서 unpacking을 사용하므로 코드가 작동하기 위해 변경할 필요가 없습니다. 이 예를 확인하십시오.

```python
>>> seq = [1, 2, 3, 4, 5, 6]
>>> first, *body, last = seq
>>> first, body, last
(1, [2, 3, 4, 5], 6)
```

만약 파이썬에서 iterable unpacking 대신에 시퀀스 slicing을 사용 한다면, 우리는 새로운 값에 대한 정확하게 slice할 인덱스를 갱신해야 합니다.

하나의 변수에서 몇개의 값을 pack하는 `*` 연산의 사용은 파이썬이 각 값을 어떤 요소들에 모호하지 않게 할당하기 위해 다양하게 값을 설정하는데 적용될 수 있습니다. 다음 예를 살펴봅시다.

```python
>>> *head, a, b = range(5)
>>> head, a, b
([0, 1, 2], 3, 4)
>>> a, *body, b = range(5)
>>> a, body, b
(0, [1, 2, 3], 4)
>>> a, b, *tail = range(5)
>>> a, b, tail
(0, 1, [2, 3, 4])
```

우리는 필요에 따라서 값을 모으기 위해 변수들의 `tuple(튜플)` (또는 `list(리스트)` )에서 `*` 위치를 이동할 수 있습니다. 유일한 조건은 파이썬이 각 값들을 어느 변수에 할당할 지 결정할 수 있다는 것입니다.

우리는 대입문에서 별 표기법을 하나를 초과하여 사용할 수 없다는 것이 중요합니다. 별 표기법을 하나를 초과할 경우 다음처럼 `SyntaxError`가 나오게 됩니다.

```python
>>> *a, *b = range(5)
  File "<input>", line 1
SyntaxError: two starred expressions in assignment
```

할당 표현식에서 `*`를 두 개 이상 사용하면 별표 두 개가 있는 표현식이 발견되었음을 알려주는 `SyntaxError`가 발생합니다. 이것은 파이썬이 각 변수에 할당할 값 (또는 값)을 명확하게 결정할 수 없기 때문입니다.

## `*`로 필요 없는 값 버리기

`*` 연산자의 또 다른 일반적인 사용 사례는 쓸모 없거나 불필요한 값을 삭제하기 위해 더미 변수 이름과 함께 사용하는 것입니다. 다음 예를 확인하십시오.

```python
>>> a, b, *_ = 1, 2, 0, 0, 0, 0
>>> a
1
>>> b
2
>>> _
[0, 0, 0, 0]
```

이 사용 사례에 대한 보다 통찰력 있는 예를 들면 사용중인 파이썬 버전을 확인하는 데 필요한 스크립트를 개발하고 있다고 가정합니다. 이를 위해 sys.version\_info 속성을 사용할 수 있습니다. 이 속성은 버전 번호의 5개 구성 요소인 `major`, `minor`, `micro`, `releaselevel` 및 `serial` 을 포함하는 튜플을 반환 합니다. 그러나 스크립트가 작동하려면 `major`, `minor` 및 `micro` 만 필요하므로 나머지는 삭제할 수 있습니다. 예를 들면 다음과 같습니다.

```python
>>> import sys
>>> sys.version_info
sys.version_info(major=3, minor=8, micro=1, releaselevel='final', serial=0)
>>> mayor, minor, micro, *_ = sys.version_info
>>> mayor, minor, micro
(3, 8, 1)
```

이제 필요한 정보가 포함 된 세 가지 새로운 변수가 있습니다. 나머지 정보는 프로그램에서 무시할 수있는 더미 변수 `_`에 저장됩니다. 이것은 우리가 `_`에 저장된 정보를 사용하고 싶지 않거나 사용할 필요가 없다는 것을 신규 개발자에게 분명하게 할 수 있습니다. 이 문자는 명백한 의미가 없기 때문입니다.

**참고:** 기본적으로 밑줄 문자 `_`는 파이썬 인터프리터에서 대화형 세션에서 실행하는 명령문의 결과 값을 저장하는 데 사용됩니다. 따라서 이 컨텍스트에서 더미 변수를 식별하기 위해 이 문자를 사용하는 것은 모호할 수 있습니다.

## 함수로 튜플 리턴하기

Python 함수는 쉼표로 구분된 여러 값을 리턴할 수 있습니다. 괄호를 사용하지 않고 `tuple(튜플)` 객체를 정의할 수 있기 때문에 이런 종류의 연산은 `tuple(튜플)` 값을 리턴하는 것으로 해석될 수 있습니다. 여러 값을 반환하는 함수를 코딩하면 리턴된 값을 사용하여 iterable packing 및 unpacking 작업을 수행할 수 있습니다.

주어진 숫자의 정사각형과 정육면체를 계산하는 함수를 정의하는 다음 예제를 확인하십시오.

```python
>>> def powers(number):
...     return number, number ** 2, number ** 3
...
>>> # Packing returned values in a tuple
>>> result = powers(2)
>>> result
(2, 4, 8)
>>> # Unpacking returned values to multiple variables
>>> number, square, cube = powers(2)
>>> number
2
>>> square
4
>>> cube
8
>>> *_, cube = powers(2)
>>> cube
8
```

쉼표로 구분된 값을 리턴하는 함수를 정의하면 이러한 값에 대해 packing 또는 unpacking 작업을 수행할 수 있습니다.

## `*` 연산자로 iterable 병합

unpacking 연산자 `*`의 또 다른 흥미로운 사용 사례는 iterable을 최종 시퀀스로 병합하는 기능입니다. 이 기능은 list, 튜플 및 set에 대해 작동합니다. 다음 예를 살펴보십시오.

```python
>>> my_tuple = (1, 2, 3)
>>> (0, *my_tuple, 4)
(0, 1, 2, 3, 4)
>>> my_list = [1, 2, 3]
>>> [0, *my_list, 4]
[0, 1, 2, 3, 4]
>>> my_set = {1, 2, 3}
>>> {0, *my_set, 4}
{0, 1, 2, 3, 4}
>>> [*my_set, *my_list, *my_tuple, *range(1, 4)]
[1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3]
>>> my_str = "123"
>>> [*my_set, *my_list, *my_tuple, *range(1, 4), *my_str]
[1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, '1', '2', '3']
```

시퀀스를 정의할 때 iterable unpacking 연산자 `*`를 사용하여 하위 시퀀스 (또는 반복 가능)의 요소를 최종 시퀀스로 unpacking할 수 있습니다. 이렇게하면 `append()`, `insert()` 등과 같은 메서드를 호출하지 않고도 다른 기존 시퀀스에서 즉시 시퀀스를 만들 수 있습니다.

마지막 두 가지 예는 이것이 iterable을 연결하는 더 읽기 쉽고 효율적인 방법임을 보여줍니다. `list (my_set) + my_list + list (my_tuple) + list (range (1, 4)) + list (my_str)` 을 쓰는 대신 `[* my_set, * my_list, * my_tuple, * range (1, 4), * my_str]`로 작성할 수 있습니다.

## `**` 연산자로 딕셔너리(dictionary) unpacking

파이썬에서 unpacking할 때, `**`는 [dictionary unpacking 연산자](https://docs.python.org/3/whatsnew/3.5.html#pep-448-additional-unpacking-generalizations)라 부릅니다. 이 연산자의 사용은 [PEP 448](https://www.python.org/dev/peps/pep-0448/)에 의해 확장되었습니다. 이제 함수 호출, comprehesion, generator 표현식, [dictionary display](https://docs.python.org/3/reference/expressions.html#dictionary-displays)에서 그것을 사용할 수 있습니다. 

dictionary unpacking 연산자의 기본적인 사용 예시는 단일 표현식으로 여러 dictionary를 하나의 최종 dictionary로 병합 합니다. 이것이 어떻게 작동하는지 봅시다.

```python
>>> numbers = {"one": 1, "two": 2, "three": 3}
>>> letters = {"a": "A", "b": "B", "c": "C"}
>>> combination = {**numbers, **letters}
>>> combination
{'one': 1, 'two': 2, 'three': 3, 'a': 'A', 'b': 'B', 'c': 'C'}
```

dictionary display 내에서 dictionary unpacking 연산자를 사용하면 위의 코드에서 했던 것처럼 dictionary의 unpack하고 결합하여 원래 dictionary의 키-값 쌍을 포함하는 최종 dictionary를 만들 수 있습니다.

유의해야 할 중요한 점은 병합하려는 dictionary에 반복되거나 공통된 키가 있는 경우 맨 오른쪽 dictionary의 값이 맨 왼쪽 dictionary의 값을 재정의 한다는 것입니다. 예를 들면 다음과 같습니다.

```python
>>> letters = {"a": "A", "b": "B", "c": "C"}
>>> vowels = {"a": "a", "e": "e", "i": "i", "o": "o", "u": "u"}
>>> {**letters, **vowels}
{'a': 'a', 'b': 'B', 'c': 'C', 'e': 'e', 'i': 'i', 'o': 'o', 'u': 'u'}
```

키가 두 dictionary 모두에 있기 때문에 가장 오른쪽에 있는 모음에서 값이 나옵니다. 이것은 파이썬이 왼쪽에서 오른쪽으로 키-값 쌍을 추가하기 시작하기 때문에 발생합니다. 프로세스에서 파이썬이 이미 종료된 키를 찾으면 인터프리터는 해당 키를 새 값으로 업데이트 합니다. 이것 이 위의 예에서 키 값이 소문자로 된 이유입니다.

## For 루프에서 unpacking

우리는 `for` 루프에서 iterable unpacking을 사용할 수 있습니다. 우리가 `for` 루프를 실행할 때 루프는 각 iteration 에서 iterable의 하나의 아이템을 타겟 변수로 대입합니다. 대입할 아이템이 iterable이면 우리는 타겟 변수의 튜플을 사용할 수 있습니다. 루프는 타겟 변수의 튜플로 iterable을 unpack할 것입니다.

예를 들어, 다음과 같은 회사 판매 데이터가 포함된 파일이 있다고 가정해 보겠습니다.

|**Product**|**Price**|**Sold Units**|
|-|-|-|
|Pencil|0.25|1500|
|Notebook|1.30|550|
|Eraser|0.75|1000|
|...|...|...|

이 테이블에서 두 요소 튜플 `리스트(list)`를 작성할 수 있습니다. 각 `튜플(tuple)`에는 제품 이름, 가격 및 판매된 단위가 포함됩니다. 이 정보를 사용하여 각 제품의 수입을 계산하려고 합니다. 이를 위해 다음과 같은 `for` 루프를 사용할 수 있습니다.

```python
>>> sales = [("Pencil", 0.22, 1500), ("Notebook", 1.30, 550), ("Eraser", 0.75, 1000)]
>>> for item in sales:
...     print(f"Income for {item[0]} is: {item[1] * item[2]}")
...
Income for Pencil is: 330.0
Income for Notebook is: 715.0
Income for Eraser is: 750.0
```


이 코드는 예상대로 작동합니다. 그러나 각 `튜플(tuple)`의 개별 요소에 액세스하기 위해 인덱스를 사용하고 있습니다. 이것은 초보 개발자가 읽고 이해하기 어려울 수 있습니다.

파이썬에서 unpacking를 사용하는 대체 가능한 구현을 살펴 보겠습니다.

```python
>>> for product, price, sold_units in sales:
...     print(f"Income for {product} is: {price * sold_units}")
...
Income for Pencil is: 330.0
Income for Notebook is: 715.0
Income for Eraser is: 750.0
```

이제 `for` 루프에서 반복 가능한 unpacking을 사용하고 있습니다. 이것은 각 `튜플(tuple)`의 요소를 식별하기 위해 설명을 잘 하는 이름을 사용하기 때문에 코드를 더 읽기 쉽고 유지 관리하기 쉽게 만듭니다. 이 작은 변화를 통해 초보 개발자는 코드의 논리를 빠르게 이해할 수 있습니다.

`for` 루프에서 `*` 연산자를 사용하여 단일 대상 변수에 여러 항목을 packing할 수도 있습니다.

```python
>>> for first, *rest in [(1, 2, 3), (4, 5, 6, 7)]:
...     print("First:", first)
...     print("Rest:", rest)
...
First: 1
Rest: [2, 3]
First: 4
Rest: [5, 6, 7]
```

이 `for` 루프에서 우리는 먼저 각 시퀀스의 `첫번째` 요소를 잡습니다. 그런 다음 `*` 연산자는 대상 변수 나머지에서 값의 `리스트(list)`를 포착합니다.

마지막으로 타겟 변수의 구조는 iterable의 구조와 일치해야합니다. 그렇지 않으면 오류가 발생합니다. 다음 예를 살펴보십시오.

```python
>>> data = [((1, 2), 2), ((2, 3), 3)]
>>> for (a, b), c in data:
...     print(a, b, c)
...
1 2 2
2 3 3
>>> for a, b, c in data:
...     print(a, b, c)
...
Traceback (most recent call last):
  ...
ValueError: not enough values to unpack (expected 3, got 2)
```

첫 번째 루프에서 타겟 변수 `(a, b), c`의 구조는 반복 가능한 항목의 구조 `((1, 2), 2)`와 일치합니다. 이 경우 루프가 예상대로 작동합니다. 반대로 두 번째 루프는 반복 가능한 항목의 구조와 일치하지 않는 대상 변수의 구조를 사용하므로 루프가 실패하고 `ValueError`가 발생합니다.

## 함수에서 Packing 과 Unpacking

우리는 함수를 정의하고 호출할 때 파이썬의 packing과 unpacking을 사용할 수 있습니다. 이는 매우 유용하며 파이썬에서 packing 과 unpacking의 유명한 사용 사례입니다.

이 섹션에서 우리는 함수 정의 또는 함수 호출에서 파이썬 함수에서 packing과 unpacking을 사용하는 방법의 기초에 대해 알아볼 것입니다.

Note: For a more insightful and detailed material on these topics, check out Variable-Length Arguments in Python with *args and **kwargs.

**참고:** 이 주제에 대한보다 통찰력 있고 자세한 자료는 `*args` 및 `**kwargs`를 사용하는 Python의 가변 길이 인수를 확인하십시오.

## `*`와 `**`로 함수 정의하기

파이썬 함수의 서명에 대해 `*` 및 `**` 연산자를 사용할 수 있습니다. 이렇게하면 가변 개수의 위치 인수 (`*`) 또는 가변 개수의 키워드 인수 또는 둘 다를 사용하여 함수를 호출 할 수 있습니다. 다음 함수를 고려해 봅시다.

```python
>>> def func(required, *args, **kwargs):
...     print(required)
...     print(args)
...     print(kwargs)
...
>>> func("Welcome to...", 1, 2, 3, site='StackAbuse.com')
Welcome to...
(1, 2, 3)
{'site': 'StackAbuse.com'}
```

위의 함수에는 `required` 라는 인수가 하나 이상 필요합니다. 가변 개수의 위치 및 키워드 인수도 허용할 수 있습니다. 이 경우 `*` 연산자는 `args`라는 튜플에서 추가 위치 인수를 수집하거나 pack하고 `**` 연산자는 `kwargs`라는 dictionary에서 추가 키워드 인수를 수집하거나 pack합니다. `args`와 `kwargs`는 모두 선택 사항이며 기본값은 각각 `()` 및 `{}`입니다.

`args` 및 `kwargs`라는 이름은 파이썬 커뮤니티에서 널리 사용되지만 이러한 기술이 작동하는 데 필요한 것은 아닙니다. 구문에는 `*` 또는 `**` 뒤에 유효한 식별자가 필요합니다. 따라서 이러한 인수에 의미있는 이름을 부여할 수 있다면 그렇게 하십시오. 그것은 확실히 코드의 가독성을 향상시킬 것입니다.
