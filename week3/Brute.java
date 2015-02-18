import java.util.Arrays;

/**
 * http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 * Created by tux on 2014-09-29.
 */
public class Brute {

    public static void main(String[] args) {
        // initilize points
        In in = new In(args[0]);
        int num = in.readInt();
        int[] coors = in.readAllInts();
        Point[] points = new Point[num];
        in.close();

        for (int i = 0; i < num; i++) {
            points[i] = new Point(coors[2 * i], coors[2 * i + 1]);
        }

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        for (int p = 0; p < num; p++) {
            for (int q = 0; q < p; q++) {
                for (int r = 0; r < q; r++) {
                    for (int s = 0; s < r; s++) {
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) &&
                                points[p].slopeTo(points[r]) == points[p].slopeTo(points[s])) {
                            Point[] dots = new Point[4];
                            dots[0] = points[p];
                            dots[1] = points[q];
                            dots[2] = points[r];
                            dots[3] = points[s];
                            Arrays.sort(dots);
                            // Print
                            StdOut.printf("%s -> %s -> %s -> %s\n", (Object[])dots);

                            // TODO: OK?
                            dots[0].drawTo(dots[3]);
                        }
                    }
                }
            }
        }
        // display to screen all at once
        StdDraw.show(0);
        // reset the pen radius
        StdDraw.setPenRadius();
    }
}
