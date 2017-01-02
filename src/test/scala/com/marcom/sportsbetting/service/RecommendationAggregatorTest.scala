package com.marcom.sportsbetting.service

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.testkit.TestProbe
import akka.util.Timeout
import com.marcom.sportsbetting.test.AbstractAkkaTest
import com.sky.assignment.components.ActorFactoryDefs.ActorFactory
import com.sky.assignment.service.RecommendationsAggregator.{GetAggregateRecommendations, RecommendationResponse}
import com.sky.assignment.service.RecommendationsService.GetRecommendations

import scala.concurrent.duration._


/**
 * Created by douglas on 09/08/15.
 */
class RecommendationAggregatorTest extends AbstractAkkaTest{




  implicit val timeout = Timeout(5 seconds)


  val probe = TestProbe()

  override val aggregatorFactory: ActorFactory = context => context.actorOf(RecommendationsAggregator.props(recClientFactory))

  override val recClientFactory: ActorFactory = context => context.actorOf(Props(classOf[ProbeDelegateActor],probe.ref))

  "a recommendations aggregator" must {
       "return a list of recommendations" in {
           val aggregator =  aggregatorFactory(system)
           aggregator ! GetAggregateRecommendations("joe")
         probe.expectMsgClass(classOf[GetRecommendations])
         probe.reply(<recommendations><recommendations><uuid>{"UUID1"}</uuid><start>{1234L}</start><end>{23456L}</end></recommendations><recommendations><uuid>{"UUID2"}</uuid><start>{123456L}</start><end>{2345678L}</end></recommendations></recommendations>)
         probe.expectMsgClass(classOf[GetRecommendations])
         probe.reply(<recommendations><recommendations><uuid>{"UUID1"}</uuid><start>{1234L}</start><end>{23456L}</end></recommendations><recommendations><uuid>{"UUID2"}</uuid><start>{123456L}</start><end>{2345678L}</end></recommendations></recommendations>)
         probe.expectMsgClass(classOf[GetRecommendations])
         probe.reply(<recommendations><recommendations><uuid>{"UUID1"}</uuid><start>{1234L}</start><end>{23456L}</end></recommendations><recommendations><uuid>{"UUID2"}</uuid><start>{123456L}</start><end>{2345678L}</end></recommendations></recommendations>)
         expectMsgClass(5.seconds, classOf[RecommendationResponse])
         probe.
       }
  }
}

class ProbeDelegateActor(val testProbe:ActorRef) extends Actor {

     def receive :Receive = {
       case  any:Any =>  testProbe forward any
     }
}

