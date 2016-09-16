package filejobs.io

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.charset.StandardCharsets

import filejobs.dsl.FileOps._
import org.apache.commons.io.IOUtils

/**
  * Created by lukewyman on 9/15/16.
  */
object StubBackend {

  def write(is: InputStream, fn: FileName, d: Directory): Option[Checksum] = {
    println(s"Writing file $fn to directory $d")
    println(s"These are the contents being written: ${IOUtils.toString(is, "UTF-8")}")
    Some("Done")
  }

  def delete(fn: FileName, d: Directory): Option[Unit] = {
    println(s"Deleting file $fn from $d")
    Some(())
  }

  def read(fn: FileName, d: Directory): Option[InputStream] = {
    val content = "some fake file content"
    println(s"Reading from file $fn in directory $d")
    Some(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)))
  }

}