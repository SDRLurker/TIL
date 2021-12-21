**출처**

[https://stackoverflow.com/questions/11277432/how-can-i-remove-a-key-from-a-python-dictionary](https://stackoverflow.com/questions/11277432/how-can-i-remove-a-key-from-a-python-dictionary)

# Python dictionary에서 키를 어떻게 없앨 수 있을까요?

dictionary에서 키를 삭제할 때 저는 다음을 사용하였습니다.

```python
if 'key' in my_dict:
    del my_dict['key']
```

이를 할 수 있는 한 줄의 방법이 있습니까?

---

## 14 개의 답변 중 1 개의 답변

dictionary에 키가 있는지 상관없이 삭제하려면, [`dict.pop()`](https://docs.python.org/ko/3/library/stdtypes.html#dict.pop)에서 2개의 인자를 사용합니다.

```python
my_dict.pop('key', None)
```

이는 `key`가 dictionary에 존재한다면 `my_dict[key]`를 리턴할 것이며 그렇지 않으면 `None`을 리턴할 것입니다. 두 번째 파라미터가 특정되지 않았고 (예 `my_dict.pop('key')`) `key`가 존재하지 않으면 `KeyError` 오류가 발생합니다.

존재하는지 보장되는 키를 지우기 위해 다음을 사용할 수 있습니다.

```python
del my_dict['key']
```

만약 dictionary에 키가 없으면 `KeyError` 오류가 발생합니다.
