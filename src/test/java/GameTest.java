import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test(expected = GameException.class)
    public void PlayerCanBetZero() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, 0, 0, -1);
    }

    @Test(expected = GameException.class)
    public void PlayerCanBetOne() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, 0, 0, 2);
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnFieldNotInGameNegativeSpinX() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, -1, 0, 0);
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnFieldNotInGameNegativeSpinY() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, 0, -1, 0);
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnFieldNotInGameSpinX() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, 3, 0, 0);
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnFieldNotInGameSpinY() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, 0, 3, 0);
    }

    @Test
    public void WhenPlayer_BetComputerBetNext() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, 0, 0, 0);

        assertEquals(2, game.usedFieldsSize());
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnDifferentTypesOfValue() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, 0, 0, 0);
        player.bet(game, 0, 1, 1);
    }

    @Test
    public void WhenThreeHorizontallyFieldsHaveTheSameNonEmptyValues_GameEnds() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, 0, 2, 0);
        player.bet(game, 1, 2, 0);
        player.bet(game, 2, 2, 0);

        assertTrue(game.end());
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnNotFreeField() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.start();
        player.bet(game, 0, 0, 0);
        player.bet(game, 0, 0, 0);
    }
}
