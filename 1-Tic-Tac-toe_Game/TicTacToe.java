import java.util.*;

public class TicTacToe {

    static String[] board;
    static String turn;

    // Check winner or draw
    static String checkWinner() {

        for(int i=0; i<8; i++){
            String line = null;

            switch(i){
                case 0:
                    line = board[0]+ board[1]+ board[2];
                break;
                case 1:
                    line = board[3]+ board[4]+ board[5];
                break;
                case 2:
                    line = board[6]+ board[7]+ board[8];
                break;
                case 3:
                    line = board[0]+ board[3]+ board[6];
                break;
                case 4:
                    line = board[1]+ board[4]+ board[7];
                break;
                case 5:
                    line = board[2]+ board[5]+ board[8];
                break;
                case 6:
                    line = board[0]+ board[4]+ board[8];
                break;
                case 7:
                    line = board[2]+ board[4]+ board[6];
                break;
            }

            if(line.equals("OOO")) return "O";
            if(line.equals("XXX")) return "X";
        }

        // for x winner
        // Check for draw
        boolean emptySlot = false;
        for (String s : board) {
            if (s.matches("[1-9]")) { // still a number -> empty
                emptySlot = true;
                break;
            }
        }
        if (!emptySlot) return "draw";

        return null; // Game continues
    }

    // Print the board using printf 
    static void printBoard() {
        int width = 3; // Width of each cell 
        System.out.printf("|-----|-----|-----|%n");
        System.out.printf("| %"+width+"s | %"+width+"s | %"+width+"s |%n", board[0], board[1], board[2]);
        System.out.printf("|-----|-----|-----|%n");
        System.out.printf("| %"+width+"s | %"+width+"s | %"+width+"s |%n", board[3], board[4], board[5]);
        System.out.printf("|-----|-----|-----|%n");
        System.out.printf("| %"+width+"s | %"+width+"s | %"+width+"s |%n", board[6], board[7], board[8]);
        System.out.printf("|-----|-----|-----|%n");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        board = new String[9];
        turn = "X";

        String winner = null;

        // Initialize board
        for (int i = 0; i < 9; i++) {
            board[i] = String.valueOf(i + 1);
        }

        System.out.println("-- Welcome to 3x3 Tic Tac Toe --");
        printBoard();
        System.out.println("X will play first.");

        // Game loop
        while (winner == null) {
            System.out.println(turn + "'s turn; enter a slot number to place " + turn + " in:");
            int numInput;

            try {
                numInput = in.nextInt();

                // Check range
                if (!(numInput > 0 && numInput <= 9)) {
                    System.out.println("Invalid input; re-enter slot number:");
                    continue;
                }

                // Check if slot is available
                if (board[numInput - 1].equals(String.valueOf(numInput))) {
                    board[numInput - 1] = turn;

                    printBoard();

                    winner = checkWinner();

                    // Toggle turn if game continues
                    if (winner == null) {
                        turn = turn.equals("X") ? "O" : "X";
                    }

                } else {
                    System.out.println("Slot already taken; re-enter slot number:");
                }

            } catch (Exception e) {
                System.out.println("Invalid input; re-enter slot number:");
                in.nextLine(); // clear the invalid input
            }
        }

        // Final result
        if (winner.equalsIgnoreCase("draw")) {
            System.out.println("It's a draw! Thanks for playing.");
        } else {
            System.out.println("Congratulations! " + winner + " has won! Thanks for playing.");
        }

        in.close();
    }
}
