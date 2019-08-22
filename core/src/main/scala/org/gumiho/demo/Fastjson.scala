package org.gumiho.demo

import java.io.{File, PrintWriter}
import java.sql.Timestamp

import com.alibaba.fastjson.{JSON, serializer}
import com.alibaba.fastjson.serializer.SerializerFeature

import scala.beans.BeanProperty

class Msg(
              @BeanProperty val time: Timestamp,
                @BeanProperty val value: V
          )

class V(
       )

object Fastjson {
    def main(args: Array[String]): Unit = {

    }

    def serialize() = {
        val msg = new Msg(new Timestamp(1), new V())
        val str = JSON.toJSONString(
            msg,
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.PrettyFormat
        )
        println(str)
    }

    def deserialize() = {
        val str = ""
        val msg = JSON.parseObject(str)
    }
}
