sealed trait LinkedList[A] {
  def flatMap[B](f: A => LinkedList[B]): LinkedList[B] = this match{
    case End() => End()
    case Pair(head, tail) => {
      val headList: LinkedList[B] = f(head)
      val tailList: LinkedList[B] = tail.flatMap(f)
      headList ++ tailList
    }
  }
  def ++(that: LinkedList[A]): LinkedList[A] = this match {
    case End() => that
    case Pair(head, tail) => Pair(head, tail ++ that)
  }
}
final case class End[A]() extends LinkedList[A]
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

object FlatMapApp extends App {
  val list = Pair(1, Pair(2, Pair(3, Pair(4, End()))))
  val filtered = list.flatMap[Int]{
    case n if n % 2 == 0 => End()
    case n => Pair(n, End())
  }
  println(list)
  println(filtered)
  assert(filtered == Pair(1, Pair(3, End())))
  println("OK")
}
