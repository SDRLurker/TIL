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
