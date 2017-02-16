package com.example.actors

import akka.actor._

/**
  * Created by Colaberry2017 on 2/15/2017.
  */
class Receiver extends Actor with ActorLogging {

  def receive = {
    case GreeterMessages.Greet => {
      var greetmessage = "your greeting received"
      println(greetmessage)
      log.info(greetmessage)

      context.sender() ! GreeterMessages.Done
    }
  }

}
