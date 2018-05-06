sealed trait Expression {
  def eval: Result = this match {
    case Number(v) => Success(v)
    case Addition(l, r) => l.eval match {
      case Success(lv) => r.eval match {
        case Success(rv) => Success(lv + rv)
        case Failure(msg) => Failure(msg)
      }
      case Failure(msg) => Failure(msg)
    }
    case Subtraction(l, r) => l.eval match {
      case Success(lv) => r.eval match {
        case Success(rv) => Success(lv - rv)
        case Failure(msg) => Failure(msg)
      }
      case Failure(msg) => Failure(msg)
    }
    case SquareRoot(n) => n.eval match {
      case Success(v) => if(v >= 0) Success(Math.sqrt(v)) else Failure("Square root of negative number")  
      case Failure(msg) => Failure(msg)
    }
    case Division(l, r) => l.eval match {
      case Success(lv) => r.eval match {
        case Success(rv) => if(rv == 0.0) Failure("Division by zero") else Success(lv / rv)
        case Failure(msg) => Failure(msg)
      }
      case Failure(msg) => Failure(msg)
    }
  }
}
final case class Addition(left: Expression, right: Expression) extends Expression
final case class Subtraction(left: Expression, right: Expression) extends Expression
final case class Number(value: Double) extends Expression
final case class SquareRoot(exp: Expression) extends Expression
final case class Division(left: Expression, right: Expression) extends Expression

sealed trait Result
final case class Success(value: Double) extends Result
final case class Failure(msg: String) extends Result

object Calculator extends App {
  assert(Addition(SquareRoot(Number(-1.0)), Number(2.0)).eval ==
       Failure("Square root of negative number"))
  assert(Addition(SquareRoot(Number(4.0)), Number(2.0)).eval ==
       Success(4.0))
  assert(Division(Number(4), Number(0)).eval ==
       Failure("Division by zero"))
  assert(Division(Addition(Number(2),Number(2)), Subtraction(Number(2),Number(2))).eval ==
       Failure("Division by zero"))
  println("Ok")
}
