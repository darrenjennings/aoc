package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AocUtils {
  public static Stream fileStream(String[] args) throws IOException {
    if (args.length == 0) {
      throw new IllegalArgumentException("Must provide a file path.");
    }

    File file = new File(args[0]);
    if (!file.exists()) {
      throw new FileNotFoundException("File not found.");
    }

    return Files.lines(Paths.get(args[0]));
  }
}
