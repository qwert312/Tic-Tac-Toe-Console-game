
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TicTacToeBoardTest {
    TicTacToeBoard testBoard;

    @BeforeEach
    public void initializeObject() {
        testBoard = new TicTacToeBoard();
    }

    @Test
    public void allCellsOfBoardAreEmptyAfterInitialization() {
        assertTrue(isBoardEmpty(testBoard.getBoardState()));
    }

    @Test
    public void allValuesAfterResetAreEmpty() {
        setValues(new String[][]{{"O", "2", "2"}, {"X", "1", "2"}});
        testBoard.resetBoard();

        assertTrue(isBoardEmpty(testBoard.getBoardState()));
    }

    @Test
    public void setValueChangesValuesOnBoardAtCorrectCoordinates() {
        setValues(new String[][]{
                {"X", "0", "2"}, {"X", "2", "2"}, {"O", "2", "0"}});
        CellState[] boardState = testBoard.getBoardState();
        assertAll(
                () -> assertSame(CellState.X, boardState[2]),
                () -> assertSame(CellState.X, boardState[8]),
                () -> assertSame(CellState.O, boardState[6]));
    }

    @Test
    public void setValueThatAlreadyOccupiedThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> setValues(new String[][]{
                {"X", "0", "2"}, {"O", "0", "2"}}));
    }

    @Test
    public void setValueWhereCoordinatesAreOutsideBoardThrowsArrayIndexOutOfBoundsException() {
        assertAll(
                () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> setValues(new String[][]{
                        {"X", "0", "5"}})),
                () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> setValues(new String[][]{
                        {"X", "3", "2"}})),
                () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> setValues(new String[][]{
                        {"O", "4", "3"}})),
                () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> setValues(new String[][]{
                        {"X", "-1", "-1"}})),
                () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> setValues(new String[][]{
                        {"X", "0", "-1"}})),
                () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> setValues(new String[][]{
                        {"O", "-1", "0"}})));
    }

    @Test
    public void isFullReturnFalseIfNotFull() {
        assertFalse(testBoard.isFull());
        setValues(new String[][] {
                {"X", "0", "0"}, {"O", "0", "1"}, {"O", "0", "2"},
                {"O", "1", "0"}, {"O", "2", "0"},
                {"X", "1", "1"}, {"X", "2", "2"}});
        assertFalse(testBoard.isFull());

    }

    @Test void isFullReturnTrueIfFull() {
        setValues(new String[][] {
                {"X", "0", "0"}, {"O", "0", "1"}, {"O", "0", "2"},
                {"O", "1", "0"}, {"O", "1", "1"}, {"X", "1", "2"},
                {"X", "2", "0"}, {"X", "2", "1"}, {"O", "2", "2"}});
        assertTrue(testBoard.isFull());
    }

    @Test
    public void checkWinningStateReturnEmptyStringIfThereIsNoWinner() {
        assertEquals(testBoard.checkWinningState(), CellState.EMPTY);

        checkWinnerAndReset(new String[][]{{"X", "0", "0"}, {"O", "0", "1"}, {"O", "0", "2"}},
                CellState.EMPTY);

        checkWinnerAndReset(new String[][]{{"O", "0", "0"}, {"X", "1", "0"}, {"O", "2", "0"}},
                CellState.EMPTY);

        checkWinnerAndReset(new String[][]{{"X", "0", "0"}, {"O", "1", "1"}, {"X", "2", "2"}},
                CellState.EMPTY);

        checkWinnerAndReset(new String[][]{{"X", "0", "2"}, {"X", "1", "1"}, {"O", "2", "0"}},
                CellState.EMPTY);
    }

    @Test
    public void checkWinnerFindsCorrectWinner() {
        checkWinnerAndReset(new String[][]{{"X", "0", "0"}, {"X", "0", "1"}, {"X", "0", "2"}},
                CellState.X);

        checkWinnerAndReset(new String[][]{{"O", "0", "0"}, {"O", "1", "0"}, {"O", "2", "0"}},
                CellState.O);

        checkWinnerAndReset(new String[][]{{"X", "0", "0"}, {"X", "1", "1"}, {"X", "2", "2"}},
                CellState.X);

        checkWinnerAndReset(new String[][]{{"X", "0", "2"}, {"X", "1", "1"}, {"X", "2", "0"}},
                CellState.X);
    }

    private boolean isBoardEmpty(CellState[] boardState) {
        return Arrays.stream(boardState).allMatch(cell -> cell == CellState.EMPTY);
    }

    private void setValues(String[][] positions) {
        for (String[] position : positions) {
            CellState cellStateToInsert = CellState.getByString(position[0]);
            int row = Integer.parseInt(position[1]);
            int column = Integer.parseInt(position[2]);
            testBoard.setCell(cellStateToInsert, row, column);
        }
    }

    private void checkWinnerAndReset(String[][] positions, CellState exceptedWinningState) {
        setValues(positions);
        assertEquals(testBoard.checkWinningState(), exceptedWinningState);
        testBoard.resetBoard();
    }
}
