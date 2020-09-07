import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {
    @Test
    public void WhenGameStarts_EmptyField() {
        Game game = new Game();

        game.init();

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

        game.init();
        player.bet(game, 0, 0, -1);
    }

    @Test(expected = GameException.class)
    public void PlayerCanBetOne() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 0, 0, 2);
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnFieldNotInGameNegativeSpinX() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, -1, 0, 0);
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnFieldNotInGameNegativeSpinY() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 0, -1, 0);
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnFieldNotInGameSpinX() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 3, 0, 0);
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnFieldNotInGameSpinY() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 0, 3, 0);
    }

    @Test
    public void WhenPlayer_BetComputerBetNext() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 0, 0, 0);

        assertEquals(2, game.usedFieldsSize());
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnDifferentTypesOfValue() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 0, 0, 0);
        player.bet(game, 0, 1, 1);
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_BetOnNotFreeField() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 0, 0, 0);
        player.bet(game, 0, 0, 0);
    }

    @Test
    public void WhenThreeHorizontallyFieldsHaveTheSameNonEmptyValues_GameEnds() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 0, 2, 0);
        player.bet(game, 1, 2, 0);
        player.bet(game, 2, 2, 0);

        assertTrue(game.end());
    }

    @Test
    public void WhenThreeVerticallyFieldsHaveTheSameNonEmptyValues_GameEnds() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 0, 0, 0);
        player.bet(game, 0, 1, 0);
        player.bet(game, 0, 2, 0);

        assertTrue(game.end());
    }

    @Test
    public void WhenThreeLeftDiagonalFieldsHaveTheSameNonEmptyValues_GameEnds() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 0, 0, 0);
        player.bet(game, 1, 1, 0);
        player.bet(game, 2, 2, 0);

        assertTrue(game.end());
    }

    @Test
    public void WhenThreeRightDiagonalFieldsHaveTheSameNonEmptyValues_GameEnds() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();

        player.bet(game, 2, 0, 0);
        player.bet(game, 1, 1, 0);
        player.bet(game, 0, 2, 0);

        assertTrue(game.end());
    }

    @Test
    public void WhenGame_EndsByExhaustingAllFields() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 1, 1, 0);
        player.bet(game, 1, 0, 0);
        player.bet(game, 2, 1, 0);
        player.bet(game, 0, 2, 0);

        assertTrue(game.end());
    }

    @Test
    public void WhenPlayer_BetsChangesMustBeVisible() throws GameException {
        Game game = new Game();
        Player player = new Player();

        game.init();
        player.bet(game, 1, 1, 0);

        String currentFieldMap =
                "y\n" +
                "2 _|_|_\n" +
                "1 _|0|_\n" +
                "0 1|_|_\n" +
                "  0|1|2 x\n";

        assertEquals(currentFieldMap, game.getFieldView());
    }

    @Test
    public void WhenGame_MustPrintName() throws GameException {
        Game game = new Game();

        game.init();

        assertEquals("Tic tac toe", game.lastMessage());
    }

    @Test
    public void WhenGame_MustPrintWelcomeMessage() throws GameException {
        Game game = new Game();

        game.welcome();

        assertEquals("Welcome to game!", game.lastMessage());
    }

    @Test
    public void WhenGame_MustPrintSelectMessage() throws GameException {
        Game game = new Game();

        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.init();

        assertEquals(0, game.selectValue());
        assertEquals("Please select type of value for use in game: 0 or 1", game.lastMessage());
    }

    @Test(expected = GameException.class)
    public void WhenPlayer_SelectWrongTypeThrowError() throws GameException {
        Game game = new Game();

        String input = "123abc";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.selectValue();
    }

    @Test
    public void WhenPlayer_SelectWrongTypePrintErrorAndAskForNewSelect() {
        Game game = new Game();

        String input = "123abc" + "\n1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.init();
        game.launchSelectValue();

        assertEquals("Please select type of value for use in game: 0 or 1", game.getMessageHistory().get(1));
        assertEquals("For input string: \"123abc\"", game.getMessageHistory().get(2));
        assertEquals("Please select type of value for use in game: 0 or 1", game.getMessageHistory().get(3));
    }

    @Test
    public void WhenGame_ShowFieldWithCoordinates() throws GameException {
        Game game = new Game();

        game.init();
        game.printField();

        String currentFieldMap =
                "y\n" +
                "2 _|_|_\n" +
                "1 _|_|_\n" +
                "0 _|_|_\n" +
                "  0|1|2 x\n";

        List<String> messageHistory = game.getMessageHistory();
        assertEquals(currentFieldMap, messageHistory.get(1));
    }

    @Test
    public void WhenGame_ShowWelcomeBetMessage() {
        Game game = new Game();

        game.welcomeBet();

        assertEquals("Make a bet, type x y point (for example, 1 1):", game.lastMessage());
    }

    @Test
    public void WhenGame_PlayerUsedOnlySelectedValueOnBet() throws GameException {
        Game game = new Game();
        Player player = new Player();

        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.init();
        game.launchSelectValue();

        player.bet(game,1, 1);

        String currentFieldMap =
                "y\n" +
                "2 _|_|_\n" +
                "1 _|1|_\n" +
                "0 0|_|_\n" +
                "  0|1|2 x\n";

        assertEquals(currentFieldMap, game.getFieldView());
    }

    @Test
    public void WhenPlayer_InputsBet_GameMakesBet() throws GameException {
        Game game = new Game();

        String input = "1" + "\n1 1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.init();
        game.welcome();
        game.launchSelectValue();
        game.printField();
        game.welcomeBet();
        game.launchPlayerBet();

        String currentFieldMap =
                "y\n" +
                "2 _|_|_\n" +
                "1 _|1|_\n" +
                "0 0|_|_\n" +
                "  0|1|2 x\n";

        assertEquals(currentFieldMap, game.getFieldView());
    }

    @Test
    public void WhenPlayer_InputsWrongBet_PrintErrorAndAskAgain() throws GameException {
        Game game = new Game();

        String input = "1" + "\nabc" + "\n1 1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.init();
        game.welcome();
        game.launchSelectValue();
        game.printField();
        game.welcomeBet();
        game.launchPlayerBet();

        String currentFieldMap =
                "y\n" +
                "2 _|_|_\n" +
                "1 _|1|_\n" +
                "0 0|_|_\n" +
                "  0|1|2 x\n";

        List<String> messageHistory = game.getMessageHistory();
        assertEquals("For input string: \"abc\"", messageHistory.get(messageHistory.size()-5));
        assertEquals("Make a bet, type x y point (for example, 1 1):", messageHistory.get(messageHistory.size()-4));
        assertEquals(currentFieldMap, messageHistory.get(messageHistory.size()-1));
        assertEquals(currentFieldMap, game.getFieldView());
    }

    @Test
    public void WhenGame_IsOverAndPlayerIsWinShowMessage() throws GameException {
        Game game = new Game();

        String input = "1" + "\n1 1" + "\n0 2" + "\n2 0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.start();

        assertEquals("You win!", game.lastMessage());
    }

    @Test
    public void WhenGame_IsOverAndPlayerIsLoseShowMessage() throws GameException {
        Game game = new Game();

        String input = "1" + "\n1 1" + "\n0 2" + "\n2 2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.start();

        assertEquals("You lose :(", game.lastMessage());
    }

    @Test
    public void WhenGame_WelcomeUserBet_ShowMessage() throws GameException {
        Game game = new Game();

        String input = "1" + "\n1 1" + "\n0 2" + "\n2 2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.start();

        List<String> messageHistory = game.getMessageHistory();
        assertEquals("Make a bet, type x y point (for example, 1 1):",
                messageHistory.get(messageHistory.size()-5));
        assertEquals("Make a bet, type x y point (for example, 1 1):",
                messageHistory.get(messageHistory.size()-9));
        assertEquals("Make a bet, type x y point (for example, 1 1):",
                messageHistory.get(messageHistory.size()-13));
    }

    @Test
    public void WhenGame_MakesComputerBet_ShowMessage() throws GameException {
        Game game = new Game();

        String input = "1" + "\n1 1" + "\n0 2" + "\n2 2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.start();

        List<String> messageHistory = game.getMessageHistory();
        assertEquals("Computer bets: 2 0", messageHistory.get(messageHistory.size()-3));
    }

    @Test
    public void WhenGame_AllFieldExhausted_ShowMessage() throws GameException {
        Game game = new Game();

        String input = "1" + "\n1 1" + "\n0 1" + "\n2 0" + "\n1 2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.start();

        assertEquals("Game over. It is a draw", game.lastMessage());
    }

    @Test
    public void WhenGame_IsOver_AskForNewGame() throws GameException {
        Game game = new Game();

        String input = "1" + "\n1 1" + "\n0 1" + "\n2 0" + "\n1 2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        game.start();

        assertEquals("Would you like to play again? (yes/no)", game.lastMessage());
    }
}
