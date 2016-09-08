package filejobs.examples

import filejobs.dsl.FileOps._
import filejobs.io.FileBackend

/**
  * Created by lukewyman on 9/8/16.
  */
object BasicReadWriteDelete extends App {

  val fbe = new FileBackend

  def job: FileOps[Unit] =
    for {
      is <- read("myfile1.txt", "myfiles", fbe)
      _  <- write(is, "yourfile1.txt", "yourfiles", fbe)
      _  <- delete("myfile1.txt", "myfiles", fbe)
    } yield ()

}
