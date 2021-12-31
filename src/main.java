import java.util.HashMap;
import java.util.Map;

public class main{
    public static void main(String[] args) {
        int DEPTH = 10;
        Board connect4Board = new Board(5, 6);
        connect4Board.makeMove(0, true);
        connect4Board.makeMove(0, false);
        HashMap<Boolean, HashMap<Board, Board>> transpositionTableFull = new HashMap<Boolean, HashMap<Board, Board>>();
        connect4Board = minimax(connect4Board, DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, transpositionTableFull);
        System.out.println(connect4Board);
    }

    private static Board minimax(
        Board currentBoard,
        int depth,
        int alpha,
        int beta,
        boolean isAI,
        HashMap<Boolean, HashMap<Board, Board>> transpositionTableFull
        ) {
        if (depth == 0 || currentBoard.isWinningBoard()) {
            return currentBoard;
        }
        if (transpositionTableFull.size() == 0) {
            transpositionTableFull.put(isAI, new HashMap<Board, Board>());
            transpositionTableFull.put(!isAI, new HashMap<Board, Board>());
        }
        Map<Board, Board> transpositionTable = transpositionTableFull.get(isAI);
        if (transpositionTable.keySet().contains(currentBoard)) {
            return transpositionTable.get(currentBoard);
        }
        Board bestBoard = currentBoard.makeCopy();
        if (isAI) {
            int value = Integer.MIN_VALUE;
            for (int i = 0; i < currentBoard.length; i++) {
                if (currentBoard.canMakeMove(i)) {
                    Board child = currentBoard.makeCopy();
                    child.makeMove(i, isAI);
                    int bestFromChildHueristic = minimax(child, depth - 1, alpha, beta, false, transpositionTableFull).heuristic();
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
        } else {
            int value = Integer.MAX_VALUE;
            for (int i = 0; i < currentBoard.length; i++) {
                Board child = currentBoard.makeCopy();
                child.makeMove(i, isAI);
                int bestFromChildHueristic = minimax(child, depth - 1, alpha, beta, true, transpositionTableFull).heuristic();
                if (bestFromChildHueristic < value) {
                    value = bestFromChildHueristic;
                    bestBoard = child;
                }
                beta = Math.min(beta, value);
                if (value <= alpha) {
                    break;
                }
            }
        }
        transpositionTable.put(currentBoard, bestBoard);
        return bestBoard;
    }
}