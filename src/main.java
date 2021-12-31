public class main{
    public static void main(String[] args) {
        int DEPTH = 3;
        Board connect4Board = new Board(5, 6);
        connect4Board.makeMove(0, true);
        connect4Board.makeMove(0, false);
        connect4Board = minimax(connect4Board, DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        System.out.println(connect4Board);
    }

    private static Board minimax(Board currentBoard, int depth, int alpha, int beta, boolean isAI) {
        if (depth == 0 || currentBoard.isWinningBoard()) {
            return currentBoard;
        }
        if (isAI) {
            int value = Integer.MIN_VALUE;
            Board bestBoard = currentBoard.makeCopy();
            int bestMoveCol = -1;
            for (int i = 0; i < currentBoard.length; i++) {
                if (currentBoard.canMakeMove(i)) {
                    Board child = currentBoard.makeCopy();
                    int bestFromChildHueristic = minimax(child, depth - 1, alpha, beta, false).heuristic();
                    if (bestFromChildHueristic > value) {
                        value = bestFromChildHueristic;
                        bestBoard = child;
                        bestMoveCol = i;
                    }
                    alpha = Math.max(alpha, value); 
                    if (value >= beta) {
                        break;
                    }
                }
            }
            bestBoard.makeMove(bestMoveCol, isAI); 
            return bestBoard;
        } else {
            int value = Integer.MAX_VALUE;
            Board bestBoard = currentBoard.makeCopy();
            int bestMoveCol = -1;
            for (int i = 0; i < currentBoard.length; i++) {
                Board child = currentBoard.makeCopy();
                int bestFromChildHueristic = minimax(child, depth - 1, alpha, beta, true).heuristic();
                if (bestFromChildHueristic < value) {
                    value = bestFromChildHueristic;
                    bestBoard = child;
                    bestMoveCol = i;
                }
                beta = Math.min(beta, value);
                if (value <= alpha) {
                    break;
                }
            }
            bestBoard.makeMove(bestMoveCol, isAI);
            return bestBoard;
        }
    }
}