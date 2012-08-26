package org.caoilte.bowling

case class GameFrames(completedFrames: List[FinishedFrame], uncompletedFrames: List[UnfinishedFrame]) {
  lazy val allFrames = (uncompletedFrames ::: completedFrames);

  def +(score: Int) = {

    val scoredFrames:List[Frame] = uncompletedFrames.map(_ + score)

    val newlyFinishedFrames:List[FinishedFrame] = scoredFrames collect {
      case f:FinishedFrame => f
    }
    val stillIncompleteFrames:List[UnfinishedFrame] = scoredFrames collect {
      case f:UnfinishedFrame => f
    }
    val newFinishedFrames = newlyFinishedFrames ::: completedFrames;

    stillIncompleteFrames match {
      case (s:StartedFrame) :: (fs:List[UnfinishedFrame]) => GameFrames(newFinishedFrames, stillIncompleteFrames)
      case _ => GameFrames(newFinishedFrames, UnStartedFrame() :: stillIncompleteFrames)
    }
  }

  def score(gameLength: Int) = {

    val allFrames = (uncompletedFrames ::: completedFrames)
    val prunedFrames = allFrames.drop(allFrames.length-gameLength)

    prunedFrames map(_.score) sum
  }

  def isNewFrame = {
    uncompletedFrames.head match {
      case u:UnStartedFrame => true
      case _ => false
    }
  }

  def maximumScore = {
    uncompletedFrames.head.maximumScore
  }
}
