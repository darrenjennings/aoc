package day1;

import java.io.*;
import java.util.ArrayList;

public class Day1 {
  public static void main(String[] args) throws IOException {
    File file = new File(args[0]);
    if (!file.exists()) {
      throw new FileNotFoundException("File not found.");
    }

    BufferedReader br = new BufferedReader(new FileReader(file));
    BufferedReader br2 = new BufferedReader(new FileReader(file));

    System.out.println(part1(br));
    System.out.println(part2(br2));
  }

  public static int part1(BufferedReader input) throws IOException {
    String st;
    var res = 0;
    int prev = -1;
    while ((st = input.readLine()) != null) {
      int curr = Integer.parseInt(st);

      // nothing to compare yet
      if (prev == -1) {
        prev = curr;
        continue;
      }

      if (prev < curr) {
        res += 1;
      }

      prev = curr;
    }

    return res;
  }

  public static int part2(BufferedReader input) throws IOException {
    String st;
    var idx = 0;
    var res = 0;
    var prev = -1;
    var strs = new ArrayList<Integer>();
    while ((st = input.readLine()) != null) {
      int curr = Integer.parseInt(st);
      strs.add(curr);
      if (idx < 2) {
        idx += 1;
        continue;
      }

      // get the current window
      var sum = strs
          .subList(strs.size() - 3, strs.size())
          .stream().reduce(0, Integer::sum);

      // nothing to compare yet
      if (prev == -1) {
        prev = sum;
        continue;
      }

      if (prev < sum) {
        res += 1;
      }

      // store the result from the previous window
      prev = sum;
    }

    return res;
  }
}