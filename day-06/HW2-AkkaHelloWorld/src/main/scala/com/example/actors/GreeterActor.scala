package com.example.actors

import akka.actor.Actor
import akka.actor.ActorLogging

// Note: Usually the message object (GreeterMessages) and the actor class (GreeterActor) will be called the same thing (eg. Greeter)
object GreeterMessages {
  case object Greet
  case object Done
}

object RandomObject{

}
class AnotherTest extends Actor {

  override def preStart() : Unit =  {

  }

  override def receive = {
    case _ => "do nothing"
  }
}
class GreeterActor extends Actor with ActorLogging {


  override def postStop(): Unit = {
    Console println "at poststop greeteractor "
  }

  override def preStart():Unit = {
    Console println "in prestart of greeter actor "
    Console println "self path "+self.path


  }

  def receive = {
    case GreeterMessages.Greet => {
      var greetMsg = "Hello World!"

      println(greetMsg)
      log.info(greetMsg)

      context.sender() ! GreeterMessages.Done // Send the 'Done' message back to the sender
    }

    case RandomObject => {
      var msgToPrint = "in Random Object"
      var whoSent = sender()
      println("who sent "+whoSent)
      println(msgToPrint)
      log.info(msgToPrint)
      context.sender() ! "sending ack from random object"
      context.sender() ! GreeterMessages.Done
    }
  }

}