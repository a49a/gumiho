package org.gumiho.demo.scala

object ImplicitParameters {
    implicit val a: String = "foo"

    def main(args: Array[String]): Unit = {
        hello("bar")
    }

    def hello(a: String)(implicit b: String) = {
        println(a concat b)
    }
}
