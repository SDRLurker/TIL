출처 : [http://stackoverflow.com/questions/16819222/how-to-return-dictionary-keys-as-a-list-in-python-3-3](http://stackoverflow.com/questions/16819222/how-to-return-dictionary-keys-as-a-list-in-python-3-3)

# Python에서 dictionary 키를 list로 리턴하는 방법

저는 뭔가 매우 이상하다는 것을 알았습니다. Python 2.7과 Python 3의 오래된 버전에서 뭔가 많이 다른 것이 있습니다.

이전에, dictionary에서 key 값, value 값, item 값을 list로 쉽게 얻을 수 있었습니다.

```python
PYTHON 2.7
>>> newdict = {1:0, 2:0, 3:0}
>>> newdict
{1: 0, 2: 0, 3: 0}
>>> newdict.keys()
[1, 2, 3]
```

하지만 다음처럼 이렇게 나옵니다.

```python
PYTHON 3.3.0
>>> newdict.keys()
dict_keys([1, 2, 3])
```

저는 Python 2.7의 예시처럼 list가 리턴되어 나오는 방법이 궁금합니다.

```python
newlist = list()
for i in newdict.keys():
    newlist.append(i)
```

*Python 3*에서 리스트를 리턴하는 최고의 방법이 있는지 궁금합니다.



----

## 3 개의 답변 중 1 개의 답변만 추려냄.

`list(newdict.keys())` 를 시도해 보세요.
이는 `dict_keys`객체를 list로 변환할 것입니다.

반면, 당신은 이것이 문제가 되는지 아닌지 스스로에게 물어봐야 합니다. 코드를 Python의 방법으로 작성하는 것은 duck typing(만약 그것이 오리처럼 생겼고, 오리처럼 꽥꽥 운다면 그것은 오리입니다.) 이라 가정합니다. dict_keys 객체는 대부분 list처럼 행동합니다. 예를 들면, 다음 소스처럼 list처럼 작동합니다.

```python
for key in newdict.keys():
  print(key)
```
