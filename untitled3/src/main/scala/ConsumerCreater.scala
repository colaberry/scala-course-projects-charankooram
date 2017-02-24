import java.io.{BufferedWriter, File, FileWriter, IOException}

import scala.util._
//import java.io._
import scala.io._
import akka.Done
import akka.actor._
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.http.scaladsl.Http
import akka.kafka.ConsumerMessage.CommittableOffsetBatch
import akka.kafka.{ConsumerSettings, ProducerSettings, Subscriptions}
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Keep, Sink}
import kafka.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, ByteArraySerializer, StringDeserializer, StringSerializer}
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.StatusCodes.{BadRequest, OK}
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.ContentNegotiator.Alternative.ContentType
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * Created by Charan Kooram on 2/18/2017.
  */
class ConsumerCreater(implicit mat: Materializer, implicit val system:ActorSystem )  extends Actor {

  override def preStart() : Unit = {
    self ! MessageTalk.Run
  }

  override def receive : Receive = {



    case MessageTalk.Run => {



      Console println "Reading File ..."
      val source = scala.io.Source.fromFile("C:\\Users\\Colaberry2017\\Downloads\\1000genomes2.csv")



      val firstLine: Array[String] = source.getLines().take(1).next().split(",")
      println("Test -- Number of Columns in First Line (Header from File) "+firstLine.length)


      // Initial Trial Code for future reference -- Creating a Source from a single HTTP Request
      //val query_string = "training/student/450 {\"firstName\"=\"loadMax\",\"lastName\"=\"gitMax\"}"

      /*Source.single({
        //val request =  RequestBuilding.Post(query_string)
        //RequestBuilding.Post.
        HttpRequest(HttpMethods.POST,uri ="/training/student/450",entity = ByteString("{\"firstName\":\"loadMax\",\"lastName\":\"gitMax\"}"))
      }).via(ipApiConnectionFlow).runWith(Sink.head).flatMap { response =>
        response.status match {

          case OK => {
            println(response.toString())
            Unmarshal(response.entity).to[String].flatMap { entity =>
              val error = s" Something with status code ${response.status} and entity $entity"

              Future.successful(error)

            }
        }

        case _ => { Console println "inside wildcard case statement"
          Unmarshal(response.entity).to[String].flatMap { entity =>
          val error = s"status is  ${response.status} and entity is  $entity"
          //logger.error(error)
            println(entity)
          Future.failed(new IOException(error))
        }}

        }
      }  */


      // Create Consumer Settings
      val consumerSettings = ConsumerSettings(context.system,new ByteArrayDeserializer,new StringDeserializer )
                                .withBootstrapServers("localhost:9092")
                                .withGroupId("group1")

      /*
      *   Source - KAFKA Consumer subscribed to topic3
      *   Map Function - Read Line/Tokenize column data/map with key  values from header for HTTP body
      *   / Create HTTP request - return HttpRequest Object
      *   via - Establish Connection with destination ipaddress and port number
      *   Sink - ignore because no real goal
      *
      * */
      val returnedValue: Future[Done] = Consumer.committableSource(consumerSettings, Subscriptions.topics("topic3")).
        map(
            f = elem => {
              val line = elem.record.value()
              var word_array = line.split(",")
              val first = word_array(0)
              val id = first.substring(1, first.length - 1) // Used to send as unique id in http request path




              var list: ArrayBuffer[String] = ArrayBuffer() // Used to collect data from each coloum for every row

              var i = 0
              var flag: Boolean = true
              var tempBuffer = StringBuilder.newBuilder


              /*
              *  Code to normalize column separated valued strings to prepare for json string preparation (each value is a  value for a key from the header)
              *  Few observations about the data -
              *  some columns have empty strings "" ,, etc (http request will fail because of invalid json type)
              *  some columns have strings which have any number of internal commas (,) (program will terminate because of OutOfBoundsException by
              *                                                                          creating more number of cols than expected)
              *  Store all valid column values in list (ArrayBuffer)
              * */
              while ( i <=  word_array.length - 1) {
                var str_ = word_array(i)

                if(str_.length()==0 || str_ ==""){
                  // If empty string found ; normalize

                  list +=("\"empty_column\"")
                }else if (str_.charAt(0) == '"' && str_.charAt(str_.length-1) !='"' ) {
                  // If column with any number of non-delimiter/internal (,) commas found
                  // Update the flag for the first occurence
                  // store the next subsequent values in a temp buffer to not create any
                  // extra columns - until valid occurence is found

                  flag = false
                  tempBuffer.append(str_ +"-")
                } else {

                  if(flag) list += str_
                  else{
                    tempBuffer.append(str_ +"-")
                    // When valid occurence is found - update the flag and resume original parsing
                    // clear the buffer
                    if(str_.charAt(str_.length-1)=='"'){
                      flag = true
                      list +=(tempBuffer.delete(tempBuffer.length()-1,tempBuffer.size).toString)
                      tempBuffer.clear()
                    }
                  }
                }
                i+=1
              }


              // For Debugging Purpose
              // Check if all rows have same number of columns
              // Otherwise print and investigate if any errors in the logical json parser
              if(list.size!=firstLine.size)  {
                  println("-----------------------------------------------------")
                  println(list+" "+list.size)
                  println("-----------------------------------------------------")
                } //write("what failed was "+line(0))



              var builder = StringBuilder.newBuilder
              builder.append("{")
              var iter2 = 0
              while(iter2<list.size){
                  var str = firstLine(iter2)+":"+list(iter2)+","
                  builder.append(str)
                  iter2 +=1
                }

              builder.delete(builder.size-1,builder.size)
              builder.append("}")



              // For Debugging json body requests...
              println(builder.toString())

              // Initial successfully working json body request
              // "{\"data\":\"" + id + "\"}"

              val req = HttpRequest(HttpMethods.POST, uri = s"/capstone223510/genomes/$id", entity = HttpEntity(ContentTypes.`application/json`, ByteString(builder.toString())))

              req


            }
        ).via(ipApiConnectionFlow)
        .runWith(Sink.ignore)













    }

    case _ => println("In default case run")
  }


  lazy val ipApiConnectionFlow: Flow[HttpRequest, HttpResponse, Any] =
    Http().outgoingConnection(ConfigFactory.load().getString("services.ip-api.host"), ConfigFactory.load().getInt("services.ip-api.port"))







}


object MessageTalk {
  case object Stop
  case object Run
}
