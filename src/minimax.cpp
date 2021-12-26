#include "minimax.h"
#include <limits>
#include <algorithm>
using namespace std;
namespace connect4ai {
    int alphabeta(Board b, int depth, int alpha, int beta, bool isAI) {
        if (depth == 0 || b.isWinningBoard()) {
            return b.heuristic();
        }
        if (isAI) {
            signed int value = numeric_limits<signed int>::min();
            for (int i = 0; i < b.boardLength; i++) {
                Board copyBoard = b.makeCopy(); 
                copyBoard.makeMove(i, isAI);
                value = max(value, alphabeta(copyBoard, depth - 1, alpha, beta, false));
                alpha = max(alpha, value);
                if (value >= beta) {
                    break;
                }
                return value;
            } 
        } else {
            signed int value = numeric_limits<signed int>::max();
            for (int i = 0; i < b.boardLength; i++) {
                Board copyBoard = b.makeCopy();
                copyBoard.makeMove(i, isAI);
                value = min(value, alphabeta(copyBoard, depth - 1, alpha, beta, true));
                beta = min(beta, value);
                if (value <= alpha) {
                    break;
                }
                return value;
            }
        }
    }
}
