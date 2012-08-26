package org.caoilte.bowling

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers


@RunWith(classOf[JUnitRunner])
class GameFramesTests extends FunSuite with ShouldMatchers {

  def score(game: GameFrames) = game.score(10);

  test("the first score should not complete scoring if the frame is uncompleted") {
    val newgame = GameFrames(Nil, List(UnStartedFrame())) + 5;
    newgame should equal (GameFrames(Nil, List(StartedFrame(5))));
  }
  test("the first score should be added to the total score") {
    val newgame = GameFrames(Nil, List(UnStartedFrame())) + 5;
    score(newgame) should equal (5);
  }
  test("scoring an open frame should close that frame to further scoring") {
    val newgame = GameFrames(Nil, List(StartedFrame(3))) + 2;
    newgame should equal (GameFrames(List(OpenFrame(3,2)), List(UnStartedFrame())));
  }
  test("scoring a spare should leave that frame open to further scoring") {
    val newgame = GameFrames(Nil, List(StartedFrame(3))) + 7;
    newgame should equal (GameFrames(Nil, List(UnStartedFrame(), SpareWithNoBonus(3))));
  }
  test("scoring a strike should leave that frame open to further scoring") {
    val newgame = GameFrames(Nil, List(UnStartedFrame())) + 10;
    newgame should equal (GameFrames(Nil, List(UnStartedFrame(), StrikeWithNoBonus())));
  }
  test("scoring an open frame should add that as a score") {
    val newgame = GameFrames(Nil, List(StartedFrame(3))) + 3;
    score(newgame) should equal (6);
  }
  test("the first score after a spare should complete the scoring on that spare") {
    val newgame = GameFrames(Nil, List(UnStartedFrame(), SpareWithNoBonus(5))) + 5;
    newgame should equal (GameFrames(List(SpareWithBonus(5, 5)), List(StartedFrame(5))));
  }
  test("the first score after a spare should be added twice to the total score") {
    val newgame = GameFrames(Nil, List(UnStartedFrame(), SpareWithNoBonus(5))) + 5;
    score(newgame) should equal(20)
  }
  test("the first score after a strike should not complete the scoring on that strike") {
    val newgame = GameFrames(Nil, List(UnStartedFrame(), StrikeWithNoBonus())) + 6;
    newgame should equal (GameFrames(Nil, List(StartedFrame(6), StrikeWithFirstBonus(6))));
  }
  test("the first score after a strike should be added twice to the total score") {
    val newgame = GameFrames(Nil, List(UnStartedFrame(), StrikeWithNoBonus())) + 6;
    score(newgame) should equal(22)
  }
  test("the second score after a strike should complete the scoring on that strike") {
    val newgame = GameFrames(Nil, List(StartedFrame(6), StrikeWithFirstBonus(6))) + 3;
    newgame should equal (GameFrames(List(OpenFrame(6,3), StrikeWithSecondBonus(9)), List(UnStartedFrame())));
  }
  test("the second score after a strike should be added twice to the total score") {
    val newgame = GameFrames(Nil, List(StartedFrame(6), StrikeWithFirstBonus(6))) + 3;
    score(newgame) should equal(28)
  }
  test("a strike followed by a spare should leave only the spare open for further scoring") {
    val newgame = GameFrames(Nil, List(StartedFrame(6), StrikeWithFirstBonus(6))) + 4;
    newgame should equal (GameFrames(List(StrikeWithSecondBonus(10)), List(UnStartedFrame(), SpareWithNoBonus(6))));
  }
  test("a strike followed by a strike should leave both open for further scoring") {
    val newgame = GameFrames(Nil, List(UnStartedFrame(), StrikeWithNoBonus())) + 10;
    newgame should equal (GameFrames(Nil, List(UnStartedFrame(), StrikeWithNoBonus(), StrikeWithFirstBonus(10))));
  }
  test("the first score after two strikes should close the first strike for further scoring") {
    val newgame = GameFrames(Nil, List(UnStartedFrame(), StrikeWithNoBonus(), StrikeWithFirstBonus(10))) + 4;
    newgame should equal (GameFrames(List(StrikeWithSecondBonus(14)), List(StartedFrame(4), StrikeWithFirstBonus(4))));
  }
  test("the first score after two strikes should be added three times to the total score") {
    val newgame = GameFrames(Nil, List(UnStartedFrame(), StrikeWithNoBonus(), StrikeWithFirstBonus(10))) + 4;
    score(newgame) should equal(42)
  }
  test("three strikes in a row should close only the first strike for further scoring") {
    val newgame = GameFrames(Nil, List(UnStartedFrame(), StrikeWithNoBonus(), StrikeWithFirstBonus(10))) + 10;
    newgame should equal (GameFrames(List(StrikeWithSecondBonus(20)), List(UnStartedFrame(), StrikeWithNoBonus(), StrikeWithFirstBonus(10))));
  }

}
