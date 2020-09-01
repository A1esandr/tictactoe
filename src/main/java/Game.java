public class Game {
    private static final int FIELD_SIZE = 3;
    int[][] field = new int[FIELD_SIZE][FIELD_SIZE];
    int userChoice = -1, computerChoice = -1;
    boolean gameOver = false;

    public void start() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = -1;
            }
        }
    }

    public int usedFieldsSize() {
        int usedFields = 0;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == 0 || field[i][j] == 1) {
                    usedFields++;
                }
            }
        }
        return usedFields;
    }

    public int[][] getField() {
        return this.field;
    }

    public int getFieldSize() {
        return FIELD_SIZE;
    }

    public void computerBet() {
        if (userChoice == 0) {
            computerChoice = 1;
        }
        boolean found = false;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == -1) {
                    found = true;
                    field[i][j] = computerChoice;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
    }

    public void bet(int x, int y, int value) throws GameException {
        if (userChoice != -1 && userChoice != value) {
            throw new GameException(String.format("You can`t bet different type of value. Your previous choice is %d", userChoice));
        }
        userChoice = value;
        if (getField()[y][x] != -1) {
            throw new GameException("This field is not empty!");
        }
        getField()[y][x] = value;
        computerBet();
    }

    public boolean end() {
        boolean foundZero = false, foundOne = false;
        // Find by horizontally
        for (int i = 0; i < FIELD_SIZE; i++) {
            foundZero = true;
            foundOne = true;
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == -1) {
                    foundZero = false;
                    foundOne = false;
                    break;
                }
                if (field[i][j] == 0) {
                    foundOne = false;
                }
                if (field[i][j] == 1) {
                    foundZero = false;
                }
            }
            if (foundZero || foundOne) {
                gameOver = true;
                break;
            }
        }
        // Find by vertically
        for (int i = 0; i < FIELD_SIZE; i++) {
            if (gameOver) {
                break;
            }
            foundZero = true;
            foundOne = true;
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[j][i] == -1) {
                    foundZero = false;
                    foundOne = false;
                    break;
                }
                if (field[j][i] == 0) {
                    foundOne = false;
                }
                if (field[j][i] == 1) {
                    foundZero = false;
                }
            }
            if (foundZero || foundOne) {
                gameOver = true;
                break;
            }
        }
        // Find by diagonally
        if (!gameOver){
            gameOver = checkDiagonally(0, true);
        }
        if (!gameOver){
            gameOver = checkDiagonally(FIELD_SIZE - 1, false);
        }

        return gameOver;
    }

    private boolean checkDiagonally(int point, boolean forwardOrder){
        boolean result = false;
        boolean foundZero = true;
        boolean foundOne = true;
        while(point < FIELD_SIZE && point > -1) {
            if (field[point][point] == -1) {
                foundZero = false;
                foundOne = false;
                break;
            }
            if (field[point][point] == 0) {
                foundOne = false;
            }
            if (field[point][point] == 1) {
                foundZero = false;
            }
            if(forwardOrder){
                point++;
            } else {
                point--;
            }
        }
        if (foundZero || foundOne) {
            result = true;
        }
        return result;
    }
}
