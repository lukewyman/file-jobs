package filejobs.io

import java.io.InputStream
import filejobs.dsl.FileOps._

/**
  * Created by lukewyman on 9/15/16.
  */
object S3Backend {

  def write(is: InputStream, fn: FileName, d: Directory): Checksum = ???

  def delete(fn: FileName, d: Directory): Unit = ???

  def read(fn: FileName, d: Directory): InputStream = ???
}