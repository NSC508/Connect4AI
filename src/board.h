#include <vector>
#include <bits/stdc++.h>
#ifndef BOARD // include guard
#define BOARD

namespace connect4ai{
    const unsigned int AI_NUMBER = 1;
    const unsigned int PLAYER_NUMBER = 2;
    class Board {
        public:
            int boardLength; 
            int boardHeight;
            vector<int> board;

            Board(int boardLength, int boardHeight);
            int heuristic();
            bool isWinningBoard();
            void makeMove(int col, bool isAI);
            void printBoard();
            Board makeCopy();
        private:
            bool isHorizontal(int index);
            bool isVertical(int index); 
            bool isDiagonal(int index);
        };
}
#endif
