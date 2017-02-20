import akka.Done
import akka.actor._
import akka.actor.ActorSystem
import akka.kafka.ConsumerMessage.CommittableOffsetBatch
import akka.kafka.{ConsumerSettings, ProducerSettings, Subscriptions}
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.stream.Materializer
import akka.stream.scaladsl.{Keep, Sink}
import kafka.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, ByteArraySerializer, StringDeserializer, StringSerializer}

import scala.concurrent.Future


/**
  * Created by Charan Kooram on 2/18/2017.
  */
class ConsumerCreater(implicit mat: Materializer )  extends Actor {

  override def preStart() : Unit = {
    self ! MessageTalk.Run
  }

  override def receive : Receive = {

    case MessageTalk.Run => {

      // Create Producer Settings for new topic -- topic3
      val producerSettings = ProducerSettings(context.system, new ByteArraySerializer, new StringSerializer)
        .withBootstrapServers("localhost:9092")

      val prodsink = Producer.plainSink(producerSettings)

      // Create Consumer Settings
      val consumerSettings = ConsumerSettings(context.system,new ByteArrayDeserializer,new StringDeserializer )
                                .withBootstrapServers("localhost:9092")
                                .withGroupId("group1")
                                //.withProperty(ConsumerConfig.AutoOffsetReset,"earliest")
      Consumer.committableSource(consumerSettings, Subscriptions.topics("topic2")).
        map(
            elem =>{



            //println("testing element "+elem.record.value())
              new ProducerRecord[Array[Byte],String]("topic3", elem.record.value())

              //elem
            }
        ).runWith(prodsink)




      /*
      val (control, future) = RandomNumberSource.create("loggingConsumer")(context.system)
        .mapAsync(2)(processMessage)
        .map(_.committableOffset)
        .groupedWithin(10, 15 seconds)
        .map(group => group.foldLeft(CommittableOffsetBatch.empty) { (batch, elem) => batch.updated(elem) })
        .mapAsync(1)(_.commitScaladsl())
        .toMat(Sink.ignore)(Keep.both)
        .run() */


    }

    case _ => println("In default case run")
  }








}


object MessageTalk {
  case object Stop
  case object Run
}
