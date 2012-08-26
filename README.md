TEN PIN BOWLING
===============

This project was written with Scala and SBT (Simple Build Tool).

It uses the new Command Line Application support in SBT to manage and validate user input as documented here.

https://github.com/harrah/xsbt/wiki/Command-Line-Applications

This feature requires you to be running at least version 0.12 of SBT

RUNNING THE APPLICATION
=======================

The following commmands run from the root directory of the project will start an play the first few frames of a game,

./sbt @bowling.build.properties
addPlayer Caoilte
addPlayer Walter
addPlayer Donny
start
score 10
score 2
score 8

etc

Because it uses SBT for command line support command history and Parser Combinator tab completion come for free.
For example, after entering score 2 above, pressing tab will display 0-8 as valid completions for the next score.

Once the game has finished it will print the final scores and then start a new game requiring new players names to be
entered.

BUILDING THE APPLICATION
========================

If you want to build the application yourself you will need to run the following commands

rm -rf project/boot/
./sbt
test
publish-local

The first rm removes the cached binaries. There is probably a good workaround for it, but I did not have sufficient
time to investigate yet.