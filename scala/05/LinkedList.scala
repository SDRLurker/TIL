sealed trait Maybe[+A]
case object Empty extends Maybe[Nothing]
case class Just[A](a: A) extends Maybe[A]

sealed trait LinkedList[+A] {
  @annotation.tailrec
  def get(n: Int): Maybe[A] = this match {
    case End => Empty
    case Pair(h, t) => if(n == 0) Just(h) else t.get(n-1)
  }
  def length: Int = length()
  @annotation.tailrec
  def length(acc: Int = 0): Int = this match {
    case End => acc
    case Pair(h, t) => t.length(acc+1)
  }
  @annotation.tailrec
  def contains[AA >: A](elem: AA): Boolean = this match {
    case End => false
    case Pair(h, t) => if(h == elem) true else t.contains(elem)
  }
  @annotation.tailrec
  def find(p: A => Boolean): Maybe[A] = this match {
    case End => Empty
    case Pair(h, t) => if(p(h)) Just(h) else t.find(p)
  }
  def filter(p: A => Boolean): LinkedList[A] = this match {
    case End => LinkedList()
    case Pair(h, t) => if(p(h)) Pair(h, t.filter(p)) else t.filter(p)
  }
  def map[B](f: A => B): LinkedList[B] = this match {
    case End => End
    case Pair(h, t) => Pair(f(h), t.map(f))
  }
  def flatMap[B](f: A => LinkedList[B]): LinkedList[B] = this match {
    case End => End
    case Pair(h, t) => f(h) ++ t.flatMap(f)
  }
  def ++[AA >: A](that: LinkedList[AA]): LinkedList[AA] = this match {
    case End => that
    case Pair(h, t) => Pair(h, t ++ that)
  }
  @annotation.tailrec
  def foldLeft[B](z: B)(op: (B, A) => B): B = this match {
    case End => z
    case Pair(h, t) => {
      println("[BEFORE] h: " + h + ", t:" + t +", z=" + z + ", op(z, h) = " + op(z, h));
      t.foldLeft(op(z, h))(op);
    }
  }
  def foldRight[B](z: B)(op: (A, B) => B): B = this match {
    case End => z
    case Pair(h, t) => {
      println("[BEFORE] h: " + h + ", t:" + t +", z=" + z);
      op(h, t.foldRight(z)(op))
    }
  }
  def reverse: LinkedList[A] = this match {
    case End => End
    case Pair(h, t) => foldLeft[LinkedList[A]](End){
      (list, a) => Pair(a, list)
    }
  }
  def foldRight2[B](z: B)(op: (A, B) => B): B = this match {
    case End => z
    case Pair(h, t) => reverse.foldLeft(z){
      (b, a) => op(a, b)
    }
  }

}
case object End extends LinkedList[Nothing]
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
object LinkedList {
  def apply[A](xs: A*): LinkedList[A] =
    if (xs.isEmpty) End else Pair(xs.head, apply(xs.tail: _*))

  def main(args: Array[String]): Unit = {
    val l = LinkedList(1, 2, 3, 4, 5)
    // get
    assert(l.get(0) == Just(1))
    assert(l.get(1) == Just(2))
    // length
    assert(l.length == 5)
    // contains
    assert(l.contains(1) == true)
    assert(l.contains(0) == false)
    // find
    assert(l.find(_ % 2 == 0) == Just(2))
    assert(l.find(_ % 6 == 0) == Empty)
    // filter
    assert(l.filter(_ % 2 == 0) == LinkedList(2, 4))
    // map
    assert(l.map(_ + 1) == LinkedList(2, 3, 4, 5, 6))
    // flatMap
    assert(l.flatMap(n => LinkedList(n, n + 1)) == LinkedList(1, 2, 2, 3, 3, 4, 4, 5, 5, 6))
    // foldLeft
    println("---foldLeft---")
    assert(l.foldLeft("")(_ + _ + 1) == "1121314151")
    // foldRight
    println("---foldRight---")
    assert(l.foldRight("")(1 + _ + _) == "23456")
    // reverse
    println("---reverse : 1. foldLeft---")
    assert(l.reverse == LinkedList(5, 4, 3, 2, 1))
    // foldRight2
    println("---foldRight2 : 1. reverse => 2. foldLeft---")
    assert(l.foldRight2("")(1 + _ + _) == "23456")
    println("OK")
  }
}
