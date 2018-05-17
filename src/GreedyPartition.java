import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GreedyPartition {
    static final int MAX = 10;
    static final int MIN = 1;
    static final int SIZE = 10;

    static List<Integer> a = new ArrayList<>();
    static List<Integer> b = new ArrayList<>();

    public static void main(String[] args) {
        List<Integer> integers = generateRandomValues();
        System.out.println("Set: " + integers);
        greedyPartition(integers);
        printResult();
    }

    public static void printResult() {
        int sumA = a.stream().mapToInt(Integer::intValue).sum();
        int sumB = b.stream().mapToInt(Integer::intValue).sum();
        System.out.println(a + " => " + sumA);
        System.out.println(b + " => " + sumB);
        System.out.println();
        if (sumA == sumB) {
            System.out.println("Solution found:");
            System.out.println(a);
            System.out.println(b);
        } else {
            System.out.println("Solution not found.");
        }
    }

    public static List<Integer> generateRandomValues() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            list.add(RandomValuesGenerator.randomInt());
        }
        return list;
    }

    public static void greedyPartition(List<Integer> list) {
        list.sort(Collections.reverseOrder());
        for (Integer integer : list) {
            int sumA = a.stream().mapToInt(Integer::intValue).sum();
            int sumB = b.stream().mapToInt(Integer::intValue).sum();
            if (sumA < sumB) {
                a.add(integer);
            } else {
                b.add(integer);
            }
        }
    }
}


