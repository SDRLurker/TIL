trait Monoid[A] {
  def zero: A
  def combine(a1: A, a2: A): A
}
object Monoid extends App{
  def apply[A](implicit m: Monoid[A]): Monoid[A] = m
  def zero[A: Monoid]: A = Monoid[A].zero
  def instance[A](z: A)(f: (A, A) => A): Monoid[A] = new Monoid[A] {
    def zero: A = z
    def combine(a1: A, a2: A): A = f(a1, a2)
  }
  implicit class MonoidOps[A](val a: A) extends AnyVal {
    def |+|(that: A)(implicit m: Monoid[A]): A = m.combine(a, that)
  }
  implicit val intMonoid: Monoid[Int] = instance[Int](0)(_+_)
  implicit def listMonoid[A]: Monoid[List[A]] = instance(List.empty[A])(_ ::: _)
  /* implicit def tuple2Monoid[A, B](implicit ma: Monoid[A], mb: Monoid[B]): Monoid[(A, B)] =
    instance((ma.zero, mb.zero)){ case ((a1, b1), (a2, b2)) => (ma.combine(a1, a2), mb.combine(b1, b2)) } */
  implicit def tuple2Monoid[A: Monoid, B: Monoid]: Monoid[(A, B)] =
    instance((zero[A], zero[B])){ case ((a1, b1), (a2, b2)) => (a1 |+| a2, b1 |+| b2) }  

  assert(implicitly[Monoid[Int]].zero == 0)
  assert(implicitly[Monoid[Int]].combine(10, 20) == 30)
  assert(Monoid[Int].zero == 0)
  assert(Monoid[Int].combine(10, 20) == 30)

  assert((10 |+| 20) == 30)
  assert((10 |+| Monoid.zero[Int]) == 10)            // 항등원
  assert((Monoid.zero[Int] |+| 10) == 10)            // 항등원
  assert((10 |+| 20 |+| 30) == (10 |+| (20 |+| 30))) // 결합법칙

  assert((List(1, 2, 3) |+| List(4, 5, 6)) == List(1, 2, 3, 4, 5, 6))
  assert((List(1, 2, 3) |+| Monoid.zero[List[Int]]) == List(1, 2, 3))

  val lhs = (List(1, 2), (1, List("a")))
  val rhs = (List(3, 4), (2, List("b")))
  assert((lhs |+| rhs) == (List(1, 2, 3, 4), (3, List("a", "b"))))

  println("Ok!")
}
