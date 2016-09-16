package filejobs.examples

import filejobs.dsl.Compiler._
import filejobs.dsl.FileOps._
import filejobs.io.StubBackend
import cats.implicits._

/**
  * Created by lukewyman on 9/8/16.
  */
object BasicReadWriteDelete extends App {

  job.foldMap(syncCompiler)

  def job =
    for {
      is <- read("myfile1.txt", "myfiles")(StubBackend.read)
      c  <- write(is, "yourfile1.txt", "yourfiles")(StubBackend.write)
      _  <- delete("myfile1.txt", "myfiles")(StubBackend.delete)
    } yield c

}
