package filejobs.io

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.charset.StandardCharsets

import org.apache.commons.io.IOUtils

/**
  * Created by lukewyman on 9/8/16.
  */
trait Backend {

  def write(is: InputStream, fn: String, d: String): String

  def read(fn: String, d: String): InputStream

  def delete(fn: String, d: String): Unit

}

class StubBackend extends Backend {
  def write(is: InputStream, fn: String, d: String): String = {
    println(s"Writing file $fn to directory $d")
    println(s"These are the contents being written: ${IOUtils.toString(is, "UTF-8")}")
    "Done"
  }

  def delete(fn: String, d: String): Unit = {
    println(s"Deleting file $fn from $d")
  }

  def read(fn: String, d: String): InputStream = {
    val content = "some fake file content"
    println(s"Reading from file $fn in directory $d")
    new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))
  }
}
