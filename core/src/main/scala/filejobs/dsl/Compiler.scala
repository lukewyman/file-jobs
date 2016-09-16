package filejobs.dsl

import cats.arrow.FunctionK
import cats.~>

/**
  * Created by lukewyman on 9/8/16.
  */
object Compiler {

  def syncCompiler: FileOpsA ~> Option = new (FileOpsA ~> Option) {

    def apply[A](fa: FileOpsA[A]): Option[A] = fa match {
      case Write(is, fn, d, f) => f(is, fn, d)
      case Read(fn, d, f) => f(fn, d)
      case Delete(fn, d, f) => f(fn, d)
    }
  }

}
