package day4;

import day4.Day4.BingoBoard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day4Test {
  BingoBoard bingoBoardVerticalWin;
  BingoBoard bingoBoardHorizontalWin;

  @BeforeEach
  public void setup() {
    this.bingoBoardVerticalWin = new BingoBoard(new String[][]{
        {"1", "1", "1", "12", "1"},
        {"1", "1", "1", "28", "1"},
        {"1", "1", "1", "0", "1"},
        {"1", "1", "1", "63", "1"},
        {"1", "1", "1", "26", "1"},
    });
    this.bingoBoardHorizontalWin = new BingoBoard(new String[][]{
        {"1", "1", "1", "1", "1"},
        {"12", "28", "0", "63", "26"},
        {"1", "1", "1", "1", "1"},
        {"1", "1", "1", "1", "1"},
        {"1", "1", "1", "1", "1"},
    });
  }

  @Test
  void Day4_BingoBoard_getScore() {
    var bingoBoard = this.bingoBoardVerticalWin;

    bingoBoard.play("12");
    bingoBoard.play("28");
    bingoBoard.play("0");
    bingoBoard.play("63");
    Assert.assertEquals(46, bingoBoard.getScore());
    bingoBoard.play("26");
    Assert.assertEquals(20, bingoBoard.getScore());
  }

  @Test
  void Day4_BingoBoard_hasBingo_Vertical() {
    var bingoBoard = this.bingoBoardVerticalWin;

    bingoBoard.play("12");
    Assert.assertFalse(bingoBoard.getHasBingo());
    bingoBoard.play("28");
    Assert.assertFalse(bingoBoard.getHasBingo());
    bingoBoard.play("0");
    Assert.assertFalse(bingoBoard.getHasBingo());
    bingoBoard.play("63");
    Assert.assertFalse(bingoBoard.getHasBingo());
    bingoBoard.play("26");
    Assert.assertTrue(bingoBoard.getHasBingo());
  }

  @Test
  void Day4_BingoBoard_hasBingo_Horizontal() {
    var bingoBoard = this.bingoBoardHorizontalWin;

    bingoBoard.play("12");
    Assert.assertFalse(bingoBoard.getHasBingo());
    bingoBoard.play("28");
    Assert.assertFalse(bingoBoard.getHasBingo());
    bingoBoard.play("0");
    Assert.assertFalse(bingoBoard.getHasBingo());
    bingoBoard.play("63");
    Assert.assertFalse(bingoBoard.getHasBingo());
    bingoBoard.play("26");
    Assert.assertTrue(bingoBoard.getHasBingo());
  }
}