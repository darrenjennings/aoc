package day3;

import aoc.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day3 {
  public static void main(String[] args) throws IOException {
    Stream<String> stream1 = AocUtils.fileStream(args);
    Stream<String> stream2 = AocUtils.fileStream(args);

    var day = new Day3();
    System.out.println(day.part1(stream1));
    System.out.println(day.part2(stream2));
  }

  public int part1(Stream<String> input) {
    List<String> lines = input.collect(Collectors.toList());
    var report = new DiagnosticsReport();
    lines.forEach(line -> {
      report.addEntry(line);
    });

    return report.calculatePowerConsumption();
  }

  public int part2(Stream<String> input) {
    List<String> lines = input.collect(Collectors.toList());
    var report = new DiagnosticsReport();
    lines.forEach(line -> {
      report.addEntry(line);
    });

    return report.calculateLifeSupportRating();
  }

  public class DiagnosticsReport {
    private List<String> report;
    private String gamma;
    private String epsilon;

    private Map<Integer, Integer> zeroes;
    private Map<Integer, Integer> ones;

    private int numberOfColumns;

    public DiagnosticsReport() {
      this.numberOfColumns = 0;
      this.gamma = "";
      this.epsilon = "";
      this.report = new ArrayList<>();
      this.calculateZeroesOnes(this.report);
    }

    private List<Boolean> lineToBinaryList(String line) {
      return Arrays.stream(line.split(""))
          .map(i -> i.equals("0") ? Boolean.FALSE : Boolean.TRUE)
          .collect(Collectors.toList());
    }

    public DiagnosticsReport addEntry(String line) {
      this.report.add(line);
      this.calculateZeroesOnes(this.report);
      return this;
    }

    public int calculatePowerConsumption() {
      this.gamma = "";
      this.epsilon = "";

      for (int i = 0; i < this.numberOfColumns; i++) {
        var zeroesWin = this.zeroes.get(i) > this.ones.get(i);
        this.gamma += zeroesWin ? 0 : 1;
        this.epsilon += zeroesWin ? 1 : 0;
      }

      return Integer.parseInt(this.gamma, 2) * Integer.parseInt(this.epsilon, 2);
    }

    public int calculateLifeSupportRating() {
      var oxygenGeneratorReport = calculateRating(new OxygenGeneratorComparator());
      var scrubberReport = calculateRating(new C02ScrubberComparator());

      return Integer.parseInt(scrubberReport.get(0), 2) * Integer.parseInt(oxygenGeneratorReport.get(0), 2);
    }

    private ArrayList<String> calculateRating(BitCountComparator comparator) {
      ArrayList<String> tmpReport;
      var tmpZeroes = new HashMap<>(this.zeroes);
      var tmpOnes = new HashMap<>(this.ones);
      var ratingReport = new ArrayList<>(this.report);

      for (int i = 0; i < this.numberOfColumns; i++) {
        tmpReport = new ArrayList<>(ratingReport);
        final int column = i;
        for (int j = 0; j < tmpReport.size(); j++) {
          var line = tmpReport.get(j);
          if (comparator.bitStringWins(this.ones, this.zeroes, line, column)) {
            ratingReport.remove(line);
          } else if (comparator.bitStringLoses(this.ones, this.zeroes, line, column)) {
            ratingReport.remove(line);
          }
        }
        this.calculateZeroesOnes(ratingReport);
        if (ratingReport.size() == 1) {
          break;
        }
      }

      this.zeroes = tmpZeroes;
      this.ones = tmpOnes;

      return ratingReport;
    }

    private void calculateZeroesOnes(List<String> report) {
      this.ones = new HashMap<>();
      this.zeroes = new HashMap<>();
      for (int i = 0; i < report.size(); i++) {
        int idx = 0;
        var line = report.get(i);
        var item = this.lineToBinaryList(line);
        for (int j = 0; j < item.size(); j++) {
          this.zeroes.putIfAbsent(idx, 0);
          this.ones.putIfAbsent(idx, 0);
          if (item.get(j) == Boolean.FALSE) {
            this.zeroes.put(idx, this.zeroes.getOrDefault(idx, 0) + 1);
          } else {
            this.ones.put(idx, this.ones.getOrDefault(idx, 0) + 1);
          }
          idx++;
        }
        this.numberOfColumns = idx;
      }
    }

    public class OxygenGeneratorComparator implements BitCountComparator {
      @Override
      public boolean bitStringWins(Map<Integer, Integer> ones, Map<Integer, Integer> zeroes, String line, int column) {
        return ones.get(column) >= zeroes.get(column) && line.charAt(column) == '0';
      }

      @Override
      public boolean bitStringLoses(Map<Integer, Integer> ones, Map<Integer, Integer> zeroes, String line, int column) {
        return ones.get(column) < zeroes.get(column) && line.charAt(column) == '1';
      }
    }

    public class C02ScrubberComparator implements BitCountComparator {
      @Override
      public boolean bitStringWins(Map<Integer, Integer> ones, Map<Integer, Integer> zeroes, String line, int column) {
        return ones.get(column) >= zeroes.get(column) && line.charAt(column) == '1';
      }

      @Override
      public boolean bitStringLoses(Map<Integer, Integer> ones, Map<Integer, Integer> zeroes, String line, int column) {
        return ones.get(column) < zeroes.get(column) && line.charAt(column) == '0';
      }
    }
  }

  private interface BitCountComparator {
    boolean bitStringWins(Map<Integer, Integer> ones, Map<Integer, Integer> zeroes, String line, int column);
    boolean bitStringLoses(Map<Integer, Integer> ones, Map<Integer, Integer> zeroes, String line, int column);
  }
}