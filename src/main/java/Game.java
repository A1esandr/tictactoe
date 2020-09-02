public class Game {
    private static final int FIELD_SIZE = 3;
    int[][] field = new int[FIELD_SIZE][FIELD_SIZE];
    int userChoice = -1, computerChoice = -1;
    boolean gameOver = false;
    String lastMessage = "Tic tac toe";
    private String fieldView =
            "_|_|_\n" +
            "_|_|_\n" +
            " | | \n";

    public String lastMessage() {
        return lastMessage;
    }

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
                    updateField(j, i, computerChoice);
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
        updateField(x, y, value);
        computerBet();
    }

    public boolean end() {
        // Find by horizontally
        gameOver = checkHorizontally();
        // Find by vertically
        if (!gameOver) {
            gameOver = checkVertically();
        }
        // Find by diagonally
        if (!gameOver){
            gameOver = checkDiagonally(0, true);
        }
        if (!gameOver){
            gameOver = checkDiagonally(FIELD_SIZE - 1, false);
        }
        if (!gameOver) {
            gameOver = checkExhaustingAllFields();
        }
        return gameOver;
    }

    private boolean checkHorizontally() {
        boolean result = false;
        boolean foundZero;
        boolean foundOne;
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
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean checkVertically() {
        boolean result = false;
        boolean foundZero;
        boolean foundOne;
        for (int i = 0; i < FIELD_SIZE; i++) {
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
                result = true;
                break;
            }
        }
        return result;
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

    private boolean checkExhaustingAllFields() {
        boolean verticalFieldExhausted = false;
        boolean horizontalFieldExhausted = false;
        boolean diagonalLeftExhausted = false;
        boolean diagonalRightExhausted = false;
        boolean foundZero;
        boolean foundOne;
        for (int i = 0; i < FIELD_SIZE; i++) {
            foundZero = false;
            foundOne = false;
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == 0) {
                    foundZero = true;
                }
                if (field[i][j] == 1) {
                    foundOne = true;
                }
            }
            if (foundZero && foundOne) {
                horizontalFieldExhausted = true;
            } else {
                horizontalFieldExhausted = false;
            }
        }
        for (int i = 0; i < FIELD_SIZE; i++) {
            foundZero = false;
            foundOne = false;
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[j][i] == 0) {
                    foundZero = true;
                }
                if (field[j][i] == 1) {
                    foundOne = true;
                }
            }
            if (foundZero && foundOne) {
                verticalFieldExhausted = true;
            } else {
                verticalFieldExhausted = false;
            }
        }
        foundZero = false;
        foundOne = false;
        for (int i = 0; i < FIELD_SIZE; i++) {
            if (field[i][i] == 0) {
                foundZero = true;
            }
            if (field[i][i] == 1) {
                foundOne = true;
            }
            if (foundZero && foundOne) {
                diagonalLeftExhausted = true;
            }
        }
        foundZero = false;
        foundOne = false;
        for (int i = 0; i < FIELD_SIZE; i++) {
            if (field[FIELD_SIZE - i - 1][i] == 0) {
                foundZero = true;
            }
            if (field[FIELD_SIZE - i - 1][i] == 1) {
                foundOne = true;
            }
            if (foundZero && foundOne) {
                diagonalRightExhausted = true;
            }
        }
        return horizontalFieldExhausted && verticalFieldExhausted && diagonalLeftExhausted && diagonalRightExhausted;
    }

    private void updateField(int x, int y, int value){
        field[y][x] = value;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                int point = field[i][j];
                if (point != -1) {
                    sb.append(point);
                } else if(i != FIELD_SIZE - 1) {
                    sb.append("_");
                } else {
                    sb.append(" ");
                }
                if (j != FIELD_SIZE - 1) {
                    sb.append("|");
                } else {
                    sb.append("\n");
                }
            }
        }
        fieldView = sb.toString();
        System.out.print(fieldView);
    }

    public String printField() {
        return fieldView;
    }
}
