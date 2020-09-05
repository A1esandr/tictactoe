public class Player {
    public void bet(Game game, int x, int y, int value) throws GameException {
        if (!(value == 0 || value == 1)) {
            throw new GameException("You can bet 0 or 1 only");
        }
        int fieldSize = game.getFieldSize() - 1;
        if(x < 0 || y < 0) {
            throw new GameException("You can't bet on negative fields");
        }
        if(x > fieldSize || y > fieldSize) {
            throw new GameException(String.format("You can bet on fields in game only. Current max field is %s", fieldSize));
        }
        game.bet(x, y, value);
    }

    public void bet(Game game, int x, int y) {

    }
}
