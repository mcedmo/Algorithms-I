import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private LineSegment[] segmentsTemp;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        Arrays.sort(points);
        checkDuplicates(points);
        List<LineSegment> results = new ArrayList<LineSegment>();
        int n = points.length;

        // check if the two points are equal
        for (int p = 0; p < n - 3; p++) {
            for (int q = 1 + p; q < n - 2; q++) {
                double targetSlope = points[p].slopeTo(points[q]);
                for (int r = 1 + q; r < n - 1; r++) {
                    if (points[q].slopeTo(points[r]) == targetSlope) {
                        for (int s = 1 + r; s < n; s++) {
                            if (points[q].slopeTo(points[s]) == targetSlope) {
                                LineSegment newSegment = new LineSegment(points[p], points[s]);
                                results.add(newSegment);
                            }
                        }
                    }
                }
            }
        }
        segmentsTemp = results.toArray(new LineSegment[0]);

    }

    // check for duplicate points
    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("duplicate points");
            }
        }
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("contains no points");
        }
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("Contains a null point");
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentsTemp.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segmentsTemp.clone();
    }

    // client provided by Princeton university
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
