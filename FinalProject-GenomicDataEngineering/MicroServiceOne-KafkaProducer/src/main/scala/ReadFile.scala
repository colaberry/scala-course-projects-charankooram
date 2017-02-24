import akka.actor.{Props, _}
import akka.stream.ActorMaterializer


/**
  * Created by Charan Kooram on 2/18/2017.
  */
object ReadFile extends App   {

    val Actor1 = classOf[Origin].getName

    akka.Main.main(Array(Actor1))




}
