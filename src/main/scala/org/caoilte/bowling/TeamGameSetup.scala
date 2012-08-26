package org.caoilte.bowling

import collection.immutable.Queue


case class Player(name: String, addedOrder: Int) {
  override def toString() = name

  def fillNameTo(padding: Int) = {
    name + List.fill(padding-name.size)(" ").mkString
  }
}

case class TeamGameSetup(players: Map[String, Player]) {
  def this() = this(Map[String,Player]())

  def addPlayer(name: String) = {
    if (players.contains(name)) {
      throw new IllegalArgumentException(
        "Cannot add player with name '%s' as a player with this name already exists".format(name))
    }
    TeamGameSetup(players.+ ((name, Player(name, players.size))))
  }

  def printPlayers(linePrinter: LinePrinter) {
    linePrinter.print("");
    linePrinter.print("PLAYING")

    players.values.foreach(player => linePrinter.print(player.toString()))
  }

  def start(): TeamGame = {
    val queue = Queue[PlayerGame]()
    val playersByOrderAdded: List[Player] = players.values.toList.sortBy(_.addedOrder)

    val namePadding = playersByOrderAdded.map(_.name.size).max + 1

    TeamGame(Nil, queue.enqueue(playersByOrderAdded.map(p => new PlayerGame(new TeamPlayer(p, namePadding), Constants.FRAMES)).toList))
  }
}
