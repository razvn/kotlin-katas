# Collection of Katas
Sources:
    - [codingdojo.org](https://codingdojo.org/)
    
## FizzBuzz
### Problem Description

Write a program that prints the numbers from 1 to 100. But for multiples of three print “Fizz” instead of the number and for the multiples of five print “Buzz”. For numbers which are multiples of both three and five print “FizzBuzz “.

Sample output:
```text
1
2
Fizz
4
Buzz
Fizz
7
8
Fizz
Buzz
11
Fizz
13
14
FizzBuzz
16
17
Fizz
19
Buzz
... etc up to 100
```

### Stage 2 - new requirements
 * A number is fizz if it is divisible by 3 or if it has a 3 in it
 * A number is buzz if it is divisible by 5 or if it has a 5 in it


## Number to LCD

### Part 1

Write a program that given a number (with arbitrary number of digits), converts it into LCD style numbers using the following format:

```text
   _  _     _  _  _  _  _  
 | _| _||_||_ |_   ||_||_|  
 ||_  _|  | _||_|  ||_| _|  
```
(each digit is 3 lines high)


Note: Please do NOT read the second part before completing the first. Part of the purpose of this kata is to make you practice refactoring and adapting to changing requirements.

### Part 2

Change your program to support variable width or height of the digits. For example for width = 3 and height = 2 the digit 2 will be:

```text
 ___
    |
    |
 ___
|
|
 ___

```

Tip: Refactor first to accept 5 lines per digit format:
```text
     -   -       -   -   -   -   - 
  |   |   | | | |   |     | | | | |
     -   -   -   -   -       -   - 
  | |     |   |   | | |   | | |   |
     -   -       -   -       -   - 
```

## Diamond

### Problem Description

Given a letter, print a diamond starting with ‘A’ with the supplied letter at the widest point.

For example: print-diamond ‘C’ prints
```text
  A
 B B
C   C
 B B
  A
```
for 'B'
```text
  A
 B B
  A
```

## Poker Hands
https://codingdojo.org/kata/PokerHands/

### Problem Description
    
Your job is to compare several pairs of poker hands and to indicate which, if either, has a higher rank.    

A poker deck contains 52 cards - each card has a suit which is one of clubs, diamonds, hearts, or spades (denoted C, D, H, and S in the input data). Each card also has a value which is one of 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace (denoted 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A). For scoring purposes, the suits are unordered while the values are ordered as given above, with 2 being the lowest and ace the highest value.

A poker hand consists of 5 cards dealt from the deck. Poker hands are ranked by the following partial order from lowest to highest.

- High Card: Hands which do not fit any higher category are ranked by the value of their highest card. If the highest cards have the same value, the hands are ranked by the next highest, and so on.
- Pair: 2 of the 5 cards in the hand have the same value. Hands which both contain a pair are ranked by the value of the cards forming the pair. If these values are the same, the hands are ranked by the values of the cards not forming the pair, in decreasing order.
- Two Pairs: The hand contains 2 different pairs. Hands which both contain 2 pairs are ranked by the value of their highest pair. Hands with the same highest pair are ranked by the value of their other pair. If these values are the same the hands are ranked by the value of the remaining card.
- Three of a Kind: Three of the cards in the hand have the same value. Hands which both contain three of a kind are ranked by the value of the 3 cards.
- Straight: Hand contains 5 cards with consecutive values. Hands which both contain a straight are ranked by their highest card.
- Flush: Hand contains 5 cards of the same suit. Hands which are both flushes are ranked using the rules for High Card.
- Full House: 3 cards of the same value, with the remaining 2 cards forming a pair. Ranked by the value of the 3 cards.
- Four of a kind: 4 cards with the same value. Ranked by the value of the 4 cards.
- Straight flush: 5 cards of the same suit with consecutive values. Ranked by the highest card in the hand.

### Suggested Test Cases

Sample input:
```text
Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH
Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S
Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C KH
Black: 2H 3D 5S 9C KD  White: 2D 3H 5C 9S KH
```

Each row of input is a game with two players. The first five cards belong to the player named “Black” and the second five cards belong to the player named “White”.

Sample output:
```text
White wins. - with high card: Ace 
Black wins. - with full house: 4 over 2 
Black wins. - with high card: 9
Tie.
```

## Args
https://codingdojo.org/kata/Args/

### Problem Description

Most of us have had to parse command-line arguments from time to time. If we don’t have a convenient utility, then we simply walk the array of strings that is passed into the main function. There are several good utilities available from various sources, but they probably don’t do exactly what we want. So let’s write another one!

The arguments passed to the program consist of flags and values. Flags should be one character, preceded by a minus sign. Each flag should have zero, or one value associated with it.

You should write a parser for this kind of arguments. This parser takes a schema detailing what arguments the program expects. The schema specifies the number and types of flags and values the program expects.

Once the schema has been specified, the program should pass the actual argument list to the args parser. It will verify that the arguments match the schema. The program can then ask the args parser for each of the values, using the names of the flags. The values are returned with the correct types, as specified in the schema.

For example if the program is to be called with these arguments:
```    
-l -p 8080 -d /usr/logs
```
this indicates a schema with 3 flags: l, p, d. The “l” (logging) flag has no values associated with it, it is a boolean flag, True if present, False if not. the “p” (port) flag has an integer value, and the “d” (directory) flag has a string value.

If a flag mentioned in the schema is missing in the arguments, a suitable default value should be returned. For example “False” for a boolean, 0 for a number, and “” for a string. If the arguments given do not match the schema, it is important that a good error message is given, explaining exactly what is wrong.

If you are feeling ambitious, extend your code to support lists eg
```
-g this,is,a,list -d 1,2,-3,5
```
So the “g” flag indicates a list of strings, [“this”, “is”, “a”, “list”] and the “d” flag indicates a list of integers, [1, 2, -3, 5].

Make sure your code is extensible, in that it is straightforward and obvious how to add new types of values.

### Clues

What the schema should look like and how to specify it is deliberately left vague in the Kata description. An important part of the Kata is to design a concise yet readable format for it.


## Lift
https://github.com/emilybache/Lift-Kata

### Description
Since lifts are everywhere and they contain software, how easy would it be to write a basic one? Let’s TDD a lift, starting with simple behaviors and working toward complex ones. Assume good input from calling code and concentrate on the main flow.

Lift features:

- a lift moves between a number of floors.
- a lift has a panel of buttons passengers can press to request floors.
- people can call the lift from other floors. A call has both a floor and a desired direction.
- a lift has doors which may be open or closed.
In this repository, that much is already implemented. The following features are not yet implemented:

- a lift fulfills a request when it moves to the requested floor and opens the doors.
- a lift fulfills a call when it moves to the correct floor, is about to go in the called direction, and opens the doors.
- a lift can only move between floors if the doors are closed.

Lifts do not respond immediately or do everything at once. To simplify handling time in this exercise, the provided LiftSystem class has a 'tick' method. Every time you call it, the lift system should simulate a unit of time passing, and update its state according to what changes occurred during that time period. Lifts can move between floors or open their doors for example.

To simplify things, Lifts only accept new calls and requests when they are on a floor. (Then we don't have to model what happens when they are between floors).

The starting code has a Lift class with basic attributes like a floor, requests and doors. Can you build on this code and create something that fulfills all the desired features? Consider Object-Oriented design principles. Can you make Lift and LiftSystem into a well-designed encapsulated objects?


### Multiple lifts
When you have a single lift working well, you may want to tackle these further features:

- there can be more than one lift.
- only one lift needs to respond to each call.
- on each floor there is a monitor above each lift door. While the lift is moving it shows which floor it is on.
- when the lift stops at a floor to answer a call, the monitor shows which direction it will go in.
- when fulfilling a call, the relevant lift makes a 'DING' as it opens the doors.

Note: the printer does not currently show the lift monitor and the ding.
