public class Game {
    private static final int FIELD_SIZE = 3;
    int[][] field = new int[FIELD_SIZE][FIELD_SIZE];
    int userChoice = 0, computerChoice = 0;

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

    public void bet(int x, int y, int value) {
        userChoice = value;
        getField()[x][y] = value;
        computerBet();;
    }

    public boolean end() {
        return false;
    }
}
