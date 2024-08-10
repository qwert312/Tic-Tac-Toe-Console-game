import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    public void initializePlayerAndBoard() {
        testPlayer = new Player("Test player", "X");
    }

    @Test
    public void toStringReturnCorrectRepresentation() {
        String testString = "Test player (X)";
        assertEquals(testString, testPlayer.toString());
        assertNotEquals(testString, new Player("Another player", "O").toString());
    }
}
