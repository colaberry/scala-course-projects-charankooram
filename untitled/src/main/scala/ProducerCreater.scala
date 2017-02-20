import akka.NotUsed
import akka.actor._
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

/**
  * Created by Charan Kooram on 2/18/2017.
  */
class ProducerCreater(implicit  mat: Materializer) extends Actor {

  override def preStart() : Unit = {
    println("in producer creater")

    self ! MessageTalk.Run



  }

  override def postStop() : Unit =  {
    println("producer stopped ")
  }

  override def receive : Receive  = {
    case MessageTalk.Run => {

      val producerSettings = ProducerSettings(context.system, new ByteArraySerializer, new StringSerializer)
        .withBootstrapServers("localhost:9092")


      val source = scala.io.Source.fromFile("C:\\Users\\Colaberry2017\\Downloads\\1000genomes.csv")
      /*val s1 =   Source.fromIterator(()=>source.getLines())
      val s2 =  s1.map(elem=>new ProducerRecord[Array[Byte],String]("topic1", manipulate(elem)))
      val s3 =   s2.runWith(Producer.plainSink(producerSettings))*/

      Source.fromIterator(()=>source.getLines()).map(elem => new ProducerRecord[Array[Byte],String]("topic2", manipulate(elem))).runWith(Producer.plainSink(producerSettings))

      // s1.

          //.map(elem => new ProducerRecord[Array[Byte],String]("topic1", elem)).runWith(Producer.plainSink(producerSettings))
      }

      println(" after producer stuff ")
      sender() ! MessageTalk.Stop

    }

   def manipulate(elem : String) : String = elem.toLowerCase


}


object MessageTalk {
  case object Run
  case object Stop
}