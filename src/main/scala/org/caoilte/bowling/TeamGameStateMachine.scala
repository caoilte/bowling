package org.caoilte.bowling

import scoresheet.ScoreSheetPrinter
import collection.immutable.Queue



object TeamGameStateMachine {

  sealed trait GameState;

  case object UnStartable extends GameState;

  case object Startable extends GameState;

  case object Started extends GameState;

}

class TeamGameStateMachine {
  import TeamGameStateMachine._;

  def printPlayers(scoreSheetPrinter: ScoreSheetPrinter) = gameSetup.printPlayers(scoreSheetPrinter);


  var teamGame = TeamGame(Nil, Queue());
  var gameSetup = new TeamGameSetup();

  def scoresAvailable: IndexedSeq[String] = {
    (0 until teamGame.maximumScore + 1).map(_.toString)
  }

  def addPlayer(scoreSheetPrinter: ScoreSheetPrinter, name: String): GameState = {
    gameSetup = gameSetup.addPlayer(name);
    gameSetup.printPlayers(scoreSheetPrinter);
    Startable
  }

  def start(scoreSheetPrinter: ScoreSheetPrinter): GameState = {
    teamGame = gameSetup.start();

    teamGame.printGameStatus(scoreSheetPrinter)
    teamGame.printTotalScores(scoreSheetPrinter)

    Started
  }

  def score(scoreSheetPrinter: ScoreSheetPrinter, score: Int): GameState = {
    teamGame = teamGame.score(score);

    if (!teamGame.isFinished()) {
      teamGame.printGameStatus(scoreSheetPrinter)
      teamGame.printTotalScores(scoreSheetPrinter)

      Started
    } else {
      scoreSheetPrinter.print("Game Over!")
      scoreSheetPrinter.print("")
      teamGame.printGameStatus(scoreSheetPrinter)
      teamGame.printTotalScores(scoreSheetPrinter)

      gameSetup = new TeamGameSetup();
      UnStartable
    }
  }

}