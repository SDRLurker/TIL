출처 : [http://stackoverflow.com/questions/9001509/how-can-i-sort-a-dictionary-by-key](http://stackoverflow.com/questions/9001509/how-can-i-sort-a-dictionary-by-key)

# 키로 dictionary를 어떻게 정렬할 수 있습니까?

 `{2:3, 1:89, 4:5, 3:3}`을 `{1:89, 2:3, 3:3, 4:5}`로 만드는 좋은 방법에는 무엇이 있습니까? 저는 몇 개의 글을 검사했는데 그들 모두 튜플을 리턴하는 "sorted" 연산자를 사용하였습니다.

----

## 30 개의 답변 중 1개의 답변만 추려냄.

표준 파이썬 dictionary들은 정렬되지 않습니다. (key, value) 쌍으로 정렬할 지라도 순서를 보전하면서 `dict`의 내용을 보관할 수는 없습니다.

가장 쉬운 방법은 값(element)이 추가되는 순서를 기억하는 [`OrderedDict`](https://docs.python.org/3/library/collections.html#collections.OrderedDict)를 사용하는 것입니다.

```python
In [1]: import collections
In [2]: d = {2:3, 1:89, 4:5, 3:0}
In [3]: od = collections.OrderedDict(sorted(d.items()))
In [4]: od
Out[4]: OrderedDict([(1, 89), (2, 3), (3, 0), (4, 5)])
```

`od`가 출력하는 방법은 신경쓰지 않아도 됩니다. 예상한대로 작동합니다.

```python
In [11]: od[1]
Out[11]: 89

In [12]: od[3]
Out[12]: 0

In [13]: for k, v in od.iteritems(): print k, v
   ....: 
1 89
2 3
3 0
4 5
```

## Python 3

Python3 사용자는 `.iteritems()` 대신에 `.items()`을 사용해야 합니다.

```python
In [13]: for k, v in od.items(): print(k, v)
   ....: 
1 89
2 3
3 0
4 5
```
