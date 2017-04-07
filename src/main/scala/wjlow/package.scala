import org.atnos.eff._

package object wjlow {

  sealed trait AppError {
    def message: String
  }
  case class InternalError(message: String) extends AppError

  object AppError {
    def internalError(message: String): AppError = InternalError(message)
  }

  type _err[R] = MemberIn[Either[AppError, ?], R]

}
