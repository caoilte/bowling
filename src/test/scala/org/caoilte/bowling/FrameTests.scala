package org.caoilte.bowling

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class FrameTests extends FunSuite with ShouldMatchers {

  test("Can create open frame with score 9 from a 4 and a 5") {
    val f = OpenFrame(4, 5);
    f.score() should equal(9);
  }

  test("Cannot create an open frame with score 10") {
    evaluating {
      OpenFrame(5, 5);
    } should produce[IllegalArgumentException]
  }

  test("Can create a strike with score 29 from a 10, 10 and 9") {
    val f = StrikeWithFirstBonus(10) + 9
    f.score() should equal(29);
  }

  test("Cannot create a Strike with score 31") {
    evaluating {
      StrikeWithFirstBonus(10) + 11
    } should produce[IllegalArgumentException]
  }

  test("Can create a spare with score 19 from a 10 and 9") {
    val f = SpareWithNoBonus(5) + 9
    f.score() should equal(19);
  }

  test("Cannot create a Spare with score 21") {
    evaluating {
      SpareWithNoBonus(5) + 11;
    } should produce[IllegalArgumentException]
  }

  test("scoring ten on an unstarted frame should produce a strike") {
    val strike = UnStartedFrame() + 10
    strike should equal(StrikeWithNoBonus())
    strike.score should equal(10)
  }
  test("scoring 5 on an unstarted frame should produce a started frame with score 5") {
    val startedFrame = UnStartedFrame() + 5
    startedFrame should equal(StartedFrame(5))
    startedFrame.score should equal(5)
  }
  test("scoring 5 on an started frame with score 5 should produce a spare") {
    val spare = StartedFrame(5) + 5
    spare should equal(SpareWithNoBonus(5))
    spare.score should equal(10)
  }
}
