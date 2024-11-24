# GwentStone Lite

## Introduction

This application simulates a friendly 1v1 game of HeartStone with some features from Gwent.

To interact with the application, the input must be formated in JSON format.

## Overview

### Gameplay

The game is for two players, each with multiple card decks.
At the start, each player picks a deck and an assigned hero.
Turn order is decided by a coin toss.

The game is turn-based, with each player ending their turn explicitly.
A new round starts after both turns are complete.
At the start of a round, players receive mana (the game’s currency) and draw a card.

Cards in hand can be played on the board using mana, as each card has a mana cost.
The board is where cards interact, with each player having 2 rows for placement.
Heroes start on the board, each having special abilities that can affect these rows.

The goal is to use cards to attack the opponent's hero until their health reaches zero.
The player who deals the final blow wins. Each player tracks their games played and won.

### Game board

The board is a 4×5 grid with 2 rows per player.
Cards are placed left to right until a row is full (max 5 cards).
If a card is defeated, cards to its right shift left.
Rows 0-1 belong to player 2, and rows 2-3 to player 1.
Rows 1-2 are front rows, while 0-3 are back rows.
Heroes have designated starting spots on the board.

## Table of contents

- [Introduction](#introduction)
- [Overview](#overview)
  - [Gameplay](#gameplay)
  - [Game board](#game-board)
- [Table of contents](#table-of-contents)
- [Interactions](#interactions)
  - [Gameplay Interactions](#gameplay-interactions)
  - [Statistical Interactions](#statistical-interactions)
  - [Debug Interactions](#debug-interactions)
- [Documentation](#documentation)
  - [Bigger Picture](#bigger-picture)
  - [Logic and design](#logic-and-design)
  - [Important Classes](#important-classes)
    - [MatchUp Class](#matchup-class)
    - [GameBoard Class](#gameboard-class)
    - [Player Class](#player-class)
    - [Deck Class](#deck-class)
    - [GenericCard Class](#genericcard-class)
    - [GenericHero Class](#generichero-class)

## Interactions

### Gameplay interactions

- **placeCard**         Places a card on the board.
- **cardUsesAttack**    Commands a card to attack another card.
- **cardUsesAbility**   Commands a special card to use its ability.
- **useAttackHero**     Commands a card to attack a hero.
- **useHeroAbility**    Commands the hero to use its ability.
- **endPlayerTurn**     Ends the turn of the current player.

### Statistical interactions

- **getPlayerOneWins**      Returns the number of games one by player one.
- **getPlayerTwoWins**      Returns the number of games one by player two.
- **getTotalGamesPlayed**   Returns the number of games played.

### Debug interactions

- **getPlayerDeck** Displays the current deck of a player.
- **getPlayerHero** Displays the hero of a player.
- **getPlayerMana** Displays the current amount of mana a player has.
- **getPlayerTurn** Returns the turn of the current active player.

- **getCardAtPosition**     Returns the card at the specified position.
- **getCardsOnTable**       Displays all the cards on the table
- **getFrozenCardsOnTable** Displays all the frozen cards on the table

- **breakpoint** Internal debug command where a breakpoint can be placed while using a debugger.

## Documentation

### Bigger picture

The design of the program is dependent on its ability to read a JSON file,
process the logic of the games and the format that output into another JSON file.
The Main class manages all of that.

The action method of the Main function manages both the passing of the input to
the MatchUp class, which implements the logic of the program and the outputting
of the results into another JSON file.

### Logic and design

Since any games played in this program is played between the same two players,
on the same game board and with the same possible decks for the players,
the program implements the singleton class MatchUp.

Another important aspect of the design regards the players. Before any game
starts, the players each get different and unique decks that must not be modified
later in the program. As such, they are stored into an array where each deck will
be copied on demand.

At the same time, each player has both a deck, copied from the possible decks and
randomised before the start of each game, and a hand which represents the cards
the player can actually place on the game board. In the program, they are implemented
using the same class, yet logically they function differently. The deck is can
be thought of as a stack, and the hand can be thought of as an array.

The last aspect that needs to be discussed is the implementation of the cards and
heroes. Every card and hero is derived from the class GenericCard, which contains
everything shared by all cards.

From this class, 3 other types of classes emerge:
- the normal card classes (Berserker, Goliath, etc.); 
- the special cards, that have specific abilities tied to them (Disciple, Miraj, etc.);
- GenericHero class, which contains all the common information of heroes and is later
specialized for each different hero.

Each card class is initialized when it is first added into a deck, but it's stored as
a GenericCard reference.

Same deal for heroes, they are initialized into their respective classes when they are
set of each player, but stored as a GenericHero reference.

### Important Classes

#### MatchUp Class

Responsible for the correctly initializing the players and the game board before any game
is played, for implementing the handling of the program commands, and formatting the
outputs of said commands.

Doesn't change between games, only resetting the relevant information in the Player
and GameBoard classes.

Contains: GameBoard and two Players

#### GameBoard Class

Handles all the interactions between cards that are on the game board. Stores both the
cards and references to the heroes of each player.

Contains: two GenericHeroes

#### Player Class

Responsible for handling any task that regards the player in any way.

Contains: GenericHero, two Decks (one for the hand and one for the deck) and one Deck array
for the available decks that are unchanging.

#### Deck Class

Handles the actions performed by both the player hand and deck.

#### GenericCard Class

The class that stores all the common information of every card:
- name;
- description;
- colors;
- mana cost;
- attack power;
- health;
- internal variables.

Every card and hero is derived from this class.

#### GenericHero Class

The class that stores all the common information of every hero.

#### Copyright: 2024 - Gheorghe Andrei-Bogdan