package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import utils.RandomValuesGenerator;
import utils.ResultSets;

public class GreedyPartition {
    public static int SIZE = 10;
    static ResultSets solution;

    public static void main(String[] args) {
//        workingExample();
        List<Integer> integers = generateRandomValues();
        System.out.println("Set: " + integers);
        greedyPartition(integers);
        printResult();
    }

    public static void workingExample() {
//        dane pozwalające na ocenę „jakości (dokładności) rozwiązania”
        System.out.println("**************WORKING EXAMPLE**************");
//        rozwiazanie dajace poprawny wynik
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        solution = new ResultSets();
        System.out.println("Set: " + integers);
        greedyPartition(integers);
        printResult();
        System.out.println("\n");

//        rozwiazanie dajace niepoprawny wynik
        List<Integer> integers2 = Arrays.asList(4, 5, 6, 7, 8);
        solution = new ResultSets();
        System.out.println("Set: " + integers2);
        greedyPartition(integers2);
        printResult();
        System.out.println("\n");
        System.out.println("**************WORKING EXAMPLE**************");
    }

    public static List<Integer> generateRandomValues() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            list.add(RandomValuesGenerator.randomInt());
        }
        return list;
    }

    public static void greedyPartition(List<Integer> list) {
        solution = new ResultSets();
        list.sort(Collections.reverseOrder());
        for (Integer integer : list) {
            int sumA = solution.getFirstSubset().stream().mapToInt(Integer::intValue).sum();
            int sumB = solution.getSecondSubset().stream().mapToInt(Integer::intValue).sum();
            if (sumA < sumB) {
                solution.getFirstSubset().add(integer);
            } else {
                solution.getSecondSubset().add(integer);
            }
        }
    }

    public static void printResult() {
        int sumA = solution.getFirstSubset().stream().mapToInt(Integer::intValue).sum();
        int sumB = solution.getSecondSubset().stream().mapToInt(Integer::intValue).sum();
        System.out.println(solution.getFirstSubset() + " => " + sumA);
        System.out.println(solution.getSecondSubset() + " => " + sumB);
        System.out.println();
        if (sumA == sumB) {
            System.out.println("Solution found:");
            System.out.println(solution.getFirstSubset());
            System.out.println(solution.getSecondSubset());
        } else {
            System.out.println("Solution not found.");
        }
    }

}


