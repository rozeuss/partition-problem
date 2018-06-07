package utils;

public class SpaceComplexity {


    public static long greedySpaceComplexity(int n) {
        return n + n + 1 + 1 + 1 * n;
    }

    public static long bruteForceComplexity(int n) {
        return n + n + 1 * n + 1 + (long) Math.pow(2, n) + n + 1 + 1 * n + 1;
    }
}
