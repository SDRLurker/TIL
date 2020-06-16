출처 : [https://ongspxm.github.io/blog/2016/11/assertraises-testing-for-errors-in-unittest/](https://ongspxm.github.io/blog/2016/11/assertraises-testing-for-errors-in-unittest/)

# assertRaises - unittest에서 오류 테스트 하기

이 글에서, 저는 파이썬의 빌트인 unittest 모듈을 사용하였습니다.

갑자기 프레임워크에 대한 (잘못된 데이터가 입력되면 예외가 발생해야 하는) 새로운 요구가 생겼을 때 저는 제 프로젝트에서 몇 가지 테스트 작업을 하고 있었습니다.

저는 몇가지를 검색하였고 제가 필요한 것 - **TestCase.assertRaises** 같은 함수를 찾았습니다.

다른 모든 assert 함수가 unittest에서 사용되는 것과 동일한 형식으로 시도했습니다. 그래서 저는 다음과 같이 작성하였습니다.

```python
import unittest

def func():
    raise Exception('lets see if this works')

class ExampleTest(unittest.TestCase):
    def test_error(self):
        self.assertRaises(func(), Exception)

if __name__=='__main__':
    unittest.main()
```

논리적으로 보입니다. 그렇지 않습니까? 글쎄요. 나는 그것을 실행하려고 시도했고 테스트는 오류로 판명되었습니다.

![](http://i.imgur.com/v58W7N9.png)

예, 오류가 있다는 것을 알고 있습니다. 그것이 제가 테스트하고 있는 것입니다. 그렇지요? 왜 이런 일이 일어나고 있는지에 대해 혼란스러워서 파이썬 문서와 stackOverflow를 찾아 답을 찾았습니다.

## lambda 해결책

다음은 제가 검색하여 찾은 가장 일반적인 해결책입니다.

```python
import unittest

def func():
    raise Exception('lets see if this works')

class ExampleTest(unittest.TestCase):
    def test_error(self):
        self.assertRaises(Exception, lambda:func())

if __name__=='__main__':
    unittest.main()
```

## 제가 좋아하는 해결책

하지만 lambda는 그 자체로 전부 새로운 괴물입니다. 함수에 대한 wrapper로 사용하기 위해 올바른 도구는 아닌 듯 합니다.

다음은 assertRaise를 사용하는 매우 더 적절한 파이썬스러운 방법입니다.

```python
import unittest

def func():
    raise Exception('lets see if this works')

class ExampleTest(unittest.TestCase):
    def test_error(self):
        with self.assertRaises(Exception): func()

if __name__=='__main__':
    unittest.main()
```

## 왜 작동하지 않았을까요?

분명히 assertRaises가 작동하려면 예외를 잡기 위해 함수 호출이 일종의 wrapper 내에 포함되어야 합니다.

첫 번째 예제처럼 실행하면, 그 함수는 스스로 실행될 것이며 그 예외는 잡히지 않고 test case는 대신에 오류가 발생하게 됩니다.

lambda 메소드를 사용하면, lambda 함수는 함수가 실행될 wrapper로 작동하기 때문에 그 예외는 잡히게 되며 비교가 발생합니다.

"with 구문"도 똑같습니다. "with 구문"으로 함수 호출을 wrapping하면, 그 예외는 구문에서 "assertRaises"로 전달될 것입니다. "assertRaises" 함수는 이제 실행될 예외와 비교할 수 있으며 그래서 유용한 결과를 얻습니다.

### 성공적인 테스트 실행

![](http://i.imgur.com/8XLeOiz.png)

더 기술적인 세부사항에 관심 있으시면, unittest 소스 코드를 살펴보세요 (소스 코드는 언제든지 가능하며, 파이썬이 짱인 이유입니다.)

다른 문제가 있으시면 저에게 세부사항에 대한 이메일이나 댓글 남겨주세요.
