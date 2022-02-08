출처 : [https://stackoverflow.com/questions/32234156/how-to-unimport-a-python-module-which-is-already-imported](https://stackoverflow.com/questions/32234156/how-to-unimport-a-python-module-which-is-already-imported)

# 이미 import된 python 모듈을 unimport 하는 방법?

저는 NumPy/SciPy에 매우 신입입니다. 요즘 Matlab을 사용하는 대신에 숫자 계산을 위해 매우 활발하게 그것을 사용하기 시작하였습니다.

간단한 계산을 위해 스크립트를 작성하는 것보다 interactive 모드에서 이를 실행합니다. 이러한 경우 이미 import한 모듈을 unimport하는 방법이 있을까요? unimport는 제가 파이썬 프로그램을 작성할 때는 필요 없겠지만, interactive 모듈에서는 필요합니다.

## 4개의 답변 중 1개의 답변

당신이 import한 것을 unload 하는 방법은 없습니다. 파이썬은 cache에 모듈의 복사본을 유지하기 때문에 다음에 reload와 다시 초기화하지 않고 그것을 (그대로) import합니다.

만약 당신이 필요한 게 그것으로 접근하지 않도록 하려면 `del`을 사용할 수 있습니다.

```python
import package
del package
```

그런 다음 패키지를 다시 import하면 모듈의 캐시된 복사본이 사용됩니다.

다시 가져올 때 코드를 다시 실행할 수 있도록 모듈의 캐시된 복사본을 무효화하려면 @DeepSOIC의 답변에 따라 대신 `sys.modules.pop`을 사용할 수 있습니다.

당신이 패키지를 변경했고 갱신된 내용을 보고 싶다면, 당신은 그것을 `reload` 할 수 있습니다. 이는 몇가지 경우 작동하지 않을 수 있는데 import된 패키지가 그것에 의존적인 패키지를 reload할 필요가 있을 때입니다. 이것에 의존적인 것 이전에 관련된 문서를 읽어봐야 합니다.

Python 버전 2.7까지는 build-in 함수인 [`reload`](https://docs.python.org/ko/2/library/functions.html#reload) 를 사용합니다.

```python
reload(package)
```

Python 3.0부터 3.3까지는 당신은 [`imp.reload`](https://docs.python.org/3.3/library/imp.html#imp.reload) 를 사용할 수 있습니다.

```python
import imp
imp.reload(package)
```

Python 3.4 이상이라면 당신은 [`importlib.reload`](https://docs.python.org/ko/3/library/importlib.html#importlib.reload) 를 사용할 수 있습니다.

```python
import importlib
importlib.reload(package)
```
