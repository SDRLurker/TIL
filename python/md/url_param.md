출처

- [https://stackoverflow.com/questions/150505/capturing-url-parameters-in-request-get](https://stackoverflow.com/questions/150505/capturing-url-parameters-in-request-get)

# request.GET에서 URL 파라미터를 얻기

저는 URL에서 파라미터를 얻기 위해 튜토리얼에 설명된대로 정규식을 정의하고 있습니다. `HttpRequest` 객체의 일부분으로 URL로부터 파라미터에 접근할 수 있을까요? 

`HttpRequest.GET`은 현재 비어있는 `QueryDict` 객체를 리턴합니다.

저는 라이브러리 없이 이를 할 수 있는 방법을 배워 Django를 더 잘 알고 싶습니다.

### 16개의 답변 중 1개의 답변

URL이 다음과 같습니다. `domain/search/?q=haha`, 이렇다면 당신은 `request.GET.get('q','')`를 사용할 수 있습니다.  

`q`는 당신이 원하는 파라미터이며, `''`는 `q`가 없을 때 기본(default) 값입니다.  

하지만, `URLconf`\*\*에 설정된 것이라면, `regex`로부터 당신이 얻은 것은 함수의 인자(또는 named argument)로 전달될 것입니다.

만약 다음 URLconf 코드라면

```python
(r'^user/(?P<username>\w{0,50})/$', views.profile_page,),
```

`views.py`에서 다음 함수가 있을 것입니다.

```python
def profile_page(request, username):
    # 메소드의 나머지 부분
```
