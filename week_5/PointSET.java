import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;

/**
 * @author Dima Pasieka
 */
public class PointSET
{
    // Set of points in BST
    private SET<Point2D> points;

    // Construct an empty set of points
    public PointSET()
    {
        points = new SET<>();
    }

    // If the set empty
    public boolean isEmpty()
    {
        return points.isEmpty();
    }


    // Number of points in the set
    public int size()
    {
        return points.size();
    }


    // Add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p == null) {
            throw new NullPointerException("Can't add empty point");
        }

        points.add(p);
    }

    // If the set contain point p
    public boolean contains(Point2D p)
    {
        if (p == null) {
            throw new NullPointerException("Can't check empty point");
        }

        return points.contains(p);
    }

    // Draw all points to standard draw
    public void draw()
    {
        for (Point2D point: points) {
            point.draw();
        }
    }

    // All points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null) {
            throw new NullPointerException("Can't use empty rectangle");
        }

        Queue<Point2D> range = new Queue<>();

        for (Point2D point: points) {
            if (rect.contains(point)) {
                range.enqueue(point);
            }
        }

        return range;
    }

    // A nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        if (p == null) {
            throw new NullPointerException("Can't check empty point");
        }

        if (isEmpty()) {
            return null;
        }

        Point2D nearest = null;

        for (Point2D point: points) {
            if (nearest == null || (p.distanceTo(point) < p.distanceTo(nearest))) {
                nearest = point;
            }
        }

        return nearest;
    }

    // Unit testing of the methods (optional)
    public static void main(String[] args)
    {
        // To implement
    }
}
