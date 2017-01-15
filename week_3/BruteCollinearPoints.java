import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Dima Pasieka
 */
public class BruteCollinearPoints
{
    // Line segments
    private LineSegment[] lineSegments;

    // Finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        if (isNullPoints(points)) {
            throw new NullPointerException("Points array can't be null or contain null values");
        }

        // to avoid mutation
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        // first sort object to be able to compare with next value
        Arrays.sort(pointsCopy);

        if (isDuplicatedPoints(pointsCopy)) {
            throw new IllegalArgumentException("Points array can't contain duplicated points");
        }

        int pointsLength = pointsCopy.length;
        ArrayList<LineSegment> lineSegmentList = new ArrayList<>();

        // add (-3, -2 ...) indent to avoid checking with same points
        for (int p = 0; p < (pointsLength - 3); p++) {
            for (int q = (p + 1); q < (pointsLength - 2); q++) {
                for (int r = (q + 1); r < (pointsLength - 1); r++) {
                    // to avoid one loop if no need
                    if (pointsCopy[p].slopeTo(pointsCopy[q]) != pointsCopy[p].slopeTo(pointsCopy[r])) {
                        continue;
                    }

                    for (int s = (r + 1); s < pointsLength; s++) {
                        if (pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])) {
                            lineSegmentList.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }

        // transform found segments to array
        lineSegments = lineSegmentList.toArray(new LineSegment[lineSegmentList.size()]);
    }

    // The number of line segments
    public int numberOfSegments()
    {
        return lineSegments.length;
    }

    // The line segments
    public LineSegment[] segments()
    {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    // Check if there no null values
    private boolean isNullPoints(Point[] points)
    {
        if (points == null) {
            return true;
        }

        for (Point point: points) {
            if (point == null) {
                return true;
            }
        }

        return false;
    }

    // Check if there no duplicated points
    private boolean isDuplicatedPoints(Point[] points)
    {
        for (int i = 0; i < (points.length - 1); i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }

        return false;
    }

    // Test client
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}