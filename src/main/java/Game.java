import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final int FIELD_SIZE = 3;
    int[][] field = new int[FIELD_SIZE][FIELD_SIZE];
    int userChoice = -1, computerChoice = -1;
    boolean gameOver = false;
    boolean playerWin = false;
    boolean computerWin = false;
    boolean computerBetLast = false;
    boolean firstPlay = false;
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
        if (!gameOver) {
            scanner = new Scanner(System.in);
            player = new Player();
            printMessage("Tic tac toe");
        }
    }

    public void reset() {
        gameOver = false;
        playerWin = false;
        computerWin = false;
        firstPlay = !firstPlay;
        computerBetLast = false;
    }

    public void start() {
        init();
        if (!gameOver) {
            welcome();
            launchSelectValue();
            printField();
        } else {
            updateField(0, 0, -1);
        }
        reset();
        while (!this.end()) {
            launchPlayerBet();
        }
        checkWin();
        launchGetAnswerTryAgain(3);
    }

    public void checkWin() {
        if (playerWin) {
            printMessage("You win!");
        } else if(computerWin) {
            printMessage("You lose :(");
        } else {
            printMessage("Game over. It is a draw");
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
        Point point = checkPlayerWinRow(userChoice);
        if (point != null) {
            makeBetByComputer(point.getX(), point.getY());
            return;
        }
        point = checkPlayerWinColumn(userChoice);
        if (point != null) {
            makeBetByComputer(point.getX(), point.getY());
            return;
        }
        point = checkPlayerWinDiagonal(userChoice);
        if (point != null) {
            makeBetByComputer(point.getX(), point.getY());
            return;
        }
        point = checkPlayerWinRow(computerChoice);
        if (point != null) {
            makeBetByComputer(point.getX(), point.getY());
            return;
        }
        point = checkPlayerWinColumn(computerChoice);
        if (point != null) {
            makeBetByComputer(point.getX(), point.getY());
            return;
        }
        point = checkPlayerWinDiagonal(computerChoice);
        if (point != null) {
            makeBetByComputer(point.getX(), point.getY());
            return;
        }
        point = checkCentreField();
        if (point != null) {
            makeBetByComputer(point.getX(), point.getY());
            return;
        }
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == -1) {
                    found = true;
                    makeBetByComputer(j, i);
                    break;
                }
            }
            if (found) {
                break;
            }
        }
    }

    protected void makeBetByComputer(int x, int y) {
        printMessage(String.format("Computer bets: %d %d", x, y));
        updateField(x, y, computerChoice);
    }

    private Point checkCentreField() {
        if (field[1][1] == -1) {
            return new Point(1, 1);
        }
        return null;
    }

    private Point checkPlayerWinRow(int oppositionChoice) {
        Point point = null;
        boolean found = false;
        int foundX = 0;
        int foundY = 0;
        for (int i = 0; i < FIELD_SIZE; i++) {
            found = false;
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == oppositionChoice) {
                    found = false;
                    break;
                }
                if (field[i][j] == -1) {
                    if(!found) {
                        found = true;
                        foundX = j;
                        foundY = i;
                    } else {
                        found = false;
                        break;
                    }
                }
            }
            if (found) {
                point = new Point(foundX, foundY);
                break;
            }
        }
        return point;
    }

    private Point checkPlayerWinColumn(int oppositionChoice) {
        Point point = null;
        boolean found = false;
        int foundX = 0;
        int foundY = 0;
        for (int i = 0; i < FIELD_SIZE; i++) {
            found = false;
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[j][i] == oppositionChoice) {
                    found = false;
                    break;
                }
                if (field[j][i] == -1) {
                    if(!found) {
                        found = true;
                        foundX = i;
                        foundY = j;
                    } else {
                        found = false;
                        break;
                    }
                }
            }
            if (found) {
                point = new Point(foundX, foundY);
                break;
            }
        }
        return point;
    }

    private Point checkPlayerWinDiagonal(int oppositionChoice) {
        boolean found = false;
        int foundX = 0;
        int foundY = 0;
        for (int i = 0; i < FIELD_SIZE; i++) {
            int j = i;
            if (field[j][i] == oppositionChoice) {
                found = false;
                break;
            }
            if (field[j][i] == -1) {
                if(!found) {
                    found = true;
                    foundX = i;
                    foundY = j;
                } else {
                    found = false;
                    break;
                }
            }
        }
        if (found) {
            return new Point(foundX, foundY);
        }
        found = false;
        for (int i = 0; i < FIELD_SIZE; i++) {
            int j = FIELD_SIZE - 1 - i;
            if (field[j][i] == computerChoice) {
                found = false;
                break;
            }
            if (field[j][i] == -1) {
                if(!found) {
                    found = true;
                    foundX = i;
                    foundY = j;
                } else {
                    found = false;
                    break;
                }
            }
        }
        if (found) {
            return new Point(foundX, foundY);
        }
        return null;
    }

    protected void playerBet(int x, int y, int value) throws GameException {
        welcomeBet();
        if (userChoice != -1 && userChoice != value) {
            throw new GameException(String.format("You can`t bet different type of value. Your previous choice is %d", userChoice));
        }
        userChoice = value;
        if (getField()[y][x] != -1) {
            throw new GameException("This field is not empty!");
        }
        updateField(x, y, value);
    }

    protected void bet(int x, int y, int value) throws GameException {
        if (firstPlay) {
            playerBet(x, y, value);
            if (this.end()) {
                return;
            }
            computerBet();
        } else {
            if(!computerBetLast) {
                computerBet();
                computerBetLast = true;
            }
            if (this.end()) {
                return;
            }
            playerBet(x, y, value);
            computerBetLast = false;
        }
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
            } else {
                computerWin = true;
            }
            return true;
        }
        return false;
    }

    private boolean checkExhaustingAllFields() {
        int empty = 0;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == -1) {
                    empty++;
                }
            }
        }
        if(empty > 1) {
            return false;
        }
        return true;



//        boolean verticalFieldExhausted = false;
//        boolean horizontalFieldExhausted = false;
//        boolean diagonalLeftExhausted = false;
//        boolean diagonalRightExhausted = false;
//        boolean foundZero;
//        boolean foundOne;
//        for (int i = 0; i < FIELD_SIZE; i++) {
//            foundZero = false;
//            foundOne = false;
//            for (int j = 0; j < FIELD_SIZE; j++) {
//                if (field[i][j] == 0) {
//                    foundZero = true;
//                }
//                if (field[i][j] == 1) {
//                    foundOne = true;
//                }
//            }
//            if (foundZero && foundOne) {
//                horizontalFieldExhausted = true;
//            } else {
//                horizontalFieldExhausted = false;
//            }
//        }
//        for (int i = 0; i < FIELD_SIZE; i++) {
//            foundZero = false;
//            foundOne = false;
//            for (int j = 0; j < FIELD_SIZE; j++) {
//                if (field[j][i] == 0) {
//                    foundZero = true;
//                }
//                if (field[j][i] == 1) {
//                    foundOne = true;
//                }
//            }
//            if (foundZero && foundOne) {
//                verticalFieldExhausted = true;
//            } else {
//                verticalFieldExhausted = false;
//            }
//        }
//        foundZero = false;
//        foundOne = false;
//        for (int i = 0; i < FIELD_SIZE; i++) {
//            if (field[i][i] == 0) {
//                foundZero = true;
//            }
//            if (field[i][i] == 1) {
//                foundOne = true;
//            }
//            if (foundZero && foundOne) {
//                diagonalLeftExhausted = true;
//            }
//        }
//        foundZero = false;
//        foundOne = false;
//        for (int i = 0; i < FIELD_SIZE; i++) {
//            if (field[FIELD_SIZE - i - 1][i] == 0) {
//                foundZero = true;
//            }
//            if (field[FIELD_SIZE - i - 1][i] == 1) {
//                foundOne = true;
//            }
//            if (foundZero && foundOne) {
//                diagonalRightExhausted = true;
//            }
//        }
//        return horizontalFieldExhausted && verticalFieldExhausted && diagonalLeftExhausted && diagonalRightExhausted;
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

    public void launchPlayerBet() {
        try {
            String coordinatesInput = getInput();
            String[] coordinates = coordinatesInput.split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            player.bet(this, x, y);
        } catch (Exception e) {
            printMessage(e.getMessage());
            launchPlayerBet();
        }
    }

    public List<String> getMessageHistory() {
        return messageHistory;
    }

    public void welcomeBet() {
        printMessage("Make a bet, type x y point (for example, 1 1):");
    }

    public int getAnswer() throws GameException {
        printMessage("Would you like to play again? (yes/no)");
        String answer;
        try {
            answer = getInput();
        } catch (Exception e) {
            throw new GameException(e.getMessage());
        }
        if (!(answer.equals("yes") || answer.equals("no"))) {
            throw new GameException(String.format("Wrong answer: %s. Please type yes or no.", answer));
        }
        return answer.equals("yes") ? 1 : 0;
    }

    public void launchGetAnswerTryAgain(int counter) {
        counter--;
        try {
            if (counter < 0) {
                return;
            }
            int answer = getAnswer();
            if (answer == 1){
                start();
            } else {
                System.exit(0);
            }
        } catch (Exception e) {
            printMessage(e.getMessage());
            launchGetAnswerTryAgain(counter);
        }
    }
}
