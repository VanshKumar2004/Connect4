

import game.Connect;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Connect 4!\n" +
                "Select game mode:\n1. Two Player\n" +
                "2. Single Player vs AI");


        int gameType = scanner.nextInt();

        AiMiniMax aiPlayer = null;
        char player1Symbol = 'R';
        char player2Symbol = 'Y';
        String player1Name = "Player 1";
        String player2Name = "Player 2";
        int currPlayer = 0;

        switch (gameType) {
            case 1:
                // Two Player:
                System.out.println("Starting a game for two players...");
                System.out.print("Enter Player 1 name: ");
                player1Name = scanner.next();
                System.out.print("Choose your symbol (‘R’ or ‘Y’): ");
                player1Symbol = scanner.next().charAt(0);
                System.out.print("Enter Player 2 name: ");
                player2Name = scanner.next();
                player2Symbol = (player1Symbol == 'R') ? 'Y' : 'R';
                System.out.println(player2Name + " will play as " + player2Symbol);
                break;
            case 2:
               // AI mode
                System.out.println("Starting a game against the AI...");
                System.out.print("Enter your name: ");
                player1Name = scanner.next();
                System.out.print("Choose your symbol (‘R’ or ‘Y’): ");
                player1Symbol = scanner.next().charAt(0);
                player2Symbol = (player1Symbol == 'R') ? 'Y' : 'R';
                System.out.print("Who should go first? (player/computer): ");
                String firstTurn = scanner.next();
                if (firstTurn.equalsIgnoreCase("player")) {
                    currPlayer = 1;
                }
                aiPlayer = new AiMiniMax(player2Symbol, player1Symbol);
                break;
            default:
                System.out.println("Invalid selection! Defaulting to two-player mode.");
                break;
        }

        Connect board = new Connect();
        boolean endGame = false;

        while (!endGame) {
            if (gameType == 2 && currPlayer == 0) {    // AI's turn
                int aiMove = aiPlayer.getBestMove(board);
                if (board.addSymbol(aiMove + 1, player2Symbol)) {  // Adjust column index for board
                    System.out.println("AI (Player " + player2Symbol + ") places a disc in column " + (aiMove + 1)); // Adjust column index for user display
                }
                if (board.checkWin(player2Symbol)) {
                    endGame = true;
                    board.displayBoard();
                    System.out.println("AI WINS!");
                } else if (board.isBoardFull()) {
                    endGame = true;
                    board.displayBoard();
                    System.out.println("The game is a draw!!");
                } else {
                    currPlayer = 1;  // Switch to human player
                }
            } else {   // Human player's turn
                board.displayBoard();
                System.out.println("Player " + (currPlayer == 1 ? player1Name : player2Name) + ", choose a column (1-7): ");
                try {
                    int column = scanner.nextInt();
                    if (board.addSymbol(column, currPlayer == 1 ? player1Symbol : player2Symbol)) {
                        System.out.println("Adding " + (currPlayer == 1 ? player1Name : player2Name) + "'s disc to column " + column);
                        if (board.checkWin(currPlayer == 1 ? player1Symbol : player2Symbol)) {
                            endGame = true;
                            board.displayBoard();
                            System.out.println("Player " + (currPlayer == 1 ? player1Name : player2Name) + " WINS!");
                        } else if (board.isBoardFull()) {
                            endGame = true;
                            board.displayBoard();
                            System.out.println("The game is a draw!!");
                        } else {
                            currPlayer = (gameType == 1) ? (currPlayer == 1 ? 2 : 1) : 0;
                        }
                    } else {
                        System.out.println("Column is full or invalid! Please choose another one");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number between 1 and 7");
                    scanner.next();
                }
            }
        }
        scanner.close();
    }
}
