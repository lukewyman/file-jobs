package filejobs

import cats.~>

/**
  * Created by lukewyman on 9/8/16.
  */
object Compiler {

  def syncCompiler: FileOpsA ~> Option = new (FileOpsA ~> Option) {

    def apply[A](fa: FileOpsA[A]): Option[A] = fa match {
      case Write(is, fn, c, f) => f(is, fn, c)
      case Read(fn, c, f) => f(fn, c)
      case Delete(fn, c, f) => f(fn, c)
      case List(c, f) => f(c)
    }
  }

}
