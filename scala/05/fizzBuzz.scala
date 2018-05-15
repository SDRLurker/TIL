object fizzBuzz extends App {
  def fizzBuzz(n: Int)(cond: (Int, String)*): Unit = {
    for(i <- 1 to n) {
      val res = cond.filter( i % _._1 == 0 )
      val s = res.map(_._2).mkString
      if(res.map(_._2).length > 0) println( s"$i = $s" )
    }
  }

  fizzBuzz(20)((3, "Fizz"), (5, "Buzz"))
}
