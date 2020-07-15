출처 : [https://stackoverflow.com/questions/40286841/what-is-the-best-way-to-raise-request-errors](https://stackoverflow.com/questions/40286841/what-is-the-best-way-to-raise-request-errors)

# request 오류를 발생하는 최고의 방법?

저는 다음 방법으로 나쁜 요청(requests)에 대해 오류를 발생하는 소스 코드를 읽고 있습니다.

```python
import requests

response = requests.get("www.google.com")   # http:// 가 없기 때문에 작동하지 않습니다.
if response.ok is False or response.json()['status'] != 'success':
    raise Exception("API error: {}".format(response.json()['message']))
```

저는 마지막 2줄을 다음 구문으로 대체해야겠다고 생각하였습니다.

```python
response.raise_for_status()
```

오류가 리턴한 내용에서 차이점을 발견하지 못했습니다. 2가지 모두 다음과 같이 나왔습니다.

```
Traceback (most recent call last):
  File "/home/kurt/Documents/Scratch/requests_test.py", line 3, in <module>
    response = requests.get("www.google.com")   # This won't work because it's missing the http://
  File "/home/kurt/.local/lib/python2.7/site-packages/requests/api.py", line 69, in get
    return request('get', url, params=params, **kwargs)
  File "/home/kurt/.local/lib/python2.7/site-packages/requests/api.py", line 50, in request
    response = session.request(method=method, url=url, **kwargs)
  File "/home/kurt/.local/lib/python2.7/site-packages/requests/sessions.py", line 451, in request
    prep = self.prepare_request(req)
  File "/home/kurt/.local/lib/python2.7/site-packages/requests/sessions.py", line 382, in prepare_request
    hooks=merge_hooks(request.hooks, self.hooks),
  File "/home/kurt/.local/lib/python2.7/site-packages/requests/models.py", line 304, in prepare
    self.prepare_url(url, params)
  File "/home/kurt/.local/lib/python2.7/site-packages/requests/models.py", line 362, in prepare_url
    to_native_string(url, 'utf8')))
requests.exceptions.MissingSchema: Invalid URL 'www.google.com': No schema supplied. Perhaps you meant http://www.google.com?
```

`raise_for_status()`는 더 간결하고 아마도 원래 예외에 대한 정보를 잃지 않는 것 같습니다 (참조. ["except Exception"대 파이썬에서 "except ... raise" 사용](https://stackoverflow.com/questions/40280776/use-of-except-exception-vs-except-raise-in-python)). 이것이 실제로 더 나은 접근법일까요?

---

### 1개의 답변

`response.raise_for_status()`는 응답의 상태 코드가 200이 아닌 응답일 때만 예외(Exception)를 발생합니다. 두 번째 경우인 `response.json()['status'] != 'success'`가 만족할 경우, 커버하지 않습니다.

하지만 **다른** 오류가 있습니다. `requests.get()` 호출에 의해 예외가 발생하므로 `if` 테스트에 도달하지 않습니다. 스키마를 전달하지 못했습니다 (문자열 앞에 `http://` 또는 `https://` 없음). requests.get() 표현식에서 예외가 발생하므로 다음 행은 절대로 실행되지 않습니다. 요청도 전송되지 않으므로 응답에 대한 assertion도 만들 수 없습니다.

테스트는 더 많은 문제가 있습니다.

-   `requests.ok is False` 관용적인 파이썬 표현이 아닙니다. 대신에 `not requests.ok`를 사용하는 것이 좋습니다.
-   만약 `requests.ok is False`를 만족하면, `requests.json()`은 대부분 실패할 것입니다. 그래서 `response.json()['message']`를 사용한 다음 줄은 다른 오류를 발생할 것입니다.
