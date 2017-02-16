package com.example.actors

import akka.actor._

/**
  * Created by Colaberry2017 on 2/15/2017.
  */
class Sender extends Actor {

  override def preStart(): Unit = {
      val receiver = context.actorOf(Props[Receiver],"receiver")
      receiver ! GreeterMessages.Greet
  }

  override def receive= {
    case GreeterMessages.Done => {
      context.stop(self)
    }
  }

}
