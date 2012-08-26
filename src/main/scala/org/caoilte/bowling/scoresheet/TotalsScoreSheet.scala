package org.caoilte.bowling.scoresheet

import org.caoilte.bowling.Frame
import annotation.tailrec


class TotalsScoreSheet(paddedCompletedFrames: List[Frame]) {

  def totalScores(): String = {
    val totalScoresWithPaddingString = totalScoreStringList().padTo(paddedCompletedFrames.size, "     ")
    totalScoresWithPaddingString.mkString("|", "|", "  |");
  }

  private def totalScoreStringList(): List[String] = {
    val scores = paddedCompletedFrames.map(_.score());
    val totalScoreList = totalScoreIntList(scores);
    totalScoreList.map(s => "%1$-3s  ".format(s))
  }

  private def totalScoreIntList(totalScores: List[Int]): List[Int] = {
    totalScores.foldLeft(List[Int]()) {
      (b, a) => a match {
        case 0 => b;
        case _ => b match {
          case Nil => a :: b
          case _ => a + b.head :: b
        }
      }
    }.reverse;
  }

}

