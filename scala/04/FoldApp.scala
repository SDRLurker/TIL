sealed trait LinkedList[A] {
  def fold[B](init: B)(f: (A, B) => B): B = this match{
    case End() => init
    case Pair(head, tail) => f(head, tail.fold(init)(f))
  }
}
final case class End[A]() extends LinkedList[A]
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

object FoldApp extends App {
  val list0 = Pair(1, Pair(2, Pair(3, Pair(4, End()))))
  assert(list0.fold(0)(_ + _) == 10)
  println(list0.fold(0)(_ + _))
  val list1 = Pair(1, Pair(2, Pair(3, Pair(4, End()))))
  assert(list1.fold(1)(_ * _) == 24)
  println(list1.fold(1)(_ * _))
}
