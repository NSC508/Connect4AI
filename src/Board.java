import java.util.HashSet;
import java.util.Set;

public class Board {

    public static final int AI_NUMBER = 1;
    public static final int PLAYER_NUMBER = 2;
    public int length; 
    public int height; 
    int[] board;
    
    public Board() {
        this(7, 6);
    }

    public Board(int length, int height) {
        this.length = length;
        this.height = height;
        this.board = new int[length * height];
    }

    @Override
    public String toString() {
        String boardAsString = "";
        for (int i = 0; i < board.length; i++) {
            boardAsString += board[i];
            if (i % this.length == this.length- 1) {
                boardAsString += "\n";
            }
        }
        return boardAsString;
    }

    public boolean isWinningBoard() {
        Set<Integer> seenIndices = new HashSet<Integer>();
        for (int i = 0; i < this.board.length; i++) {
            if (board[i] != 0 && !seenIndices.contains(i)) {
                if (board[i] == PLAYER_NUMBER) {
                    isHorizontal(i, seenIndices, false);
                    isVertical(i, seenIndices, false);
                    isDiagonal(i, seenIndices, false);
                } else {
                    isHorizontal(i, seenIndices, true);
                    isVertical(i, seenIndices, true);
                    isDiagonal(i, seenIndices, true);
                }
            }
        }
        return false;
    }

    public int heuristic() {
        return 0; 
    }

    public Board makeCopy() {
        Board newBoard = new Board(length, height);
        return newBoard;
    }

    public void makeMove(int col, boolean isAI) {
        int location = (this.length * (this.height - 1)) + col;
        while (this.board[location] != 0 && (location > 0)) {
            location -= this.length;
        }
        if (isAI) {
            board[location] = AI_NUMBER;
        } else {
            board[location] = PLAYER_NUMBER;
        }
    }

    private boolean isHorizontal(int index, Set<Integer> seenIndices, boolean isAI) {
        return false;
    }

    private boolean isVertical(int index, Set<Integer> seenIndices, boolean isAI) {
        return false;
    }

    private boolean isDiagonal(int index, Set<Integer> seenIndices, boolean isAI) {
        return false;
    }

}
