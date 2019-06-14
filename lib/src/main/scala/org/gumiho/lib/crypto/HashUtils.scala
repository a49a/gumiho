package org.gumiho.lib.crypto

import java.security.MessageDigest

object HashUtils {
    def getMD5Hex32(content: String) = {
        val digest = MessageDigest.getInstance("MD5")
        val hashBytes = digest.digest(content.getBytes())
        hashBytes.map((x) => {
            val hex = Integer.toHexString(x & 0xFF)
            hex match {
                case x if x.length == 1 => s"0$x"
                case _ => hex
            }
        }).reduce((x, y) => {
            x + y
        })
    }
}
