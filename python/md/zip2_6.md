# Python 2.7 코드를 Python 2.6에서도 작동하게 만들기

저는 zip 파일을 풀 수 있는 간단한 Python 함수를 만들었습니다. (플랫폼 독립적으로)

```python
def unzip(source, target):
    with zipfile.ZipFile(source , "r") as z:
        z.extractall(target)
    print "Extracted : " + source +  " to: " + target
```

이는 Python 2.7에서는 잘 실행되지만 Python 2.6에서는 실패합니다.

```
AttributeError: ZipFile instance has no attribute '__exit__':
```

저는 2.6에서 2.7로 업그레이드 할 것을 제안한 글을 찾았습니다.
하지만 위의 코드를 Python 2.6에서 작동할 수 있도록 플랫폼에 관계없이 이식이 가능한가요??

------

## 2개의 답변 중 1 개의 답변만 추려냄.

다음 코드는 어떻습니까?

```python
import contextlib

def unzip(source, target):
    with contextlib.closing(zipfile.ZipFile(source , "r")) as z:
        z.extractall(target)
    print "Extracted : " + source +  " to: " + target
```

contextlib.closing는 ZipFile에서 빠져있는 ```__exit__```메소드가 하려고 했던 것을 분명히 실행합니다. 이름처럼 close메소드를 호출합니다.



출처: https://sdr1982.tistory.com/229?category=637927 [려리군의 블로그]
