import java.util.Arrays;

public class FastCollinearPoints {

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points);
        for (int y = 0; y < points.length; y++) {
            Point p = points[y];
            Point[] pointsBySlope = points.clone();
            Arrays.sort(pointsBySlope, p.slopeOrder());

            for (int i = 0; i < pointsBySlope.length - 2; i++) {
                if (pointsBySlope[i] == pointsBySlope[i + 1] && pointsBySlope[i] == pointsBySlope[i
                        + 2]) {
                    for (int j = i + 3; j <)
                }
            }
        }
    }

/*    // the number of line segments
    public int numberOfSegments() {

    }

    // the line segments
    public LineSegment[] segments() {

    }*/
}
