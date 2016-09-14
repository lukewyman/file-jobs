package filejobs.io

import filejobs.dsl.FileOps._
import java.io.{ByteArrayInputStream, InputStream}
import java.nio.charset.StandardCharsets

import org.apache.commons.io.IOUtils


/**
  * Created by lukewyman on 9/8/16.
  */
object StubBackend {

  def write(is: InputStream, fn: FileName, d: Directory): Checksum = {
    println(s"Writing file $fn to directory $d")
    println(s"These are the contents being written: ${IOUtils.toString(is, "UTF-8")}")
    "Done"
  }

  def delete(fn: FileName, d: Directory): Unit = {
    println(s"Deleting file $fn from $d")
  }

  def read(fn: FileName, d: Directory): InputStream = {
    val content = "some fake file content"
    println(s"Reading from file $fn in directory $d")
    new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))
  }

}

object FileBackend {

  def write(is: InputStream, fn: FileName, d: Directory): Checksum = ???

  def delete(fn: FileName, d: Directory): Unit = ???

  def read(fn: FileName, d: Directory): InputStream = ???
}

object S3Backend {

  def write(is: InputStream, fn: FileName, d: Directory): Checksum = ???

  def delete(fn: FileName, d: Directory): Unit = ???

  def read(fn: FileName, d: Directory): InputStream = ???
}