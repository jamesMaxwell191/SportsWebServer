package com.marcom.sportsbetting.config

import akka.actor._
import com.typesafe.config.Config
import scala.concurrent.duration._

/**
 * Created by douglas on 08/08/15.
 */
object Settings extends ExtensionId[SettingsImpl] with ExtensionIdProvider {
  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem) =
    new SettingsImpl(system.settings.config)

  override def get(system: ActorSystem): SettingsImpl = super.get(system)

}

class SettingsImpl(config: Config) extends Extension {

  lazy val Host = {
    try {
      config.getString("recs.remoting.host")
    } catch {
      case _ :Throwable => "localhost"
    }
  }

  lazy val Port = {
    try {
      config.getInt("recs.remoting.port")
    } catch {
      case _ :Throwable => 66166
    }
  }

  lazy val TimeToLive = {
    try {
      config.getInt("recs.cache.timeToLive").seconds
    } catch {
      case _ :Throwable => 120 seconds
    }
  }

  lazy val TimeToIdle = {
    try {
      config.getInt("recs.cache.timeToIdle").seconds
    } catch {
      case _ : Throwable => 60 seconds
    }
  }

  lazy val RestHost = {
    try {
      config.getString("recs.rest.remoting.host")
    } catch {
      case _ :Throwable => "localhost"
    }
  }

  lazy val RestPort = {
    try {
      config.getInt("recs.rest.remoting.port")
    } catch {
      case _ :Throwable => 9080
    }
  }

  lazy val PageSize = {
    try {
      config.getInt("recs.pageSize")
    } catch {
      case _ :Throwable => 5
    }
  }

  lazy val TotalRecommendations = {
    try {
      config.getInt("recs.totalRecommendations")
    } catch {
      case _ :Throwable => 3
    }
  }

  lazy val AggregatorTimeout = {
    try {
      config.getInt("recs.aggregatorTimeout").seconds
    } catch {
      case _ :Throwable => 10.seconds
    }
  }

  lazy val ApplicationTimeout = {
    try {
      config.getInt("recs.applicationTimeout").seconds
    } catch {
      case _ :Throwable => 10.seconds
    }
  }


}
