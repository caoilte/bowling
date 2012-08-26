package org.caoilte.bowling.scoresheet

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.caoilte.bowling._


@RunWith(classOf[JUnitRunner])
class FinalFrameBallScoreSheetTests extends FunSuite with ShouldMatchers {

  import PrintableFrames._;

  test("final frame score of no frames should print an empty sheet") {
    new FinalFrameBallScoreSheet(Nil).ballScores() should equal(" | | | |")
  }

  test("final frame score of one unstarted frame should print an empty sheet") {
    new FinalFrameBallScoreSheet(oneUnstartedFrame).ballScores() should equal(" | | | |")
  }

  test("final frame score of one started frame with a score of 1 should just print 1 for ball one") {
    new FinalFrameBallScoreSheet(oneStartedFrameWithScore1).ballScores() should equal(" |1| | |")
  }

  test("final frame score of one open frame with a score of 1 and 2 should print 1 for ball one and 2 for ball 2 ") {
    new FinalFrameBallScoreSheet(oneOpenFrameWithScores1And2).ballScores() should equal(" |1|2| |")
  }

  test("final frame score of one spare with a first ball score of 5 should print 5 for ball one and / for ball 2 ") {
    new FinalFrameBallScoreSheet(oneSpareWithFirstBall5).ballScores() should equal(" |5|/| |")
  }

  test("final frame score of one strike should just print X for ball one") {
    new FinalFrameBallScoreSheet(oneStrike).ballScores() should equal(" |X| | |")
  }

  test("final frame score of one spare and one started frame should print three ball scores") {
    new FinalFrameBallScoreSheet(oneSpareWithFirstBall5AndOneStartedWithScore1).ballScores() should equal(" |5|/|1|")
  }

  test("final frame score of one strike and one finished frame should print three ball scores") {
    new FinalFrameBallScoreSheet(oneStrikeAndOneOpenFrameWithScores5And2).ballScores() should equal(" |X|5|2|")
  }

  test("final frame score of two strikes and one started frame should print three ball scores") {
    new FinalFrameBallScoreSheet(twoStrikesAndOneStartedFrameWithScore3) ballScores() should equal(" |X|X|3|")
  }

  test("final frame score of three strikes should print three ball scores") {
    new FinalFrameBallScoreSheet(threeStrikes) ballScores() should equal(" |X|X|X|")
  }

}
