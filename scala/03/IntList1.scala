sealed trait IntList {
  def length: Int = this match {
    case End => 0
    case Pair(head, tail) => 1 + tail.length
  }
}
final case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

object IntList1 extends App {
  val example = Pair(1, Pair(2, Pair(3, End)))
  assert(example.length == 3)
  assert(example.tail.length == 2)
  assert(End.length == 0)
  println("OK")
}
