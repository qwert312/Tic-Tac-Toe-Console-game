import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellStateTest {

    @Test
    public void findsStateMethodReturnCorrectState() {
        CellState xState = CellState.getByString("X");
        CellState xState2 = CellState.getByString(" x ");
        CellState oState = CellState.getByString("O");
        CellState oState2 = CellState.getByString(" o ");
        CellState emptyState = CellState.getByString(" ");

        assertAll(
                () -> assertEquals(CellState.X, xState),
                () -> assertEquals(CellState.X, xState2),
                () -> assertEquals(CellState.O, oState),
                () -> assertEquals(CellState.O, oState2),
                () -> assertEquals(CellState.EMPTY, emptyState));
    }

    @Test
    public void findsStateMethodThrowsIllegalArgumentExceptionIfStateWasntFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            CellState.getByString("A");
        });
    }

    @Test
    public void findsStateMethodReturnsCorrectMessageWhenExceptionIsThrown() {
        String incorrectRepresentation = "A";
        String exceptionMessage = "";

        try {
            CellState.getByString(incorrectRepresentation);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }

        assertEquals("There is no cell state with such representation here: " + incorrectRepresentation,
                exceptionMessage);
    }
}
