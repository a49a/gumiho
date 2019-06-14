package org.gumiho.util

import java.util.Properties


object ConfUtils {
    def readProperties(path: String) = {
        val prop: Properties = new Properties()
        val inputStream = ConfUtils.getClass.getClassLoader.getResourceAsStream(path)
        prop.load(inputStream)
        inputStream.close()
        prop
    }
}
