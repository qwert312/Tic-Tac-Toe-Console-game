import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class AITest {
    Board3x3 board3x3;
    AI testAI;

    @BeforeEach
    public void initializeAIAndBoard() {
        testAI = new AI("O");
        board3x3 = new Board3x3();
    }

    @Test
    public void makeMoveChangesValuesOnBoardAtCorrectCoordinates() {
        List<Integer> firstCoords = testAI.makeMove(board3x3);
        List<Integer> secondCoords = testAI.makeMove(board3x3);
        List<Integer> thirdCoords = testAI.makeMove(board3x3);

        String[][] boardCopy = board3x3.copyOfBoard();

        assertAll(
                () -> assertNotEquals(" ", boardCopy[firstCoords.get(0)][firstCoords.get(1)]),
                () -> assertNotEquals(" ", boardCopy[secondCoords.get(0)][secondCoords.get(1)]),
                () -> assertNotEquals(" ", boardCopy[thirdCoords.get(0)][thirdCoords.get(1)]));
    }

    @Test
    public void toStringReturnCorrectRepresentation() {
        assertEquals("AI (O)", testAI.toString());
        assertNotEquals("AI (O)", new AI("X").toString());
    }
}
