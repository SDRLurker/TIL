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
