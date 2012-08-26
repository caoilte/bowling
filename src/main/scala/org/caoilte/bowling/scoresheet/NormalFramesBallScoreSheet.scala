package org.caoilte.bowling.scoresheet

import org.caoilte.bowling.{UnStartedFrame, GameFrames, Frame}



class NormalFramesBallScoreSheet(frames: List[Frame]) extends BallScoreSheet {

  def ballScores(): List[String] = {
    frames.map(f => " |%s|%s|".format(stringForFirstBall(f), stringForSecondBall(f)))
  }

  def ballScoresWithPadding(): String = {
    val ballScoreList = ballScores();
    ballScoreList.mkString("|", "", "");
  }
}


