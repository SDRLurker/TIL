출처 : [http://stackoverflow.com/questions/2682745/how-to-create-a-constant-in-python](http://stackoverflow.com/questions/2682745/how-to-create-a-constant-in-python)

# Python에서 상수를 만드는 방법

파이썬에서 상수를 선언할 수 있는 방법이 있습니까? 자바에서는 다음 방법으로 상수를 만들 수 있습니다.

```java
public static final String CONST_NAME = "Name";
```

파이썬에서 위의 자바 상수 선언과 같은 방법은 무엇입니까?

---

## 43 개의 답변 중 2 개의 답변

아니오. 방법이 없습니다. 파이썬에서 상수를 선언할 수 없습니다. 그저 그 변수를 바꾸지 마세요.만약 클래스에 있으면, 같은 방법은

```python
class Foo(object): 
    CONST_NAME = "Name"
```

클래스에 없다면,

```python
CONST_NAME = "Name"
```

처럼 사용할 수 있습니다.

하지만, Alex Martelli에 [Constants in Python](https://code.activestate.com/recipes/65207-constants-in-python/?in=user-97991) 의 코드 조각을 보시길 원하실 거라 생각합니다.

Python 3.8부터, (mypy처럼) 정적 타입 체커가 변수가 재할당되지 말아야 한다고 알려주는 typing.Final 표기법이 있습니다. 이는 Java의 final과 거의 같습니다. 하지만, **재할당을 실제 막지는 못합니다.**

```python
from typing import Final

a: Final = 1

# 잘 실행됩니다, 하지만 mypy에서 이를 실행한다면 오류를 보고할 것입니다.
a = 2
```

---

다른 언어처럼 `const` 키워드가 없습니다. 하지만, 데이터를 읽는 **"getter 함수"를 가지지만** 데이터를 다시 쓰는 **"setter 함수"가 없는** Property를 생성할 수 있습니다. **이는 본질적으로 식별자가 바뀌는 것을 방지합니다.**

여기에 클래스 구현을 사용하여 다른 방법으로 구현하였습니다.

이 코드는 상수에 관해 독자가 *쉽게 이해할 수 없습니다. 아래 설명이 있습니다.*

```python
def constant(f):
    def fset(self, value):
        raise TypeError
    def fget(self):
        return f()
    return property(fget, fset)

class _Const(object):
    @constant
    def FOO():
        return 0xBAADFACE
    @constant
    def BAR():
        return 0xDEADBEEF

CONST = _Const()

print CONST.FOO
##3131964110

CONST.FOO = 0
##Traceback (most recent call last):
##    ...
##    CONST.FOO = 0
##TypeError: None
```

**코드 설명:**

1. 표현식을 취하는 `constant` 함수를 정의하고 "getter"를 생성하기 위해 그것을 사용합니다. 함수는 오직 표현식의 값만 리턴합니다.
2. setter 함수는 읽기 전용이기 때문에 TypeError를 발생시킵니다.
3. 읽기 전용 properties를 정의하기 위한 decoration으로써 우리가 방금 생성한 함수 `constant`를 사용합니다.

그리고 좀 더 구식으로:

(코드는 꽤 까다롭습니다. 아래에 더 많은 설명이 있습니다.)

```python
class _Const(object):
    def FOO():
        def fset(self, value):
            raise TypeError
        def fget(self):
            return 0xBAADFACE
        return property(**locals())
    FOO = FOO()  # Define property.

CONST = _Const()

print(hex(CONST.FOO))  # -> '0xbaadfaceL'

CONST.FOO = 0
##Traceback (most recent call last):
##  File "example2.py", line 16, in <module>
##    CONST.FOO = 0
##  File "example2.py", line 6, in fset
##    raise TypeError
##TypeError
```

1. 식별자 FOO를 정의하기 위해 먼저 두 개의 함수를 정의합니다(fset, fget - 이름은 내가 선택함).
2. 그런 다음 내장 `property` 기능을 사용하여 "set" 또는 "get"가 가능한 개체를 구성합니다.
3. `property` 함수의 처음 두 매개변수의 이름은 fset 및 fget입니다.
4. 우리 고유의 getter 및 setter에 대해 바로 이 이름을 선택했다는 사실을 사용하고 `property` 함수에 매개변수를 전달하기 위해 해당 범위의 모든 로컬 정의에 적용된 **(이중 별표)를 사용하여 키워드 사전을 만듭니다.
