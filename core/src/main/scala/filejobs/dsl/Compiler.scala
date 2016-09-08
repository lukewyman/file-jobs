package filejobs.dsl

import cats.arrow.FunctionK
import cats.{Id, ~>}

/**
  * Created by lukewyman on 9/8/16.
  */
object Compiler {

  def syncCompiler: FileOpsA ~> Id = new (FileOpsA ~> Id) {

    def apply[A](fa: FileOpsA[A]): Id[A] = fa match {
      case Write(is, fn, d, be) => be.write(is, fn, d)
      case Read(fn, d, be) => be.read(fn, d)
      case Delete(fn, d, be) => be.delete(fn, d)
    }
  }

}
