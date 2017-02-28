import java.io.IOException
import akka.http.scaladsl.server.Directives.logRequestResult
import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.server.PathMatchers.Segment
import akka.http.scaladsl.model.StatusCodes._
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import spray.json.DefaultJsonProtocol
import scala.concurrent.Future

// Revisit to try Unmarshalling with case classes
case class PoSoResult(Sample : String,Family_ID : String, Population: String , Population_Description: String, Gender: String
                      /*Relationship:Option[String], Unexpected_Parent_Child:Option[String], Non_Paternity:Option[String], Siblings:Option[String], Grandparents:Option[String],
                      Avuncular:Option[String], Half_Siblings:Option[String], Unknown_Second_Order:Option[String], Third_Order:Option[String], In_Low_Coverage_Pilot:Option[String],
                      LC_Pilot_Platforms:Option[String], LC_Pilot_Centers:Option[String], In_High_Coverage_Pilot:Option[String], HC_Pilot_Platforms:Option[String], HC_Pilot_Centers:Option[String],
                      In_Exon_Targetted_Pilot:Option[String], ET_Pilot_Platforms:Option[String], ET_Pilot_Centers:Option[String], Has_Sequence_in_Phase1:Option[String], Phase1_LC_Platform:Option[String],
                      Phase1_LC_Centers:Option[String], Phase1_E_Platform:Option[String], Phase1_E_Centers:Option[String], In_Phase1_Integrated_Variant_Set:Option[String], Has_Phase1_chrY_SNPS:Option[String],
                      Has_phase1_chrY_Deletions:Option[String], Has_phase1_chrMT_SNPs:Option[String], Main_project_LC_Centers:Option[String], Main_project_LC_platform:Option[String],
                      Total_LC_Sequence:Option[String], LC_Non_Duplicated_Aligned_Coverage:Option[String], Main_Project_E_Centers:Option[String], Main_Project_E_Platform:Option[String],
                      Total_Exome_Sequence:Option[String], X_Targets_Covered_to_20x_or_greater:Option[String], VerifyBam_E_Omni_Free:Option[String], VerifyBam_E_Affy_Free:Option[String],
                      VerifyBam_E_Omni_Chip:Option[String], VerifyBam_E_Affy_Chip:Option[String], VerifyBam_LC_Omni_Free:Option[String], VerifyBam_LC_Affy_Free:Option[String],
                      VerifyBam_LC_Omni_Chip:Option[String], VerifyBam_LC_Affy_Chip:Option[String], LC_Indel_Ratio:Option[String], E_Indel_Ratio:Option[String], LC_Passed_QC:Option[String],
                      E_Passed_QC:Option[String], In_Final_Phase_Variant_Calling:Option[String], Has_Omni_Genotypes:Option[String], Has_Axiom_Genotypes:Option[String], Has_Affy_6_0_Genotypes:Option[String],
                      Has_Exome_LOF_Genotypes:Option[String], EBV_Coverage:Option[String], DNA_Source_from_Coriell:Option[String], Has_Sequence_from_Blood_in_Index:Option[String], Super_Population:Option[String],
                      Super_Population_Description:Option[String]*/)

/**
  * Created by Charan Kooram on 2/24/2017.
  */
object GenomeDataServices extends App with DefaultJsonProtocol  {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  //implicit val poSoFormat = jsonFormat5(PoSoResult.apply)






  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  lazy val ipApiConnectionFlow: Flow[HttpRequest, HttpResponse, Any] =
    Http().outgoingConnection(config.getString("services.ip-api.host"), config.getInt("services.ip-api.port"))

  def createFlow(request: HttpRequest): Future[HttpResponse] = Source.single(request).via(ipApiConnectionFlow).runWith(Sink.head)

  def sendFirstRequest(id: String): Future[Either[String,String]]={
    // Sample uri -- working
    ///capstone223510/genomes/hg00579

    var uri_string = config.getString("constants.uri")
    createFlow(RequestBuilding.Get(uri_string+id))flatMap { response =>
      response.status match {
        case OK => Future.successful(Right(response.entity.toString()))
        //case BadRequest => Future.successful(Left(s"$id: incorrect IP format"))
        /*case _ => Unmarshal(response.entity).to[String].flatMap { entity =>
          val error = s"Elastic Search request failed with status code ${response.status} and entity $entity"
          logger.error(error)
          Future.failed(new IOException(error))
        }*/

        case _ => Future.failed(new IOException("error"))
      }
    }

  }


  val routes = {
    logRequestResult("api-final-project") {
       pathPrefix("api") {
        (get & path(Segment)) { elem =>
          complete {
            sendFirstRequest(elem).map[String]{
              case Right(poSoResult) =>  poSoResult
              case Left(errorMessage) => errorMessage
            }

          }
        }
      }
    }
  }




  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))

}
