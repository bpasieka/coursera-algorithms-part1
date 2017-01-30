import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author Dima Pasieka
 */
public class KdTree
{
    // Vertical division
    private static final int VERTICAL = 1;

    // Horizontal division
    private static final int HORIZONTAL = 0;

    // Root element of the 2d-tree
    private Node root;

    // Size of the 2d-tree
    private int size;

    // Node representation in 2d-tree
    private static class Node {
        // The point
        private Point2D point;

        // Division/Color of the Node (red/vertical or black/horizontal)
        private int division;

        // The left/bottom subtree
        private Node left;

        // The right/top subtree
        private Node right;

        private Node(Point2D point) {
            this.point = point;
            left = null;
            right = null;
        }
    }

    // Construct an empty set of points
    public KdTree()
    {
        root = null;
        size = 0;
    }

    // If the set empty
    public boolean isEmpty()
    {
        return (root == null);
    }


    // Number of points in the set
    public int size()
    {
        return size;
    }


    // Add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p == null) {
            throw new NullPointerException("Can't add empty point");
        }

        if (!contains(p)) {
            Node node = new Node(p);

            root = insertNode(node, root);
            size++;
        }
    }

    // If the set contain point p
    public boolean contains(Point2D p)
    {
        if (p == null) {
            throw new NullPointerException("Can't check empty point");
        }

        Node current = root;

        while (true) {
            if (current == null) {
                return false;
            }

            if (current.point.equals(p)) {
                return true;
            }

            if (isLessThanPoint(p, current.point, current.division)) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
    }

    // Draw all points to standard draw
    public void draw()
    {
        drawNode(root, 0, 1, 0, 1);
    }

    // All points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null) {
            throw new NullPointerException("Can't use empty rectangle");
        }

        Queue<Point2D> range = new Queue<>();

        rangeNode(root, rect, range);

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

        return nearestNode(p, root, root.point);
    }

    // Insert a node for specific parent node
    private Node insertNode(Node node, Node parentNode) {
        if (parentNode == null) {
            node.division = VERTICAL;
            return node;
        }

        if (isLessThanPoint(node.point, parentNode.point, parentNode.division)) {
            if (parentNode.left == null) {
                node.division = getDivisionByNode(parentNode);
                parentNode.left = node;
            } else {
                parentNode.left = insertNode(node, parentNode.left);
            }
        } else {
            if (parentNode.right == null) {
                node.division = getDivisionByNode(parentNode);
                parentNode.right = node;
            } else {
                parentNode.right = insertNode(node, parentNode.right);
            }
        }

        return parentNode;
    }

    private void drawNode(Node node, double minX, double maxX, double minY, double maxY)
    {
        if (node == null) {
            return;
        }

        drawPoint(node.point);
        drawDivision(node, minX, maxX, minY, maxY);
        if (node.division == VERTICAL) {
            drawNode(node.left, minX, node.point.x(), minY, maxY);
            drawNode(node.right, node.point.x(), maxX, minY, maxY);
        } else {
            drawNode(node.left, minX, maxX, minY, node.point.y());
            drawNode(node.right, minX, maxX, node.point.y(), maxY);
        }
    }

    // Draw a point
    private void drawPoint(Point2D point)
    {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);

        point.draw();
    }

    // Draw node division
    private void drawDivision(Node node, double minX, double maxX, double minY, double maxY)
    {
        StdDraw.setPenRadius();

        if (node.division == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), minY, node.point.x(), maxY);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(minX, node.point.y(), maxX, node.point.y());
        }
    }

    // Check range points inside of the node
    private void rangeNode(Node node, RectHV rect, Queue<Point2D> range)
    {
        if (node == null) {
            return;
        }

        if (rect.contains(node.point)) {
            range.enqueue(node.point);
        }

        if (node.division == VERTICAL) {
            if (node.point.x() > rect.xmin()) {
                rangeNode(node.left, rect, range);
            }

            if (node.point.x() <= rect.xmax()) {
                rangeNode(node.right, rect, range);
            }
        } else {
            if (node.point.y() > rect.ymin()) {
                rangeNode(node.left, rect, range);
            }

            if (node.point.y() <= rect.ymax()) {
                rangeNode(node.right, rect, range);
            }
        }
    }

    private Point2D nearestNode(Point2D point, Node node, Point2D nearest)
    {
        if (node == null) {
            return nearest;
        }

        if (node.point.distanceTo(point) < nearest.distanceTo(point)) {
            nearest = node.point;
        }

        Point2D nearestPossibleFromAnotherNode;
        Node morePrioritizedNode;
        Node lessPrioritizedNode;

        if (node.division == VERTICAL) {
            nearestPossibleFromAnotherNode = new Point2D(node.point.x(), point.y());

            if (point.x() < node.point.x()) {
                morePrioritizedNode = node.left;
                lessPrioritizedNode = node.right;
            } else {
                morePrioritizedNode = node.right;
                lessPrioritizedNode = node.left;
            }
        } else {
            nearestPossibleFromAnotherNode = new Point2D(point.x(), node.point.y());

            if (point.y() < node.point.y()) {
                morePrioritizedNode = node.left;
                lessPrioritizedNode = node.right;
            } else {
                morePrioritizedNode = node.right;
                lessPrioritizedNode = node.left;
            }
        }

        nearest = nearestNode(point, morePrioritizedNode, nearest);

        if (nearestPossibleFromAnotherNode.distanceTo(point) < nearest.distanceTo(point)) {
            nearest = nearestNode(point, lessPrioritizedNode, nearest);
        }

        return nearest;
    }

    // Get opposite direction to node
    private int getDivisionByNode(Node node)
    {
        if (node.division == VERTICAL) {
            return HORIZONTAL;
        }

        return VERTICAL;
    }

    // Check if point less than another point (depends on division)
    private boolean isLessThanPoint(Point2D nodePoint, Point2D rootPoint, int division)
    {
        if (division == VERTICAL) {
            return nodePoint.x() < rootPoint.x();
        }

        return nodePoint.y() < rootPoint.y();
    }

    // Unit testing of the methods (optional)
    public static void main(String[] args)
    {
        // To implement
    }
}
