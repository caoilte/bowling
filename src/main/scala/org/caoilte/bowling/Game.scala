package org.caoilte.bowling

import scoresheet.ScoreSheetPrinter

trait Game {
  def +(score: Int): Game;
  def isFinished(): Boolean;
  def isNewFrame(): Boolean;
  def addedOrder(): Int;
  def score(): Int;
  def maximumScore(): Int;
  def printScoreSheet(scoreSheetPrinter: ScoreSheetPrinter);
  def printNameAndScore(linePrinter: LinePrinter);
}

case class PlayerGame(player: TeamPlayer, gameFrames: GameFrames, gameLength: Int) extends Game {
  def this(player: TeamPlayer, gameLength: Int) = this(player, GameFrames(Nil, List(UnStartedFrame())), gameLength)

  def +(score: Int): PlayerGame = {
    PlayerGame(player, gameFrames+score, gameLength);
  }

  def isFinished(): Boolean = {
    gameFrames.completedFrames.size >= gameLength;
  }

  def isNewFrame(): Boolean = {
    gameFrames.isNewFrame;
  }

  def addedOrder() = player.player.addedOrder;

  def score() = gameFrames.score(gameLength);

  def maximumScore() = gameFrames.maximumScore;

  def printNameAndScore(linePrinter: LinePrinter) = linePrinter.print(player.fillNameWithExtraPadding(1) + " : " + score());

  override def toString() = player.toString();

  def printScoreSheet(scoreSheetPrinter: ScoreSheetPrinter) {
    val allFrames = gameFrames.allFrames;
    val numFinalFrames = allFrames.size - gameLength + 1;

    val normalFrames = allFrames.drop(numFinalFrames).reverse.padTo(gameLength - 1, UnStartedFrame());
    val finalFrames = allFrames.take(numFinalFrames).reverse;

    val paddedCompletedFrames = gameFrames.completedFrames.reverse.take(gameLength).padTo(gameLength, UnStartedFrame());

    scoreSheetPrinter.print(player, normalFrames, finalFrames, paddedCompletedFrames);
  }
}