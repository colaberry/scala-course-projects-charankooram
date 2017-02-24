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
import scala.concurrent.ExecutionContext.Implicits.global

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

     var count = 0

   // Create Producer Settings for new topic -- topic3
      val producerSettings = ProducerSettings(context.system, new ByteArraySerializer, new StringSerializer)
        .withBootstrapServers("localhost:9092")

      val prodsink = Producer.plainSink(producerSettings)

      // Create Consumer Settings
      val consumerSettings = ConsumerSettings(context.system,new ByteArrayDeserializer,new StringDeserializer )
                                .withBootstrapServers("localhost:9092")
                                .withGroupId("group1")

      /*
      *  Source - KAFKA Consumer to subscribe from topicA
      *  Map function - Manipulate (lowercase all the string objects) each record from this topic and push into Another topic in KAFKA
      *  Sink - Producer sink created earlier
      * */
      Consumer.committableSource(consumerSettings, Subscriptions.topics("topicA")).
        map(
            elem =>{
             count+=1
              println("testing element "+elem.record.value()+" "+count)
              new ProducerRecord[Array[Byte],String]("topic3", manipulate(elem.record.value()))

              //elem
            }
        ).runWith(prodsink).onComplete({
        case success => {
          println("number of lines read from kafka "+count)
        }
      })




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



  def manipulate(s:String) : String = s.toLowerCase






}


object MessageTalk {
  case object Stop
  case object Run
}
