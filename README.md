# NxM-tile Puzzle Solver

## Problem Definition

Given an `input.txt` file, our objective is to solve the Bounded NxM-tile Puzzle.

The board is NxM size, with NxM-1 blocks with numbers from 1 to NxM-1, where there is one empty block marked as `_`.

Some of the blocks are colored in RED, and some in WHITE.
With every White block, comes a number which indicates how much we can move this cell in order to solve the Puzzle.

The board is given in some initial state (not ordered), and the main goal is to find the minimum moves to the final state, where the final state is that the board is ordered from 1,2,...,NxM-1 where the empty block is located at the right bottom part.

### Cost of moving WHITE cell vs. RED cell

When considering the total cost of the solution we need to take into account the color of the cell.

If the cell is White, that the cost of each move is 1, but we can move it only the number of times that comes with that value.

For example, if White: (7,2), then the cell with value 7 is white but we could move it only 2 times and each move cost 1.

All other cells is Red, which we can move how much we want, but each move cost 30.

## Algorithms I used in order to solve the problem

1. `A*`: is implemented with closed list, hash table for the open list as well.
2. `IDA*` and `DFBnB`: is implemented with a Stack, and without a closed-list but with loop avoidance.
3. `DFID`: is implemented in a recursive way, without a closed-list but with loop avoidance.

## Running the program

In the project, you have a couple of `input.txt` examples. If you want to create a new input for the program, please keep the same format where:

- The first line is the name of the algorithm (`IDA*`, `A*`, `DFID`, `DFBnB`)
- The second line (`with time` / `no time`) indicated if you would want to log the total time that took the algorithm to solve the puzzle.
- The third line (`with open` / `no open`) indicates if in addition of saving the output to the `output.txt` file, you would want to log to the console each step that the Algorithm took when trying to solve the puzzle.
- The fourth line is the size of the board (`NxM`)
- The fifth line indicates the White cells (if exist at all)
- Then is the board itself.

### Running from the cmd/terminal

```bash
# Clone the repository
$ git clone https://github.com/MightyArty/NxM-tile-Puzzle.git
# cd the repository
# open your terminal on the same directory
$ Run "javac *.java"
# after this step, the classes of each java file will be created
$ Run "java Ex1"
```
