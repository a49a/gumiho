package org.gumiho.lib

import java.io.{ByteArrayInputStream, InputStream}
import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem, Path}
import org.apache.hadoop.io.IOUtils

object HdfsUtils {
    def main(args: Array[String]): Unit = {

    }

    def appendToFile(targetPath: Path, conf: Configuration, input: String): Unit = {
        this.synchronized({
            val fs = FileSystem.get(URI.create(targetPath.toString), conf)
            var out:FSDataOutputStream = null
            if(!fs.exists(targetPath)) {
                out = fs.create(targetPath)
            }else {
                out = fs.append(targetPath)
            }
            val bytes = input.getBytes("UTF-8")
//            val in: InputStream = new ByteArrayInputStream(bytes);
//            IOUtils.copyBytes(in, out, 4096, true);
            out.write(bytes)
            out.close()
            fs.close()
        })
    }
}
