package filejobs.io

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.charset.StandardCharsets

import filejobs.{Context, FileOps}
import filejobs.FileContext
import FileOps._
import org.apache.commons.io.IOUtils

/**
  * Created by lukewyman on 9/15/16.
  */
object StubBackend {

  def write(is: InputStream, fn: FileName, c: Context): Option[Checksum] = {
    val dir = c match { case FileContext(d) => d}
    println(s"Writing file $fn to directory $dir")
    println(s"These are the contents being written: ${IOUtils.toString(is, "UTF-8")}")
    Some("Done")
  }

  def delete(fn: FileName, c: Context): Option[Unit] = {
    val dir = c match { case FileContext(d) => d }
    println(s"Deleting file $fn from $dir")
    Some(())
  }

  def read(fn: FileName, c: Context): Option[InputStream] = {
    val content = "some fake file content"
    val dir = c match { case FileContext(d) => d}
    println(s"Reading from file $fn in directory $dir")
    Some(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)))
  }

  def list(c: Context): Option[Seq[String]] = {
    val dir = c match { case FileContext(d) => d}
    println(s"Listing the files in directory $dir")
    Some(Seq("file1.txt", "file2.txt", "file3.txt", "file4.txt"))
  }

}