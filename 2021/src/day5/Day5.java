package day5;

import aoc.AocUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {
  public static void main(String[] args) throws Exception {
    Stream<String> stream1 = AocUtils.fileStream(args);
    Stream<String> stream2 = AocUtils.fileStream(args);
    var day = new Day5();
    System.out.println(day.part1(stream1));
    System.out.println(day.part2(stream2));
  }

  public int part1(Stream<String> input) {
    List<LineSegment> lines = getLineSegments(input);

    var plot = new Plot();
    for (var line : lines) {
      // ignore diagonal lines for now
      if (line.coordinate2.x == line.coordinate1.x || line.coordinate2.y == line.coordinate1.y) {
        plot.addLineSegment(line);
      }
    }

    return plot.getOverlap(2);
  }

  public int part2(Stream<String> input) {
    List<LineSegment> lines = getLineSegments(input);

    var plot = new Plot();
    for (var line : lines) {
      // all lines, including diagonal!
      plot.addLineSegment(line);
    }

    return plot.getOverlap(2);
  }

  private List<LineSegment> getLineSegments(Stream<String> input) {
    return input.map(line -> {
      var parts = line.split(" -> ");
      var xy1 = Arrays.stream(parts[0].split(",")).map(s -> Integer.parseInt(s)).toArray(Integer[]::new);
      var xy2 = Arrays.stream(parts[1].split(",")).map(s -> Integer.parseInt(s)).toArray(Integer[]::new);

      return new LineSegment(new Coordinate(xy1[0], xy1[1]), new Coordinate(xy2[0], xy2[1]));
    }).collect(Collectors.toList());
  }

  public class Plot {
    public String[][] plot;
    private final static String EMPTY_CELL = ".";

    public Plot() {
      this.plot = new String[1000][1000];
      for (int i = 0; i < 1000; i++) {
        for (int j = 0; j < 1000; j++) {
          this.plot[i][j] = EMPTY_CELL;
        }
      }
    }

    /**
     * for debugging
     */
    public void print() {
      for (int i = 0; i < 1000; i++) {
        for (int j = 0; j < 1000; j++) {
          System.out.print(this.plot[i][j]);
        }
        System.out.print("\n");
      }
    }

    public void addLineSegment(LineSegment line) {
      for (int x = 0; x < 1000; x++) {
        for (int y = 0; y < 1000; y++) {
          boolean addToPlot = line.isPointAlongLine(x, y);
          if (!addToPlot) {
            continue;
          }

          if (plot[y][x] != EMPTY_CELL) {
            plot[y][x] = String.format("%s", Integer.parseInt(plot[y][x]) + 1);
          } else {
            plot[y][x] = "1";
          }

        }
      }
    }

    public int getOverlap(int threshold) {
      int count = 0;
      for (int i = 0; i < 1000; i++) {
        for (int j = 0; j < 1000; j++) {
          if (plot[i][j] == EMPTY_CELL) {
            continue;
          }
          boolean exceedsThreshold = Integer.parseInt(plot[i][j]) >= threshold;
          if (exceedsThreshold) {
            count++;
          }
        }
      }

      return count;
    }
  }

  public static class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  public static class LineSegment {
    private Coordinate coordinate1;
    private Coordinate coordinate2;

    public LineSegment(Coordinate coordinate1, Coordinate coordinate2) {
      this.coordinate1 = coordinate1;
      this.coordinate2 = coordinate2;
    }

    public boolean isPointAlongLine(int x, int y) {
      var maxX = Math.max(coordinate1.x, coordinate2.x);
      var minX = Math.min(coordinate1.x, coordinate2.x);
      var maxY = Math.max(coordinate1.y, coordinate2.y);
      var minY = Math.min(coordinate1.y, coordinate2.y);

      // vertical line
      if (getSlope() == null) {
        return y >= minY && y <= maxY && x == coordinate2.x;
      }

      int _y = (int)(getSlope() * x + getIntercept());

      if (x < minX || x > maxX || y < minY || y > maxY) {
        return false;
      }

      return _y == y;
    }

    public Double getSlope() {
      var rise = coordinate2.y - coordinate1.y;
      var run = coordinate2.x - coordinate1.x;

      // vertical lines have undefined slope
      if (run == 0) {
        return null;
      }

      // horizontal lines have slope = 0
      if (rise == 0) {
        return 0.0;
      }

      return (double) (rise / run);
    }

    public double getIntercept() {
      if (this.getSlope() == null) {
        return this.coordinate1.y;
      }
      return this.coordinate1.y - (this.getSlope() * this.coordinate1.x);
    }
  }
}