package tests;

import algorithm.BruteForcePartition;
import algorithm.GreedyPartition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {

    public static void main(String[] args) {
        Map<Integer, List<Long>> greedySizeTimes = new HashMap<>();
        Map<Integer, List<Long>> bruteForceSizeTimes = new HashMap<>();
        long startTime, endTime, duration;
        List<Long> times;
        for (int size = 2; size <= 15; size++) {
            GreedyPartition.SIZE = size;
            BruteForcePartition.SIZE = size;

            times = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                List<Integer> integers = GreedyPartition.generateRandomValues();
                startTime = System.nanoTime();
                GreedyPartition.greedyPartition(integers);
                endTime = System.nanoTime();
                duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                times.add(duration);
            }
            greedySizeTimes.put(size, times);

            times = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                int[] ints = BruteForcePartition.generateRandomValues();
                startTime = System.nanoTime();
                BruteForcePartition.bruteForcePartition(ints);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                times.add(duration);
            }
            bruteForceSizeTimes.put(size, times);
        }
        ExcelGenerator.write(greedySizeTimes, bruteForceSizeTimes);
    }
}
