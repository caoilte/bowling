package org.caoilte.bowling

import sbt._
import complete.Parser.Failure
import complete.{ValidParser, Parser}
import java.io.{File, PrintWriter}
import complete.DefaultParsers._
import collection.immutable.Queue
import scoresheet.{ConsoleScoreSheetPrinter, ScoreSheetPrinter}
import org.caoilte.bowling.TeamGameStateMachine.{Startable, UnStartable, Started, GameState}

final class Main extends xsbti.AppMain {
  /**Defines the entry point for the application.
   * The call to runLogged starts command processing. */
  def run(configuration: xsbti.AppConfiguration): xsbti.MainResult =
    MainLoop.runLogged(mapGameState(UnStartable, configuration))

  def mapGameState(gameState: GameState, configuration: xsbti.AppConfiguration): State = {
    gameState match {
      case UnStartable => {
        val commandDefinitions = welcomeCommand +: addPlayerCommand +: showPlayersCommand +:
          BasicCommands.allBasicCommands

        val commandsToRun = welcomeCommandName +: "iflast shell" +: configuration.arguments.map(_.trim)
        State(configuration, commandDefinitions, Set.empty, None, commandsToRun, State.newHistory,
          AttributeMap.empty, initialGlobalLogging, State.Continue)
      }
      case Startable => {
        val commandDefinitions = welcomeCommand +: addPlayerCommand +: showPlayersCommand +:
          startCommand +: BasicCommands.allBasicCommands

        val commandsToRun = "iflast shell" +: configuration.arguments.map(_.trim)
        State(configuration, commandDefinitions, Set.empty, None, commandsToRun, State.newHistory,
          AttributeMap.empty, initialGlobalLogging, State.Continue)
      }
      case Started => {

        val commandDefinitions = scoreCommand +: BasicCommands.allBasicCommands

        val commandsToRun = "iflast shell" +: configuration.arguments.map(_.trim)
        State(configuration, commandDefinitions, Set.empty, None, commandsToRun, State.newHistory,
          AttributeMap.empty, initialGlobalLogging, State.Continue)
      }
    }
  }

  val teamGameStateMachine = new TeamGameStateMachine;

  val welcomeCommandName = "welcome"
  val welcomeCommand = Command.command(welcomeCommandName) {
    s =>
      s.log.info("")
      s.log.info("Welcome to a new Game of Ten Pin Bowling!")
      s
  }

  def addPlayerAction(state: State, name: String): State = {
    mapGameState(teamGameStateMachine.addPlayer(state, name), state.configuration)
  }
  val addPlayerCommand = Command.single("addPlayer")(addPlayerAction);


  val showPlayersCommand = Command.command("showPlayers") {
    state =>
      teamGameStateMachine.printPlayers(state);
      state;
  }


  val startCommand = Command.command("start") {
    state =>
      mapGameState(teamGameStateMachine.start(state), state.configuration)
  }


  val digits: Parser[Seq[Char]] = charClass(_.isDigit, "digit").+
  val num: Parser[Int] = digits map {
    (chars: Seq[Char]) => chars.mkString.toInt
  }
  def pins = Space ~> num.examples(teamGameStateMachine.scoresAvailable toSet)


  def scoreCommand = Command("score")(_ => pins) {
    (state, score) =>
      mapGameState(teamGameStateMachine.score(state, score), state.configuration);
  }

  /**Configures logging to log to a temporary backing file as well as to the console.
   * An application would need to do more here to customize the logging level and
   * provide access to the backing file (like sbt's last command and logLevel setting).*/
  def initialGlobalLogging: GlobalLogging =
    GlobalLogging.initial(MainLogging.globalDefault _, File.createTempFile("hello", "log"))


  implicit def stateToScoreSheetPrinter(state: State): ScoreSheetPrinter = {
    new ConsoleScoreSheetPrinter(Constants.FRAMES, new SBTLinePrinter(state))
  }
}

trait LinePrinter {
  def print(line: String)
}

class SBTLinePrinter(state: State) extends LinePrinter {
  def print(line: String) {
    state.log.info(line)
  }
}
