object fizzBuzz extends App {
  // teacher's solution
  def fizzBuzz(n: Int)(cond: (Int, String)*): Unit = 1 to n map { i =>
    cond.foldLeft(""){ case (acc, (x, s)) => if (i % x == 0) acc + s else acc } match {
      case "" => i.toString
      case other => other
    }
  } foreach println

  // my solution
  def fizzBuzz2(n: Int)(cond: (Int, String)*): Unit = {
    for(i <- 1 to n) {
      val res = cond.filter( i % _._1 == 0 )
      val s = res.map(_._2).mkString
      if(res.map(_._2).length == 0) println(s"$i") else println( s"$s" )
    }
  }

  println("Teacher's Solution");
  fizzBuzz(20)((3, "Fizz"), (5, "Buzz"))
  println();
  println("My Solution");
  fizzBuzz2(20)((3, "Fizz"), (5, "Buzz"))
}
