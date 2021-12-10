package day6;

import aoc.AocUtils;

import java.util.*;
import java.util.stream.Stream;

public class Day6 {
  public static void main(String[] args) throws Exception {
    Stream<String> stream1 = AocUtils.fileStream(args);
    Stream<String> stream2 = AocUtils.fileStream(args);
    var day = new Day6();
    System.out.println(String.format("%.0f", day.part1(stream1))); // test: 5934, input: 352151
    System.out.println(String.format("%.0f", day.part2(stream2))); // test: 26984457539, input: 1601616884019
  }

  public double part1(Stream<String> input) {
    return howManyLanternFishAfterDays(input, 80);
  }

  public double part2(Stream<String> input) {
    return howManyLanternFishAfterDays(input, 256);
  }

  private double howManyLanternFishAfterDays(Stream<String> input, int days) {
    int[] initialTimers = Arrays.stream(input.toArray(String[]::new)[0].split(",")).mapToInt(x -> Integer.parseInt(x)).toArray();
    var swarm = new LaternFishSwarm(initialTimers);

    swarm.elapseDays(days);

    return swarm.getNumberOfFish();
  }

  public static class LaternFishSwarm {
    private double[] fishTimerMap;
    private int[] timers;

    public LaternFishSwarm(int[] timers) {
      this.fishTimerMap = new double[9];
      this.timers = Arrays.stream(timers).sorted().toArray();

      for (int i = 0; i < 9; i++) {
        fishTimerMap[i] = 0;
      }
    }

    private void elapseDays(int days) {
      var daysElapsed = 0;
      for (int i = 0; i < this.timers.length; i++) {
        fishTimerMap[this.timers[i]] += 1;
      }

      while (daysElapsed < days) {
        var fishAboutToDie = fishTimerMap[0];

        // shift the index-counts to simulate decrementing ttl
        for (int i = 0; i < this.fishTimerMap.length - 1; i++) {
          fishTimerMap[i] = fishTimerMap[i + 1];
        }

        // push to front and end for dying fishes / fishes being born
        fishTimerMap[8] = fishAboutToDie;
        fishTimerMap[6] = fishTimerMap[6] + fishAboutToDie;

        daysElapsed++;
      }
    }

    private double getNumberOfFish () {
      return Arrays.stream(fishTimerMap).sum();
    }
  }
}