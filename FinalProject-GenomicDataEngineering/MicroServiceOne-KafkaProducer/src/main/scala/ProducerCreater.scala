import akka.{Done, NotUsed}
import akka.actor._
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Charan Kooram on 2/18/2017.
  */
class ProducerCreater(implicit  mat: Materializer) extends Actor {


  override def preStart() : Unit = self ! MessageTalk.Run

  override def postStop() : Unit = println("producer stopped next ")

  override def receive : Receive  = {
    case MessageTalk.Run => {

      Console println "Starting produer "

      val producerSettings = ProducerSettings(context.system, new ByteArraySerializer, new StringSerializer)
        .withBootstrapServers("localhost:9092")


     val source = scala.io.Source.fromFile("C:\\Users\\Colaberry2017\\Downloads\\1000genomes2.csv")

      // count the number of lines processed
      // cannot use mapAsync - because of mutable object
      var count = 0

      /*
      *  Source object created from the iterable from the i/o file
      *  Map - created producer record to publish into kafka publisher
      *  Sink - Producer from the sink ( Reactive Kafka example )
      * */
      val something: Future[Done] = Source.fromIterator(()=>source.getLines())
                                          .map(elem => {
                                                        count+=1
                                                        new ProducerRecord[Array[Byte],String]("topicA", elem)
                                                        })
                                          .runWith(Producer.plainSink(producerSettings))

      something.onComplete({
          _  => {
          println("in producer onComplete "+count)

        }
      })





      // Test - if the count printed is same in both statement - {Multithreading test}
      println("Testing number of lines1 :"+count)
      Thread.sleep(2500)
      println("Testing number of lines2 :"+count)


      }


      sender() ! MessageTalk.Stop

    }

   def manipulate(elem : String) : String = elem.toLowerCase


}


object MessageTalk {
  case object Run
  case object Stop
}