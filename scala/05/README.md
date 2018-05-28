## 복습: Maybe, LinkedList

```scala
sealed trait Maybe[+A]
case object Empty extends Maybe[Nothing]
case class Just[A](a: A) extends Maybe[A]
```

```scala
sealed trait LinkedList[+A]
case object End extends LinkedList[Nothing]
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
object LinkedList {
  def apply[A](xs: A*): LinkedList[A] =
    if (xs.isEmpty) End else Pair(xs.head, apply(xs.tail: _*))
}
```
--

Get ~ FoldRight2 다음 모든 소스들을 여기에 구현하였습니다.

=> https://github.com/SDRLurker/TIL/blob/master/scala/05/LinkedList.scala

foldLeft, foldRight2 도움될만한 링크

=> https://twitter.github.io/scala_school/ko/collections.html

=> http://knight76.tistory.com/entry/foldLeft%EC%99%80-foldRight-%EC%82%AC%EC%9A%A9%EB%B2%95-%EB%94%94%EB%B2%84%EA%B7%B8-%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95

--

### Get

```scala
def get(n: Int): Maybe[A] = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.get(0) == Just(1))
```

--

### Length

```scala
def length: Int = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.length == 5)
```

--

### Contains

```scala
def contains[AA >: A](elem: AA): Boolean = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.contains(1) == true)
assert(l.contains(0) == false)
```

--

### Find

```scala
def find(p: A => Boolean): Maybe[A] = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.find(_ % 2 == 0) == Just(2))
assert(l.find(_ % 6 == 0) == Empty)
```

--

### Filter

```scala
def filter(p: A => Boolean): LinkedList[A] = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.filter(_ % 2 == 0) == LinkedList(2, 4))
```

--

### Map

```scala
def map[B](f: A => B): LinkedList[B] = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.map(_ + 1) == LinkedList(2, 3, 4, 5, 6))
```

--

### FlatMap

```scala
def flatMap[B](f: A => LinkedList[B]): LinkedList[B] = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.flatMap(n => LinkedList(n, n + 1)) == LinkedList(1, 2, 2, 3, 3, 4, 4, 5, 5, 6))
```

--

### FoldLeft

```scala
def foldLeft[B](z: B)(op: (B, A) => B): B = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.foldLeft("")(_ + _ + 1) == "1121314151")
```

--

### FoldRight

```scala
def foldRight[B](z: B)(op: (A, B) => B): B = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.foldRight("")(1 + _ + _) == "23456")
```

--

### Reverse

```scala
def reverse: LinkedList[A] = ???
```

```scala
val l = LinkedList(1, 2, 3, 4, 5)
assert(l.reverse == LinkedList(5, 4, 3, 2, 1))
```

> `hint`: foldLeft 이용!

--

### FoldRight2

`foldLeft`는 쉽게 tail recursion을 적용해 구현할 수 있는 반면, `foldRight`를 그냥 구현하면 tail recursion이 적용되지 않는 형태가 되기 쉽습니다.

`foldRight2`을 `foldLeft`를 써서 구현해보세요.

---

## Exercise: RunLength

```scala
def runLength[A](l: List[A]): List[(A, Int)] = ???
```

```scala
assert(runLength("abbbcc".toList) ==
  List(('a', 1), ('b', 3), ('c', 2))
)
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/05/runLength.scala

--

## Exercise: FizzBuzz

```scala
def fizzBuzz(n: Int)(cond: (Int, String)*): Unit = ???
```

```scala
fizzBuzz(20)((3, "Fizz"), (5, "Buzz"))
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/05/fizzBuzz.scala
