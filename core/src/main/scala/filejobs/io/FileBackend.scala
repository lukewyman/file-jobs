package filejobs.io

import java.security.{DigestInputStream, MessageDigest}

import filejobs.{Context, FileOps}
import filejobs.FileContext
import FileOps._
import java.io._
import java.nio.charset.StandardCharsets

import org.apache.commons.io.IOUtils


/**
  * Created by lukewyman on 9/8/16.
  */
object FileBackend {

  def write(is: InputStream, fn: FileName, c: Context): Option[Checksum] = {
    val md: MessageDigest = MessageDigest.getInstance("MD5")
    val dis: DigestInputStream = new DigestInputStream(is, md)
    try {
      val dir = c match {
        case FileContext(d) => d
        case _ => throw new IllegalArgumentException("Invalid file context provided")
      }
      val os = new FileOutputStream(new File(s"$dir/$fn"))
      IOUtils.copy(dis, os)
      dis.close
      os.close
      Some(IOUtils.toString(md.digest, "UTF-8"))
    } catch { case e: Exception => None }
  }

  def delete(fn: FileName, c: Context): Option[Unit] = {
    try {
      val dir = c match {
        case FileContext(d) => d
        case _ => throw new IllegalArgumentException("Invalid file context provided")
      }
      val f = new File(s"$dir/$fn")
      if (f.delete) Some(())
      else None
    } catch { case e: Exception => None}
  }

  def read(fn: FileName, c: Context): Option[InputStream] = {
    try {
      val dir = c match {
        case FileContext(d) => d
        case _ => throw new IllegalArgumentException("Invalid file context provided")
      }
      Some(new FileInputStream(s"$dir/$fn"))
    } catch { case e: Exception => None}
  }

  def list(c: Context): Option[Seq[String]] = ???
}

