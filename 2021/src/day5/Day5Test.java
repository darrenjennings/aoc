package day5;

import day5.Day5.LineSegment;
import day5.Day5.Coordinate;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day5Test {
  @BeforeEach
  public void setup() {
    //
  }

  @Test
  void Day5_LineSegment_isPointAlongLine_VerticalLine() {
    var line = new LineSegment(new Coordinate(2,3), new Coordinate(2,1));
    Assert.assertFalse(line.isPointAlongLine(0,0));
    Assert.assertFalse(line.isPointAlongLine(0,1));
    Assert.assertTrue(line.isPointAlongLine(2,1));
    Assert.assertTrue(line.isPointAlongLine(2,2));
    Assert.assertTrue(line.isPointAlongLine(2,3));
  }

  @Test
  void Day5_LineSegment_isPointAlongLine_HorizontalLine() {
    var line = new LineSegment(new Coordinate(0,9), new Coordinate(5,9));
    Assert.assertFalse(line.isPointAlongLine(0,0));
    Assert.assertFalse(line.isPointAlongLine(6,9));
    Assert.assertFalse(line.isPointAlongLine(9,9));
    Assert.assertTrue(line.isPointAlongLine(0,9));
    Assert.assertTrue(line.isPointAlongLine(1,9));
    Assert.assertTrue(line.isPointAlongLine(2,9));
    Assert.assertTrue(line.isPointAlongLine(3,9));
    Assert.assertTrue(line.isPointAlongLine(4,9));
    Assert.assertTrue(line.isPointAlongLine(5,9));
  }

  @Test
  void Day5_LineSegment_isPointAlongLine_DiagonalLine() {
    var line = new LineSegment(new Coordinate(1,1), new Coordinate(3,3));
    Assert.assertFalse(line.isPointAlongLine(1,0));
    Assert.assertTrue(line.isPointAlongLine(1,1));
    Assert.assertTrue(line.isPointAlongLine(2,2));
    Assert.assertTrue(line.isPointAlongLine(3,3));
  }
}