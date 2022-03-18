출처 : [https://stackoverflow.com/questions/32613375/python-2-7-exception-handling-syntax](https://stackoverflow.com/questions/32613375/python-2-7-exception-handling-syntax)

# 파이썬 2.7 예외 처리 구문

파이썬 2.7에서 try exception 사용에 관해 좀 혼란스럽습니다.

```python
try:
    raise valueError("sample value error")
except Exception as e:
    print str(e)    

try:
    raise valueError("sample value error")
except Exception,exception:
    print str(exception)


try:
    raise valueError("sample value error")
except exception:
    print str(exception)


try:
    raise valueError("sample value error")
except Exception:
    print str(Exception) # 이는 객체 참조만 출력합니다.
```

위의 예시를 이해할 수 있도록 저에게 설명해주실 수 있나요?

----

## 4 개의 답변 중 1개의 답변

`except` 변형의 다른 변형 간의 차이점을 이해하는 데 도움이 되는 몇 가지 개념:

* `except Exception, e` – `except Exception as e`과 유사하게 더 이상 사용되지 않는 오래된 변형
* `except Exception as e` - `Exception`(또는 모든 하위 클래스)의 예외를 catch하고 추가 처리, 메시징 또는 이와 유사한 것을 `e` 변수에 저장합니다.
* `except Exception` –  `Exception` 타입(또는 모든 하위 클래스)의 예외를 catch하지만 예외에 제공된 값/정보는 무시합니다.
* `except e` – 컴파일 오류가 발생합니다. 이것이 파이썬 버전과 관련이 있는지 확실하지 않지만 그렇다면 예외 유형에 신경 쓰지 않고 해당 정보에 액세스하려는 것을 의미해야 합니다.
* `except` – 어떤 예외도 포착하고 예외 정보를 무시합니다.

무엇을 사용할지는 많은 요인에 따라 다르지만 예외에 제공된 정보가 필요하지 않은 경우 이 정보를 포착하기 위해 변수를 제시할 필요가 없습니다.

catch할 `Exception` 타입과 관련하여 정확한 유형의 예외를 포착하도록 주의하십시오. 일반적인 모든 예외의 catch를 작성하는 경우 `except Exception`을 사용하는 것이 정확할 수 있지만 귀하가 제공한 예제의 경우 실제로 `except ValueError` 오류를 직접 사용하도록 선택할 것입니다. 이렇게 하면 잠재적으로 다른 예외가 코드의 다른 수준에서 적절하게 처리될 수 있습니다. 요점은 처리할 준비가 되지 않은 예외를 catch하지 마십시오.

원하는 경우 공식 문서에서 [python 2.7 예외 처리](https://docs.python.org/ko/2/tutorial/errors.html#handling-exceptions) 또는 사용 가능한 [python 2.7 예외](https://docs.python.org/ko/2/library/exceptions.html)에 대해 자세히 읽을 수 있습니다.
