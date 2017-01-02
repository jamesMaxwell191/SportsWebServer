package com.marcom.sportsbetting.test

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.sky.assignment.components.AppComponents
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

/**
 * Created by douglas on 09/08/15.
 */
abstract class AbstractAkkaTest extends TestKit(ActorSystem.create("recs-test")) with WordSpecLike with AppComponents
                                 with ImplicitSender with BeforeAndAfterAll{

  override protected def afterAll(): Unit = system.shutdown()

}
