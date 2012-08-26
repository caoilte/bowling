package org.caoilte.bowling

trait Frame {
  def score(): Int
}

trait UnfinishedFrame extends Frame {
  def +(score: Int): Frame
  def maximumScore: Int
}

trait OneBallFrame extends Frame {
  def ball1Score: Int
}

trait TwoBallFrame extends OneBallFrame {
  def ball2Score: Int
}

trait FinishedFrame extends Frame

trait ClosedFrame extends Frame {
  def bonusScore(): Int

  def score = 10 + bonusScore
}

case class UnStartedFrame() extends UnfinishedFrame {
  def score() = 0;

  def maximumScore() = 10;

  def +(score: Int) = {
    if (score > 10) throw new IllegalArgumentException("You cannot score higher than 10");

    score match {
      case 10 => StrikeWithNoBonus()
      case _ => StartedFrame(score)
    }
  }
}

case class StartedFrame(ball1Score: Int) extends OneBallFrame with UnfinishedFrame {

  def score() = ball1Score;

  def maximumScore() = 10 - ball1Score;

  def +(ball2Score: Int) = {
    val finalScore = (ball1Score + ball2Score)
    if (finalScore > 10) throw new IllegalArgumentException("You cannot score higher than " + (10-ball1Score));

    finalScore match {
      case 10 => SpareWithNoBonus(ball1Score)
      case _ => OpenFrame(ball1Score, ball2Score)
    }
  }
}

case class OpenFrame(ball1Score: Int, ball2Score: Int) extends TwoBallFrame with FinishedFrame {
  if (ball1Score + ball2Score > 9) {
    throw new IllegalArgumentException("A normal frame cannot score higher than 9")
  }

  def score() = ball1Score + ball2Score
}

trait Strike extends OneBallFrame with ClosedFrame {
  def ball1Score = 10
  def maximumScore = 10
}
case class StrikeWithNoBonus() extends Strike with ClosedFrame with UnfinishedFrame {
  def +(score: Int) = {
    StrikeWithFirstBonus(bonusScore+score)
  }
  def bonusScore() = 0
}
case class StrikeWithFirstBonus(bonusScore: Int) extends Strike with UnfinishedFrame {
  if (bonusScore > 10) {
    throw new IllegalArgumentException("The maximum bonus score for a strike is 10")
  }

  def +(newScore: Int) = {
    StrikeWithSecondBonus(bonusScore+newScore)
  }
}
case class StrikeWithSecondBonus(bonusScore: Int) extends Strike with ClosedFrame with FinishedFrame {
  if (bonusScore > 20) {
    throw new IllegalArgumentException("The maximum bonus score for a strike is 20")
  }
}

trait Spare extends TwoBallFrame with ClosedFrame {

  def ball2Score = 10

}
case class SpareWithNoBonus(ball1Score: Int) extends Spare with UnfinishedFrame {
  if (ball1Score > 9) {
    throw new IllegalArgumentException("The maximum ball1 score for a spare is 9")
  }
  def +(newScore: Int) = {
    SpareWithBonus(ball1Score, newScore)
  }

  def maximumScore = 10;
  def bonusScore = 0
}


case class SpareWithBonus(ball1Score: Int, bonusScore: Int) extends Spare with FinishedFrame {
  if (ball1Score > 9) {
    throw new IllegalArgumentException("The maximum ball1 score for a spare is 9")
  }
  if (bonusScore > 10) {
    throw new IllegalArgumentException("The maximum bonus score for a spare is 10")
  }
}
