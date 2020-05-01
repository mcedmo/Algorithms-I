public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // check if the two points are equal 
        for (int i = 0; i < points.length; i++) {
            for (int x = i; x < points.length; x++) {
                double slope = points[i].slopeTo(points[x]);

                for ()
            }
        }
    }

    public int numberOfSegments()        // the number of line segments

    public LineSegment[] segments()                // the line segments
}
