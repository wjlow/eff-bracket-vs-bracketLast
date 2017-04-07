package wjlow

import java.util.concurrent.locks.ReentrantLock

import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._

class LockHandler {

  val lock = new ReentrantLock()

  def run[R: _Safe : _err](str: String): Eff[R, Int] = {

    val acquire = protect[R, Unit](lock.lock())

    val use: (Unit) => Eff[R, Int] = _ => for {
      n <- 0.pureEff
      x <- if (n > 0) right(n)
                         else {
                           val error = AppError.internalError(str)
                           left(error)
                         }
    } yield x

    val release = (_: Unit) => protect[R, Unit](lock.unlock())

    bracket(acquire)(use)(release) // changing this to use bracketLast passes BOTH tests!
  }
}
