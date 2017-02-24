package com.example.actors

import akka.actor._
import akka.actor.Props

class HelloWorldActor extends Actor with ActorLogging {

  override def postStop(): Unit = {
    Console println "at poststop helloworld actor "
  }

  override def preStart(): Unit = {

    Console println "in the prestart of HelloWorldActor"

    // create the greeter actor
    val greeter = context.actorOf(Props[GreeterActor], "greeter")

    Console println context.system

    // Send it the 'Greet' message
    //greeter ! GreeterMessages.Greet
    greeter ! RandomObject
  }

  def receive = {
    // When we receive the 'Done' message, stop this actor
    // (which if this is still the initialActor will trigger the deathwatch and stop the entire ActorSystem)
    case GreeterMessages.Done => {
      context.stop(self)
    }

    case "sending ack from random object" => {
      println("received ack from random object")
      var msg = "received ack from random object"
      log.info(msg)
    }
  }
}

