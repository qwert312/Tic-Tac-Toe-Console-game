import java.util.List;
import java.util.Scanner;

public class GameManager {
    private final Scanner scan;
    private Player player;
    private AI ai;
    private final Board3x3 board = new Board3x3();

    public GameManager(Scanner scanner) {
        this.scan = scanner;
    }

    public void start() {
        System.out.println("TicTacToe console game");

        typePlayerSymbol();

        System.out.println();

        while (true) {
            System.out.println("Start! Type the coordinates on your turn in this format: row,column\n");

            playGame();

            System.out.print("Next match! (type n to stop) ");
            String answer = scan.nextLine();
            if (answer.equals("n")) break;
            System.out.println();
            board.resetBoard();
        }
    }

    private void typePlayerSymbol() {
        while (true) {
            System.out.print("Choose player symbol (X/O): ");
            String selectedSymbol = scan.nextLine().trim().toUpperCase();
            try {
                if (selectedSymbol.equals("X")) {
                    player = new Player(selectedSymbol);
                    ai = new AI("O");
                    break;
                } else if (selectedSymbol.equals("O")) {
                    player = new Player(selectedSymbol);
                    ai = new AI("X");
                    break;
                }

                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal symbol! Try again");
            }
        }
    }

    private void playGame() {
        while (true) {
            for (int i = 0; i < 2; i++) {
                if ((player.getSymbol().equals("X") && i == 0)
                        || (player.getSymbol().equals("O") && i == 1))
                    playerTurn();
                else
                    aiTurn();

                System.out.println(board.toString() + "\n");
                String winnerSymbol = board.checkWinnerSymbol();
                if (!winnerSymbol.equals(" ")) {
                    String winner = player.getSymbol().equals(winnerSymbol)
                            ? player.toString() : ai.toString();
                    System.out.println("Winner winner chicken dinner. " + winner + " won!");
                    return;
                }

                if (board.isFull()) {
                    System.out.println("Board is full. Draw!");
                    return;
                }
            }
        }
    }

    private void playerTurn() {
        while (true) {
            System.out.println(player + " turn");
            System.out.print("Coordinates: ");

            try {
                String[] coordinates = scan.nextLine().split(",");
                int row = Integer.parseInt(coordinates[0]) - 1;
                int column = Integer.parseInt(coordinates[1]) - 1;
                player.makeMove(board, row, column);
                break;
            } catch (Exception e) {
                System.out.println("Illegal coordinates! Try again");
            }
        }
    }

    private void aiTurn() {
        System.out.println(ai + " turn");
        System.out.print("Coordinates: ");

        List<Integer> coordinates = ai.makeMove(board);

        System.out.println((coordinates.get(0) + 1) + "," + (coordinates.get(1) + 1));
    }
}
