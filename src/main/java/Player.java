public class Player {
    public void bet(int value) throws Exception {
        if (!(value == 0 || value == 1)) {
            throw new Exception("You can bet 0 or 1 only");
        }
    }
}
