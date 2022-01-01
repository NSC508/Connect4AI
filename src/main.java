import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main{
    public static void main(String[] args) {
        // int DEPTH = 2;
        // Board connect4Board = new Board();
        // connect4Board.makeMove(2, true);
        // connect4Board.makeMove(3, true);
        // connect4Board.makeMove(4, true);
        // connect4Board.makeMove(5, true);
        // System.out.println(connect4Board);
        // HashMap<Boolean, HashMap<Board, Board>> transpositionTableFull = new HashMap<Boolean, HashMap<Board, Board>>();
        // connect4Board = minimax(connect4Board, DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, transpositionTableFull);
        // System.out.println(connect4Board);
        playGame();
    }

    public static void playGame() {
        int DEPTH = 10;
        System.out.println("Hello! Welcome to this game of connect 4!");
        System.out.println("Would you like to start first, or would you like the AI to move first?");
        System.out.println("Enter Y if you want to move first, N if you want the AI to move first");
        Scanner scanner = new Scanner(System.in);
        boolean isAI = scanner.nextLine().toLowerCase() == "n";
        Board connect4Board = new Board();
        HashMap<Boolean, HashMap<Board, Board>> transpositionTableFull = new HashMap<Boolean, HashMap<Board, Board>>();
        System.out.println("The board looks like so:");
        System.out.println(connect4Board);
        while (!connect4Board.isWinningBoard()) {
            if (isAI) {
                connect4Board = minimax(connect4Board, DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, transpositionTableFull);
                if (connect4Board.isWinningBoard()) {
                    System.out.println("The AI won!");
                }
            } else {
                System.out.println(
                    "Where would you like to place your piece? Enter a number between 0 and "
                    + connect4Board.length
                    + ". Enter a number less than 0 if you would like to quit");
                int col = scanner.nextInt();
                if (col < 0) {
                    System.out.println("Thanks for playing!");
                    break;
                }
                connect4Board.makeMove(col, isAI);
            }
            System.out.println("The board looks like so:");
            System.out.println(connect4Board);
            isAI = !isAI;
        }
        scanner.close(); 
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
