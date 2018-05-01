class Avengers(val realName: String, val position: String) {
  override def toString = realName + "(" + position + ")"
}
object GodFollower {
  def inspect(avengers: Avengers): String = if(avengers.position == "God") "Oh My God!" else if(avengers.position == "Leader") "Mere mortal" else "aaaaa"
}
object Avengers extends App {
  val CaptainAmerica = new Avengers("Steve Rogers", "Leader")
  val IronMan = new Avengers("Tony Stark", "Range Deler")
  val Hulk = new Avengers("Bruce", "Tanker")
  val Thor = new Avengers("Thor Odinson", "God")
  println(CaptainAmerica)
  println(IronMan)
  println(Hulk)
  println(Thor)
  println(GodFollower.inspect(Thor) == "Oh My God!")
  println(GodFollower.inspect(CaptainAmerica) == "Mere mortal")
  println(GodFollower.inspect(Hulk) == "aaaaa")
}
