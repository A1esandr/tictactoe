import java.util.Collection;

public class Game {
    private static final int FIELD_SIZE = 3;
    int[][] field = new int[FIELD_SIZE][FIELD_SIZE];

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
}
