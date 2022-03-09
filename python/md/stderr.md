출처 : [https://stackoverflow.com/questions/5574702/how-to-print-to-stderr-in-python](https://stackoverflow.com/questions/5574702/how-to-print-to-stderr-in-python)

# Python에서 표준에러로 출력하는 방법?

표준 출력으로 쓰는 몇가지 방법이 있습니다.

```python
# Note: 첫번째 문장은 Python 3에서 실행되지 않습니다.
print >> sys.stderr, "spam"

sys.stderr.write("spam\n")

os.write(2, b"spam\n")

from __future__ import print_function
print("spam", file=sys.stderr)
```

그것은 파이썬의 선 #13†과 모순되는 것처럼 보입니다. 그래서 여기의 차이점은 무엇이며 어떤 방법을 사용하던 장단점이 있습니까? 어떤 방법을 사용해야 합니까?

† 문제를 해결할 하나의 - 바람직하고 유일한 - 명백한 방법이 있을 것이다.

### 16개의 답변 중 1개의 답변

저는 짧고 유연하고 이식하기 좋고 읽기 좋은 유일한 방법을 찾았습니다.

```python
# 이 줄은 Python2에 관해 다룰 때만 있으면 됩니다.
from __future__ import print_function
import sys

def eprint(*args, **kwargs):
    print(*args, file=sys.stderr, **kwargs)
```

함수 `eprint`는 표준 `print` 함수와 같은 방법으로 사용될 수 있습니다.

```python
>>> print("Test")
Test
>>> eprint("Test")
Test
>>> eprint("foo", "bar", "baz", sep="---")
foo---bar---baz
```
