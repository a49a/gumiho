package org.gumiho.lib.crypto

import java.util.{Base64, UUID}

import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

object AESUtils {
    val KEY_ALGORITHM = "AES"
    val CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"
    val IV = "AAAAAAAAAAAAAAAA"

    def encrypt(content: String, key: String, iv: String) = {
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, genSecretKey(key), genIv(iv))
        val result = cipher.doFinal(content.getBytes())
        new String(Base64.getEncoder.encode(result), "UTF-8")
    }

    def decrypt(content: String, key: String, iv: String) = {
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        //genIv(iv)
        cipher.init(Cipher.DECRYPT_MODE, genSecretKey(key), genIv(iv))
        val result = cipher.doFinal(Base64.getDecoder.decode(content))
        new String(result, "UTF-8")
    }

    private def genSecretKey(key: String) = {
        new SecretKeySpec(key.getBytes(), KEY_ALGORITHM)
    }

    private def genIv(iv: String) = {
        //val iv = key.substring(0, 16).getBytes()
        new IvParameterSpec(iv.getBytes())
    }

    def genKey(): String = {
        val uuid = UUID
            .randomUUID()
            .toString()
            .replaceAll("-", "")
            .toLowerCase()
        uuid
    }

    private def oidGenIv(key: String) = {
        val iv = key.substring(0, 16).getBytes()
        new IvParameterSpec(iv)
    }

}
