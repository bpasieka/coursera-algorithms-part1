import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Dima Pasieka
 */
public class FastCollinearPoints
{
    // Line segments - array representation
    private LineSegment[] lineSegments;

    // Line segments - array list representation
    private ArrayList<LineSegment> lineSegmentList = new ArrayList<>();

    // To store starting points of each line segment
    private HashMap<Double, ArrayList<Point>> foundStartingPoints = new HashMap<>();

    // Finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
    {
        if (isNullPoints(points)) {
            throw new NullPointerException("Points array can't be null or contain null values");
        }

        // to avoid mutation + to be able to sort it separately from origin
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        // first sort object to be able to compare with next value
        Arrays.sort(pointsCopy);

        if (isDuplicatedPoints(pointsCopy)) {
            throw new IllegalArgumentException("Points array can't contain duplicated points");
        }

        for (Point startingPoint: points) {
            // sort by slope from current point
            Arrays.sort(pointsCopy, startingPoint.slopeOrder());

            // to store points that lie on the same line segment
            ArrayList<Point> slopePoints = new ArrayList<>();
            double currentSlope = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;

            for (int i = 1; i < pointsCopy.length; i++) {
                currentSlope = startingPoint.slopeTo(pointsCopy[i]);

                if (currentSlope == previousSlope) {
                    slopePoints.add(pointsCopy[i]);
                } else {
                    addSegment(slopePoints, startingPoint, previousSlope);

                    slopePoints.clear();
                    // add first point of the line for next iterations
                    slopePoints.add(pointsCopy[i]);
                }

                previousSlope = currentSlope;
            }

            addSegment(slopePoints, startingPoint, previousSlope);
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

    private void addSegment(ArrayList<Point> slopePoints, Point startingPoint, double slopeKey)
    {
        // we are looking only for 4+ lines that lie on the same line segment
        if (slopePoints.size() < 3) {
            return;
        }

        // starting point should be always included (as we started from it)
        slopePoints.add(startingPoint);

        // get starting points with such a slope key/value
        ArrayList<Point> startingPoints = foundStartingPoints.get(slopeKey);
        Collections.sort(slopePoints);

        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);

        if (startingPoints == null) {
            startingPoints = new ArrayList<>();
            startingPoints.add(startPoint);
            foundStartingPoints.put(slopeKey, startingPoints);
        } else {
            // if the line with such starting point was already added
            for (Point point: startingPoints) {
                if (startPoint.compareTo(point) == 0) {
                    return;
                }
            }
            startingPoints.add(startPoint);
        }

        lineSegmentList.add(new LineSegment(startPoint, endPoint));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
