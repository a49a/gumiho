package org.gumiho.net.akka

import akka.actor.{Actor, ActorSystem, Props}

object HelloWorld extends App {
    val system = ActorSystem("HelloActors")
    val talker = system.actorOf(Props[SimpleTalker], "talker")

    talker ! SimpleGreet("haha")
}

case class SimpleGreet(name: String)

class SimpleTalker extends Actor {
    def receive = {
        case SimpleGreet(name) => println(s"Hello ${name}")
    }
}
