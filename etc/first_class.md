**출처**

* [https://stackoverflow.com/questions/10777333/functions-are-first-class-values-what-does-this-exactly-mean](https://stackoverflow.com/questions/10777333/functions-are-first-class-values-what-does-this-exactly-mean)

# "함수는 일급 객체이다." 는 정확히 무슨 의미입니까?

누군가 뭔가 좋은 예시로 분명하게 설명해주실 수 있나요? 저는 함수 프로그래밍에 관해 설명할 때 이 문장을 Scala에서 보았습니다.

## 6개의 답변 중 1개의 답변만 추려냄

"일급"은 공식적으로 정의된 표현은 아닙나다만, 3가지 속성을 가지는 entity로 다음 일반적인 의미가 있습니다.

1. 그것은 "일반적인" 값을 함수에 전달되거나 함수로 컨테이너 안으로 넣는 등 제한없이 사용될 수 있습니다.
2. 그것은 "일반적인" 값을 표현식 등으로 지역변수 형태로 제한없이 생성될 수 있습니다.
3. 그것은 "일반적인" 값과 유사한 방식으로 입력할 수 있습니다. 해당 entity처럼 지정된 타입이 있고 다른 타입으로 자유롭게 구성될 수 있습니다.

함수는 (2) 특별하게 지역 함수는 scope안에 모든 이름을 사용할 수 있습니다. 예를 들면 closure가 있습니다. 그것은 (익명함수처럼) 생성될 때 익명의 형태로 나타날 수 있습니다. 하지만 엄격하게 요구되지는 않습니다. (예시: 언어는 일반적으로 let-표현식으로 충분합니다.) (3)은 타입이 없는 언어에서 사실입니다.

그래서 Scala(와 함수형 언어)에서 왜 함수가 일급으로 불리는지 알 수 있습니다. 여기에는 다른 예시가 있습니다.

* C/C++의 함수는 일급이 아닙니다. (1)과 (3)은 함수 포인터로 가능하지만 (2)는 함수에서 적절하게 제공되지 않습니다. (이점이 자주 간과됩니다)
* 똑같이, C에서 배열과 구조체는 일급이 아닙니다.
* Scala의 Class는 일급이 아닙니다. 당신은 Class를 정의하고 내포(nest)할 수 있지만, 함수(오직 그것의 객체)로 그들을 전달할 수 없습니다. 일급 클래스를 갖춘 객체지향 언어가 있고 Scala의 디자인은 nuObj calculus라 불리는 방법으로 그것을 허용합니다.
* 일급 모듈은 ML같은 언어에서 종종 필요한 기능입니다. 결정할 수 없는 타입 검사로 이끌기 때문에 그것은 어렵습니다. ML dialect는 일급 객체로 wrap되는 것을 허용합니다만 그들 자체를 일급 클래스 모듈로 만들지는 않습니다.