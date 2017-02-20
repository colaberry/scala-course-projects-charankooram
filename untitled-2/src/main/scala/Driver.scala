/**
  * Created by Charan Kooram on 2/18/2017.
  */
object Driver  {

  def main(args: Array[String]): Unit = {

    val Actor1 = classOf[Origin].getName

    akka.Main.main(Array(Actor1))
  }

}
