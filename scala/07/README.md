## 연습: Monoid

항등원(zero)과 combine operation이 정의되는 타입클래스를 Monoid라고 합니다.

```scala
trait Monoid[A] {
  def zero: A
  def combine(a1: A, a2: A): A
}
```

다음이 성립하도록 만들어보세요.

```scala
assert(implicitly[Monoid[Int]].zero == 0)
assert(implicitly[Monoid[Int]].combine(10, 20) == 30)
assert(Monoid[Int].zero == 0)
assert(Monoid[Int].combine(10, 20) == 30)
```

--

다음이 성립하도록 만들어보세요.

```scala
assert((10 |+| 20) == 30)                          // 덧셈
assert((10 |+| Monoid.zero[Int]) == 10)            // 항등원
assert((Monoid.zero[Int] |+| 10) == 10)            // 항등원
assert((10 |+| 20 |+| 30) == (10 |+| (20 |+| 30))) // 결합법칙
```

--

Monoid[List[A]] 타입클래스 인스턴스를 작성해보세요. 다음이 성립하도록 해야 합니다.

```scala
assert((List(1, 2, 3) |+| List(4, 5, 6)) == List(1, 2, 3, 4, 5, 6))
assert((List(1, 2, 3) |+| Monoid.zero[List[Int]]) == List(1, 2, 3))
```

--

원소 두 개짜리 Tuple에 대한 Monoid Type Class 인스턴스를 작성해보세요.

다음이 성립하도록 해야 합니다.

```scala
val lhs = (List(1, 2), (1, List("a")))
val rhs = (List(3, 4), (2, List("b")))
assert((lhs |+| rhs) == (List(1, 2, 3, 4), (3, List("a", "b"))))
```

=> https://github.com/SDRLurker/TIL/blob/master/scala/07/Monoid.scala
