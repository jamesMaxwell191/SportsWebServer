package com.marcom.sportsbetting.services

import java.util

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Keep, Sink, Source}
import com.marcom.sports.model.SportsFixture
import com.marcom.sportsbetting.services.SportingFixtureService.UpdateFixture
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, Serializer, StringSerializer}

/**
  * Created by douglasm on 02/01/2017.
  */
class SportingFixtureService extends Actor with ActorLogging {

  implicit val mat = ActorMaterializer()

  val producerSettings = ProducerSettings(context.system, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers("Douglass-Pro:32768")

  val stream = Source.queue[SportsFixture](20,OverflowStrategy.dropHead).map(sf => new ProducerRecord[Array[Byte],String]("test","joe") ).toMat(Producer.plainSink(producerSettings))(Keep.left)

  val queue = stream.run()

  override def receive: Receive = {
    case UpdateFixture(sf) => sender ! "published fixture"
       queue offer sf
       log.info("updated kafka")
    case _ => sender ! "got message"
  }
}

object SportingFixtureService {

  def props = Props[SportingFixtureService]

  case class UpdateFixture(sf:SportsFixture)

  class SportsFixtureSerializer extends Serializer[SportsFixture] {
    override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = ()

    override def serialize(topic: String, data: SportsFixture): Array[Byte] = {

    }

    override def close(): Unit = ()
  }
}
