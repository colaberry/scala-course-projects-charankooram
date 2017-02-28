import akka.actor._
import akka.stream.ActorMaterializer


/**
  * Created by Charan Kooram on 2/18/2017.
  */
class Origin  extends Actor {

  override def preStart():Unit = {
    implicit val materializer = ActorMaterializer()

    var producer = context.system.actorOf(Props(new ProducerCreater),"producer")

    //producer ! MessageTalk.Run



  }

  override def receive: Receive = {


    case MessageTalk.Stop => {

      println(".............. sender here is .....")
      var sendingperson = sender()
      println(sendingperson)
      context.stop(sendingperson)

    }

    case _ => println("went inside default case instead")


  }
}
