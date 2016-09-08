package filejobs.dsl

import java.io.InputStream

import cats.free.Free
import cats.free.Free.liftF

/**
  * Created by lukewyman on 9/7/16.
  */
sealed trait FileOpsA[A]
case class Write(is: InputStream, fn: String, d: String) extends FileOpsA[String]
case class Read(fn: String, d: String) extends FileOpsA[InputStream]
case class Delete(fn: String, d: String) extends FileOpsA[Unit]

object FileOps {
  type FileOps[A] = Free[FileOpsA, A]

  def write(is: InputStream, fn: String, d: String): FileOps[String] =
    liftF[FileOpsA, String](Write(is, fn, d))

  def read(fn: String, d: String): FileOps[InputStream] =
    liftF[FileOpsA, InputStream](Read(fn, d))

  def delete(fn: String, d: String) =
    liftF[FileOpsA, Unit](Delete(fn, d))
}

object Jobs {

  import filejobs.dsl.FileOps._

  def job: FileOps[Unit] =
    for {
      is <- read("myfile1.txt", "myfiles")
      _  <- write(is, "yourfile1.txt", "yourfiles")
      _  <- delete("myfile1.txt", "myfiles")
    } yield ()
}

object Compiler {



}


