package wjlow

import org.atnos.eff.{Eff, Member, Safe}
import org.atnos.eff.all.left
import org.atnos.eff.syntax.all._

object EffUtil {


  // Interprets a Safe + Eval effect and transforms any resultant Throwable into an AppError
  // Sensitive to the order of the member implicits
  // see http://atnos-org.github.io/eff/org.atnos.site.TransformStack.html
  def convertSafe[R, A, V](safe: Eff[R, A])(implicit
                                               s: Member.Aux[Safe, R, V],
                                               err: Member[Either[AppError, ?], V]): Eff[V, A] = {

    safe.execSafe.flatMap {
      case Right(payload) => payload.pureEff
      case Left(t) => left(AppError.internalError(t.toString))
    }
  }

}