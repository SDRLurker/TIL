object Calculator extends App {
  // teacher's solution
  def readInt(str: String): Option[Int] = if (str matches "\\d+") Some(str.toInt) else None
  def calculator(operand1: String, operator: String, operand2: String): String = {
    def eval(a: Int, op: String, b: Int): Option[Int] = op match {
      case "+" => Some(a + b)
      case "-" => Some(a - b)
      case "*" => Some(a * b)
      case "/" => if (b == 0) None else Some(a / b)
      case _ => None
    }
    val ansOp = for(a<-readInt(operand1); b<-readInt(operand2); ans<-eval(a, operator, b)) yield ans
    ansOp.fold(s"Error calculating $operand1 $operator $operand2")(ans => s"The answer is $ans!")
  }
  // my solution 
  def op(a: Int, operator: String, b: Int): Option[Int] = {
    operator match {
      case "+" => Some(a+b)
      case "-" => Some(a-b)
      case "*" => Some(a*b)
      case "/" => if(b == 0) None else Some(a/b)
      case _ => None
    }
  }
  def calculator2(operand1: String, operator: String, operand2: String): String = {
    val res = for {
      a <- readInt(operand1)
      b <- readInt(operand2)
    } yield op(a, operator, b)
    res match {
      case Some(some) => some match { 
        case Some(number) => s"The answer is $number!" 
        case None => s"Error calculating $operand1 $operator $operand2"
      }
      case None => s"Error calculating $operand1 $operator $operand2"
    }
  }   

  println("Teacher's Solution");
  println(calculator("1", "+", "1"))
  println(calculator("1", "/", "0"))
  assert(calculator("1", "+", "1") == "The answer is 2!")
  assert(calculator("1", "/", "0") == "Error calculating 1 / 0")
  println()
  println("My Solution");
  println(calculator2("1", "+", "1"))
  println(calculator2("1", "/", "0"))
  assert(calculator2("1", "+", "1") == "The answer is 2!")
  assert(calculator2("1", "/", "0") == "Error calculating 1 / 0")

  println("Ok!")
}
