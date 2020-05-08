import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    private Node root;
    private int size;
    private Queue<Point2D> pointsInRangeQ;
    private Point2D champion;
    private double distance = 0;

    private static class Node {
        private final Point2D point;
        private Node left;
        private Node right;
        private final RectHV rect;

        public Node(Point2D p, RectHV rect) {
            this.point = p;
            this.rect = rect;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        // calls helper method
        root = insert(root, p, VERTICAL, 0, 0, 1, 1);
    }

    // insert helper method
    private Node insert(Node x, Point2D point, boolean direction, double xmin, double ymin,
                        double xmax, double ymax) {
        if (x == null) {
            size++;
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax));
        }
        if (x.point.equals(point)) return x;
        double cmp = comparePoints(point, x, direction);

        if (direction == VERTICAL) {
            // left of a vertical
            if (cmp < 0) {
                x.left = insert(x.left, point, !direction, xmin, ymin, x.point.x(), ymax);
            }
            // right of a vertical
            else {
                x.right = insert(x.right, point, !direction, x.point.x(), ymin, xmax, ymax);
            }
        }
        else {
            // below horizontal
            if (cmp < 0) {
                x.left = insert(x.left, point, !direction, xmin, ymin, xmax, x.point.y());
            }
            // above horizontal
            else {
                x.right = insert(x.right, point, !direction, xmin, x.point.y(), xmax, ymax);
            }
        }

        return x;

    }

    // compares a point and the point of a node
    private double comparePoints(Point2D point, Node x, boolean direction) {
        return (direction == VERTICAL) ? point.x() - x.point.x() : point.y() - x.point.y();
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node x, Point2D p, boolean direction) {
        if (x == null) return false;
        if (p.equals(x.point)) return true;
        double cmp = comparePoints(p, x, direction);
        if (cmp < 0) return contains(x.left, p, !direction);
        else return contains(x.right, p, !direction);
    }

    // draw all points to standard draw
    public void draw() {
        if (root == null) return;
        // call helper method
        draw(root, VERTICAL);

    }

    // draw helper
    private void draw(Node x, boolean direction) {
        if (x == null) return;
        StdDraw.setPenColor();
        StdDraw.setPenRadius(0.01);
        x.point.draw();

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        if (direction == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.point.x(), x.rect.ymin(), x.point.x(), x.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.point.y(), x.rect.xmax(), x.point.y());
        }

        draw(x.left, !direction);
        draw(x.right, !direction);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        pointsInRangeQ = new Queue<Point2D>();
        if (isEmpty()) return pointsInRangeQ;
        pointsInRange(root, rect);
        return pointsInRangeQ;
    }

    private void pointsInRange(Node x, RectHV rect) {
        if (x.point.x() >= rect.xmin() && x.point.x() <= rect.xmax() && x.point.y() >= rect.ymin()
                && x.point.y() <= rect.ymax()) pointsInRangeQ.enqueue(x.point);
        if (x.left != null && rect.intersects(x.left.rect)) pointsInRange(x.left, rect);
        if (x.right != null && rect.intersects(x.right.rect)) pointsInRange(x.right, rect);
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        distance = root.point.distanceSquaredTo(p);

        return nearest(p, root, root.point, VERTICAL);
    }

    private Point2D nearest(Point2D p, Node x, Point2D champ, boolean direction) {
        if (x == null) return champ;
        if (x.rect.distanceSquaredTo(p) < distance) {
            if (x.point.distanceSquaredTo(p) < distance) {
                champ = x.point;
                distance = x.point.distanceSquaredTo(p);
            }
            if (comparePoints(p, x, direction) < 0) {
                champ = nearest(p, x.left, champ, !direction);
                champ = nearest(p, x.right, champ, !direction);
            }
            else {
                champ = nearest(p, x.right, champ, !direction);
                champ = nearest(p, x.left, champ, !direction);
            }
        }
        return champ;
    }

}
