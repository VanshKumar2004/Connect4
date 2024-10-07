import game.Connect;

public class AiMiniMax {
    private final char aiSymbol;
    private final char humanSymbol;
    private static final int MAX_DEPTH = 4;
    private static final int WIN_SCORE = 10000;
    private static final int THREE_IN_A_ROW_SCORE = 400;
    private static final int TWO_IN_A_ROW_SCORE = 10;

    public AiMiniMax(char aiSymbol, char humanSymbol) {
        this.aiSymbol = aiSymbol;
        this.humanSymbol = humanSymbol;
    }

    public int getBestMove(Connect board) {
        System.out.println("\nAI is thinking...");
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;
        Connect boardCopy = board.copy();
        for (int col = 0; col < Connect.COLUMNS; col++) {
            if (boardCopy.isColumnAvailable(col + 1)) {
                boardCopy.addSymbol(col + 1, aiSymbol);
                int score = alphaBeta(boardCopy, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                boardCopy.removeSymbol(col + 1);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }

        // If bestMove is still -1 here, it means all columns are full and no valid move can be made
        return bestMove;
    }

    private int alphaBeta(Connect board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || board.isGameOver()) {
            return scorePosition(board);
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < Connect.COLUMNS; col++) {
                if (board.isColumnAvailable(col + 1)) {
                    board.addSymbol(col + 1, aiSymbol);
                    int eval = alphaBeta(board, depth - 1, alpha, beta, false);
                    board.removeSymbol(col + 1);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < Connect.COLUMNS; col++) {
                if (board.isColumnAvailable(col + 1)) {
                    board.addSymbol(col + 1, humanSymbol);
                    int eval = alphaBeta(board, depth - 1, alpha, beta, true);
                    board.removeSymbol(col + 1);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) break;
                }
            }
            return minEval;
        }
    }

    public static int evaluateBoardForPlayer(Connect board, char player) {
        int score = 0;

        // Check for 4 in a row, 3 in a row, and 2 in a row.
        score += checkLines(board, player, 4) * WIN_SCORE;
        score += checkLines(board, player, 3) * THREE_IN_A_ROW_SCORE;
        score += checkLines(board, player, 2) * TWO_IN_A_ROW_SCORE;

        return score;
    }

    private static int checkLines(Connect board, char player, int length) {
        int count = 0;

        for (int row = 0; row < Connect.ROWS; row++) {
            for (int col = 0; col < Connect.COLUMNS; col++) {
                if (col + length <= Connect.COLUMNS) { // Check horizontal
                    boolean match = true;
                    for (int i = 0; i < length; i++) {
                        if (board.getCell(row, col + i) != player) {
                            match = false;
                            break;
                        }
                    }
                    if (match) count++;
                }
                if (row + length <= Connect.ROWS) { // Check vertical
                    boolean match = true;
                    for (int i = 0; i < length; i++) {
                        if (board.getCell(row + i, col) != player) {
                            match = false;
                            break;
                        }
                    }
                    if (match) count++;
                }
                if (row + length <= Connect.ROWS && col + length <= Connect.COLUMNS) { // Check diagonal (\)
                    boolean match = true;
                    for (int i = 0; i < length; i++) {
                        if (board.getCell(row + i, col + i) != player) {
                            match = false;
                            break;
                        }
                    }
                    if (match) count++;
                }
                if (row + length <= Connect.ROWS && col - length + 1 >= 0) { // Check anti-diagonal (/)
                    boolean match = true;
                    for (int i = 0; i < length; i++) {
                        if (board.getCell(row + i, col - i) != player) {
                            match = false;
                            break;
                        }
                    }
                    if (match) count++;
                }
            }
        }

        return count;
    }

    private int scorePosition(Connect board) {
        int playerScore = evaluateBoardForPlayer(board, aiSymbol);
        int opponentScore = evaluateBoardForPlayer(board, humanSymbol);
        return playerScore - opponentScore;
    }
}
