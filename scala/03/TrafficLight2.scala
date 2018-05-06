sealed trait TrafficLight{
  def next:TrafficLight
}
case object Red    extends TrafficLight{
  override val next:TrafficLight = Green
}
case object Green  extends TrafficLight{
  override val next:TrafficLight = Yellow
}
case object Yellow extends TrafficLight{
  override val next:TrafficLight = Red
}
object TrafficLight2 extends App {
  assert(Red.next == Green)
  assert(Green.next == Yellow)
  assert(Yellow.next == Red)
  println("OK")
}
