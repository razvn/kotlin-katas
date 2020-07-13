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

``text
 ___
    |
    |
 ___
|
|
 ___

``   

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
