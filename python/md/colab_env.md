출처  
[https://stackoverflow.com/questions/49684495/is-it-possible-to-set-environment-variables-in-googles-colaboratory](https://stackoverflow.com/questions/49684495/is-it-possible-to-set-environment-variables-in-googles-colaboratory)

## Google Colaboratory에서 환경 변수를 설정하는 것이 가능합니까?

저는 Google [Colaboratory](https://colab.research.google.com/) 플랫폼에서 파이썬 스크립트를 실행하고 있습니다. 이제, 저는 다음처럼 시스템의 환경 변수를 세팅하야 합니다.

```shell
!export PATH=drive/app/tf-models-fork/research;drive/app/tf-models-fork/research/object_detection;drive/app/tf-models-fork/research/slim;$PATH
```

저는 PATH 변수를 추가하는 것을 시도했습니다. 하지만 다음과 같은 오류가 발생하였습니다.

```shell
/bin/sh: 1: drive/app/tf-models-fork/research/object_detection: Permission denied
/bin/sh: 1: drive/app/tf-models-fork/research/slim: Permission denied
/bin/sh: 1: drive/app/tf-models-fork/research: Permission denied
```

이 플랫폼에서 환경 변수를 설정할 수 있는 방법이 있을까요?

---

### 2개의 답변 중 1개의 답변만 추려냄

저는 다음처럼 `os.environ`으로 `PATH`를 다음처럼 설정합니다.

```python
import os
os.environ['PATH'] += ":/usr/local/go/bin"
```
