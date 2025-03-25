/**
 * The model class for the Connect Four game logic.
 * Manages the game board, player turns, and win conditions.
 */
package connectfour;

/**
 * Initializes a new Connect Four game board.
 */
public class ConnectFourModel {
    public static final int ROWS = 6;
    public static final int COLS = 7;
    private final char[][] board;
    private char currentPlayer;
    private int turnCount;
    private boolean gameOver;
    private double scorePl1;
    private double scorePl2;

    public ConnectFourModel() {
        board = new char[ROWS][COLS];
        initializeBoard();
        currentPlayer = '●';
        turnCount = 1;
        gameOver = false;
        scorePl1 = 0;
        scorePl2 = 0;
    }
    /**
     * Resets the board by filling it with empty spaces.
     */
    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = ' ';
            }
        }
    }

    public void updateScore(boolean isDraw, char winnerToken) {
        if (isDraw) {
            scorePl1 += 0.5;
            scorePl2 += 0.5;
        } else {
            if (winnerToken == '●') {
                scorePl1 += 1;
            } else {
                scorePl2 += 1;
            }
        }
    }

    public void restartGame() {
        initializeBoard();
        currentPlayer = getCurrentPlayer() == '●' ? '○' : '●';
        turnCount = 1;
        gameOver = false;
    }

    /**
     * Attempts to drop a token in the specified column
     * @param column 0-based column index
     * @return row where token landed, or -1 if invalid move
     */
    public int dropToken(int column) {
        if (column < 0 || column >= COLS || gameOver) {
            return -1;
        }

        int row = findFreePosition(column);
        if (row == -1) return -1;

        board[row][column] = currentPlayer;
        turnCount++;
        return row;
    }

    private int findFreePosition(int column) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][column] == ' ') {
                return row;
            }
        }
        return -1;
    }

    /**
     * Checks if the last move resulted in a win.
     *
     * @param lastRow The row of the last move.
     * @param lastCol The column of the last move.
     * @return True if the move resulted in a win, false otherwise.
     */
    public boolean checkWin(int lastRow, int lastCol) {
        // Check if last move caused a win
        return checkHorizontal(lastRow, lastCol) ||
               checkVertical(lastRow, lastCol) ||
               checkDiagonalPrimary(lastRow, lastCol) ||
               checkDiagonalSecondary(lastRow, lastCol);
    }

    private boolean checkHorizontal(int row, int col) {
        int count = 1;
        // Check left
        for (int i = col-1; i >= 0 && board[row][i] == currentPlayer; i--) count++;
        // Check right
        for (int i = col+1; i < COLS && board[row][i] == currentPlayer; i++) count++;
        return count >= 4;
    }

    private boolean checkVertical(int row, int col) {
        int count = 1;
        // Check down (only possible direction for Connect Four)
        for (int i = row-1; i >= 0 && board[i][col] == currentPlayer; i--) count++;
        return count >= 4;
    }

    private boolean checkDiagonalPrimary(int row, int col) {
        int count = 1;
        // Check up-left
        for (int i = row-1, j = col-1; i >= 0 && j >= 0 && board[i][j] == currentPlayer; i--, j--) count++;
        // Check down-right
        for (int i = row+1, j = col+1; i < ROWS && j < COLS && board[i][j] == currentPlayer; i++, j++) count++;
        return count >= 4;
    }

    private boolean checkDiagonalSecondary(int row, int col) {
        int count = 1;
        // Check up-right
        for (int i = row-1, j = col+1; i >= 0 && j < COLS && board[i][j] == currentPlayer; i--, j++) count++;
        // Check down-left
        for (int i = row+1, j = col-1; i < ROWS && j >= 0 && board[i][j] == currentPlayer; i++, j--) count++;
        return count >= 4;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == '●') ? '○' : '●';
    }

    public boolean isBoardFull() {
        return turnCount > ROWS * COLS;
    }

    // Getters
    public char getCurrentPlayer() { return currentPlayer; }
    public String getCurrentPlayerName() { return currentPlayer == '●' ? "Player 1" : "Player 2"; }
    public char[][] getBoard() { return board; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    public double getScorePl1() { return scorePl1; }
    public double getScorePl2() { return scorePl2; }
}