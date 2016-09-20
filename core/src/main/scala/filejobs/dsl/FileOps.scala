package filejobs.dsl

import java.io.InputStream

import cats.free.Free
import cats.free.Free.liftF
import filejobs.dsl.FileOps._

/**
  * Created by lukewyman on 9/7/16.
  */
sealed trait FileOpsA[A]
case class Write(is: InputStream, fn: FileName, d: Directory, f: WriteF) extends FileOpsA[String]
case class Read(fn: FileName, d: Directory, f: ReadF) extends FileOpsA[InputStream]
case class Delete(fn: FileName, d: Directory, f: DeleteF) extends FileOpsA[Unit]
case class List(d: Directory, f: ListF) extends FileOpsA[Seq[String]]

sealed trait Context
case class FileContext(d: Directory) extends Context
case class S3Context(dir: Directory, buck: String, region: String) extends Context

object FileOps {
  type FileOps[A] = Free[FileOpsA, A]
  type FileName = String
  type Directory = String
  type Checksum = String
  type WriteF = (InputStream, FileName, Directory) => Option[Checksum]
  type ReadF = (FileName, Directory) => Option[InputStream]
  type DeleteF = (FileName, Directory) => Option[Unit]
  type ListF = Directory => Seq[String]

  def write(is: InputStream, fn: FileName, d: Directory)(f: WriteF): FileOps[Checksum] =
    liftF[FileOpsA, String](Write(is, fn, d, f))

  def read(fn: FileName, d: Directory)(f: ReadF): FileOps[InputStream] =
    liftF[FileOpsA, InputStream](Read(fn, d, f))

  def delete(fn: FileName, d: Checksum)(f: DeleteF) =
    liftF[FileOpsA, Unit](Delete(fn, d, f))

  def list(d: Directory)(f: ListF) =
    liftF[FileOpsA, Seq[String]](List(d, f))
}



