package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testZeroDistance() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);
        Assert.assertEquals(p1.distance(p2), 0);
    }

    @Test
    public void testXDistance() {
        Point p1 = new Point(10, 0);
        Point p2 = new Point(5, 0);
        Assert.assertEquals(p1.distance(p2), 5);
    }

    @Test
    public void testYDistance() {
        Point p1 = new Point(0, 20);
        Point p2 = new Point(0, 10);
        Assert.assertEquals(p1.distance(p2), 10);
    }

    @Test
    public void testSomeDistance() {
        Point p1 = new Point(3, 0);
        Point p2 = new Point(0, 4);
        Assert.assertEquals(p1.distance(p2), 5);
    }
}
