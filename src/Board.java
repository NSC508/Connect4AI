import java.util.Arrays;
import java.util.StringJoiner;

public class Board {

    public static final int AI_NUMBER = 1;
    public static final int PLAYER_NUMBER = 2;
    public static final int MAX_WINNING_SCORE = Integer.MAX_VALUE;
    public static final int MIN_WINNING_SCORE = Integer.MIN_VALUE;
    public int length; 
    public int height; 
    int[][] board;
    
    public Board() {
        this(7, 6);
    }

    public Board(int length, int height) {
        this.length = length;
        this.height = height;
        this.board = new int[height][length];
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        for (int[] row : this.board) {
            sj.add(Arrays.toString(row));
        }
        String result = sj.toString();
        return result;
    }

    public boolean isWinningBoard() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] == PLAYER_NUMBER) {
                        if (isHorizontal(i, j, false) ||
                        isVertical(i, j, false) ||
                        isDiagonal(i, j, false)) {
                            return true;
                        }
                    } else {
                        if (isHorizontal(i, j, true) ||
                        isVertical(i, j, true) ||
                        isDiagonal(i, j, true)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //parent array
    public static int[] parent = new int[10];

    //connect4 evaluation function that takes into account the number of pieces in a row for each player
    public int hueristic(int[][] board, int player) {
        if (isWinningBoard()) {
            return MAX_WINNING_SCORE;
        }
        if (player == PLAYER_NUMBER) {
            return -1 * hueristic(board, PLAYER_NUMBER);
        }
        int score = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == player) {
                    score += 1;
                } else if (board[i][j] == 0) {
                    score += 0;
                } else {
                    score -= 1;
                }
            }
        }
        return score;
    }

    //Find function
    public static int find(int[] parent, int i) {
        while (parent[i] != i) {
            i = parent[i];
        }
        return i;
    } 

    //union function
    public static void union(int[] parent, int i, int j) {
        int root1 = find(parent, i);
        int root2 = find(parent, j);
        parent[root1] = root2;
    }

    public Board makeCopy() {
        Board newBoard = new Board(length, height);
        int[][] newBoardData = new int[height][length];
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                newBoardData[i][j] = this.board[i][j];
            }
        }
        newBoard.board = newBoardData;
        return newBoard;
    }

    public Board makeMove(int col, boolean isAI) {
        if (col < 0) {
            throw new IllegalArgumentException("col must be greater than zero");
        }
        Board toBeReturned = this.makeCopy();
        int currentHeight = 0;
        while (currentHeight < this.height - 1) {
            if (toBeReturned.board[(this.height - 1) - currentHeight][col] == 0) {
                if (isAI) {
                    toBeReturned.board[(this.height - 1) - currentHeight][col] = AI_NUMBER;
                } else {
                    toBeReturned.board[(this.height - 1) - currentHeight][col] = PLAYER_NUMBER;
                }
                break;
            }
            currentHeight++;
        }
        return toBeReturned;
    }

    public boolean canMakeMove(int col) {
        return this.board[0][col] == 0;
    }

    private boolean isHorizontal(int i, int j, boolean isAI) {
        int playerNum;
        if (isAI) {
            playerNum = AI_NUMBER;
        } else {
            playerNum = PLAYER_NUMBER;
        }
        int countInARowLeft = 0;
        int countInARowRight = 0;
        //Chcek 4 to the left and right to see if we find anything that is not the player
        for (int row = 0; row < 4; row++) {
            if (j + row < this.length) {
                if (this.board[i][j + row] == playerNum) {
                    countInARowRight++;
                }
            } 
            if (j - row > 0) {
                if (this.board[i][j - row] == playerNum) {
                    countInARowLeft++;
                }
            }
        }
        return (countInARowRight == 4 || countInARowLeft == 4);
    }

    private boolean isVertical(int i, int j, boolean isAI) {
        int playerNum;
        if (isAI) {
            playerNum = AI_NUMBER;
        } else {
            playerNum = PLAYER_NUMBER;
        }
        int countInARowUp = 0;
        int countInARowDown = 0;
        //Check 4 on top and bottom to see if we find anything that is not the player
        for (int col = 0; col < 4; col++) {
            if (i + col < this.height) {
                if (this.board[i + col][j] == playerNum) {
                    countInARowDown++;
                }
            } 
            if (i - col > 0) {
                if (this.board[i - col][j] == playerNum) {
                    countInARowUp++;
                }
            }
        }
        return (countInARowUp == 4 || countInARowDown == 4);
    }

    private boolean isDiagonal(int i, int j, boolean isAI) {
        int playerNum;
        if (isAI) {
            playerNum = AI_NUMBER;
        } else {
            playerNum = PLAYER_NUMBER;
        }
        int countInARowLeft = 0; 
        int countInARowRight = 0;
        //Check diagonally left and right to see if we find anything that is not the player
        for (int diag = 0; diag < 4; diag++) {
            if (i - diag > 0 && j + diag < this.length) {
                if (this.board[i - diag][j + diag] == playerNum) {
                    countInARowRight++;
                }
            }
            if (i + diag < this.height && j - diag  > 0) {
                if (this.board[i + diag][j - diag] == playerNum) {
                    countInARowLeft++;
                }
            }
        }
        return (countInARowLeft == 4 || countInARowRight == 4);
    }
}
