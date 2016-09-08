package filejobs.io

import java.io.InputStream

/**
  * Created by lukewyman on 9/8/16.
  */
trait Backend {

  def write(is: InputStream, fn: String, d: String): String

  def read(fn: String, d: String): InputStream

  def delete(fn: String, d: String): Unit

}

class FileBackend extends Backend {
  def write(is: InputStream, fn: String, d: String): String = ???

  def delete(fn: String, d: String): Unit = ???

  def read(fn: String, d: String): InputStream = ???
}
