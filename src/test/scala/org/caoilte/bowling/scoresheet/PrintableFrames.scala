package org.caoilte.bowling.scoresheet

import org.caoilte.bowling._


object PrintableFrames {
  val oneUnstartedFrame = List(UnStartedFrame())

  val oneStartedFrameWithScore1 = List(StartedFrame(1))

  val oneOpenFrameWithScores1And2 = List(OpenFrame(1, 2))

  val oneSpareWithFirstBall5 = List(SpareWithNoBonus(5))

  val oneStrike = List(StrikeWithNoBonus())

  val oneSpareWithFirstBall5AndOneStartedWithScore1 = List(SpareWithBonus(5, 1), StartedFrame(1))

  val oneStrikeAndOneOpenFrameWithScores5And2 = List(StrikeWithSecondBonus(7), OpenFrame(5, 2))

  val twoStrikesAndOneStartedFrameWithScore3 = List(StrikeWithSecondBonus(13), StrikeWithFirstBonus(3), StartedFrame(3))

  val twoStrikesAndOneOpenFrameWithScores3And6 = List(StrikeWithSecondBonus(13), StrikeWithSecondBonus(9), OpenFrame(3,6))

  val threeStrikes = List(StrikeWithSecondBonus(20), StrikeWithFirstBonus(10), StrikeWithNoBonus())
}
