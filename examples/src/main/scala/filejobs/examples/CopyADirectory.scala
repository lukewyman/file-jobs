package filejobs.examples

import java.io.InputStream

import filejobs.{FileOps, FileContext}
import FileOps._
import filejobs.io.StubBackend

/**
  * Created by lukewyman on 9/19/16.
  */
object CopyADirectory extends App {

  def job: FileOps[Seq[FileOps[InputStream]]] = ???
}
