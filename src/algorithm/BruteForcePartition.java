package algorithm;

import utils.RandomValuesGenerator;
import utils.ResultSets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteForcePartition {
    public static int SIZE = 10;
    static List<ResultSets> solutions;
    static int[] positions;
    static boolean overflow;

    public static void main(String[] args) {
//        workingExample();
        int[] set = generateRandomValues();
        System.out.println("Set: " + Arrays.toString(set));
        System.out.println("Sum = " + Arrays.stream(set).sum());
        bruteForcePartition(set);
        printResult();
    }

    public static void workingExample() {
//        dane pozwalające na ocenę „jakości (dokładności) rozwiązania”
        System.out.println("**************WORKING EXAMPLE**************");
        int[] set = new int[]{1, 2, 3, 4};
//        int[] set = new int[]{4, 5, 6, 7, 8};
        positions = new int[set.length];
        solutions = new ArrayList<>();
        System.out.println("Set: " + Arrays.toString(set));
        System.out.println("Sum = " + Arrays.stream(set).sum());
        bruteForcePartition(set);
        printResult();
        overflow = false;
        System.out.println("**************WORKING EXAMPLE**************");
        System.out.println("\n");
    }

    private static int checkDifference(int firstSum, int secondSum) {
        return Math.abs(secondSum - firstSum);
    }

    public static void bruteForcePartition(int[] set) {
        positions = new int[set.length];
        solutions = new ArrayList<>();
        overflow = false;
//        if (Arrays.stream(set).sum() % 2 != 0) {
//            System.out.print("\nOdd sum. ");
//            return;
//        }
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
                ResultSets resultSets = new ResultSets();
                resultSets.setFirstSubset(first);
                resultSets.setSecondSubset(second);
                solutions.add(resultSets);
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
        } else if (positions[i] == 1) {
            positions[i] = 0;
            i++;
            getPositions(i);
        }
    }

    private static void printResult() {
        System.out.println();
        if (!solutions.isEmpty()) {
            System.out.println("Solution(s) found:");
            solutions.forEach(resultSets -> {
                System.out.println("###############");
                System.out.println(resultSets.getFirstSubset().toString()
                        + " => " + resultSets.getFirstSubset().stream().mapToInt(Integer::intValue).sum());
                System.out.println(resultSets.getSecondSubset().toString()
                        + " => " + resultSets.getSecondSubset().stream().mapToInt(Integer::intValue).sum());
            });
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
