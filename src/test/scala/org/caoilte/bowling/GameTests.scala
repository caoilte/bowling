package org.caoilte.bowling

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalamock.scalatest.MockFactory
import org.scalamock.ProxyMockFactory
import scoresheet.ScoreSheetPrinter

@RunWith(classOf[JUnitRunner])
class GameTests extends FunSuite with ShouldMatchers with MockFactory with ProxyMockFactory {

  val PLAYER = TeamPlayer(Player("c",0),1);
  val scoreSheetPrinter = mock[ScoreSheetPrinter];

  val UNSTARTED_FRAME_LIST = List(UnStartedFrame());

  test("Should print an unstarted game correctly") {
    val completedFrames = List();
    val uncompletedFrames = List(UnStartedFrame());

    val gameFrames = GameFrames(completedFrames, uncompletedFrames);

    val game = new PlayerGame(PLAYER, gameFrames, 2);


    scoreSheetPrinter expects 'print withArgs(PLAYER,
      List(UnStartedFrame()),Nil,
      List(UnStartedFrame(), UnStartedFrame()));

    game.printScoreSheet(scoreSheetPrinter);
  }

}
