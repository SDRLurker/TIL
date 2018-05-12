final case class Pair[A, B](one: A, two: B)

object Pairs extends App {
    val pair1 = Pair("foo", 1)
    val pair2 = Pair(2, "bar")
    assert(pair1.one == "foo")
    assert(pair1.two == 1)
    assert(pair2.one == 2)
    assert(pair2.two == "bar")
    println("OK")
}
