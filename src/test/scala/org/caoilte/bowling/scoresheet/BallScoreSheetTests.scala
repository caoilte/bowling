package org.caoilte.bowling.scoresheet

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.caoilte.bowling._
import org.caoilte.bowling.SpareWithBonus
import org.caoilte.bowling.StrikeWithSecondBonus
import org.caoilte.bowling.UnStartedFrame
import org.caoilte.bowling.OpenFrame


@RunWith(classOf[JUnitRunner])
class BallScoreSheetTests extends FunSuite with ShouldMatchers {

  val ballScoreSheet = new BallScoreSheet;

  test("stringForFirstBall if a frame that scored two for first ball should be '2'") {
    ballScoreSheet.stringForFirstBall(OpenFrame(2,3)) should equal ("2")
  }

  test("stringForFirstBall if strike should be 'X'") {
    ballScoreSheet.stringForFirstBall(StrikeWithSecondBonus(4)) should equal ("X")
  }

  test("stringForFirstBall if unstarted frame should be ' '") {
    ballScoreSheet.stringForFirstBall(UnStartedFrame()) should equal (" ")
  }


  test("stringForSecondBall if a frame that scored three for second ball should be '3'") {
    ballScoreSheet.stringForSecondBall(OpenFrame(2,3)) should equal ("3")
  }

  test("stringForSecondBall if spare should be '/'") {
    ballScoreSheet.stringForSecondBall(SpareWithBonus(4, 5)) should equal ("/")
  }

  test("stringForSecondBall if unstarted frame should be ' '") {
    ballScoreSheet.stringForSecondBall(UnStartedFrame()) should equal (" ")
  }

  val aPlayer = Player("name", 0);
  val samplePlayerPadding = 6
  val normalFramesScoreSheet = new NormalFramesBallScoreSheet(Nil);
  val finalFrameScoreSheet = new FinalFrameBallScoreSheet(Nil);
  val totalsScoreSheet = new TotalsScoreSheet(Nil);
  val gameLength = 3;


//  test("scoresheet padding should pad the player name length and game length correctly") {
//    val scoreSheet = new ScoreSheetPrinter(aPlayer, samplePlayerPadding, normalFramesScoreSheet, finalFrameScoreSheet, totalsScoreSheet, 3);
//    scoreSheet.padding('=') should equal("|======|=====|=====|=======|");
//  }

}
