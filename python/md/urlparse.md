출처 : [https://stackoverflow.com/questions/7734569/how-do-i-remove-a-query-string-from-url-using-python](https://stackoverflow.com/questions/7734569/how-do-i-remove-a-query-string-from-url-using-python)

# Python에서 URL의 query 문자열을 삭제하는 방법

예시:
```
http://example.com/?a=text&q2=text2&q3=text3&q2=text4
```
"**q2**"를 제거한 후에 다음을 리턴할 것입니다.
```
http://example.com/?q=text&q3=text3
```
이 경우, 여러개의 "**q2**"가 있고 모두가 삭제되어야 합니다.

----

## 8 개의 답변 중 1 개의 답변만 추려냄.

```python
import sys

if sys.version_info.major == 3:
    from urllib.parse import urlencode, urlparse, urlunparse, parse_qs
else:
    from urllib import urlencode
    from urlparse import urlparse, urlunparse, parse_qs

url = 'http://example.com/?a=text&q2=text2&q3=text3&q2=text4&b#q2=keep_fragment'
u = urlparse(url)
query = parse_qs(u.query, keep_blank_values=True)
query.pop('q2', None)
u = u._replace(query=urlencode(query, True))
print(urlunparse(u))
```

출력:
```
http://example.com/?a=text&q3=text3&b=#q2=keep_fragment
```
