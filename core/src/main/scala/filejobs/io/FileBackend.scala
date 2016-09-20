package filejobs.io

import java.security.{DigestInputStream, MessageDigest}

import filejobs.dsl.FileOps._
import java.io._
import java.nio.charset.StandardCharsets

import org.apache.commons.io.IOUtils


/**
  * Created by lukewyman on 9/8/16.
  */
object FileBackend {

  def write(is: InputStream, fn: FileName, d: Directory): Option[Checksum] = {
    val md: MessageDigest = MessageDigest.getInstance("MD5")
    val dis: DigestInputStream = new DigestInputStream(is, md)
    try {
      val os = new FileOutputStream(new File(s"$d/$fn"))
      IOUtils.copy(dis, os)
      dis.close
      os.close
      Some(IOUtils.toString(md.digest, "UTF-8"))
    } catch { case e: Exception => None }
  }

  def delete(fn: FileName, d: Directory): Option[Unit] = {
    try {
      val f = new File(s"$d/$fn")
      if (f.delete) Some(())
      else None
    } catch { case e: Exception => None}
  }

  def read(fn: FileName, d: Directory): Option[InputStream] = {
    try {
      Some(new FileInputStream(s"$d/$fn"))
    } catch { case e: Exception => None}
  }

  def list(d: Directory): Option[Seq[String]] = ???
}

