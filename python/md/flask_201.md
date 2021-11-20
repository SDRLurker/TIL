**출처**

[https://stackoverflow.com/questions/7824101/return-http-status-code-201-in-flask](https://stackoverflow.com/questions/7824101/return-http-status-code-201-in-flask)

# Flask에서 HTTP 상태 코드 201 리턴하기

우리는 API 중 하나를 위해 Flask를 사용하였고 저는 HTTP 응답 201을 리턴하는 방법을 알고 계신 분이 있는지 궁금합니다.

404 같은 오류는 다음과 같이 호출할 수 있습니다.

```python
from flask import abort
abort(404)
```

하지만 201을 얻으려면

> LookupError: no exception for 201

이 문서에서 [이것](http://werkzeug.pocoo.org/docs/exceptions/#custom-errors)처럼 예외를 생성할 필요가 있습니까?

---

## 11 개의 답변 중 1 개의 답변

당신은 아무 HTTP 상태 코드를 리턴할 수 있는 Response를 사용할 수 있습니다.

```python
> from flask import Response
> return Response("{'a':'b'}", status=201, mimetype='application/json')
```
