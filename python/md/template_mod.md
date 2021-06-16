출처 : [http://stackoverflow.com/questions/8494209/modulus-in-django-template](http://stackoverflow.com/questions/8494209/modulus-in-django-template)

# Django Template의 나머지(%) 연산

저는 django에서 나머지 연산자 같은 것을 사용하는 방법을 찾고 있습니다. 제가 하려고 하는 것은 루프문에서 4번째 요소마다 클래스 이름을 추가하는 것입니다. 

나머지 연산자를 사용하여 다음처럼 작성하였습니다.

당연히 %가 탬플릿에서 예약된 문자이기 때문에 작동을 안할 것입니다. 이를 할 수 있는 다른 방법이 있을까요?

----

## 3 개의 답변 중 1개의 답변만 추려냄.

내장된(build-in) django filter인 [divisibleby](https://docs.djangoproject.com/en/dev/ref/templates/builtins/?from=olddocs#divisibleby)가 필요합니다.

```HTML
{% for p in posts %}
    <div class="post width1 height2 column {% if forloop.counter0|divisibleby:4 %}first{% endif %}">
        <div class="preview">

        </div>
        <div class="overlay">

        </div>
        <h2>p.title</h2>
    </div>
{% endfor %}
```
