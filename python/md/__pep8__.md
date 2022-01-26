출처 

출처 : [https://stackoverflow.com/questions/8689964/why-do-some-functions-have-underscores-before-and-after-the-function-name](https://stackoverflow.com/questions/8689964/why-do-some-functions-have-underscores-before-and-after-the-function-name)

# 파이썬 : 왜 함수 이름 앞과 뒤에 "__"를 가진 함수들이 있을까요?

이러한 "밑줄" 형태는 많이 보이는 거 같고 파이썬 언어의 요구사항인지 아니면 convention의 문제인지 궁금합니다.

또한, 누군가가 어떤 함수가 밑줄(_)을 가지는 경향이 있고 그 이유(예를 들어, `__init__`)가 무엇인지 설명해주셨으면 합니다.

----

## 6 개의 답변 중 1개의 답변

[Python PEP 8 -- 파이썬 코드의 스타일 가이드](https://www.python.org/dev/peps/pep-0008/)로 부터:

> ### [이름 스타일 설명](https://www.python.org/dev/peps/pep-0008/#descriptive-naming-styles)
>
> the following special forms using leading or trailing underscores are recognized (these can generally be combined with any case convention):
>
> 앞이나 뒤에 밑줄표기(_)를 사용하는 다음 특별한 형태는 다음처럼 인식될 수 있습니다. (이들은 일반적으로 어떤 경우의 convention과 결합될 수 있습니다.)
>
> * `_single_leading_underscore`: weak "internal use" indicator. E.g. `from M import *` does not import objects whose name starts with an underscore.
>
> * `_하나의_앞에있는_밑줄`: 약한 "내부적 사용"을 표시함. 예) "from M import *"은 밑줄로 시작하는 이름의 객체들을 가져오지(import) 않습니다.
>
> * `single_trailing_underscore_`: used by convention to avoid conflicts with Python keyword, e.g.
>
> * `하나의_뒤에있는_밑줄_`: 파이썬 키워드와 혼동을 막기 위해 사용하는 convention입니다. 예를들어
> ```python
> Tkinter.Toplevel(master, class_='ClassName')
> ```
> * `__double_leading_underscore`: when naming a class attribute, invokes name mangling (inside class FooBar, `__boo` becomes `_FooBar__boo`; see below).
> 
> * `__두개의_앞에있는_밑줄`: 클래스 속성에 이름을 붙일 때 속성의 이름을 내부적으로 바꿉니다. (내부적으로 바꾸는 규칙에 의해 Foobar 클래스 내부에 있는 `__boo`는 `_FooBar_boo`;가 됩니다.)
>
> * `__double_leading_and_trailing_underscore__`: "magic" objects or attributes that live in user-controlled namespaces. E.g. `__init__`, `__import__` or `__file__`. Never invent such names; only use them as documented.
>
> * `__두개의_앞에있는_그리고_뒤에있는_밑줄__` : 사용자가 제어하는 이름공간에 "마법" 객체나 속성. 예시 `__init__`, `__import__` 또는 `__file__`. 이러한 이름을 새로 만들지 말아야 합니다. 오직 문서화된대로만 이들을 사용해야 합니다.)

__두개의_앞에있는_그리고_뒤에있는_밑줄__은 본질적으로 파이썬 자체적으로 예약되어 있습니다. "이러한 이름을 새로 만들지 말고 문서화된 대로만 그들을 사용해야 합니다."
