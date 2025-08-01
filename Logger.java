public class Logger {
    private static Logger Instance;

    private Logger() {

    }
    public static Logger getInstance() {
        if(Instance == null) {
            Instance = new Logger();
        }

        return Instance;
    }
}

class Main{
    public static void main(String[] args) {
        Logger I1 = Logger.getInstance();
        Logger I2 = Logger.getInstance();
        if(I1 == I2) {
            System.out.println("They belongs to the same Instance");
        }else{
            System.out.println("the do not belong");
        }
    }
}
