package com.marcom.sportsbetting

import akka.actor.ActorSystem
import akka.cluster.Cluster
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.kafka.ProducerSettings
import com.marcom.sportsbetting.services.SportingFixtureService
import com.marcom.sportsbetting.services.SportingFixtureService.UpdateFixture
import spray.json.DefaultJsonProtocol._
import akka.pattern.ask
import akka.util.Timeout
import com.marcom.sports.model.{FixtureId, SportsFixture}
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

import scala.concurrent.duration._
import scala.io.StdIn


object WebServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("ClusterSystem")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    implicit val timeout = Timeout(10 seconds)
    val sportingFixtureService = system.actorOf(SportingFixtureService.props)

    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      } ~
      path("sports"){
        get {
          val fut = (sportingFixtureService ? UpdateFixture(SportsFixture(FixtureId("4"),"Rangers vs Celtic","3-0"))).mapTo[String]
          onSuccess(fut){ extraction =>
             complete(extraction)
          }
        }
      }




    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    val cluster = Cluster(system)
    cluster.registerOnMemberUp {

    }
    cluster.registerOnMemberRemoved{
      system.registerOnTermination {
        System.exit(1)
      }

      bindingFuture
        .flatMap(_.unbind()) // trigger unbinding from the port
        .onComplete(_ => system.terminate()) // and shutdown when done

    }

  }
}