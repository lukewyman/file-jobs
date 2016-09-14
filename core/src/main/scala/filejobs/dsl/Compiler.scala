package filejobs.dsl

import cats.arrow.FunctionK
import cats.{Id, ~>}

/**
  * Created by lukewyman on 9/8/16.
  */
object Compiler {

  def syncCompiler: FileOpsA ~> Id = new (FileOpsA ~> Id) {

    def apply[A](fa: FileOpsA[A]): Id[A] = fa match {
      case Write(is, fn, d, f) => f(is, fn, d)
      case Read(fn, d, f) => f(fn, d)
      case Delete(fn, d, f) => f(fn, d)
    }
  }

}
