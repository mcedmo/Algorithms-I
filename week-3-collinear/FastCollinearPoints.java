import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private final LineSegment[] segmentsTemp;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        Point[] sortByPoint = points.clone();
        Arrays.sort(sortByPoint);
        checkDuplicates(sortByPoint);
        List<LineSegment> results = new ArrayList<LineSegment>();

        int n = points.length;
        for (int i = 0; i < n; i++) {
            Point p = sortByPoint[i];
            Point[] pointsBySlope = sortByPoint.clone();
            Arrays.sort(pointsBySlope, p.slopeOrder());

            int j = 1;
            while (j < n) {

                List<Point> possible = new ArrayList<Point>();
                double targetSlope = p.slopeTo(pointsBySlope[j]);
                do {
                    possible.add(pointsBySlope[j++]);
                } while (j < n && p.slopeTo(pointsBySlope[j]) == targetSlope);
                if (possible.size() >= 3 && p.compareTo(possible.get(0)) < 0) {
                    LineSegment newSegment = new LineSegment(p, possible.get(possible.size() - 1));
                    results.add(newSegment);

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
