package org.caoilte.bowling.scoresheet

import org.caoilte.bowling._


trait ScoreSheetPrinter extends LinePrinter {
  def print(teamPlayer: TeamPlayer, normalFrames: List[Frame], finalFrames: List[Frame], paddedCompletedFrames: List[Frame])
}

class ConsoleScoreSheetPrinter(gameLength: Int, linePrinter: LinePrinter) extends ScoreSheetPrinter {
  def print(player: TeamPlayer, normalFrames: List[Frame], finalFrames: List[Frame], paddedCompletedFrames: List[Frame]) {

    val normalFramesBallScoreSheet = new NormalFramesBallScoreSheet(normalFrames);
    val finalFrameBallScoreSheet = new FinalFrameBallScoreSheet(finalFrames);
    val totalsScoreSheet = new TotalsScoreSheet(paddedCompletedFrames);

    linePrinter.print(padding(player.padding, '='))
    linePrinter.print("|" + player.fillNameWithPadding() + normalFramesBallScoreSheet.ballScoresWithPadding() + finalFrameBallScoreSheet.ballScores())
    linePrinter.print(paddingString(player.padding, ' ') + totalsScoreSheet.totalScores())
    linePrinter.print(padding(player.padding, '='))

  }

  def paddingString(padding: Int, padChar: Char) = List.fill(padding)(padChar).mkString("|", "", "")

  def padding(padding: Int, padChar: Char): String = {
    paddingString(padding, padChar) + List.fill(gameLength - 1)("=====").mkString("|", "|", "|") + "=======|";
  }

  def print(line: String) { linePrinter.print(line) }
}



