sealed trait IntList {
  def length: Int = {
    @annotation.tailrec
    def loop(current: IntList, acc: Int = 0): Int = current match {
      case End => acc
      case Pair(head, tail) => loop(tail, acc + 1)
    }
    loop(this)
  }
}
final case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

object IntList3 extends App {
  val example = Pair(1, Pair(2, Pair(3, End)))
  assert(example.length == 3)
  assert(example.tail.length == 2)
  assert(End.length == 0)
  println("Ok")
}
