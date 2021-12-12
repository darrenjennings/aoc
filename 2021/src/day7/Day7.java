package day7;

import aoc.AocUtils;

import java.util.*;
import java.util.stream.Stream;

public class Day7 {
  public static void main(String[] args) throws Exception {
    Stream<String> stream1 = AocUtils.fileStream(args);
    Stream<String> stream2 = AocUtils.fileStream(args);

    var day = new Day7();
    System.out.println(String.format("%s", day.part1(stream1))); // test: 37, input: 339321
    System.out.println(String.format("%s", day.part2(stream2))); // test: 168, input: 95476244
  }

  public int part1(Stream<String> input) {
    var positions = Arrays.stream(input.toArray(String[]::new)[0].split(",")).mapToInt(x -> Integer.parseInt(x)).sorted().toArray();

    // The median is sufficient to determine the target.
    int medianIdx = (positions.length + 1) / 2;
    int medianValue = positions[medianIdx];

    var fuel = 0;
    for (int i = 0; i < positions.length; i++) {
      fuel += Math.abs(medianValue - positions[i]);
    }

    return fuel;
  }

  public int part2(Stream<String> input) {
    var positions = Arrays.stream(input.toArray(String[]::new)[0].split(",")).mapToInt(x -> Integer.parseInt(x)).sorted().toArray();
    int min = positions[0];
    int max = positions[positions.length - 1];

    Integer lowestCost = null;

    // go through each possible step from min to max, and calculate
    // the associated fuel costs and use the lowest.
    for (int i = min; i < max; i++) {
      int tmp = lowestCost(positions, i);
      if (lowestCost == null || tmp < lowestCost) {
        lowestCost = tmp;
      }
    }

    return lowestCost;
  }

  public int lowestCost(int[] positions, int n) {
    int fuel = 0;
    for (int i = 0; i < positions.length; i++) {
      int cost = seriesSum(Math.abs(positions[i] - n));
      fuel += cost;
    }

    return fuel;
  }

  /**
   * sum of a series with d = 1
   */
  public int seriesSum(int n) {
    return n * (n + 1) / 2;
  }
}