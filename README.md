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
  - [Data Structures](#data-structures)
    - [Doubly Linked Lists](#doubly-linked-list)
    - [Queue](#queue)
    - [Graph](#graph)
    - [Tree](#tree)
  - [Files](#files)

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

**getPlayerDeck** Displays the current deck of a player.
**getPlayerHero** Displays the hero of a player.
**getPlayerMana** Displays the current amount of mana a player has.
**getPlayerTurn** Returns the turn of the current active player.

**getCardAtPosition**     Returns the card at the specified position.
**getCardsOnTable**       Displays all the cards on the table
**getFrozenCardsOnTable** Displays all the frozen cards on the table

**breakpoint** Internal debug command where a breakpoint can be placed while using a debugger.

## Documentation

### Bigger picture

The program relies rather heavily on generic functions and functional
programming to achive its goals, since a lot of the tasks can be broken
down into similar enough functions.

The connections between users are modeled using a list graph.
The list graph is composed of a vector of DLL lists. Each element in the vector
is a user and each element in the DLL list is a friend of that user.

The posts are stored in a "database" which is a cronological vector.
Each (re)post is a structure that contains among others a structure of likes
and a tree of reposts.

The likes structure contains the number of likes a (re)post has and a vector
of user_ids of the people that have liked that post.

The tree of reposts is the representation for which reposts are reposts of
other (re)posts. The root of this tree is the original post and contains no
data (important for how functions are implemented inside the program, where
functions usually treat the root differently from other nodes).
Every other node contains the structure to the repost.
Reposts don't have a tree of reposts.

### Data Structures

#### Doubly Linked List

Defined by 3 structs:
1. Header struct
2. Node struct
3. Info struct

1. Header struct is responsible for holding the pointer to the first node,
   the size of the list and the size of the nodes.

2. Node struct is the node of the DLL and is generic, holding only the
   pointers to the next and previous node along with a void pointer to data.

3. Info struct is the one responsible for holding the actual data.
   In its current implementation it stores only an integer.

#### Queue

The queue is implemented generically and is comprised of the following
elements:
1. A buffer which stores the elements in the queue.
2. Size of the elements in the queue.
3. Maximum size of the queue.
4. Size of the queue.
5. Read index.
6. Write index.
7. Data duplicator.
8. Data destructor.

Each of this elements shouldn't be accessed (unless absolutely necessary),
since the functions already implemented should allow full control of the queue.

#### Graph

The graph we use is a generic list graph.
It is implemented using a vector for every node that stores a DLL which
represents all the connections that node has.

#### Tree

The tree we use is a generic k-nary tree.
Defined by 3 structs:
1. Header struct
2. Node struct

1. Header struct is responsible for holding the pointer to root,
   the size of the nodes and a destructor for the data in the nodes.

2. Node struct is the node of the tree and is generic, holding the pointer
   to data, a vector which stores pointers to the children of the node and
   the occupied and allocated size of the children vector.

### Files

1. <helpers.c/h> stores implementations to random helper functions, along
   with some functional programming functions.

2. <data_structure.c/h> stores implementations to the data structures.

3. <algorithms.c/h> stores the implementations of all the algorithms used.
   They are dependent on the implementation of the data structures.

4. <friends.c/h> stores the user interactions.

5. <posts.c/h> stores the post interactions.

6. <feed.c/h> stores the platform interactions.

#### Copyright: 2024 - Gheorghe Andrei-Bogdan