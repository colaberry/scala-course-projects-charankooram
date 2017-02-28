import akka.Done
import akka.actor._
import akka.actor.ActorSystem
import akka.kafka.ConsumerMessage.CommittableOffsetBatch
import akka.kafka.{ConsumerSettings, ProducerSettings, Subscriptions}
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.stream.Materializer
import akka.stream.scaladsl.{Keep, Sink}
import com.typesafe.config.ConfigFactory
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
        .withBootstrapServers(ConfigFactory.load().getString("constants.ip")+":"+ConfigFactory.load().getString("constants.port"))

      val prodsink = Producer.plainSink(producerSettings)

      // Create Consumer Settings
      val consumerSettings = ConsumerSettings(context.system,new ByteArrayDeserializer,new StringDeserializer )
                                .withBootstrapServers(ConfigFactory.load().getString("constants.ip")+":"+ConfigFactory.load().getString("constants.port"))
                                .withGroupId(ConfigFactory.load().getString("constants.group"))

      /*
      *  Source - KAFKA Consumer to subscribe from topicA
      *  Map function - Manipulate (lowercase all the string objects) each record from this topic and push into Another topic in KAFKA
      *  Sink - Producer sink created earlier
      * */
      Consumer.committableSource(consumerSettings, Subscriptions.topics(ConfigFactory.load().getString("constants.consumer-topic"))).
        map(
            elem =>{
             count+=1
              println("testing element "+elem.record.value()+" "+count)
              new ProducerRecord[Array[Byte],String](ConfigFactory.load().getString("constants.producer-topic"), manipulate(elem.record.value()))

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
