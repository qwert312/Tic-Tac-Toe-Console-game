import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player testPlayer;
    private Board3x3 testBoard;

    @BeforeEach
    public void initializePlayerAndBoard() {
        testPlayer = new Player("X");
        testBoard = new Board3x3();
    }

    @Test
    public void makeMoveWithCoordinatesWhereValueIsAlreadyOccupiedThrowsIllegalArgumentException() {
        testPlayer.makeMove(testBoard, 2, 1);
        assertThrows(IllegalArgumentException.class,
                () -> testPlayer.makeMove(testBoard, 2, 3));
    }

    @Test
    public void makeMoveWithCoordinatesAreOutsideBoardThrowsIllegalArgumentException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> testPlayer.makeMove(testBoard, 3, 3)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> testPlayer.makeMove(testBoard, 3, 2)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> testPlayer.makeMove(testBoard, 2, 3)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> testPlayer.makeMove(testBoard, -1, -1)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> testPlayer.makeMove(testBoard, -1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> testPlayer.makeMove(testBoard, 0, -1)));
    }

    @Test
    public void makeMoveChangesValuesOnBoardAtCorrectCoordinates() {
        testPlayer.makeMove(testBoard, 1, 2);
        testPlayer.makeMove(testBoard, 2, 2);
        Player secondTestPlayer = new Player( "O");
        secondTestPlayer.makeMove(testBoard, 0, 1);

        assertAll(
                () -> assertEquals("X", testBoard.copyOfBoard()[1][2]),
                () -> assertEquals("X", testBoard.copyOfBoard()[2][2]),
                () -> assertEquals("O", testBoard.copyOfBoard()[0][1]));
    }

    @Test
    public void toStringReturnCorrectRepresentation() {
        assertEquals("Player (X)", testPlayer.toString());
        assertNotEquals("Player (X)", new Player("O").toString());
    }
}
