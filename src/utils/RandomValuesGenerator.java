package utils;

import java.util.Random;

public class RandomValuesGenerator {
    static final long LONG_MAX = 100;
    static final int MAX = 100;
    static final long LONG_MIN = 1;
    static final int MIN = 1;

    public static long randomLong() {
        Random generator = new Random();
        long n = LONG_MAX - LONG_MIN + 1;
        long t = (generator.nextLong()) % (n);

        if (t == (long) 0)
            return (LONG_MAX);
        else if (t < (long) 0)
            return (Math.abs(t));
        else
            return t;
    }

    public static int randomInt() {
        Random generator = new Random();
        int n = MAX - MIN + 1;
        int t = (generator.nextInt()) % (n);
        if (t == 0)
            return (MAX);
        else if (t < 0)
            return (Math.abs(t));
        else
            return t;
    }
}
