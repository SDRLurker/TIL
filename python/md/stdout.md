출처 : [https://stackoverflow.com/Questions/4374455/how-to-set-sys-stdout-encoding-in-python-3](https://stackoverflow.com/Questions/4374455/how-to-set-sys-stdout-encoding-in-python-3)

# Python3에서 sys.stdout 인코딩 하는 방법

Python 2에서 기본 출력 인코딩으로 설정하는 것은 잘 알려진 구문입니다.

```python
sys.stdout = codecs.getwriter("utf-8")(sys.stdout)    
```

이는 UTF-8로 출력을 인코딩하여 codec writer에서 `sys.stdout`를 포장(wrap)합니다.

하지만, 이 기술은 Python 3 에서는 작동하지 않습니다. 그 이유는 `sys.stdout.write()`는 `str`를 예상하는데 인코딩의 결과는 `bytes`이고 `codecs`가 원래 `sys.stdout`로 인코딩된 바이트배열을 출력하려고 할 때 오류가 발생합니다.

Python 3에서 이를 할 수 있는 올바른 방법은 무엇입니까?

---

## 7개의 답변 중 2개의 답변

Python 3.7부터 당신은 [`reconfigure()`](https://docs.python.org/ko/3/library/io.html#io.TextIOWrapper.reconfigure)로 표준 스트림의 인코딩을 변경할 수 있습니다.

```python
sys.stdout.reconfigure(encoding='utf-8')
```

당신은 `errors` 파라미터를 추가하여 인코딩 오류가 다뤄질 수 있도록 수정할 수 있습니다.

---

Python 3.1에 `io.TextIOBase.detach()`가 추가되었습니다. 다음은 [`sys.stdout`](https://docs.python.org/ko/3/library/sys.html#sys.stdout)에 대한 문서 내용입니다.

> 표준 스트림은 기본으로 text 모드입니다. 이 스트림에 이진(binary) 데이터를 쓰거나 읽기 위해 기본 바이너리 버퍼를 사용합니다. 예를 들어 `stdout`에 바이트 배열을 쓰기 위해 `sys.stdout.buffer.write(b'abc')`를 사용합니다. `io.TextIOBase.detach()`를 사용함으로써, 스트림은 기본으로 바이너리가 될 수 있습니다. 이 함수는 바이너리로 `stdin`과 `stdout`을 설정합니다.
> ```python
> def make_streams_binary():
>     sys.stdin = sys.stdin.detach()
>     sys.stdout = sys.stdout.detach()
> ```

그리하여, Python 3.1 이상에서 대응되는 구문은 다음과 같습니다.

```python
sys.stdout = codecs.getwriter("utf-8")(sys.stdout.detach())
```
