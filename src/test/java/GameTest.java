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
    public void WhenGame_HasFieldThreeByThree() {
        Game game = new Game();

        assertEquals(3, game.getField().length);
        assertEquals(3, game.getField()[0].length);
        assertEquals(3, game.getField()[1].length);
        assertEquals(3, game.getField()[2].length);
    }

    @Test(expected = Exception.class)
    public void PlayerCanBetZero() throws Exception {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game.getField(), -1);

        assertEquals(0, game.usedFieldsSize());
    }

    @Test(expected = Exception.class)
    public void PlayerCanBetOne() throws Exception {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game.getField(), 2);

        assertEquals(0, game.usedFieldsSize());
    }

    @Test
    public void PlayerCanBetOnGameField() throws Exception {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game.getField(), 0);

        assertEquals(1, game.usedFieldsSize());
    }
}
