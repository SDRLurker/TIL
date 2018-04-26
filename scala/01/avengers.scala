object CaptainAmerica {
  val realName = "Steve Rogers"
  val position = "Leader"
  override val toString: String = realName + "(" + position + ")"
}
object IronMan {
  val realName = "Tony Stark"
  val position = "Range Deler"
  override val toString: String = realName + "(" + position + ")"
}
object Hulk {
  val realName = "Bruce Banner"
  val position = "Tanker"
  override val toString: String = realName + "(" + position + ")"
}
object Thor {
  val realName = "Thor Odinson"
  val position = "God"
  override val toString: String = realName + "(" + position + ")"
}
object avengers extends App{
  println(CaptainAmerica)
  println(IronMan)
  println(Hulk)
  println(Thor)
}
