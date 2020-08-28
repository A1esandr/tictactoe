import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
    @Test
    public void WhenGameStarts_EmptyField() {
        Game game = new Game();

        game.start();

        assertEquals(0, game.usedFieldsSize());
    }

    @Test
    public void PlayerCanBetZero() {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(0);

        assertEquals(1, game.usedFieldsSize());
    }

    @Test
    public void PlayerCanBetOne() {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(1);

        assertEquals(1, game.usedFieldsSize());
    }
}
