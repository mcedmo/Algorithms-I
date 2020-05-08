import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {


    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (points.contains(p)) return;
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> rangePoints = new Stack<Point2D>();
        if (isEmpty()) return rangePoints;
        for (Point2D point : points) {
            if (rect.contains(point)) {
                rangePoints.push(point);
            }
        }
        return rangePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D nearestPoint = null;
        double distance = 0.0;
        for (Point2D q : points) {
            double checkDistance = p.distanceSquaredTo(q);
            if (nearestPoint == null || checkDistance < distance) {
                nearestPoint = q;
                distance = checkDistance;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        PointSET kdtree = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        /*        kdtree.draw();*/
        System.out.println(kdtree.size());
        System.out.println(kdtree.nearest(new Point2D(0.3, 0.0)));

    }
}
