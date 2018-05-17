import java.util.ArrayList;
import java.util.Arrays;

public class BruteForcePartition {
    static final int SIZE = 10;
    static ArrayList<Integer> firstSubset = new ArrayList<>();
    static boolean found;
    static int[] positions;
    static ArrayList<Integer> secondSubset = new ArrayList<>();
    static int[] set;
    static boolean overflow;

    public static void main(String[] args) {
//        set = new int[]{1, 2, 3, 4, 64, 52, 21321312};
        set = generateRandomValues();
        positions = new int[set.length];
        System.out.println("Set: " + Arrays.toString(set));
        System.out.println("Sum = " + Arrays.stream(set).sum());
        evaluate();
        printResult();
    }

    private static int checkDifference(int firstSum, int secondSum) {
        return Math.abs(secondSum - firstSum);
    }

    private static void evaluate() {
        if (Arrays.stream(set).sum() % 2 != 0) {
            System.out.print("\nOdd sum. ");
            return;
        }
        int count = 1;
        ArrayList<Integer> first;
        ArrayList<Integer> second;
        while (!overflow) {
            first = new ArrayList<>();
            second = new ArrayList<>();
            for (int i = 0; i < set.length; i++) {
                if (positions[i] == 0) {
                    first.add(set[i]);
                } else if (positions[i] == 1) {
                    second.add(set[i]);
                }
            }
            System.out.println("Iteration " + count + ":");
            System.out.print(first.toString() + " => ");
            int leftSum = first.stream().mapToInt(Integer::intValue).sum();
            System.out.println(leftSum);
            System.out.print(second.toString() + " => ");
            int rightSum = second.stream().mapToInt(Integer::intValue).sum();
            System.out.println(rightSum);
            if (checkDifference(leftSum, rightSum) == 0) {
                firstSubset = first;
                secondSubset = second;
                found = true;
                break;
            }
            count++;
            getPositions(0);
        }
    }

    private static void getPositions(int i) {
        if (positions[i] == 0) {
            positions[i] = 1;
            if (i == positions.length - 1)
                overflow = true;
            return;
        } else if (positions[i] == 1) {
            positions[i] = 0;
            i++;
            getPositions(i);
        }
    }

    private static void printResult() {
        System.out.println();
        if (found) {
            System.out.println("Solution found:");
            System.out.println(firstSubset.toString()
                    + " => " + firstSubset.stream().mapToInt(Integer::intValue).sum());
            System.out.println(secondSubset.toString()
                    + " => " + secondSubset.stream().mapToInt(Integer::intValue).sum());
        } else {
            System.out.println("Solution not found.");
        }
    }

    public static int[] generateRandomValues() {
        int[] tab = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            tab[i] = RandomValuesGenerator.randomInt();
        }
        return tab;
    }

}
