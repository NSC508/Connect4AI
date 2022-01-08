import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main{
    public static void main(String[] args) {
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
                connect4Board = alphaBeta(connect4Board, DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, transpositionTableFull);
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

    //visited array
    public static boolean[] visited = new boolean[10];

    //alpha-beta pruning that returns the best graph with a transposition table that has board as inputs and board as outputs
    public static Board alphaBeta(Board board, int depth, int alpha, int beta, boolean isMax, HashMap<Boolean, HashMap<Board, Board>> transpositionTableFull) {
        if (depth == 0 || board.isWinningBoard()) {
            return board;
        }
        int player = isMax ? Board.AI_NUMBER : Board.PLAYER_NUMBER;
        if (isMax) {
            int bestScore = Integer.MIN_VALUE;
            Board bestBoard = null;
            for (int i = 0; i < board.length; i++) {
                if (board.canMakeMove(i)) {
                    Board newBoard = board.makeMove(i, isMax);
                    if (transpositionTableFull.containsKey(isMax) && transpositionTableFull.get(isMax).containsKey(newBoard)) {
                        newBoard = transpositionTableFull.get(isMax).get(newBoard);
                    } else {
                        newBoard = alphaBeta(newBoard, depth - 1, alpha, beta, false, transpositionTableFull);
                        transpositionTableFull.get(isMax).put(newBoard, newBoard);
                    }
                    if (newBoard.hueristic(newBoard.board, player) > bestScore) {
                        bestScore = newBoard.hueristic(newBoard.board, player);
                        bestBoard = newBoard;
                    }
                    alpha = Math.max(alpha, bestScore);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestBoard;
        } else {
            int bestScore = Integer.MAX_VALUE;
            Board bestBoard = null;
            for (int i = 0; i < board.length; i++) {
                if (board.canMakeMove(i)) {
                    Board newBoard = board.makeMove(i, isMax);
                    if (transpositionTableFull.containsKey(isMax) && transpositionTableFull.get(isMax).containsKey(newBoard)) {
                        newBoard = transpositionTableFull.get(isMax).get(newBoard);
                    } else {
                        newBoard = alphaBeta(newBoard, depth - 1, alpha, beta, true, transpositionTableFull);
                        transpositionTableFull.get(isMax).put(newBoard, newBoard);
                    }
                    if (newBoard.hueristic(newBoard.board, player) < bestScore) {
                        bestScore = newBoard.hueristic(newBoard.board, player);
                        bestBoard = newBoard;
                    }
                    beta = Math.min(beta, bestScore);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestBoard;
        }
    }
}
