case class Avengers(realName: String, position: String)

object GodFollower extends App{
  def inspect(avengers: Avengers): String = avengers match {
    case Avengers(_, "God") => "Oh My God!"
    case Avengers(_, _) => "Mere mortal"
  }
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
}

