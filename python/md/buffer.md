출처 : [http://stackoverflow.com/questions/3422685/what-is-python-buffer-type-for](http://stackoverflow.com/questions/3422685/what-is-python-buffer-type-for)

# Python의 buffer 타입은 무엇을 위한 건가요?

Python에는 buffer타입이 있습니다만 저는 어떻게 그것을 사용해야 할 지 모르겠습니다. 

[Python doc](https://docs.python.org/ko/3/library/functions.html#buffer) 설명은 다음과 같습니다.

> `buffer(object[, offset[, size]])`
> 
> object 매개변수는 buffer 호출 인터페이스(문자열, 배열, 버퍼등)를 지원하는 객체여야 합니다. 새로운 buffer 객체는 object 매개변수를 참조하도록 만들어질 것입니다. buffer 객체는 객체의 시작부터 (또는 offset 위치부터) slice 될 수 있습니다. 이 slice는 객체의 끝 (또는 size 매개변수로 주어진 길이)까지 확장될 수 있습니다.

---
## 2 개의 답변 중 1 개의 답변

다음은 사용 예시입니다.

```python
>>> s = 'Hello world'
>>> t = buffer(s, 6, 5)
>>> t
<read-only buffer for 0x10064a4b0, size 5, offset 6 at 0x100634ab0>
>>> print t
world
```

이 경우 buffer는 6번째 위치에서 시작하며 길이가 5인 부분 문자열(sub-string)입니다. 그리고 이는 추가 저장 공간을 필요로 하지 않습니다. 그 이유는 <u>string의 slice를 참조하기 때문</u> 입니다.

이 코드 예시처럼 짧은 문자열에는 매우 유용하지는 않습니다. 하지만 큰 데이터를 사용할 때 필요할 수 있습니다. 이 예시는 변경가능한 `bytearray`를 사용하였습니다.

```python
>>> s = bytearray(1000000)   # 백만개의 0 값인 바이트배열 
>>> t = buffer(s, 1)         # 인덱스 1의 바이트만 자름
>>> s[1] = 5                 # s에서 두 번째 요소를 5로 세팅
>>> t[0]                     # t에서 첫번째 요소가 s[1]이기도 함
'\x05'
```

이는 데이터에서 하나 이상의 view가 필요할 때 매우 유용하며 메모리에서 여러개의 복사본을 만들고 싶지 않을 때 유용합니다.

[`buffer`](https://docs.python.org/ko/3/library/functions.html#buffer)는 Python 2.7에도 사용할 수 있지만 Python 3에서 더 좋은 이름인 [`memoryview`](https://docs.python.org/ko/3/library/stdtypes.html#memoryview)로 바뀌었습니다.

또한 C API를 살펴 보지 않고는 자체 객체에 대한 버퍼 인터페이스를 구현할 수 없습니다. 즉, 순수 Python에서는 수행 할 수 없습니다.
