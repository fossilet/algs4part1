import java.util.Comparator;

/**
 * http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 * Created by tux on 2014-09-29.
 */

public class Point implements Comparable<Point> {

    // compare points by slope
    // FIXME: http://stackoverflow.com/q/3984257/547578
    public final Comparator<Point> SLOPE_ORDER = new slopeOrder();

    public class slopeOrder implements Comparator<Point> {
        public int compare(Point q, Point r) {
            if (slopeTo(q) > slopeTo(r)) {
                return 1;
            } else if (slopeTo(q) == slopeTo(r)) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing

    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y != that.y) {
                return Double.POSITIVE_INFINITY;
            } else {
                return Double.NEGATIVE_INFINITY;
            }
        } else {
            return ((double) (that.y - this.y)) / (that.x - this.x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        } else if (this.y == that.y) {
            if (this.x < that.x) {
                return -1;
            } else if (this.x == that.x) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point p = new Point(1, 2);
        Point q = new Point(1, 3);
        Point r = new Point(1, 2);
        Point s = new Point(4, 1);

        assert p.slopeTo(q) == Double.POSITIVE_INFINITY;
        assert p.slopeTo(r) == Double.NEGATIVE_INFINITY;
        assert p.slopeTo(s) == -1.0 / 3;
    }
}
