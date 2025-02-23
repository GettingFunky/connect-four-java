package connectfour;

public class ConnectFourModel {
    public static final int ROWS = 6;
    public static final int COLS = 7;
    private char[][] board;
    private char currentPlayer;
    private int turnCount;
    private boolean gameOver;

    public ConnectFourModel() {
        board = new char[ROWS][COLS];
        initializeBoard();
        currentPlayer = '●';
        turnCount = 1;
        gameOver = false;
    }

    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = ' ';
            }
        }
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

    public boolean checkWin(int lastRow, int lastCol) {
        // Check if last move caused a win
        return checkHorizontal(lastRow, lastCol) ||
               checkVertical(lastRow, lastCol) ||
               checkDiagonalDownRight(lastRow, lastCol) ||
               checkDiagonalDownLeft(lastRow, lastCol);
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

    private boolean checkDiagonalDownRight(int row, int col) {
        int count = 1;
        // Check up-left
        for (int i = row-1, j = col-1; i >= 0 && j >= 0 && board[i][j] == currentPlayer; i--, j--) count++;
        // Check down-right
        for (int i = row+1, j = col+1; i < ROWS && j < COLS && board[i][j] == currentPlayer; i++, j++) count++;
        return count >= 4;
    }

    private boolean checkDiagonalDownLeft(int row, int col) {
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
    public char[][] getBoard() { return board; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
}