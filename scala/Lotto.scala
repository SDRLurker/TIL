import scala.collection.mutable.TreeSet
class Lotto(val start: Int = 1+2+3+4+5+6, val end: Int = 45+44+43+42+41+40) {
  val SLIMIT = 1+2+3+4+5+6
  val ELIMIT = 45+44+43+42+41+40
  if(start < SLIMIT || end < SLIMIT || start > ELIMIT || end > ELIMIT)
    throw new IllegalArgumentException("start and end must be" + SLIMIT + " ~ " + ELIMIT + ".");
  var nums = new TreeSet[Int]()
  def raffle() {
    val s = if(start>end) end else start
    val e = if(start>end) start else end
    while(nums.sum < s || nums.sum > e) {
      nums = new TreeSet[Int]()
      while(nums.size < 6) {
        val one = scala.util.Random.nextInt(45) + 1
        nums += one
      }
    }
  }
  override def toString = nums.mkString(" ") + ", sum=" + nums.sum
}
object Lotto{
  def main(args: Array[String]): Unit = {
    for(i <- 1 to 5) {
      val lotto = new Lotto(100, 170)
      lotto.raffle()
      println(lotto)
    }
  }
}
