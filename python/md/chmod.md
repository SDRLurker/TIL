출처 : [https://stackoverflow.com/questions/12791997/how-do-you-do-a-simple-chmod-x-from-within-python](https://stackoverflow.com/questions/12791997/how-do-you-do-a-simple-chmod-x-from-within-python)

# python에서 간단하게 "chmod +x"을 어떻게 합니까?

저는 실행할 수 있는 python 스크립트 파일을 만들고 싶습니다.

```python
import os
import stat
os.chmod('somefile', stat.S_IEXEC)
```

`os.chmod`는 unix가 `chmod`를 하는 방법으로 권한을 '추가'하지는 않는듯이 보입니다. 마지막 행을 주석 처리한 상태에서 그 파일의 파일모드는 `-rw-r--r--`입니다. 모드의 나머지를 그대로 유지하면서 어떻게 u+x 플래그를 추가할 수 있습니까?

## 7개의 답변 중 1개의 답변만 추려냄

현재 권한을 얻기 위해 `os.stat()` 사용하시고, 비트를 결합하기 위해 `|` 사용하여 갱신된 권한을 설정하기 위해 `os.chmod()`를 사용하시면 됩니다.

예시:

```python
import os
import stat

st = os.stat('somefile')
os.chmod('somefile', st.st_mode | stat.S_IEXEC)
```
