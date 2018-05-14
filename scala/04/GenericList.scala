sealed trait LinkedList[A]
final case class End[A]() extends LinkedList[A]
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

object GenericList extends App{
  val intList = Pair(1, Pair(2, Pair(3, End())))
  val strList = Pair("a", Pair("b", Pair("c", End())))
  println("OK")
}
