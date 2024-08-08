import java.util.Arrays;
import java.util.stream.IntStream;

public class Board3x3 {
    private String[][] board = new String[3][3];

    public Board3x3() {
        resetBoard();
    }

    public String[][] copyOfBoard() {
        return Arrays.stream(board).map(String[]::clone).toArray(String[][]::new);
    }

    public void resetBoard() {
        board = Arrays.stream(board)
                .map(row -> row = new String[]{" ", " ", " "})
                .toArray(String[][]::new);
    }

    public void setSymbol(String symbol, int row, int col) throws IllegalArgumentException {
        if ((row >= board.length || row < 0 || col >= board.length || col < 0)
                || !board[row][col].equals(" "))
            throw new IllegalArgumentException();

        board[row][col] = symbol;
    }

    public boolean isFull() {
        return Arrays.stream(board).allMatch(row -> Arrays.stream(row).noneMatch(" "::equals));
    }

    public String checkWinnerSymbol() {
        String checkRowsResult = checkRows();
        if (!checkRowsResult.equals(" "))
            return checkRowsResult;

        String checkColumnsResult = checkColumns();
        if (!checkColumnsResult.equals(" "))
            return checkColumnsResult;

        String checkDiagonalResult = checkDiagonals();
        if (!checkDiagonalResult.equals(" "))
            return checkDiagonalResult;

        return " ";
    }

    private String checkRows() {
        return Arrays.stream(board)
                .map(row -> checkLine(Arrays.stream(row).toArray(String[]::new)))
                .filter(winner -> !winner.equals(" ")).findFirst().orElse(" ");
    }

    private String checkColumns() {
        return IntStream
                .range(0, board.length)
                .mapToObj(column ->
                        checkLine(Arrays.stream(board)
                                .map(row -> row[column])
                                .toArray(String[]::new)))
                .filter(winner -> !winner.equals(" ")).findFirst().orElse(" ");
    }

    private String checkDiagonals() {
        return IntStream
                .range(0, 2)
                .mapToObj(diagonal ->
                        checkLine(IntStream.range(0, board.length).mapToObj((ind) -> {
                                    if (diagonal == 0)
                                        return board[ind][ind];
                                    else
                                        return board[ind][board.length - 1 - ind];
                                })
                                .toArray(String[]::new)))
                .filter(winner -> !winner.equals(" ")).findFirst().orElse(" ");
    }

    private String checkLine(String[] line) {
        long xCount = Arrays.stream(line).filter("X"::equals).count();
        long oCount = Arrays.stream(line).filter("O"::equals).count();

        if (xCount == 3) return "X";
        if (oCount == 3) return "O";

        return " ";
    }

    public String toString() {
        StringBuilder stringBoard = new StringBuilder();

        for (int row = 0; row < board.length; row++) {
            stringBoard.append(String.join("|", board[row]));
            if (row != board.length - 1)
                stringBoard.append("\n-+-+-\n");
        }

        return stringBoard.toString();
    }
}
