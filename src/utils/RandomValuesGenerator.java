package utils;

        import java.util.Random;

public class RandomValuesGenerator {
    private static final int MAX = 100;
    private static final int MIN = 1;

    private RandomValuesGenerator() {
    }

    public static int randomInt() {
        Random generator = new Random();
        int n = MAX - MIN + 1;
        int t = (generator.nextInt()) % (n);
        if (t == 0) {
            return (MAX);
        } else if (t < 0) {
            return (Math.abs(t));
        } else {
            return t;
        }
    }
}
