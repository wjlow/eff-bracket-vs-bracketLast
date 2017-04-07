package wjlow

import org.atnos.eff.Eff
import org.atnos.eff.all._

class Delegator(f: LockHandler) {

  def delegate[R : _Safe : _err](str: String): Eff[R, Int] = f.run(str)
  def delegateWithFlatMap[R : _Safe : _err](str: String): Eff[R, Int] = for {
    x <- f.run(str)
  } yield x

}
