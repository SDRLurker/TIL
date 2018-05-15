object runLength extends App {
  def runLength[A](l: List[A], debug: Boolean = true): List[(A, Int)] = l.foldLeft(List.empty[(A, Int)]) {
    case ((x, n) :: xs, a) if x == a => {
      if(debug) println(s"case ((x, n) :: xs, a) if x == a => x = $x, n = $n, xs = $xs, a = $a")
      (x, n + 1) :: xs
    }
    case (acc, a) => {
      if(debug) println(s"case (acc, a) => acc = $acc, a = $a")
      (a, 1) :: acc
    }
  }.reverse

  println("--- runLength(\"aabbcc\".toList) ---")
  assert(runLength("abbbcc".toList) ==
    List(('a', 1), ('b', 3), ('c', 2))
  )
  println(runLength("abbbcc".toList, false))
  println("OK")
}
