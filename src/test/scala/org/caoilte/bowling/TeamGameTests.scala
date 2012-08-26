package org.caoilte.bowling

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalamock.scalatest.MockFactory
import collection.immutable.Queue
import org.scalamock.{GeneratedMockFactoryBase, ProxyMockFactory}
import scoresheet.ScoreSheetPrinter


@RunWith(classOf[JUnitRunner])
class TeamGameTests extends FunSuite with ShouldMatchers with MockFactory with ProxyMockFactory {

  val finished = List();
  val mockFirstGame = mock[Game];
  val mockSecondGame = mock[Game];
  val mockNewGame = mock[Game];
  val onePlayerPlaying = Queue(mockFirstGame);
  val twoPlayersPlaying = Queue(mockFirstGame, mockSecondGame);
  
  val A_SCORE = 5;

  test("Not completing a frame in a one player game should return the game for playing again") {
    val teamGame = new TeamGame(finished, onePlayerPlaying);

    mockFirstGame expects '$plus withArgs(A_SCORE) returning mockNewGame;
    mockNewGame expects 'isFinished returning false;
    mockNewGame expects 'isNewFrame returning false;

    val newTeamGame = teamGame.score(A_SCORE);

    newTeamGame.playing should equal(Queue(mockNewGame));
  }

  test("Completing a frame in a one player game should return the game for playing again") {
    val teamGame = new TeamGame(finished, onePlayerPlaying);

    mockFirstGame expects '$plus withArgs(A_SCORE) returning mockNewGame;
    mockNewGame expects 'isFinished returning false;
    mockNewGame expects 'isNewFrame returning true;

    val newTeamGame = teamGame.score(A_SCORE);

    newTeamGame.playing should equal(Queue(mockNewGame));
  }

  test("Completing a game in a one player game should mark the game as finished and not have a game for playing") {
    val teamGame = new TeamGame(finished, onePlayerPlaying);

    mockFirstGame expects '$plus withArgs(A_SCORE) returning mockNewGame;
    mockNewGame expects 'isFinished returning true;

    val newTeamGame = teamGame.score(A_SCORE);

    newTeamGame.playing should equal(Nil);
    newTeamGame.finished should equal(List(mockNewGame));
  }

  test("Not Completing a frame in a two player game should return the game for playing again") {
    val teamGame = new TeamGame(finished, twoPlayersPlaying);

    mockFirstGame expects '$plus withArgs(A_SCORE) returning mockNewGame;
    mockNewGame expects 'isFinished returning false;
    mockNewGame expects 'isNewFrame returning false;

    val newTeamGame = teamGame.score(A_SCORE);

    newTeamGame.playing should equal(Queue(mockNewGame, mockSecondGame));
  }

  test("Completing a frame in a two player game should return the second players game for playing next") {
    val teamGame = new TeamGame(finished, twoPlayersPlaying);

    mockFirstGame expects '$plus withArgs(A_SCORE) returning mockNewGame;
    mockNewGame expects 'isFinished returning false;
    mockNewGame expects 'isNewFrame returning true;

    val newTeamGame = teamGame.score(A_SCORE);

    newTeamGame.playing should equal(Queue(mockSecondGame, mockNewGame));
  }

  test("Completing a game in a two player game should mark the game as finished and return the second players game for playing next") {
    val teamGame = new TeamGame(finished, twoPlayersPlaying);

    mockFirstGame expects '$plus withArgs(A_SCORE) returning mockNewGame;
    mockNewGame expects 'isFinished returning true;

    val newTeamGame = teamGame.score(A_SCORE);

    newTeamGame.playing should equal(Queue(mockSecondGame));
    newTeamGame.finished should equal(List(mockNewGame));
  }

  test("Printing Game Status should print status once for each player and print that the first player should bowl next") {
    val teamGame = new TeamGame(finished, twoPlayersPlaying);

    mockFirstGame expects 'addedOrder returning 0;
    mockSecondGame expects 'addedOrder returning 1;

    val scoreSheetPrinter = mock[ScoreSheetPrinter];

    mockFirstGame expects 'printScoreSheet withArgs(scoreSheetPrinter);
    mockSecondGame expects 'printScoreSheet withArgs(scoreSheetPrinter);

    scoreSheetPrinter expects 'print withArgs("") twice();
    scoreSheetPrinter expects 'print withArgs("To Bowl : " + mockFirstGame);

    teamGame.printGameStatus(scoreSheetPrinter);

  }


}
