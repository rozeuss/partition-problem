package tests;

import algorithm.BruteForcePartition;
import algorithm.GreedyPartition;
import utils.SpaceComplexity;

import java.util.*;

public class Tests {

    public static void main(String[] args) {
        Map<Integer, List<Long>> greedySizeTimes = new HashMap<>();
        Map<Integer, Long> greedySizeSpaces = new HashMap<>();
        Map<Integer, List<Long>> bruteForceSizeTimes = new HashMap<>();
        Map<Integer, Long> bruteForceSizeSpaces = new HashMap<>();
        long startTime, endTime, duration;
        List<Long> times;
        GreedyPartition.greedyPartition(Arrays.asList(1, 2, 3, 4));
        BruteForcePartition.bruteForcePartition(new int[]{1, 2, 3, 4});
        for (int size = 2; size <= 15; size++) {
            GreedyPartition.SIZE = size;
            BruteForcePartition.SIZE = size;

            times = new ArrayList<>();
            List<Integer> integers;
            for (int i = 0; i < 10; i++) {
                integers = GreedyPartition.generateRandomValues();
                startTime = System.nanoTime();
                GreedyPartition.greedyPartition(integers);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                times.add(duration);
            }
            greedySizeTimes.put(size, times);
            greedySizeSpaces.put(size, SpaceComplexity.greedySpaceComplexity(GreedyPartition.SIZE));
            times = new ArrayList<>();
            int[] ints;
            for (int i = 0; i < 10; i++) {
                ints = BruteForcePartition.generateRandomValues();
                startTime = System.nanoTime();
                BruteForcePartition.bruteForcePartition(ints);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                times.add(duration);
            }
            bruteForceSizeTimes.put(size, times);
            bruteForceSizeSpaces.put(size, SpaceComplexity.bruteForceComplexity(BruteForcePartition.SIZE));
        }
        ExcelGenerator.write(greedySizeTimes, bruteForceSizeTimes, greedySizeSpaces, bruteForceSizeSpaces);
    }
}
