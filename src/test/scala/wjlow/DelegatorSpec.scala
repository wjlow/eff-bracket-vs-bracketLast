package wjlow

import java.util.concurrent.Executors

import org.atnos.eff._
import org.atnos.eff.syntax.all._
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class DelegatorSpec extends Specification {

  "Delegator" should {

    "#1. This test times out, Delegator has a for comprehension" in new Context {

      val numOfRuns = 1000
      val numOfThreads = 10
      val delegator = new Delegator(new LockHandler())

      implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(numOfThreads))

      val fs = Future.traverse(1 to numOfRuns)(_ => Future {
        val f1 = EffUtil.convertSafe(delegator.delegateWithFlatMap[TestEffects]("Bob")).runEither.run
        val f2 = EffUtil.convertSafe(delegator.delegateWithFlatMap[TestEffects]("Bob")).runEither.run
        (f1, f2)
      })
      val (results1, results2) = Await.result(fs, Duration("10s")).toList.unzip
      results1.length ==== numOfRuns
      results2.length ==== numOfRuns

    }

    "#2. This test does not time out, Delegator does not have a for comprehension" in new Context {

      val numOfRuns = 1000
      val numOfThreads = 10
      val delegator = new Delegator(new LockHandler())

      implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(numOfThreads))

      val fs = Future.traverse(1 to numOfRuns)(_ => Future {
        val f1 = EffUtil.convertSafe(delegator.delegate[TestEffects]("Bob")).runEither.run // this line is different than in the previous test
        val f2 = EffUtil.convertSafe(delegator.delegate[TestEffects]("Bob")).runEither.run // this line is different than in the previous test
        (f1, f2)
      })
      val (results1, results2) = Await.result(fs, Duration("10s")).toList.unzip
      results1.length ==== numOfRuns
      results2.length ==== numOfRuns

    }

  }

}

trait Context extends Scope {

  type TestEffects = Fx.fx2[Safe, Either[AppError, ?]]

}