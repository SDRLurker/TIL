sealed trait Shape {
  def sides:Int = this match {
    case Circle(r) => 0
    case Rectangle(x,y) => 4
    case Square(x) => 4
  }
  def perimeter:Double = this match {
    case Circle(r) => 2 * r * math.Pi
    case Rectangle(x,y) => 2 * x + 2 * y
    case Square(x) => 4 * x
  }
  def area:Double = this match {
    case Circle(r) => r * r * math.Pi
    case Rectangle(x,y) => x* y
    case Square(x) => x * x
  }
  override def toString: String = s"sides: $sides, perimeter:$perimeter, area:$area"
}

final case class Circle(r: Double) extends Shape
final case class Rectangle(x: Double,y: Double) extends Shape
final case class Square(x: Double) extends Shape

object Shape extends App {
  println(Circle(3))
  println(Rectangle(2,4))
  println(Square(5))
}
