sealed trait TrafficLight
case object Red    extends TrafficLight
case object Green  extends TrafficLight
case object Yellow extends TrafficLight

object TrafficLight1 extends App {
    def next(current: TrafficLight): TrafficLight =  current match {
        case Red=> Green
        case Green=> Yellow
        case Yellow=> Red
    }
    assert(next(Red) == Green)
    assert(next(Green) == Yellow)
    assert(next(Yellow) == Red)

    println("OK")
}
