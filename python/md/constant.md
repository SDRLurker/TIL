출처 : [http://stackoverflow.com/questions/2682745/how-to-create-a-constant-in-python](http://stackoverflow.com/questions/2682745/how-to-create-a-constant-in-python)

# Python에서 상수를 만드는 방법

파이썬에서 상수를 선언할 수 있는 방법이 있습니까? 자바에서는 다음 방법으로 상수를 만들 수 있습니다.

```java
public static final String CONST_NAME = "Name";
```

파이썬에서 위의 자바 상수 선언과 같은 방법은 무엇입니까?

---

## 18 개의 답변 중 2 개의 답변만 추려냄.

아니오. 방법이 없습니다. 파이썬에서 상수를 선언할 수 없습니다. 그저 그 변수를 바꾸지 마세요.만약 클래스에 있으면, 같은 방법은

```python
class Foo(object): CONST_NAME = "Name"
```

클래스에 없다면,

```python
CONST_NAME = "Name"
```

처럼 사용할 수 있습니다.

하지만, Alex Martelli에 [Constants in Python](https://code.activestate.com/recipes/65207-constants-in-python/?in=user-97991) 의 코드 조각을 보시길 원하실 거라 생각합니다.

---

다른 언어처럼 키워드가 없습니다. 하지만, 데이터를 읽는 **"getter 함수"를 가지지만** 데이터를 다시 쓰는 **"setter 함수"가 없는** Property를 생성할 수 있습니다. **이는 본질적으로 식별자가 바뀌는 것을 방지합니다.**

여기에 클래스 구현을 사용하여 다른 방법으로 구현하였습니다.

이 코드는 상수에 관해 독자가 쉽게 이해할 수 없습니다. 아래 설명이 있습니다.

 

코드 설명:

1. 표현식을 취하는 constant 함수를 정의하고 "getter"를 생성하기 위해 그것을 사용합니다. 함수는 오직 표현식의 값만 리턴합니다.
2. setter 함수는 읽기 전용이기 때문에 TypeError를 발생시킵니다.
3. 읽기 전용 properties를 정의하기 위한 decoration으로써 우리가 방금 생성한 함수 constant를 사용합니다.

... 중략 ...
