package org.caoilte.bowling.scoresheet

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.caoilte.bowling._


@RunWith(classOf[JUnitRunner])
class NormalFramesBallScoreSheetTests extends FunSuite with ShouldMatchers {

  import PrintableFrames._;

  test("ball scores of one unstarted frame should print an empty sheet") {
    new NormalFramesBallScoreSheet(oneUnstartedFrame).ballScores() should equal(List(" | | |"))
  }

  test("ball scores of one started frame with a score of 1 should print 1 for ball one and nothing for ball two") {
    new NormalFramesBallScoreSheet(oneStartedFrameWithScore1).ballScores() should equal(List(" |1| |"))
  }

  test("ball scores of one open frame with a score of 1 and 2 should print 1 for ball one and 2 for ball 2 ") {
    new NormalFramesBallScoreSheet(oneOpenFrameWithScores1And2).ballScores() should equal(List(" |1|2|"))
  }

  test("ball scores of one spare with a first ball score of 5 should print 5 for ball one and / for ball 2 ") {
    new NormalFramesBallScoreSheet(oneSpareWithFirstBall5).ballScores() should equal(List(" |5|/|"))
  }

  test("ball scores of one strike should print X for ball one and nothing for ball 2") {
    new NormalFramesBallScoreSheet(oneStrike).ballScores() should equal(List(" |X| |"))
  }

  test("ball scores of one finished and one started frame should print three ball scores") {
    new NormalFramesBallScoreSheet(oneSpareWithFirstBall5AndOneStartedWithScore1).ballScores() should equal(List(" |5|/|", " |1| |"))
  }

  test("ball scores of two finished frames should print four ball scores") {
    new NormalFramesBallScoreSheet(oneStrikeAndOneOpenFrameWithScores5And2).ballScores() should equal(List(" |X| |", " |5|2|"))
  }

  test("ball scores of three finished frames should print six ball scores") {
    new NormalFramesBallScoreSheet(twoStrikesAndOneOpenFrameWithScores3And6) ballScores() should equal(List(" |X| |", " |X| |", " |3|6|"))
  }

}
