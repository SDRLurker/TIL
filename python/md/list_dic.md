**출처**

[http://stackoverflow.com/questions/16819222/how-to-return-dictionary-keys-as-a-list-in-python-3-3](http://stackoverflow.com/questions/16819222/how-to-return-dictionary-keys-as-a-list-in-python-3-3)

**Youtube**

[https://youtu.be/tnJqNEc9knk](https://youtu.be/tnJqNEc9knk)

**Google Colab**

[https://colab.research.google.com/drive/1PZGbth0ZArjDPkzPBNpT4uCZjRDKPhQ0?usp=sharing](https://colab.research.google.com/drive/1PZGbth0ZArjDPkzPBNpT4uCZjRDKPhQ0?usp=sharing)

# Python에서 dictionary 키를 list로 리턴하는 방법

저는 뭔가 매우 이상하다는 것을 알았습니다. Python 2.7과 Python 3의 오래된 버전에서 뭔가 많이 다른 것이 있습니다.

이전에, dictionary에서 key 값, value 값, item 값을 list로 쉽게 얻을 수 있었습니다.

* Python 2.7

```python
>>> newdict = {1:0, 2:0, 3:0} 
>>> newdict
{1: 0, 2: 0, 3: 0} 
>>> newdict.keys()
[1, 2, 3]
```

하지만 Python 3에서는 다음처럼 이렇게 나옵니다.

* PYTHON 3.3.0 

```python
>>> newdict.keys() 
dict_keys([1, 2, 3])
```

---

## 3 개의 답변 중 1 개의 답변만 추려냄.

`list(newdict.keys())` 를 시도해 보세요.

이는 dict\_keys 객체를 list로 변환할 것입니다.  
반면, 당신은 이것이 문제가 되는지 아닌지 스스로에게 물어봐야 합니다. 코드를 Python의 방법으로 작성하는 것은 [duck typing](https://ko.wikipedia.org/wiki/%EB%8D%95_%ED%83%80%EC%9D%B4%ED%95%91)(만약 그것이 오리처럼 생겼고, 오리처럼 꽥꽥 운다면 그것은 오리입니다.) 이라 가정합니다. dict\_keys 객체는 대부분 list처럼 행동합니다. 예를 들면, 다음 소스처럼 list처럼 작동합니다.

```python
for key in newdict.keys():
    print(key)
    
1
2
3
```

## 답변 후 추가 편집 내용

감사합니다. list(newdict.keys())가 제가 원하는대로 작동합니다.

현재는 다른 것이 저를 괴롭히고 있습니다. 저는 value 값으로 정렬하여 dictionary key 값과 value 값들을 뒤집어(reverse) list로 생성하고 싶습니다. 다음처럼 소스를 작성하였습니다. (다음은 나쁜 예시입니다. 모든 value 값들이 0이기 때문입니다.)

```python
>>> zip(newdict.values(), newdict.keys())
[(0, 1), (0, 2), (0, 3)]
```

하지만, Python 3에서는 다음처럼 나옵니다.

```python
>>> zip(list(newdict.keys()), list(newdict.values())) <zip object at 0x7f367c7df488>
```

아, 죄송합니다. 

zip() 밖으로 list() 함수를 사용해야 됨을 알았습니다.

```python
list(zip(newdict.values(), newdict.keys())) [(0, 1), (0, 2), (0, 3)]
```

