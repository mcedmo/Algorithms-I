import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] segmentsTemp;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        Point[] sortByPoint = points.clone();
        Arrays.sort(sortByPoint);
        checkDuplicates(sortByPoint);
        List<LineSegment> results = new ArrayList<LineSegment>();
        int n = sortByPoint.length;

        // check if the two points are equal
        for (int p = 0; p < n - 3; p++) {
            for (int q = 1 + p; q < n - 2; q++) {
                double targetSlope = sortByPoint[p].slopeTo(sortByPoint[q]);
                for (int r = 1 + q; r < n - 1; r++) {
                    if (sortByPoint[q].slopeTo(sortByPoint[r]) == targetSlope) {
                        for (int s = 1 + r; s < n; s++) {
                            if (sortByPoint[q].slopeTo(sortByPoint[s]) == targetSlope) {
                                LineSegment newSegment = new LineSegment(sortByPoint[p],
                                                                         sortByPoint[s]);
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

}
