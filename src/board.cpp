#include "board.h"
using namespace std;
namespace connect4ai {
    
    class Board {
        const unsigned int AI_NUMBER = 1;
        const unsigned int PLAYER_NUMBER = 2;
        public:
            int boardLength; 
            int boardHeight;
            vector<int> board;

            Board(int length, int height) {
                boardLength = length; boardHeight = height;
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < height; j++) {
                        board.push_back(0);
                    }
                }
            }

            int heuristic() {
                return 0;
            }

            bool isWinningBoard() {
                
                return false;
            }

            void makeMove(int col, bool isAI) {
                // Go to the location that we want to palce at
                int location = (boardLength * (boardHeight - 1)) + col;
                while (board[location] != 0 && (location > 0)) {
                    location -= boardLength;
                }
                if (isAI) {
                    board[location] = AI_NUMBER;
                } else {
                    board[location] = PLAYER_NUMBER;
                }
            }

            void printBoard() {
                for (int i = 0; i < board.size(); i++) {
                    cout << board[i];
                    if ((boardLength - 1) % i == 0) {
                        cout<< endl;
                    }
                }
            }

            Board makeCopy() {
                Board newBoard = Board(boardLength, boardHeight);
                for (int i = 0; i < board.size(); i++) {
                    newBoard.board[i] = board[i];
                }
            }
        private:
            bool isHorizontal(int index) {

            }
            bool isVertical(int index) {

            }
            bool isDiagonal(int index) {

            }
    };
}
