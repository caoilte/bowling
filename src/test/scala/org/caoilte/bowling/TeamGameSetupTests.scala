package org.caoilte.bowling

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import collection.immutable.Queue
import org.scalamock.scalatest.MockFactory
import org.scalamock.ProxyMockFactory


@RunWith(classOf[JUnitRunner])
class TeamGameSetupTests extends FunSuite with ShouldMatchers with MockFactory with ProxyMockFactory {

  val A_NAME = "caoilte"
  val ANOTHER_NAME = "donny"

  val A_PLAYER = Player(A_NAME, 0);
  val ANOTHER_PLAYER = Player(ANOTHER_NAME, 1);

  val A_SINGLE_PLAYER_GAME_SETUP = TeamGameSetup(Map((A_NAME, A_PLAYER)));
  val A_TWO_PLAYER_GAME_SETUP = TeamGameSetup(Map(
    (A_NAME, A_PLAYER),
    (ANOTHER_NAME, ANOTHER_PLAYER)));

  test("Can add player to empty game") {
    val emptyGameSetup = new TeamGameSetup();
    emptyGameSetup.addPlayer(A_NAME) should equal (A_SINGLE_PLAYER_GAME_SETUP)
  }

  test("Can add player to game with player") {
    val gameSetup = A_SINGLE_PLAYER_GAME_SETUP
    gameSetup.addPlayer(ANOTHER_NAME) should equal (A_TWO_PLAYER_GAME_SETUP)
  }

  test("Cannot add player to game if already in that game") {
    evaluating {
      val gameSetup = A_SINGLE_PLAYER_GAME_SETUP
      gameSetup.addPlayer(A_NAME)
    } should produce[IllegalArgumentException]
  }

  test("Printing Players should print all of the added players") {
    val setup = A_TWO_PLAYER_GAME_SETUP;

    val mockLinePrinter = mock[LinePrinter];
    mockLinePrinter expects 'print withArgs ("");
    mockLinePrinter expects 'print withArgs ("PLAYING");
    mockLinePrinter expects 'print withArgs ("caoilte");
    mockLinePrinter expects 'print withArgs ("donny");

    setup.printPlayers(mockLinePrinter);

  }

  test("Starting a game should initialise it with the longest players name as padding") {
    val setup = A_TWO_PLAYER_GAME_SETUP;
    setup.start() should equal (TeamGame(Nil, Queue(
      PlayerGame(TeamPlayer(A_PLAYER, 8), GameFrames(Nil, List(UnStartedFrame())), 10),
      PlayerGame(TeamPlayer(ANOTHER_PLAYER, 8), GameFrames(Nil, List(UnStartedFrame())), 10))));

  }

}
