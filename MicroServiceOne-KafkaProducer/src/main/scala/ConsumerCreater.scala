import akka.actor._
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.Materializer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}
import akka.stream.scaladsl.Sink
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Charan Kooram on 2/21/2017.
  */
class ConsumerCreater(implicit mat : Materializer) extends Actor {

  override def preStart():Unit = {
    self ! MessageTalk.Run
  }

  override def postStop() : Unit = {
    Console println "Ending child consumer first"
  }

  override def receive: Receive = {

    case MessageTalk.Run => {

      Console println "Running Consumer"

      // Create Consumer Settings
      val consumerSettings = ConsumerSettings(context.system,new ByteArrayDeserializer,new StringDeserializer )
        .withBootstrapServers("localhost:9092")
        .withGroupId("group1")

      Consumer.committableSource(consumerSettings, Subscriptions.topics("topicA")).
        map(
          elem =>{
            //count+=1
            println("testing element "+elem.record.value())
            new ProducerRecord[Array[Byte],String]("topic_Another", elem.record.value())

            //elem
          }
        ).runWith(Sink.ignore).onComplete({
        case success => {
          println("number of lines read from kafka -- Never reached ???")
        }
      })

    }

    case _ => {
      println("default messages ")
    }
  }

}
