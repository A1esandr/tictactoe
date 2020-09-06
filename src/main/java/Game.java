import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final int FIELD_SIZE = 3;
    int[][] field = new int[FIELD_SIZE][FIELD_SIZE];
    int userChoice = -1, computerChoice = -1;
    boolean gameOver = false;
    boolean playerWin = false;
    String lastMessage = "";
    List<String> messageHistory = new ArrayList<>();
    private String fieldView =
            "y\n" +
            "2 _|_|_\n" +
            "1 _|_|_\n" +
            "0 _|_|_\n" +
            "  0|1|2 x\n";
    Scanner scanner;
    Player player;

    public String lastMessage() {
        return lastMessage;
    }

    public int getUserChoice() {
        return userChoice;
    }

    public void printMessage(String message) {
        messageHistory.add(message);
        lastMessage = message;
        System.out.println(message);
    }

    public void init() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = -1;
            }
        }
        scanner = new Scanner(System.in);
        player = new Player();
        printMessage("Tic tac toe");
    }

    public void start() throws GameException {
        init();
        welcome();
        launchSelectValue();
        printField();
        while (!this.end()) {
            welcomeBet();
            launchPlayerBet();
        }
        checkWin();
    }

    public void checkWin() {
        if (playerWin) {
            printMessage("You win!");
        } else {
            printMessage("You lose :(");
        }
    }

    public void welcome(){
        printMessage("Welcome to game!");
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
        } else {
            computerChoice = 0;
        }
        boolean found = false;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == -1) {
                    found = true;
                    printMessage(String.format("Computer bets: %d %d", j, i));
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
            gameOver = checkLeftDiagonally();
        }
        if (!gameOver){
            gameOver = checkRightDiagonally();
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
            result = checkResult(foundZero, foundOne);
            if (result) {
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
            result = checkResult(foundZero, foundOne);
            if (result) {
                break;
            }
        }
        return result;
    }

    private boolean checkLeftDiagonally(){
        boolean result = false;
        boolean foundZero = true;
        boolean foundOne = true;
        int point = 0;
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
            point++;
        }
        return checkResult(foundZero, foundOne);
    }

    private boolean checkRightDiagonally(){
        boolean foundZero = true;
        boolean foundOne = true;
        int point = 0;
        while(point < FIELD_SIZE && point > -1) {
            if (field[FIELD_SIZE - point - 1][point] == -1) {
                foundZero = false;
                foundOne = false;
                break;
            }
            if (field[FIELD_SIZE - point - 1][point] == 0) {
                foundOne = false;
            }
            if (field[FIELD_SIZE - point - 1][point] == 1) {
                foundZero = false;
            }
            point++;
        }
        return checkResult(foundZero, foundOne);
    }

    private boolean checkResult(boolean foundZero, boolean foundOne) {
        if (foundZero || foundOne) {
            if (foundZero && userChoice == 0 || foundOne && userChoice == 1) {
                playerWin = true;
            }
            return true;
        }
        return false;
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
        sb.append("y\n");
        for (int i = FIELD_SIZE - 1; i > -1; i--) {
            sb.append(i);
            sb.append(" ");
            for (int j = 0; j < FIELD_SIZE; j++) {
                int point = field[i][j];
                if (point != -1) {
                    sb.append(point);
                } else {
                    sb.append("_");
                }
                if (j != FIELD_SIZE - 1) {
                    sb.append("|");
                } else {
                    sb.append("\n");
                }
            }
        }
        sb.append("  0|1|2 x\n");
        fieldView = sb.toString();
        printMessage(fieldView);
    }

    public String getFieldView() {
        return fieldView;
    }

    public void printField() {
        printMessage(fieldView);
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public int selectValue() throws GameException {
        printMessage("Please select type of value for use in game: 0 or 1");
        try {
            userChoice = Integer.parseInt(getInput());
        } catch (Exception e) {
            throw new GameException(e.getMessage());
        }
        if (!(userChoice == 1 || userChoice == 0)) {
            throw new GameException(String.format("Selected wrong type of value: %s", userChoice));
        }
        return userChoice;
    }

    public void launchSelectValue() {
        try {
            selectValue();
        } catch (Exception e) {
            printMessage(e.getMessage());
            launchSelectValue();
        }
    }

    public void launchPlayerBet() throws GameException {
        try {
            String coordinatesInput = getInput();
            String[] coordinates = coordinatesInput.split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            player.bet(this, x, y);
        } catch (Exception e) {
            printMessage(e.getMessage());
            welcomeBet();
            launchPlayerBet();
        }
    }

    public List<String> getMessageHistory() {
        return messageHistory;
    }

    public void welcomeBet() {
        printMessage("Make a bet, type x y point (for example, 1 1):");
    }
}
