출처 - https://stackoverflow.com/questions/27387786/reload-a-module-in-python-3-4

# Python 3.4에서 모듈 다시 불러오기

## 질문

## 답변

imp 모듈은 importlib 모듈을 위해 Python 3.4에서 더 이상 사용되지 않습니다. imp 모듈에 대한 문서에서 :

버전 3.4부터는 더 이상 사용되지 않습니다 : imp 패키지는 importlib에 찬성하여 지원 중단 될 예정입니다.

그래서 거기에서 reload 함수를 사용해야합니다.

```python
>>> import importlib
>>> importlib.reload
<function reload at 0x01BA4030>
>>> importlib.reload (the_module)
```

---
출처 - https://stackoverflow.com/questions/22442546/how-to-reload-after-from-module-import

# “from <module> import *” 이후에 모듈 다시 불러오는 방법?

## 질문

정상적으로 가져올 때(imp.reload(모듈 또는 alias))를 통해 간단히 모듈을 다시 로드할 수 있습니다. 그러나 (from module import *)로 현재 네임스페이스로 가져온 후 모든 것을 다시 불러올 수 있습니까?

imf.reload(모듈)은 작동하지 않으며 "name: module is not defined" 라고 출력됩니다.

## 답변
```from module import *```에서 해당 모듈의 모든 항목을 현재 네임 스페이스로 가져 오면 끝에 있는 ```module``` 참조가 제거됩니다. 하지만 모듈 캐싱으로 인해 모듈 객체는 ```sys.modules```에서 계속 액세스 할 수 있으므로 나중에 모듈을 가져올 필요가 없다기 보다 나중에 import를 또 수행 할 수 있습니다.

즉, 기대하는 바를 수행하는 한 가지 방법은 다음과 같습니다.

```python
import sys
from foo import *
print A, B      # 1, 2를 출력합니다.
A, B = 100, 200
mod = reload(sys.modules['foo'])        # 파이썬 3에 imp.reload 사용
mod = reload(sys.modules.get('foo','')) # 파이썬 3.5에 imp.reload 사용
vars(). update (mod .__ dict__) # 전역 네임 스페이스 업데이트
print A, B      # 1, 2를 출력합니다.
```
부수적으로, import *를 사용하는 것은 보통 다음과 같은 경우에 [눈살을 찌푸리게됩니다](https://docs.python.org/2/tutorial/modules.html#more-on-modules).

> 일반적으로 모듈 또는 패키지에서 *를 가져 오는 연습은 눈에 띄지 않습니다. 왜냐하면 종종 가독성이 낮은 코드가 생성되기 때문입니다. 그러나 대화 형 세션에서 타이핑을 저장하는 데 사용할 수 있습니다.
