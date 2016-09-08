package filejobs.dsl

import java.io.InputStream

import cats.free.Free
import cats.free.Free.liftF
import filejobs.io.Backend

/**
  * Created by lukewyman on 9/7/16.
  */
sealed trait FileOpsA[A]
case class Write(is: InputStream, fn: String, d: String, be: Backend) extends FileOpsA[String]
case class Read(fn: String, d: String, be: Backend) extends FileOpsA[InputStream]
case class Delete(fn: String, d: String, be: Backend) extends FileOpsA[Unit]

object FileOps {
  type FileOps[A] = Free[FileOpsA, A]

  def write(is: InputStream, fn: String, d: String, be: Backend): FileOps[String] =
    liftF[FileOpsA, String](Write(is, fn, d, be))

  def read(fn: String, d: String, be: Backend): FileOps[InputStream] =
    liftF[FileOpsA, InputStream](Read(fn, d, be))

  def delete(fn: String, d: String, be: Backend) =
    liftF[FileOpsA, Unit](Delete(fn, d, be))
}



