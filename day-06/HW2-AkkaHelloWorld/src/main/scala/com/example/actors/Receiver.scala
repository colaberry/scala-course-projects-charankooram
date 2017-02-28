package com.example.actors

import akka.actor._

/**
  * Created by Colaberry2017 on 2/15/2017.
  */
class Receiver extends Actor with ActorLogging {


  override def preStart():Unit = {
    Console println ("in prestart of receiver!")
  }

  def receive = {
    case GreeterMessages.Greet => {
      var greetmessage = "your greeting received"
      Console println greetmessage
      log info greetmessage

      context.sender() ! GreeterMessages.Done
    }
  }

}
