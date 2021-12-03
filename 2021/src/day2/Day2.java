package day2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Day2 {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      throw new IllegalArgumentException("Must provide a file path.");
    }

    File file = new File(args[0]);
    if (!file.exists()) {
      throw new FileNotFoundException("File not found.");
    }

    Stream<String> stream1 = Files.lines(Paths.get(args[0]));
    Stream<String> stream2 = Files.lines(Paths.get(args[0]));

    var day2 = new Day2();
    System.out.println(day2.part1(stream1));
    System.out.println(day2.part2(stream2));
  }

  public int part1(Stream<String> input) {
    var submarine = new Submarine(0, 0, 0);
    input.forEach(line -> {
      String[] parts = line.split(" ");
      String direction = parts[0];
      int value = Integer.parseInt(parts[1]);

      submarine.receive(new SubmarineCommand(direction, value));
    });

    return submarine.result();
  }

  public int part2(Stream<String> input) {
    var submarine = new Submarine(0, 0, 0);
    input.forEach(line -> {
      String[] parts = line.split(" ");
      String direction = parts[0];
      int value = Integer.parseInt(parts[1]);

      submarine.receive2(new SubmarineCommand(direction, value));
    });

    return submarine.result();
  }

  public class Submarine {
    private int aim;
    private int horizontalPosition;
    private int depth;

    public Submarine(int aim, int horizontalPosition, int depth) {
      this.aim = aim;
      this.horizontalPosition = horizontalPosition;
      this.depth = depth;
    }

    public Submarine receive(SubmarineCommand command) {
      this.horizontalPosition += command.getIncrementX();
      this.depth += command.getIncrementY();

      return this;
    }

    /**
     * Takes into account aim
     * @return
     */
    public Submarine receive2(SubmarineCommand command) {
      this.horizontalPosition += command.getIncrementX();
      this.aim += command.getIncrementY();

      if (command.getDirection().equals("forward")) {
        this.depth += this.aim * command.getIncrementX();
      }

      return this;
    }

    public int result() {
      return this.horizontalPosition * this.depth;
    }
  }

  public class SubmarineCommand {
    private String direction;
    private int value;

    public SubmarineCommand(String direction, int value) {
      this.direction = direction;
      this.value = value;
    }

    public String getDirection() {
      return direction;
    }

    public int getValue() {
      return value;
    }

    public int getIncrementX() {
      if (this.getDirection().equals("forward")) {
        return this.getValue();
      }

      return 0;
    }

    public int getIncrementY() {
      if (this.getDirection().equals("forward")) {
        return 0;
      }

      return this.getDirection().equals("down") ? 1 * this.getValue() : -1 * this.getValue();
    }
  }
}