import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameManager {
    private final Scanner scan;
    private final TicTacToeBoard board = new TicTacToeBoard();
    private Player human;
    private Player ai;
    private boolean isHumanTurn;

    public GameManager(Scanner scanner) {
        this.scan = scanner;
    }

    public void run() {
        System.out.println("TicTacToe console game with bot");

        while (true) {
            askHumanForSymbol();

            System.out.println("\nStart! Type coordinates on your turn in this format: row,column\n");

            playGame();

            System.out.print("Next match! (type n to stop) ");
            String answer = scan.nextLine();
            if (answer.equals("n")) break;
            System.out.println();
            board.resetBoard();
        }
    }

    private void askHumanForSymbol() {
        while (true) {
            System.out.print("Choose your symbol (X/O): ");
            String selectedSymbol = scan.nextLine().trim().toUpperCase();

            if (selectedSymbol.equals("X")) {
                human = new Player("Human", selectedSymbol);
                ai = new Player("AI", "O");
                isHumanTurn = true;
                break;
            } else if (selectedSymbol.equals("O")) {
                human = new Player("Human", selectedSymbol);
                ai = new Player("AI", "X");
                isHumanTurn = false;
                break;
            }

            System.out.println("Illegal symbol! Try again");
        }
    }

    private void playGame() {
        while (true) {
            for (int i = 0; i < 2; i++) {
                if (isHumanTurn)
                    humanTurn();
                else
                    aiTurn();

                isHumanTurn = !isHumanTurn;

                printBoard();

                CellState winningState = board.checkWinningState();
                if (winningState != CellState.EMPTY) {
                    String winner = human.getSymbol().equals(winningState.toString())
                            ? human.toString() : ai.toString();
                    System.out.println("\nWinner winner chicken dinner. " + winner + " won!");
                    return;
                }

                if (board.isFull()) {
                    System.out.println("\nBoard is full. Draw!");
                    return;
                }
            }
        }
    }

    private void humanTurn() {
        while (true) {
            System.out.println(human + " turn");
            System.out.print("Coordinates: ");

            try {
                String[] coordinates = scan.nextLine().split(",");
                int row = Integer.parseInt(coordinates[0]) - 1;
                int column = Integer.parseInt(coordinates[1]) - 1;
                CellState cellStateToInsert = CellState.getByString(human.getSymbol());

                board.setCell(cellStateToInsert, row, column);
                break;
            } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println("\n" + e.getMessage() + "\n");
            }
        }
    }

    private void aiTurn() {
        System.out.println(ai + " turn");
        System.out.print("Coordinates: ");

        List<Integer> coordinates = getRandomEmptyCoordinates();
        int row = (coordinates.get(0));
        int column = (coordinates.get(1));
        CellState cellStateToInsert = CellState.getByString(ai.getSymbol());

        board.setCell(cellStateToInsert, row, column);

        System.out.println((row + 1) + "," + (column + 1));
    }

    private List<Integer> getRandomEmptyCoordinates() {
        Random rand = new Random();
        List<List<Integer>> possibleCoordinates = new ArrayList<>();
        CellState[] boardState = board.getBoardState();
        int rowSize = boardState.length / 3;

        for (int i = 0; i < boardState.length; i++) {
            if (boardState[i] == CellState.EMPTY) {
                possibleCoordinates.add(List.of(i / rowSize, i % rowSize));
            }
        }

        return possibleCoordinates.get(rand.nextInt(0, possibleCoordinates.size()));
    }

    private void printBoard() {
        CellState[] boardState = board.getBoardState();
        int rowSize = boardState.length / 3;

        for (int i = 0; i < boardState.length; i++) {
            System.out.print(boardState[i]);
            if ((i + 1) % rowSize != 0)
                System.out.print("|");
            else if (i != boardState.length - 1)
                System.out.println("\n-+-+-");
        }
        System.out.println();
    }
}
