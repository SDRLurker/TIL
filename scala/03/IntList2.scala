sealed trait IntList {
  def length: Int = length()
  @annotation.tailrec
  def length(acc: Int = 0): Int = this match {
    case End => acc
    case Pair(head, tail) => tail.length(acc+1)
  }
}
final case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

object IntList2 extends App {
  val example = Pair(1, Pair(2, Pair(3, End)))
  assert(example.length == 3)
  assert(example.tail.length == 2)
  assert(End.length == 0)
  println("OK")
}
