sealed trait Maybe[A]
final case class Empty[A]() extends Maybe[A]
final case class Just[A](v: A) extends Maybe[A]

object MaybeApp extends App {
  def divide(a: Int, b: Int): Maybe[Int] = b match {
    case 0 => Empty()
    case _ => Just(a/b)
  }
  assert(divide(10, 2) == Just(5))
  assert(divide(10, 0) == Empty())
  println(Just(5))
}
