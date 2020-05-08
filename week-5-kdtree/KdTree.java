import edu.princeton.cs.algs4.In;
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
        private Point2D point;
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
        if (contains(p)) return;
        RectHV rect = new RectHV(0, 0, 1, 1);

        // calls helper method
        root = insert(root, p, VERTICAL, rect);
        size++;
    }

    // insert helper method
    private Node insert(Node x, Point2D point, boolean direction, RectHV rect) {
        if (x == null) return new Node(point, rect);
        double cmp = comparePoints(point, x, direction);

        if (direction == VERTICAL) {
            // left of a vertical
            if (cmp < 0) {
                x.left = insert(x.left, point, !direction,
                                new RectHV(x.rect.xmin(), x.rect.ymin(), x.point.x(),
                                           x.rect.ymax()));
            }
            // right of a vertical
            else {
                x.right = insert(x.right, point, !direction,
                                 new RectHV(x.point.x(), x.rect.ymin(), x.rect.xmax(),
                                            x.rect.ymax()));
            }
        }
        else if (direction == HORIZONTAL) {
            // below horizontal
            if (cmp < 0) {
                x.left = insert(x.left, point, !direction,
                                new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(),
                                           x.point.y()));
            }
            // above horizontal
            else {
                x.right = insert(x.right, point, !direction,
                                 new RectHV(x.rect.xmin(), x.point.y(), x.rect.xmax(),
                                            x.rect.ymax()));
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
        if (isEmpty()) return false;
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node x, Point2D p, boolean direction) {
        if (x == null) return false;
        double cmp = comparePoints(p, x, direction);
        if (cmp < 0) contains(x.left, p, !direction);
        if (cmp > 0) contains(x.right, p, !direction);
        if (p.equals(x.point)) return true;
        if (cmp == 0) {
            contains(x.left, p, !direction);
            contains(x.right, p, !direction);
        }
        return false;
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
        distance = p.distanceSquaredTo(root.point);
        champion = root.point;
        nearest(p, root, VERTICAL);
        return champion;
    }

    private void nearest(Point2D p, Node x, boolean direction) {
        if (p.distanceSquaredTo(x.point) < distance) {
            distance = p.distanceSquaredTo(x.point);
            champion = x.point;
        }

        if (x.left != null && x.left.rect.distanceSquaredTo(p) < distance)
            nearest(p, x.left, !direction);
        if (x.right != null && x.right.rect.distanceSquaredTo(p) < distance)
            nearest(p, x.right, !direction);
    }

    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        System.out.println(kdtree.contains(new Point2D(0.25, 0.75)));
    }

}
