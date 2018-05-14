sealed trait LinkedList[A] {
  def map[B](f: A => B): LinkedList[B] = this match {
    case End() => End()
    case Pair(head, tail) => Pair(f(head), tail.map(f))
  }
}
final case class End[A]() extends LinkedList[A]
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

object MapApp extends App {
  val l = Pair(1, Pair(2, Pair(3, End())))
  assert(l.map(_ + 1) == Pair(2, Pair(3, Pair(4, End()))))
  println(l.map(_ + 1))
}
