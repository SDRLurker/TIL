sealed trait TrafficLight{
  def next:TrafficLight = this match {
    case Red => Green
    case Green => Yellow
    case Yellow => Red
  }
}
case object Red    extends TrafficLight
case object Green  extends TrafficLight
case object Yellow extends TrafficLight
object TrafficLight3 extends App {
  assert(Red.next == Green)
  assert(Green.next == Yellow)
  assert(Yellow.next == Red)
  println("OK")
}
