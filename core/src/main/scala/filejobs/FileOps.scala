package filejobs

import java.io.InputStream

import cats.free.Free
import cats.free.Free.liftF
import filejobs.FileOps._

/**
  * Created by lukewyman on 9/7/16.
  */
sealed trait FileOpsA[A]
case class Write(is: InputStream, fn: FileName, c: Context, f: WriteOp) extends FileOpsA[String]
case class Read(fn: FileName, c: Context, f: ReadOp) extends FileOpsA[InputStream]
case class Delete(fn: FileName, c: Context, f: DeleteOp) extends FileOpsA[Unit]
case class List(c: Context, f: ListOp) extends FileOpsA[Seq[String]]

sealed trait Context
case class FileContext(dir: String) extends Context
case class S3Context(dir: String, bucket: String, region: String) extends Context

object FileOps {
  type FileOps[A] = Free[FileOpsA, A]
  type FileName = String
  type Checksum = String
  type WriteOp = (InputStream, FileName, Context) => Option[Checksum]
  type ReadOp = (FileName, Context) => Option[InputStream]
  type DeleteOp = (FileName, Context) => Option[Unit]
  type ListOp = Context => Option[Seq[String]]

  def write(is: InputStream, fn: FileName, c: Context)(f: WriteOp): FileOps[Checksum] =
    liftF[FileOpsA, String](Write(is, fn, c, f))

  def read(fn: FileName, c: Context)(f: ReadOp): FileOps[InputStream] =
    liftF[FileOpsA, InputStream](Read(fn, c, f))

  def delete(fn: FileName, c: Context)(f: DeleteOp) =
    liftF[FileOpsA, Unit](Delete(fn, c, f))

  def list(c: Context)(f: ListOp) =
    liftF[FileOpsA, Seq[String]](List(c, f))
}



