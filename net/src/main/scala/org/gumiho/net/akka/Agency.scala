package org.gumiho.net.akka

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object Agency extends App {
    val system = ActorSystem("agency", ConfigFactory.load().getConfig("agency"))
    val customerActor = system.actorOf(Props[Customer], name = "customer")

    customerActor ! Send
}

case object Send

class Customer extends Actor {
    val companyActor = context.actorSelection("akka.tcp://company@192.168.124.12:40414/user/founder")

    def receive = {
        case Send => companyActor ! Business("customer demand")
    }
}
