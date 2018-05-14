### 연습: SHAPE

Shape trait을 구현하세요.

다음 abstract method를 갖고 있도록 하세요.

>* sides는 면의 숫자를 구합니다.
>* perimeter는 둘레를 구합니다.
>* area는 면적을 구합니다.

Shape를 확장한 다음 세 클래스를 구현해보세요:

Circle, Rectangle, Square
(참고: π 의 값은 math.Pi로 구할 수 있습니다.)

=> https://github.com/SDRLurker/TIL/blob/master/scala/03/Shape.scala

--

### 연습: 신호등

Red, Green, Yellow로 구성된

TrafficLight 데이터타입을 구현해보세요

Red → Green → Yellow → Red 로 순환되는 method next를 구현해보세요.

* 메소드를 클래스 외부에 구현해보세요.

=> https://github.com/SDRLurker/TIL/blob/master/scala/03/TrafficLight1.scala
* 메소드를 클래스 내부에 polymorphism으로 구현해보세요.

=> https://github.com/SDRLurker/TIL/blob/master/scala/03/TrafficLight2.scala
* 메소드를 클래스 내부에 패턴 매칭으로 구현해보세요.

=> https://github.com/SDRLurker/TIL/blob/master/scala/03/TrafficLight3.scala
* 어떤 방법이 가장 나은가요? 그 이유는?

--

### 연습: INTLIST

다음 IntList정의를 활용해서 length 메소드를 구현해보세요.

```scala
sealed trait IntList
final case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList
```

```scala
val example = Pair(1, Pair(2, Pair(3, End)))
assert(example.length == 3)
assert(example.tail.length == 2)
assert(End.length == 0)
```
* 재귀

=> https://github.com/SDRLurker/TIL/blob/master/scala/03/IntList1.scala
* 꼬리재귀 1

=> https://github.com/SDRLurker/TIL/blob/master/scala/03/IntList2.scala
* 꼬리재귀 2

=> https://github.com/SDRLurker/TIL/blob/master/scala/03/IntList3.scala

--

### 연습: CALCULATOR

다음을 구현해보세요

> * Expression은 Addition, Subtraction, 혹은 Number입니다.
> * Addition은 left와 right Expression을 가지고 있습니다.
> * Subtraction은 left와 right Expression을 가지고 있습니다.
> * Number는 Double타입의 value 하나를 갖고 있습니다,

Expression을 실제로 계산해서 Double로 바꿔주는

eval 메소드를 구현해보세요

실패할 수 있는 계산도 있습니다.

Double에는 NaN이라는 값이 이를 나타내긴 합니다만, 계산 실패와 그 이유를 명시적으로 나타내면 더 좋을 겁니다.

적절한 데이터 타입 Success, Failure을 구현하시고 eval을 그에 맞추어 수정해보세요.

Division과 SquareRoot 연산을 추가해보세요. 다음을 통과할 수 있어야 합니다.

```scala
assert(Addition(SquareRoot(Number(-1.0)), Number(2.0)).eval ==
       Failure("Square root of negative number"))
assert(Addition(SquareRoot(Number(4.0)), Number(2.0)).eval ==
       Success(4.0))
assert(Division(Number(4), Number(0)).eval ==
       Failure("Division by zero"))
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/03/Calculator.scala
