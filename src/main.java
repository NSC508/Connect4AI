public class main{
    public static void main(String[] args) {
        Board connect4Board = new Board(5, 6);
        connect4Board.makeMove(0, true);
        connect4Board.makeMove(0, true);
        minimax(connect4Board);
        System.out.println(connect4Board);
    }

    private static Board minimax(Board currentBoard, int depth, int alpha, int beta, boolean isAI) {
        if (depth == 0 || currentBoard.isWinningBoard()) {
            return currentBoard;
        }
        if (isAI) {
            int value = Integer.MIN_VALUE;
            Board bestBoard = currentBoard.makeCopy();
            for (int i = 0; i < currentBoard.length; i++) {
                if (currentBoard.canMakeMove(i)) {
                    Board child = currentBoard.makeCopy();
                    int bestFromChildHueristic = minimax(child, depth - 1, alpha, beta, false).heuristic();
                    if (bestFromChildHueristic > value) {
                        value = bestFromChildHueristic;
                        bestBoard = child;
                    }
                    alpha = Math.max(alpha, value); 
                    if (value >= beta) {
                        break;
                    }
                }
            } 
            return bestBoard;
        } else {
            int value = Integer.MAX_VALUE;
            Board bestBoard = currentBoard.makeCopy();
            for (int i = 0; i < currentBoard.length; i++) {
                Board child = currentBoard.makeCopy();
                int bestFromChildHueristic = minimax(child, depth - 1, alpha, beta, true).heuristic();
                if (bestFromChildHueristic < value) {
                    value = bestFromChildHueristic;
                    bestBoard = child;
                }
                beta = Math.min(beta, value);
                if (value <= alpha) {
                    break;
                }
            }
            return bestBoard;
        }
    }
}