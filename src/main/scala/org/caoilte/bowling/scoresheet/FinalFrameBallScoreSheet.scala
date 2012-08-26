package org.caoilte.bowling.scoresheet

import org.caoilte.bowling.{Spare, Strike, Frame}


class FinalFrameBallScoreSheet(finalFrames: List[Frame]) extends BallScoreSheet {

  def ballScores(): String = {
    finalFrames match {
      case (s1: Strike) :: (s2: Strike) :: (f: Frame) :: otherFrames => " |X|X|%s|".format(stringForFirstBall(f))
      case (cf: Strike) :: (f: Frame) :: otherFrames => " |X|%s|%s|".format(stringForFirstBall(f), stringForSecondBall(f))
      case (sp: Spare) :: (f: Frame) :: otherFrames => " |%s|/|%s|".format(stringForFirstBall(sp), stringForFirstBall(f))
      case (f: Frame) :: otherFrames => " |%s|%s| |".format(stringForFirstBall(f), stringForSecondBall(f))
      case Nil => " | | | |"
      case frames => throw new IllegalStateException("Unexpected frames '" + frames.toString + "'")
    }
  }
}
