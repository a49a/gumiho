package org.gumiho.net.akka

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object Company extends App {
    val system = ActorSystem("company",  ConfigFactory.load().getConfig("company"))
    val founder = system.actorOf(Props[FounderActor], "founder")
}

trait Message {
    val content: String
}

case class SimpleGreet(content: String) extends Message {}
case class Business(content: String) extends Message {}
case class Meeting(content: String) extends Message {}

class SimpleTalker extends Actor {
    def receive = {
        case SimpleGreet(name) => println(s"Hello ${name}")
    }
}

class FounderActor extends Actor {
    def receive = {
        case business: Business => {
            val inspector = context.actorOf(Props[Inspector], "inspector")
            inspector ! Meeting("meeting" + business.content)
        }
        case _ => throw new Exception("Some Error")
    }
}

class Inspector extends Actor {
    def receive = {
        case meeting: Meeting => {
            println(meeting)
            println(self.path)
        }
    }
}