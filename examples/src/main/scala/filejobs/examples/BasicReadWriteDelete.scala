package filejobs.examples

import filejobs.dsl.FileOps._
import filejobs.io.{StubBackend}

/**
  * Created by lukewyman on 9/8/16.
  */
object BasicReadWriteDelete extends App {

  import filejobs.dsl.Compiler._

  val fbe = new StubBackend

  job.foldMap(syncCompiler)

  def job =
    for {
      is <- read("myfile1.txt", "myfiles", fbe)
      _  <- write(is, "yourfile1.txt", "yourfiles", fbe)
      _  <- delete("myfile1.txt", "myfiles", fbe)
    } yield ()

}
