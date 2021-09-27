출처 - https://stackoverflow.com/questions/27387786/reload-a-module-in-python-3-4

# Python 3.4에서 모듈 다시 불러오기

## 질문

나는 이것이 정말로 어리석은 질문처럼 들릴 수 있다는 것을 알고 있습니다. 나는 파이썬으로 작은 스크립트를 만들었고 쉘에 있는 동안 몇 가지를 변경했습니다. 일반적으로 OS X 컴퓨터(Python 2.7 실행)에서 `reload(the_module)`를 입력하면 변경 사항이 포함된 모듈이 다시 로드됩니다. 그러나 여기에 모듈을 다시 로드할 때(Windows python v. 3.4에서) 다음과 같이 표시됩니다.

```python
>>> reload(instfile)
Traceback (most recent call last):
  File "<pyshell#5>", line 1, in <module>
    reload(instfile)
NameError: name 'reload' is not defined
```
  
그런 다음 `imp.reload(my_module)`를 입력하면 단순히 해당 함수가 더 이상 사용되지 않는다는 메시지가 표시됩니다. 나는 새로운 기능(또는 그에 상응하는 기능)이 어디에 있는지 찾을 수 없는 것 같으므로 누군가가 나를 도와주실 수 있으시면 좋겠습니다! :)

## 답변

`imp` 모듈은 [`importlib 모듈`](https://docs.python.org/ko/3/library/importlib.html#module-importlib)을 위해 Python 3.4에서 더 이상 사용되지 않습니다. `imp` 모듈에 대한 [문서](https://docs.python.org/ko/3/library/imp.html)에서 :

> 버전 3.4부터는 더 이상 사용되지 않습니다 : `imp` 패키지는 `importlib`에 찬성하여 지원 중단될 예정입니다.

그래서 거기에서 `reload` 함수를 사용해야합니다.

```python
>>> import importlib
>>> importlib.reload
<function reload at 0x01BA4030>
>>> importlib.reload (the_module)
```

---

출처 - https://stackoverflow.com/questions/32234156/how-to-unimport-a-python-module-which-is-already-imported

# 이미 import된 python 모듈을 unimport 하는 방법?

## 질문

저는 NumPy/SciPy에 매우 신입입니다. 요즘 Matlab을 사용하는 대신에 숫자 계산을 위해 매우 활발하게 그것을 사용하기 시작하였습니다.

간단한 계산을 위해 스크립트를 작성하는 것보다 interactive 모드에서 이를 실행합니다. 이러한 경우 이미 import한 모듈을 unimport하는 방법이 있을까요? unimport는 제가 파이썬 프로그램을 작성할 때는 필요 없겠지만, interactive 모듈에서는 필요합니다.

## 답변

당신이 import한 것을 unload 하는 방법은 없습니다. 파이썬은 cache에 모듈의 복사본을 유지하기 때문에 다음에 reload와 다시 초기화하지 않고 그것을 (그대로) import합니다.  
만약 당신이 필요한 게 그것으로 접근하지 않도록 하려면 `del`을 사용할 수 있습니다.

```python
import package
del package
```

그런 다음 패키지를 다시 가져오면 모듈의 캐시된 복사본이 사용됩니다.

다시 가져올 때 코드를 다시 실행할 수 있도록 모듈의 캐시된 복사본을 무효화하려면 @DeepSOIC의 답변에 따라 대신 `ys.modules.pop`을 사용할 수 있습니다.

패키지를 변경했고 업데이트를 보려면 `reload`할 수 있습니다. 예를 들어 가져온 패키지가 종속된 패키지도 다시 로드해야 하는 경우와 같이 일부 경우에는 작동하지 않습니다. 이에 의존하기 전에 관련 문서를 읽어야 합니다.

Python 버전 2.7까지는 build-in 함수인 [reload](https://docs.python.org/2/library/functions.html#reload) 를 사용합니다.

```python
reload(package)
```

Python 3.0부터 3.3까지는 당신은 [imp.reload](https://docs.python.org/3.3/library/imp.html#imp.reload) 를 사용할 수 있습니다.

```python
import imp
imp.reload(package)
```

Python 3.4 이상이면 당신은 [importlib.reload](https://docs.python.org/3.4/library/importlib.html#importlib.reload) 를 사용할 수 있습니다.

```python
import importlib
importlib.reload(package)
```

---
출처 - https://stackoverflow.com/questions/22442546/how-to-reload-after-from-module-import

# “from \<module\> import *” 이후에 모듈 다시 불러오는 방법?

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
부수적으로, `import *`를 사용하는 것은 보통 다음과 같은 경우에 [눈살을 찌푸리게됩니다](https://docs.python.org/2/tutorial/modules.html#more-on-modules).

> 일반적으로 모듈 또는 패키지에서 *를 가져 오는 연습은 눈에 띄지 않습니다. 왜냐하면 종종 가독성이 낮은 코드가 생성되기 때문입니다. 그러나 대화 형 세션에서 타이핑을 저장하는 데 사용할 수 있습니다.
