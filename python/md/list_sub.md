출처 : [https://stackoverflow.com/questions/3428536/python-list-subtraction-operation](https://stackoverflow.com/questions/3428536/python-list-subtraction-operation)

# 파이썬 리스트 차집합 연산

저는 다음과 비슷하게 뭔가 하고 싶습니다.

```python
>>> x = [1,2,3,4,5,6,7,8,9,0]  
>>> x  
[1, 2, 3, 4, 5, 6, 7, 8, 9, 0]  
>>> y = [1,3,5,7,9]  
>>> y  
[1, 3, 5, 7, 9]  
>>> y - x   # ([2,4,6,8,0]를 리턴해야 합니다.)
```

이 방법은 파이썬 리스트에서 지원되지 않습니다. 이를 할 수 있는 최고의 방법은 무엇입니까?

## 13개의 답변 중 1개의 답변만 추려냄

리스트 내포(comprehension)을 사용합니다.

```python
[item for item in x if item not in y]
```

만약 `-` 중위 연산 문법을 사용하고 싶으면 다음처럼 할 수 있습니다.

```python
class MyList(list):
    def __init__(self, *args):
        super(MyList, self).__init__(args)

    def __sub__(self, other):
        return self.__class__(*[item for item in self if item not in other])
```

다음처럼 사용할 수 있습니다.

```python
x = MyList(1, 2, 3, 4)
y = MyList(2, 5, 2)
z = x - y
```

하지만 만약 리스트 속성이 절대적으로 필요하지 않다면(예시, 순서), 다른 답변처럼 set(집합)을 사용하는 것을 추천합니다.
