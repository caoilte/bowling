package org.caoilte.bowling.scoresheet

import org.caoilte.bowling._


class BallScoreSheet {


  def stringForFirstBall(frame: Frame): String = {
    frame match {
      case st: Strike => "X";
      case obf: OneBallFrame => obf.ball1Score.toString
      case _ => " "
    }
  }

  def stringForSecondBall(frame: Frame): String = {
    frame match {
      case sp: Spare => "/"
      case t: TwoBallFrame => t.ball2Score.toString()
      case _ => " "
    }
  }

}
