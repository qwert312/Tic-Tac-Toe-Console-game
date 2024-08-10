import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicTacToeBoard {
    private final CellState[] board = new CellState[9];

    public TicTacToeBoard() {
        resetBoard();
    }

    public void resetBoard() {
        Arrays.fill(board, CellState.EMPTY);
    }

    public CellState[] getBoardState() {
        return board.clone();
    }

    public void setCell(CellState cellState, int row, int column) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (row >= 3 || row < 0 || column >= 3 || column < 0) {
            String errorMessage =
                    String.format("Coordinates %s,%s are out of bounds.", row + 1, column + 1);
            throw new ArrayIndexOutOfBoundsException(errorMessage);
        }

        if (board[row * 3 + column] != CellState.EMPTY) {
            String errorMessage =
                    String.format("The cell at coordinates %s,%s is already occupied!", row + 1, column + 1);
            throw new IllegalArgumentException(errorMessage);
        }

        if (cellState == CellState.EMPTY)
            throw new IllegalArgumentException("You cannot set an empty symbol.");
        else
            board[row * 3 + column] = cellState;
    }

    public boolean isFull() {
        return Arrays.stream(board).noneMatch(cellState -> cellState == CellState.EMPTY);
    }

    public CellState checkWinningState() {
        int rowSize = board.length / 3;

        for (int i = 0; i < board.length; i += rowSize) {
            if (lineHasWinner(board[i], board[i + 1], board[i + 2]))
                return board[i];
        }

        for (int i = 0; i < rowSize; i++) {
            if (lineHasWinner(board[i], board[i + rowSize], board[i + 2 * rowSize]))
                return board[i];
        }

        if (lineHasWinner(board[0], board[board.length / 2], board[board.length - 1]))
            return board[board.length / 2];

        if (lineHasWinner(board[rowSize - 1], board[board.length / 2], board[board.length - rowSize]))
            return board[board.length / 2];

        return CellState.EMPTY;
    }

    private boolean lineHasWinner(CellState a, CellState b, CellState c) {
        return (a != CellState.EMPTY && b != CellState.EMPTY && c != CellState.EMPTY)
                && (a == b) && (b == c);
    }
}
