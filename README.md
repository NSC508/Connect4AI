Connect4Ai
==========

This is an implementation of the minimax algorithm using alpha-beta pruning in order to play Connect 4. 

Playing the game
================

Simply run the main file. The current board is 7 x 6. If you want to change this, change the line ```Board connect4Board = new Board();``` and pass in the dimensions into the ```Board()``` function. 

Future Improvements
===================

Currently, the AI can see 10 moves ahead. We can make some improvements to this by making a few improvement 
* Use a faster langauge than Java (C or C++ might be slightly faster)
* Use a more advanced technique rather than alpha beta pruning
* Write the transposition table to a file at the end of the first execution of the program, then read from that file for future runs. The first run will still be slow and limited to 10 moves ahead, but all subsequent runs can go much faster. 

Acknowledgements
================

The evaluation function (which I call the heuristic function) was heavily influenced by existing evalutaion functions for the Connect 4 game. 