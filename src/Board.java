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
                        return isHorizontal(i, j, false) ||
                        isVertical(i, j, false) ||
                        isDiagonal(i, j, false);
                    } else {
                        return isHorizontal(i, j, true) ||
                        isVertical(i, j, true) ||
                        isDiagonal(i, j, true);
                    }
                }
            }
        }
        return false;
    }

    public int heuristic() {
        int vertical_points=0, horizontal_points=0, descDiagonal_points=0, ascDiagonal_points=0, total_points=0;

        for (int row = 0; row < this.height - 3; row++) {
            for (int column = 0; column < this.length; column++) {
                int tempScore = calcScorePosition(row, column, 1, 0);
                vertical_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 0; row < this.height ; row++) {
            for (int column = 0; column < this.length - 3; column++) {
                int tempScore = calcScorePosition(row, column, 0, 1);
                horizontal_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 0; row < this.height - 3 ; row++) {
            for (int column = 0; column < this.length - 3; column++) {
                int tempScore = calcScorePosition(row, column, 1, 1);
                descDiagonal_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 3; row < this.height  ; row++) {
            for (int column = 0; column < this.length - 4; column++) {
                int tempScore = calcScorePosition(row, column, -1, 1);
                ascDiagonal_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        total_points = vertical_points + horizontal_points + descDiagonal_points + ascDiagonal_points;
        return total_points;
    }

    private int calcScorePosition(int row, int column, int increment_row, int increment_col) {
        int ai_points = 0, player_points = 0;

        for (int i = 0; i < 4; i++) //connect "4"
        {
            if(board[row][column] == AI_NUMBER)
            {
                ai_points++;
            }
            else if (board[row][column] == PLAYER_NUMBER)
            {
                player_points++;
            }

            row += increment_row;
            column += increment_col;
        }

        if(player_points == 4)
            return MIN_WINNING_SCORE;
        else if(ai_points == 4)
            return MAX_WINNING_SCORE;
        else
            return ai_points;
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

    public void makeMove(int col, boolean isAI) {
        if (col < 0) {
            throw new IllegalArgumentException("col must be greater than zero");
        }
        int currentHeight = 0;
        while (currentHeight < this.height - 1) {
            if (this.board[(this.height - 1) - currentHeight][col] == 0) {
                if (isAI) {
                    this.board[(this.height - 1) - currentHeight][col] = AI_NUMBER;
                } else {
                    this.board[(this.height - 1) - currentHeight][col] = PLAYER_NUMBER;
                }
                break;
            }
            currentHeight++;
        }
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
        //Chcek 4 to the left and right to see if we find anything that is not the player
        for (int row = 0; row < 4; row++) {
            if (j + row < this.length) {
                if (this.board[i][j + row] != playerNum) {
                    return false;
                }
            } 
            if (j - row > 0) {
                if (this.board[i][j - row] != playerNum) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isVertical(int i, int j, boolean isAI) {
        int playerNum;
        if (isAI) {
            playerNum = AI_NUMBER;
        } else {
            playerNum = PLAYER_NUMBER;
        }
        //Check 4 on top and bottom to see if we find anything that is not the player
        for (int col = 0; col < 4; col++) {
            if (i + height < this.height) {
                if (this.board[i + col][j] != playerNum) {
                    return false;
                }
            } 
            if (i - col > 0) {
                if (this.board[i - col][j] != playerNum) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isDiagonal(int i, int j, boolean isAI) {
        int playerNum;
        if (isAI) {
            playerNum = AI_NUMBER;
        } else {
            playerNum = PLAYER_NUMBER;
        }
        //Check diagonally left and right to see if we find anything that is not the player
        for (int diag = 0; diag < 4; diag++) {
            if (i + diag < this.height && j + diag < this.length) {
                if (this.board[i + diag][j + diag] != playerNum) {
                    return false;
                }
            }
            if (i - diag > 0 && j - diag  > 0) {
                if (this.board[i - diag][j - diag] != playerNum) {
                    return false;
                }
            }
        }
        return true;
    }
}
