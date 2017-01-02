package com.marcom.sportsbetting.config

import akka.actor.ActorSystem

/**
 * Created by douglas on 08/08/15.
 */
trait AppSettings {
   val system:ActorSystem

    lazy val settings = Settings(system)
}
