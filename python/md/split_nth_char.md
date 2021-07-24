출처 : [http://stackoverflow.com/questions/9475241/split-python-string-every-nth-character](http://stackoverflow.com/questions/9475241/split-python-string-every-nth-character)

# n번째 문자마다 문자열을 나누는 방법?

n번째 문자마다 파이썬 문자열을 나누는 것이 가능한가요?

예를 들어, 다음을 포함하는 문자열이 있다고 가정합니다.

```python
'1234567890'
```

다음처럼 보이게 하려면 어떻게 해야 할까요?

```python
['12','34','56','78','90']
```

----

## 17 개의 답변 중 3개의 답변만 추려냄.

```python
>>> line = '1234567890'
>>> n = 2
>>> [line[i:i+n] for i in range(0, len(line), n)]
['12', '34', '56', '78', '90']
```

----

파이썬에 내장 함수로 이를 구현한 것이 이미 있습니다.

```python
>>> from textwrap import wrap
>>> s = '1234567890'
>>> wrap(s, 2)
['12', '34', '56', '78', '90']
```

docstring이 wrap에 대해 말하는 내용 입니다.

```python
>>> help(wrap)
'''
Help on function wrap in module textwrap:

wrap(text, width=70, **kwargs)
    Wrap a single paragraph of text, returning a list of wrapped lines.

    Reformat the single paragraph in 'text' so it fits in lines of no
    more than 'width' columns, and return a list of wrapped lines.  By
    default, tabs in 'text' are expanded with string.expandtabs(), and
    all other whitespace characters (including newline) are converted to
    space.  See TextWrapper class for available keyword args to customize
    wrapping behaviour.
'''
```

----

이 소스가 itertools 보다 더 짧고 읽기 좋다고 생각합니다.

```python
def split_by_n( seq, n ):
    """A generator to divide a sequence into chunks of n units."""
    while seq:
        yield seq[:n]
        seq = seq[n:]

print list(split_by_n("1234567890",2))
```

--------

위의 소스와 관련하여

http://haerakai.tistory.com/34

위 주소의 제너레이터와 이더레이터 부분을 보시면 더 이해하기 좋다고 생각합니다.

--------

위의 답변을 참고하여 
https://github.com/SDRLurker/TIL/blob/master/python/slice.py
소스를 제작하였습니다.
