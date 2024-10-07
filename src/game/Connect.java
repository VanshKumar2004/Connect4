package game;

public class Connect {
    // 2D array for board
    private final char[][] board;

    public static final int ROWS = 6;
    public static final int COLUMNS = 7;

    public static final char EMPTY_SLOT = '-';

    public Connect() {
        board = new char[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = EMPTY_SLOT;
            }
        }
    }

    public Connect copy() {
        Connect newBoard = new Connect();
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(this.board[i], 0, newBoard.board[i], 0, COLUMNS);
        }
        return newBoard;
    }

    public void displayBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(" 1  2  3  4  5  6  7 ");
    }

    // Adding disc
    public boolean addSymbol(int column, char symbol) {
        if (column < 1 || column > COLUMNS) {
            System.out.println("Column out of bounds");
            return false;
        }
        column--;
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column] == EMPTY_SLOT) {
                board[i][column] = symbol;
                return true;
            }
        }
        System.out.println("Column is full");
        return false;
    }


    public boolean isBoardFull() {
        for (int j = 0; j < COLUMNS; j++) {
            if (board[0][j] == EMPTY_SLOT) {
                return false;   // At least one slot is available
            }
        }
        return true;   // No top slots available
    }




    public boolean checkWin(char symbol) {
        // Horizontal
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                if (board[row][col] == symbol && board[row][col + 1] == symbol &&
                board[row][col + 2] == symbol && board[row][col + 3] == symbol) {
                    return true;
                }
            }
        }

        // Vertical
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == symbol && board[row + 1][col] == symbol &&
                board[row + 2][col] == symbol && board[row + 3][col] == symbol) {
                    return true;
                }
            }
        }

        // Diagonal , Asc
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                if (board[row][col] == symbol && board[row - 1][col + 1] == symbol &&
                board[row - 2][col + 2] == symbol && board[row - 3][col + 3] == symbol) {
                    return true;
                }
            }
        }

        // Diagonal , Desc
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLUMNS - 3; col++) {
                if (board[row][col] == symbol && board[row + 1][col + 1] == symbol &&
                board[row + 2][col + 2] == symbol && board[row + 3][col + 3] == symbol) {
                    return true;
                }
            }
        }
        return false; // No win condition met
    }

    public boolean isGameOver() {
        return checkWin('Y') || checkWin('R') || isBoardFull();

    }


    public boolean isColumnAvailable(int column) {
        if (column < 1 || column > COLUMNS) {
            return false;
        }
        column--;
        return board[0][column] == EMPTY_SLOT;
    }

    // Remove last disc (Minimax backtracking)
    public void removeSymbol(int column) {
        if (column < 1 || column > COLUMNS) {
            return;
        }
        column--;
        for (int i = 0; i < ROWS; i++) {
            if (board[i][column] != EMPTY_SLOT) {
                board[i][column] = EMPTY_SLOT;
                return;
            }
        }
    }

    public char getCell(int row, int column) {
        return board[row][column];
    }
}