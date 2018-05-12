sealed trait Sum[A, B]
case class First[A, B](value: A) extends Sum[A, B]
case class Second[A, B](value: B) extends Sum[A, B]

object GenericSum extends App {
    assert(First[Int, String](1).value == 1)
    assert(Second[Int, String]("foo").value == "foo")
    val sum: Sum[Int, String] = Second("foo")
    val matched: String = sum match {
        case First(x) => x.toString
        case Second(x) => x
    }
    assert(matched == "foo")
    
    println("OK")
}
