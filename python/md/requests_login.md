출처 : [https://stackoverflow.com/questions/11892729/how-to-log-in-to-a-website-using-pythons-requests-module/](https://stackoverflow.com/questions/11892729/how-to-log-in-to-a-website-using-pythons-requests-module/)

# Python의 Requests 모듈을 사용하여 웹사이트에 "로그인"하는 방법

저는 Python으로 Requests 모듈을 사용하여 웹사이트로 로그인하는 요청을 post 방식으로 처리하려 합니다만 잘 작동하지 않습니다. 저는 이걸 처음 해 봅니다... 그래서 제 사용자명과 비밀번호 쿠키나 HTTP 인증 같은 형태를 만들어야 하는지 알 수 없습니다.

```python
from pyquery import PyQuery
import requests

url = 'http://www.locationary.com/home/index2.jsp'
```

지금부터 저는 "post" 방식과 쿠키를 사용합니다.

```python
ck = {'inUserName': 'USERNAME/EMAIL', 'inUserPass': 'PASSWORD'}

r = requests.post(url, cookies=ck)

content = r.text

q = PyQuery(content)

title = q("title").text()

print title
```

저는 쿠키에서 뭔가 잘못하고 있는 거 같지만... 모르겠습니다.
제가 정확히 로그인하지 못 했다면, 홈페이지 제목은 "Locationary.com"이 나올 것이고 로그인을 했다면 "Home Page"가 되어야 합니다.
만약 requests와 쿠키에 관한 몇가지를 저에게 설명해 주시고 이에 관해 저를 도와주신다면 정말 감사하겠습니다.
감사합니다.
... 아직 잘 작동하지는 않지만 로그인 전에 홈페이지의 HTML은 다음처럼 나옵니다.

저는 잘하고 있다고 생각하지만, 출력은 아직 "Locationary.com"입니다.

2번째 편집:

저는 그 도메인의 페이지를 요청할 때마다 오래동안 로그인을 유지할 수 있기를 원합니다. 제가 로그인을 했다면 나타날 그 내용이 나타나기를 원합니다.

---

### 4개의 답변 중 1 개의 답변만 추려냄.

저는 당신이 다른 해결책을 찾았다는 것을 알고 있지만 저처럼 이 질문의 해결책 혹은 같은 질문을 찾기 원하는 사람들을 위해 다음처럼 requests를 사용하여 할 수 있습니다.

첫째, 마커스(Marcus)가 한 것처럼 로그인 form(양식)에 post할 URL, 사용자이름과 비밀번호 필드의 name 속성을 소스에서 확인합니다. 그의 예시에서는 inUserName과 inUserPass 입니다.

일단, payload로 로그인 세부 정보를 post 방식으로 요청하기 위해 request.Session() 인스턴스를 사용할 수 있습니다. Session 인스턴스로부터 요청하는 것은 본질적으로 일반적인 requests를 사용하는 것과 같습니다. 이는 간단하게 쿠키들을 사용하고 저장하도록 하는 지속성이 추가된 것입니다.

당신의 로그인 시도가 성공적이었다 가정하면 그 사이트에서 이후 요청을 하기 위해 session 인스턴스를 간단하게 사용할 수 있습니다. 당신을 식별하기 위한 쿠키가 요청을 승인하는 데 사용됩니다.

**예시**

```python
import requests

# 로그인 form에 post 방식으로 전송될 세부 내용을 작성합니다.
payload = {
    'inUserName': 'username',
    'inUserPass': 'password'
}

# 'with'는 사용한 뒤에 session이 닫히도록 보장합니다.
with requests.Session() as s:
    p = s.post('LOGIN_URL', data=payload)
    # 성공적으로 로그인 했는지 보기 위해 더 현명하게 어떤게 리턴되었는지 html을 출력합니다.
    print p.text

    # 승인된 요청
    r = s.get('A protected web page url')
    print r.text
        # 기타...
```        
