import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
    @Test
    public void WhenGameStarts_EmptyField() {
        Game game = new Game();

        game.start();

        assertEquals(0, game.usedFieldsSize());
    }

    private class Game {
        public void start() {
        }

        public int usedFieldsSize() {
            return 1;
        }
    }
}
