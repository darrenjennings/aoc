package day4;

import aoc.AocUtils;

import java.util.*;
import java.util.stream.Stream;

public class Day4 {
  public static final int BOARD_SIZE = 5;

  public static void main(String[] args) throws Exception {
    Stream<String> stream1 = AocUtils.fileStream(args);
    Stream<String> stream2 = AocUtils.fileStream(args);
    var day = new Day4();
    System.out.println(day.part1(stream1)); // 44088
    System.out.println(day.part2(stream2)); // 23670
  }

  public static class BingoBoard {
    private final String HIT_CHARACTER = "X";

    private String[][] board;
    private boolean hasBingo;

    public BingoBoard(String[][] board) {
      this.board = board;
      this.hasBingo = false;
    }

    public void play(String number) {
      boolean hit = false;
      for (int i = 0; i < BOARD_SIZE; i++) {
        if (hit) break;
        for (int j = 0; j < BOARD_SIZE; j++) {
          if (this.board[i][j].equals(number)) {
            this.board[i][j] = HIT_CHARACTER;
            break;
          }
        }
      }

      this.hasBingo = this.hasBingo();
    }

    public boolean getHasBingo() {
      return this.hasBingo;
    }

    private boolean hasBingo() {
      int countRows = 0;
      for (int i = 0; i < BOARD_SIZE; i++) {
        if (this.board[0][i].equals(HIT_CHARACTER)) {
          for (int j = 0; j < BOARD_SIZE; j++) {
            if (this.board[j][i].equals(HIT_CHARACTER)) {
              countRows++;
              if (countRows == BOARD_SIZE) {
                return true;
              }
            } else {
              countRows = 0;
              break;
            }
          }
        }
      }

      int countColumns = 0;
      for (int i = 0; i < BOARD_SIZE; i++) {
        if (this.board[i][0].equals(HIT_CHARACTER)) {
          for (int j = 0; j < BOARD_SIZE; j++) {
            if (this.board[i][j].equals(HIT_CHARACTER)) {
              countColumns++;
              if (countColumns == BOARD_SIZE) {
                return true;
              }
            } else {
              countColumns = 0;
              break;
            }
          }
        }
      }

      return false;
    }

    public int getScore() {
      int sum = 0;
      for (int i = 0; i < BOARD_SIZE; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
          if (!this.board[i][j].equals(HIT_CHARACTER)) {
            sum += Integer.parseInt(this.board[i][j]);
          }
        }
      }

      return sum;
    }
  }

  private class BingoGame {
    private List<String> numbersDrawn;
    private HashMap<Integer, BingoBoard> boards;

    public BingoGame(String[] lines) {
      this.boards = new HashMap<>();
      for (int i = 0; i < lines.length; i++) {
        if (i == 0) {
          this.numbersDrawn = List.of(lines[i].split(","));
        }
        if (lines[i].equals("")) {
          continue;
        }

        if (i > 0) {
          this.addBoard(lines, i);
          i += 5;
        }
      }
    }

    private void addBoard(String[] lines, int startIdx) {
      String[] board = Arrays.copyOfRange(lines, startIdx, startIdx + BOARD_SIZE);
      String[][] boardList = new String[BOARD_SIZE][BOARD_SIZE];
      for (int i = 0; i < BOARD_SIZE; i++) {
        var line = board[i];
        boardList[i] = Arrays.stream(line.split("\\s+"))
            .filter(item -> !item.equals(""))
            .toArray(String[]::new);
      }
      this.boards.put(this.boards.size(), new BingoBoard(boardList));
    }
  }

  /**
   * Find the first board that will win.
   */
  public int part1(Stream<String> input) throws RuntimeException {
    var game = new BingoGame(input.toArray(String[]::new));

    for (int i = 0; i < game.numbersDrawn.size(); i++) {
      var number = game.numbersDrawn.get(i);
      for (int j = 0; j < game.boards.size(); j++) {
        game.boards.get(j).play(number);
        if (game.boards.get(j).getHasBingo()) {
          int score = game.boards.get(j).getScore();
          return score * Integer.parseInt(number);
        }
      }
    }

    throw new RuntimeException("No board won.");
  }

  /**
   * Find the last board that will win.
   */
  public int part2(Stream<String> input) throws RuntimeException {
    var game = new BingoGame(input.toArray(String[]::new));
    var winningBoards = new ArrayList<BingoBoard>();
    String winningNumber = "-1";

    for (int i = 0; i < game.numbersDrawn.size(); i++) {
      if (winningBoards.size() == game.boards.size()) {
        break;
      }

      var number = game.numbersDrawn.get(i);

      for (int j = 0; j < game.boards.size(); j++) {
        if (winningBoards.size() == game.boards.size()) {
          break;
        }

        if (winningBoards.contains(game.boards.get(j))) {
          continue;
        }

        if (winningBoards.isEmpty() || !winningBoards.isEmpty() && !winningBoards.contains(game.boards.get(j))) {
          game.boards.get(j).play(number);
        }
        if (game.boards.get(j).getHasBingo()) {
          winningBoards.add(game.boards.get(j));
          winningNumber = number;
        }
      }
    }

    if (winningBoards.isEmpty()) {
      throw new RuntimeException("No board won.");
    }

    var lastToWin = winningBoards.get(winningBoards.size() - 1);
    return lastToWin.getScore() * Integer.parseInt(winningNumber);
  }
}