package com.marcom.sportsbetting.services

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import com.marcom.sports.model.SportsFixture
import com.marcom.sportsbetting.services.SportingFixtureService.UpdateFixture

/**
  * Created by douglasm on 02/01/2017.
  */
class SportingFixtureService extends Actor with ActorLogging {

  val mediator = DistributedPubSub(context.system).mediator

  override def receive: Receive = {
    case UpdateFixture(sf) => sender ! "published fixture"
       mediator ! Publish("sportscontent",sf)
    case _ => sender ! "got message"
  }
}

object SportingFixtureService {

  def props = Props[SportingFixtureService]

  case class UpdateFixture(sf:SportsFixture)
}
