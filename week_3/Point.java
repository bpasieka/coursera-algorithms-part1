/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    // Points comparator
    private class PointComparator implements Comparator<Point>
    {
        // compare two points
        public int compare(Point p1, Point p2)
        {
            double p1Slope = slopeTo(p1);
            double p2Slope = slopeTo(p2);

            if (p1Slope > p2Slope) {
                return 1;
            }

            if (p1Slope < p2Slope) {
                return -1;
            }

            // if equals
            return 0;
        }
    }

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double ySlope = that.y - y;
        double xSlope = that.x - x;

        // (x0, y0) and (x1, y1) are equal
        if (ySlope == 0 && xSlope == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        // line segment is horizontal
        if (ySlope == 0) {
            return +0.0;
        }

        // line segment is vertical
        if (xSlope == 0) {
            return Double.POSITIVE_INFINITY;
        }

        return (ySlope / xSlope);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        // first check by 'y'
        if (y > that.y) {
            return 1;
        }

        if (y < that.y) {
            return -1;
        }

        // then check by 'x'
        if (x > that.x) {
            return 1;
        }

        if (x < that.x) {
            return -1;
        }

        // if equals
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p1 = new Point(10, 20);
        Point p2 = new Point(20, 25);
        Point p3 = new Point(10, 20);

        Point p4 = new Point(10, 20);
        Point p5 = new Point(1, 20);
        Point p6 = new Point(5, 1);
        Point p7 = new Point(32128, 32128);
        Point p8 = new Point(32128, 32128);
        Point p9 = new Point(10, 123);

        Point p10 = new Point(10, 30);
        Point p11 = new Point(30, 20);

        assert (p1.compareTo(p2) < 0) : p1 + " " + p2;
        assert (p1.compareTo(p3) == 0) : p1 + " " + p3;
        assert (p2.compareTo(p3) > 0) : p2 + " " + p3;

        assert (p4.compareTo(p5) > 0) : p4 + " " + p5;
        assert (p5.compareTo(p4) < 0) : p5 + " " + p4;
        assert (p5.compareTo(p6) > 0) : p5 + " " + p6;
        assert (p4.compareTo(p6) > 0) : p4 + " " + p6;
        assert (p7.compareTo(p8) == 0) : p7 + " " + p8;


        assert (Double.compare(p7.slopeTo(p8), Double.NEGATIVE_INFINITY) == 0) : "Wrong value for degraded line";
        assert (Double.compare(p4.slopeTo(p9), Double.POSITIVE_INFINITY) == 0) : "Wrong value for vertical line";
        assert (Double.compare(p4.slopeTo(p5), +0.0) == 0) : "Wrong value for horizontal line";
        assert (Double.compare(p1.slopeTo(p2), 0.5) == 0);
        assert (Double.compare(p10.slopeTo(p11), -0.5) == 0);


        assert (p4.slopeOrder().compare(p3, p5) < 0);
        assert (p4.slopeOrder().compare(p5, p3) > 0);
        assert (p1.slopeOrder().compare(p7, p8) == 0);
    }
}
