import akka.actor._
import akka.stream.ActorMaterializer
/**
  * Created by Charan Kooram on 2/18/2017.
  */
class Origin extends Actor {

  override def preStart() : Unit = {
    implicit val materializer = ActorMaterializer()
    implicit val system = ActorSystem()

    var consumer = context.system.actorOf(Props(new ConsumerCreater),"consumer")

  }

  override def receive : Receive = {
    case _ => println()
  }



}
