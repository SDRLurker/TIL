**출처**

[https://stackoverflow.com/questions/12385179/how-to-send-a-multipart-form-data-with-requests-in-python](https://stackoverflow.com/questions/12385179/how-to-send-a-multipart-form-data-with-requests-in-python)

# 파이썬에서 요청과 함께 "multipart/form-data"를 보내는 방법은 무엇입니까?

파이썬에서 `requests`와 함께 `multipart/form-data`를 보내는 방법은 무엇입니까? 파일을 보내는 방법은 알겠는데 이 방법으로 폼(form) 데이터를 보내는 방법을 모르겠습니다.

---

## 13 개의 답변 중 1 개의 답변

기본적으로 `files` 매개변수(dictionary)를 지정하면 요청은 `application/x-www-form-urlencoded` POST 대신 `multipart/form-data` POST를 보냅니다. 그러나 해당 dictionary에서 실제 파일을 사용하는 것으로 제한되지는 않습니다.

```python
>>> import requests
>>> response = requests.post('http://httpbin.org/post', files=dict(foo='bar'))
>>> response.status_code
200
```

httpbin.org는 게시한 헤더를 알려줍니다. `response.json()`에는 다음이 있습니다.

```python
>>> from pprint import pprint
>>> pprint(response.json()['headers'])
{'Accept': '*/*',
 'Accept-Encoding': 'gzip, deflate',
 'Connection': 'close',
 'Content-Length': '141',
 'Content-Type': 'multipart/form-data; '
                 'boundary=c7cbfdd911b4e720f1dd8f479c50bc7f',
 'Host': 'httpbin.org',
 'User-Agent': 'python-requests/2.21.0'}
```

더 나은 방법은 단일 문자열 또는 바이트열 개체 대신 튜플을 사용하여 각 부분에 대한 파일 이름, 콘텐츠 유형 및 추가 헤더를 추가로 제어할 수 있다는 것입니다. 튜플은 2~4개의 요소를 포함할 것으로 예상됩니다. 파일 이름, 콘텐츠, 선택적으로 콘텐츠 유형 및 추가 헤더의 선택적 사전입니다.

파일 이름으로 `None`이 있는 튜플 형식을 사용하여 해당 부분에 대한 요청에서 `filename="..."` 매개변수를 삭제합니다.

```python
>>> files = {'foo': 'bar'}
>>> print(requests.Request('POST', 'http://httpbin.org/post', files=files).prepare().body.decode('utf8'))
--bb3f05a247b43eede27a124ef8b968c5
Content-Disposition: form-data; name="foo"; filename="foo"

bar
--bb3f05a247b43eede27a124ef8b968c5--
>>> files = {'foo': (None, 'bar')}
>>> print(requests.Request('POST', 'http://httpbin.org/post', files=files).prepare().body.decode('utf8'))
--d5ca8c90a869c5ae31f70fa3ddb23c76
Content-Disposition: form-data; name="foo"

bar
--d5ca8c90a869c5ae31f70fa3ddb23c76--
```

`files`는 순서 지정 및/또는 동일한 이름의 여러 필드가 필요한 경우 값이 2개인 튜플의 목록일 수도 있습니다.

```python
requests.post(
    'http://requestb.in/xucj9exu',
    files=(
        ('foo', (None, 'bar')),
        ('foo', (None, 'baz')),
        ('spam', (None, 'eggs')),
    )
)
```


`files`와 `data`를 모두 지정하는 경우 POST 본문을 만드는 데 사용할 데이터 *값*에 따라 다릅니다. `data`가 문자열인 경우에만 사용됩니다. 그렇지 않으면 `data`와 `files`이 모두 사용되며 `data`의 요소가 먼저 나열됩니다.

[고급 멀티파트 지원](https://toolbelt.readthedocs.io/en/latest/uploading-data.html)을 포함하는 우수한 `requests-toolbelt` 프로젝트도 있습니다. `files` 매개변수와 동일한 형식으로 필드 정의를 사용하지만 `requests`와 달리 기본적으로 파일 이름 매개변수를 설정하지 않습니다. 또한 `requests`가 먼저 메모리에 요청 본문을 구성하는 열린 파일 개체에서 요청을 스트리밍할 수 있습니다.

```python
from requests_toolbelt.multipart.encoder import MultipartEncoder

mp_encoder = MultipartEncoder(
    fields={
        'foo': 'bar',
        # plain file object, no filename or mime type produces a
        # Content-Disposition header with just the part name
        'spam': ('spam.txt', open('spam.txt', 'rb'), 'text/plain'),
    }
)
r = requests.post(
    'http://httpbin.org/post',
    data=mp_encoder,  # The MultipartEncoder is posted as data, don't use files=...!
    # The MultipartEncoder provides the content-type header with the boundary:
    headers={'Content-Type': mp_encoder.content_type}
```

필드는 동일한 규칙을 따릅니다. 파일 이름, 부분 MIME 유형 또는 추가 헤더를 추가하려면 2~4개 요소가 있는 튜플을 사용하십시오. `files` 매개변수와 달리 튜플을 사용하지 않는 경우 기본 `filename` 값을 찾으려고 시도하지 않습니다.
