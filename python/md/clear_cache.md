**출처**

[https://stackoverflow.com/questions/20198274/how-do-i-clear-cache-with-python-requests](https://stackoverflow.com/questions/20198274/how-do-i-clear-cache-with-python-requests)

# Python Requests로 캐시를 지우려면 어떻게 하나요?

기본으로 파이썬의 `requests` 패키지는 데이터를 캐시 하나요?

예를 들어

```python
import requests
resp = requests.get('https://some website')
```

이 응답은 캐시됩니까? 그렇다면 어떻게 지우나요?

---

## 5 개의 답변 중 2 개의 답변

`'Cache-Control: no-cache'` 헤더를 추가합니다.

```python
self.request = requests.get('http://google.com',
                            headers={'Cache-Control': 'no-cache'})
```

완벽한 답변은 아래 글을 확인하세요.

---

답변이 늦었지만 [python requests](https://docs.python-requests.org/en/master/)은 **요청을 캐시하지 않으므로** 대신 `Cache-Control` 및 `Pragma` 헤더를 사용해야 합니다.

```python
import requests
h = {
    ...
    "Cache-Control": "no-cache",
    "Pragma": "no-cache"
}
r = requests.get("url", headers=h)
...
```

[HTTP/Headers](https://developer.mozilla.org/ko/docs/Web/HTTP/Headers)

> [`Cache-Control`](https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Cache-Control)
> Cache-Control 일반 헤더 필드는 요청과 응답 모두에서 캐싱 메커니즘에 대한 지시문을 지정하는 데 사용됩니다. 캐싱 지시문은 단방향입니다. 즉, 요청에 지정된 지시문이 응답에 동일한 지시문이 제공되어야 함을 의미하지 않습니다.

> [`Pragma`](https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Pragma)
> 요청-응답 체인을 따라 다양한 영향을 미칠 수 있는 구현별 헤더입니다. Cache-Control 헤더가 아직 존재하지 않는 HTTP/1.0 캐시와의 하위 호환성을 위해 사용됩니다.

**지시자**

> [`no-cache`](https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Cache-Control#directives)
> 캐시된 복사본을 응답(배포)하기 전에 유효성 검사를 위해 원본 서버에 요청을 제출하도록 캐시를 (사용하지 않도록) 강제합니다.

`Pragma`에 대한 참고 사항:

> Pragma는 HTTP 응답에 대해 지정되지 않았으므로 Cache-Control 헤더 필드가 생략된 경우 Cache-Control: no-cache와 동일하게 동작하지만 일반 HTTP/1.1 Cache-Control 헤더를 신뢰할 수 있는 대체품이 아닙니다. 요청. HTTP/1.0 클라이언트와의 역호환성을 위해서만 Pragma를 사용하십시오.
