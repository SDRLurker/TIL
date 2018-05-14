## Exercise: Generic List

다음 IntList를 Generic을 써서 일반적인 타입의 데이터를 담을 수 있도록 수정한 LinkedList 타입을 작성해 보세요.

```scala
sealed trait IntList
final case class End() extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/04/GenericList.scala

--

## Exercise: Pairs

`Pair` 를 구현해보세요. 다음이 성립하도록 해야 합니다.

```scala
val pair1 = Pair("foo", 1)
val pair2 = Pair(2, "bar")
assert(pair1.one == "foo")
assert(pair1.two == 1)
assert(pair2.one == 2)
assert(pair2.two == "bar")
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/04/Pairs.scala

--

## Exercise: Generic Sum

두 개의 서브타입 `First`와 `Second`를 가지는 `Sum`타입을 구현해보세요. 다음 테스트가 통과되도록 하셔야 합니다.

```scala
assert(First[Int, String](1).value == 1)
assert(Second[Int, String]("foo").value == "foo")
val sum: Sum[Int, String] = Second("foo")
val matched: String = sum match {
  case First(x) => x.toString
  case Second(x) => x
}
asert(matched == "foo")
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/04/GenericSum.scala

--

## Exercise: Maybe

두 개의 서브타입 `Just`와 `Empty`를 가지는 `Maybe`타입을 구현해보세요. 다음 테스트가 통과되도록 하셔야 합니다.

```scala
def divide(a: Int, b: Int): Maybe[Int] = b match {
  case 0 => Empty()
  case _ => Just(a/b)
}
assert(divide(10, 2) == Just(5))
assert(divide(10, 0) == Empty())
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/04/MaybeApp.scala

--

## Exercise: map

앞서 작성했던 `LinkedList`에 `map`메소드를 구현해보세요.

타입 시그니쳐는 다음과 같습니다.

```scala
def map[B](f: A => B): LinkedList[B] = ???
```

다음 테스트를 통과해야 합니다.

```scala
val l = Pair(1, Pair(2, Pair(3, End())))
assert(l.map(_ + 1) == Pair(2, Pair(3, Pair(4, End()))))
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/04/MapApp.scala

--

## Exercise: fold

`LinkedList`에 `fold`메소드를 구현해보세요.

타입 시그니쳐는 다음과 같습니다.

```scala
def fold[B](init: B)(f: (A, B) => B): B = ???
```

다음 테스트를 통과해야 합니다.

```scala
val list0 = Pair(1, Pair(2, Pair(3, Pair(4, End()))))
assert(list0.fold(0)(_ + _) == 10)
val list1 = Pair(1, Pair(2, Pair(3, Pair(4, End()))))
assert(list1.fold(1)(_ * _) == 24)
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/04/FoldApp.scala

--

## Exercise: flatMap

LinkedList에 flatMap메소드를 구현해보세요.

타입 시그니쳐는 다음과 같습니다.

```scala
def flatMap[B](f: A => LinkedList[B]): LinkedList[B] = ???
```

다음 테스트를 통과해야 합니다.

```scala
val list = Pair(1, Pair(2, Pair(3, Pair(4, End()))))
val filtered = list.flatMap{
  case n if x % 2 == 0 => End()
  case n => Pair(n, End())
}
assert(filtered == Pair(1, Pair(3, End())))
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/04/FlatMapApp.scala

--

## Exercise: Calculator

다음에서 `eval` 메소드를 구현해보세요

```scala
sealed trait Expression {
  def eval: Sum[String, Double] = ???
}
final case class Addition(left: Expression, right: Expression) extends Expression
final case class Subtraction(left: Expression, right: Expression) extends Expression
final case class Division(left: Expression, right: Expression) extends Expression
final case class SquareRoot(value: Expression) extends Expression
final case class Number(value: Double) extends Expression
```

다음 테스트를 통과해야합니다.

```scala
assert(Addition(Number(1), Number(2)).eval == Success(3))
assert(SquareRoot(Number(-1)).eval == Failure("Square root of negative number"))
assert(Division(Number(4), Number(0)).eval == Failure("Division by zero"))
assert(Division(Addition(Subtraction(Number(8), Number(6)), Number(2)), Number(2)).eval == Success(2.0))
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/04/Calculator.scala
