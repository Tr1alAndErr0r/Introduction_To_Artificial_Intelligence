A connect 4 AI using mini-max and the following evaluaton function: 

Point criteria broke down as follows (assuming player 1 is the current program and player 2 is the opponent/naive player):
\n A win by player 1: +512 points
A win by player 2: -512 points
A draw: 0

If there is no winner then the grid is broken down into all possible straight 4 segments (vertically, horizontally, and diagonally) and the following points were assigned:
◦ -50 points: for 3 pieces in the segment from player 2 and 0 pieces from player 1
◦ -10 points: for 2 pieces in the segment from player 2 and 0 pieces from player 1
◦ -1 point: for 1 piece in the segment from player 2 and 0 pieces from player 1
◦ +50 points: for 3 pieces in the segment from player 1 and 0 pieces from player 2
◦ +10 points: for 2 pieces in the segment from player 1 and 0 pieces from player 2
◦ +1 point: for 1 piece in the segment from player 1 and 0 pieces from player 2
◦ +16 if player 1 goes next
◦ -16 if player 2 goes next

The java program reads the game state in JSON format from standard input, calculates a move and then writes the move in JSON format to standard output.
Run with connect-four-driver or connect-four-graphical-driver (written by Doug Williams) to test against naive (connect-four-naive) or other connect four Ai's
