package filejobs.examples

import java.io.InputStream

import filejobs.dsl.FileOps._
import filejobs.dsl.FileContext
import filejobs.io.StubBackend

/**
  * Created by lukewyman on 9/19/16.
  */
object CopyADirectory extends App {

  def job: FileOps[Seq[FileOps[InputStream]]] = for {
    fns <- list(FileContext("myfiles"))(StubBackend.list)
    is <- fns.tra
  } yield is

}
