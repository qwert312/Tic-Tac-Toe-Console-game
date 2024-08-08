
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class Board3x3Test {
    Board3x3 testBoard;

    @BeforeEach
    public void initializeObject() {
        testBoard = new Board3x3();
    }

    @Test
    public void boardEmptyAfterInitialization() {
        assertTrue(isBoardEmpty(testBoard.copyOfBoard()));
    }

    @Test
    public void boardCopyChangesDoesntChangeOriginalBoard() {
        String[][] copyOfBoard = testBoard.copyOfBoard();
        assertArrayEquals(copyOfBoard, testBoard.copyOfBoard());
        copyOfBoard[2][1] = "AbraKadabra";
        assertFalse(Arrays.deepEquals(copyOfBoard, testBoard.copyOfBoard()));
    }

    @Test
    public void boardHasDimension3x3() {
        assertEquals(9,
                testBoard.copyOfBoard().length * testBoard.copyOfBoard().length);
    }

    @Test
    public void allValuesAfterResetAreEmpty() {
        setValues(new String[][]{{"O", "2", "2"}, {"X", "1", "2"}});
        testBoard.resetBoard();

        assertTrue(isBoardEmpty(testBoard.copyOfBoard()));
    }

    @Test
    public void setValueChangesValuesOnBoardAtCorrectCoordinates() {
        setValues(new String[][]{
                {"X", "0", "2"}, {"X", "2", "2"}, {"O", "2", "0"}});
        assertAll(
                () -> assertEquals("X", testBoard.copyOfBoard()[0][2]),
                () -> assertEquals("X", testBoard.copyOfBoard()[2][2]),
                () -> assertEquals("O", testBoard.copyOfBoard()[2][0]));
    }

    @Test
    public void setValueThatAlreadyOccupiedThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> setValues(new String[][]{
                {"X", "0", "2"}, {"O", "0", "2"}}));
    }

    @Test
    public void setValueWhereCoordinatesAreOutsideBoardThrowsIllegalArgumentException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> setValues(new String[][]{
                        {"X", "0", "5"}})),
                () -> assertThrows(IllegalArgumentException.class, () -> setValues(new String[][]{
                        {"X", "3", "2"}})),
                () -> assertThrows(IllegalArgumentException.class, () -> setValues(new String[][]{
                        {"O", "4", "3"}})),
                () -> assertThrows(IllegalArgumentException.class, () -> setValues(new String[][]{
                        {"X", "-1", "-1"}})),
                () -> assertThrows(IllegalArgumentException.class, () -> setValues(new String[][]{
                        {"X", "0", "-1"}})),
                () -> assertThrows(IllegalArgumentException.class, () -> setValues(new String[][]{
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
    public void checkWinnerReturnEmptyStringIfThereIsNoWinner() {
        assertEquals(testBoard.checkWinnerSymbol(), " ");

        checkWinnerAndReset(new String[][]{{"X", "0", "0"}, {"O", "0", "1"}, {"O", "0", "2"}},
                " ");

        checkWinnerAndReset(new String[][]{{"O", "0", "0"}, {"X", "1", "0"}, {"O", "2", "0"}},
                " ");

        checkWinnerAndReset(new String[][]{{"X", "0", "0"}, {"O", "1", "1"}, {"X", "2", "2"}},
                " ");

        checkWinnerAndReset(new String[][]{{"X", "0", "2"}, {"X", "1", "1"}, {"O", "2", "0"}},
                " ");
    }

    @Test
    public void checkWinnerFindsCorrectWinner() {
        checkWinnerAndReset(new String[][]{{"X", "0", "0"}, {"X", "0", "1"}, {"X", "0", "2"}},
                "X");

        checkWinnerAndReset(new String[][]{{"O", "0", "0"}, {"O", "1", "0"}, {"O", "2", "0"}},
                "O");

        checkWinnerAndReset(new String[][]{{"X", "0", "0"}, {"X", "1", "1"}, {"X", "2", "2"}},
                "X");

        checkWinnerAndReset(new String[][]{{"X", "0", "2"}, {"X", "1", "1"}, {"X", "2", "0"}},
                "X");
    }

    @Test
    public void toStringReturnCorrectRepresentationOfBoardWhetItsEmpty() {
        assertEquals(testBoard.toString(),
                " | | \n"
                    + "-+-+-\n"
                    + " | | \n"
                    + "-+-+-\n"
                    + " | | ");
    }

    @Test
    public void toStringReturnCorrectRepresentationOfBoardWhenItsNotEmpty() {
        setValues(new String[][] {
                {"X", "0", "0"}, {"O", "0", "1"}, {"O", "0", "2"},
                {"O", "1", "0"}, {"O", "1", "1"}, {"X", "1", "2"},
                {"X", "2", "0"}, {"X", "2", "1"}, {"O", "2", "2"}});
        assertEquals(testBoard.toString(),
                    "X|O|O\n"
                        + "-+-+-\n"
                        + "O|O|X\n"
                        + "-+-+-\n"
                        + "X|X|O");
        assertNotEquals(testBoard.toString(),
                   "X|O|O\n"
                        + "-+-+-\n"
                        + "O|O|X\n"
                        + "-+-+-\n"
                        + "X|X|X");
    }

    private void setValues(String[][] positions) {
        Arrays.stream(positions).forEach(position -> testBoard.setSymbol
                (position[0], Integer.parseInt(position[1]), Integer.parseInt(position[2])));
    }

    private void checkWinnerAndReset(String[][] positions, String exceptedWinner) {
        setValues(positions);
        assertEquals(testBoard.checkWinnerSymbol(), exceptedWinner);
        testBoard.resetBoard();
    }

    private boolean isBoardEmpty(String[][] board) {
        return Arrays.stream(board)
                .allMatch(row -> Arrays.stream(row).allMatch(" "::equals));
    }
}
