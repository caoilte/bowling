package org.caoilte.bowling

import collection.immutable.Queue
import scoresheet.ScoreSheetPrinter


case class TeamPlayer(player: Player, padding: Int) {

  def fillNameWithPadding() = {
    fillNameWithExtraPadding(0)
  }

  def fillNameWithExtraPadding(extraPadding: Int) = {
    player.name + List.fill(padding+extraPadding-player.name.size)(" ").mkString
  }

  override def toString() = player.toString();

}

case class TeamGame(finished: List[Game], playing: Queue[Game]) {
  def maximumScore() = playing.head.maximumScore();


  def score(score: Int): TeamGame = {
    val currentPlayerGame = playing.head

    val newPlayerGame = currentPlayerGame + score;

    if (newPlayerGame.isFinished()) {
      TeamGame(newPlayerGame :: finished, playing.tail)
    } else if (newPlayerGame.isNewFrame()) {
      TeamGame(finished, playing.tail.enqueue(newPlayerGame))
    } else {
      TeamGame(finished, newPlayerGame +: playing.tail)
    }
  }

  def printGameStatus(scoreSheetPrinter: ScoreSheetPrinter) {
    scoreSheetPrinter.print("");
    val gamesByName = (finished ::: playing.toList).sortBy(_.addedOrder);

    gamesByName.foreach ( pg => {
      pg.printScoreSheet(scoreSheetPrinter);
    });

    scoreSheetPrinter.print("");

    if (!playing.isEmpty) {
      scoreSheetPrinter.print("To Bowl : " + playing.head)
    }
  }

  lazy val gamesByScore = (finished ::: playing.toList).sortBy(_.score()).reverse

  def printTotalScores(linePrinter: LinePrinter) {
    linePrinter.print("");
    linePrinter.print("TOTAL SCORES")

    gamesByScore.foreach ( pg => {
      pg.printNameAndScore(linePrinter);
    });

    linePrinter.print("");

    (currentWinnerNames, isFinished()) match {
      case (List(oneWinner), false) => linePrinter.print(oneWinner + " is winning!");
      case (List(oneWinner, secondWinner), false) => linePrinter.print(oneWinner + " and " + secondWinner + " are winning!");
      case (threeOrMoreWinners, false) => linePrinter.print(threeOrMoreWinners.mkString(" and ") + " are all winning!");
      case (List(oneWinner), true) => linePrinter.print(oneWinner + " has won!");
      case (List(oneWinner, secondWinner), true) => linePrinter.print(oneWinner + "and " + secondWinner + " have both won!");
      case (threeOrMoreWinners, true) => linePrinter.print(threeOrMoreWinners.mkString(" and ") + " have all won!");
    }
    linePrinter.print("");
    linePrinter.print("Total Score : " + gamesByScore.map(_.score()).sum)
  }

  def currentWinnerNames: List[String] = currentWinnerGames().map(_.toString)

  def currentWinnerGames(): List[Game] = {
    gamesByScore.filter(_.score() == gamesByScore.head.score())
  }

  def isFinished() = {
    playing.isEmpty
  }


}
