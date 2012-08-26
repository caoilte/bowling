package org.caoilte.bowling.scoresheet

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.caoilte.bowling.{UnStartedFrame, StrikeWithSecondBonus, SpareWithBonus, OpenFrame}


@RunWith(classOf[JUnitRunner])
class TotalsScoreSheetTests extends FunSuite with ShouldMatchers {

  val oneOpenFrameWithScores1And2 = List(OpenFrame(1, 2))
  test("total scores of one open frame with a score of 1 and 2 should print 3") {
    new TotalsScoreSheet(oneOpenFrameWithScores1And2).totalScores() should
      equal("|3      |");
  }

  val oneSpareWithFirstBall5 = List(SpareWithBonus(5, 0))
  test("total scores of one spare with a first ball score of 5 should print 10") {
    new TotalsScoreSheet(oneSpareWithFirstBall5).totalScores() should
      equal("|10     |")
  }

  val oneStrike = List(StrikeWithSecondBonus(0))
  test("total scores of one strike should print 10") {
    new TotalsScoreSheet(oneStrike).totalScores() should
      equal("|10     |")
  }

  val twoFinished = List(StrikeWithSecondBonus(7), OpenFrame(5, 2))
  test("total scores of two finished should print accumulated total scores") {
    new TotalsScoreSheet(twoFinished).totalScores() should
      equal("|17   |24     |")
  }

  val threeFinished = List(StrikeWithSecondBonus(20), StrikeWithSecondBonus(9), OpenFrame(3, 6))
  test("total scores of three finished should print accumulated total scores") {
    new TotalsScoreSheet(threeFinished).totalScores() should
      equal("|30   |49   |58     |")
  }

  val oneFinishedOneUnStarted = List(OpenFrame(1, 2), UnStartedFrame())
  test("total scores of one finished and one unstarted frame should print longer blank space for unstarted frame") {
    new TotalsScoreSheet (oneFinishedOneUnStarted).totalScores() should
    equal("|3    |       |")
  }

  val oneFinishedTwoUnStarted = List(OpenFrame(1, 2), UnStartedFrame(), UnStartedFrame())
  test("total scores of one finished and two unstarted frames should print longer blank space for unstarted frame") {
    new TotalsScoreSheet (oneFinishedTwoUnStarted).totalScores() should
      equal("|3    |     |       |")
  }

}
