sealed trait Expression {
  def eval0(l: Sum[String, Double], r: Sum[String, Double], f: (Double, Double) => Sum[String, Double]): Sum[String, Double] = (l, r) match {
    case (Success(v1), Success(v2)) => f(v1, v2)
    case (Failure(msg), _) => Failure(msg)
    case (_, Failure(msg)) => Failure(msg)
  }
  def eval: Sum[String, Double] = this match {
    case Number(value) => Success(value)
    case Addition(l, r) => eval0(l.eval, r.eval, (v1:Double, v2:Double) => Success(v1 + v2))
    case Subtraction(l, r) => eval0(l.eval, r.eval, (v1:Double, v2:Double) => Success(v1 - v2))
    case SquareRoot(v) => eval0(v.eval, v.eval, (v1:Double, v2:Double) => if(v1 < 0.0 && v2 < 0.0) Failure("Square root of negative number") else Success(Math.sqrt(v1)))
    case Division(l, r) => eval0(l.eval, r.eval, (v1:Double, v2:Double) => if(v2 == 0.0) Failure("Division by zero") else Success(v1 / v2))
  }
}
final case class Addition(left: Expression, right: Expression) extends Expression
final case class Subtraction(left: Expression, right: Expression) extends Expression
final case class Division(left: Expression, right: Expression) extends Expression
final case class SquareRoot(value: Expression) extends Expression
final case class Number(value: Double) extends Expression

sealed trait Sum[String, Double]
final case class Success[String, Double](value: Double) extends Sum[String, Double]
final case class Failure[String, Double](msg: String) extends Sum[String, Double]

object Calculator extends App {
  assert(Addition(Number(1), Number(2)).eval == Success(3))
  assert(SquareRoot(Number(-1)).eval == Failure("Square root of negative number"))
  assert(Division(Number(4), Number(0)).eval == Failure("Division by zero"))
  assert(Division(Addition(Subtraction(Number(8), Number(6)), Number(2)), Number(2)).eval == Success(2.0))

  assert(Addition(SquareRoot(Number(-1.0)), Number(2.0)).eval ==
    Failure("Square root of negative number"))
  assert(Addition(SquareRoot(Number(4.0)), Number(2.0)).eval ==
    Success(4.0))
  assert(Division(Number(4), Number(0)).eval ==
    Failure("Division by zero"))
  assert(Division(Addition(Number(2),Number(2)), Subtraction(Number(2),Number(2))).eval ==
    Failure("Division by zero"))

  println("OK")
}
