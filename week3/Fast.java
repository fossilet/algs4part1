import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * * http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 * Created by tux on 2014-09-29.
 */
public class Fast {
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

        double[] slopes = new double[num];
        for (int j = 0; j < num; j++) {
            slopes[j] = points[0].slopeTo(points[j]);
        }

//        for (double slope : slopes) {
//            StdOut.println(slope);
//        }

        // find colinear points in a loop
        Point[] points1 = points.clone();
        HashSet<ArrayList<Point>> pp = new HashSet<ArrayList<Point>>();
        for (int k = 0; k < num; k++) {
            // sort by slope to the first point.
            Arrays.sort(points, points1[k].SLOPE_ORDER);
            for (int m = 0; m < num; m++) {
                slopes[m] = points[0].slopeTo(points[m]);
//                StdOut.printf("%s %.2f\n", points[m], slopes[m]);
            }

//            for (double slope : slopes) {
//                StdOut.println(slope);
//            }

            // Find colinear points
            HashSet<Integer> a = new HashSet<Integer>();
            for (int n = 1; n < num - 1; n++) {
                if (slopes[n] == slopes[n + 1]) {
//                    StdOut.printf("%s %s\n", printf[n], points[n+1]);
                    a.add(n);
                    a.add(n + 1);
                }
            }
            a.add(0);
            Integer[] aa = a.toArray(new Integer[a.size()]);
            if (aa.length < 3) continue;
            ArrayList<Point> pl = new ArrayList<Point>();
            for (int i = 0; i < aa.length; i++) {
                pl.add(points[aa[i]]);
//                if (i != aa.length - 1) {
//                    StdOut.printf("%s -> ", points[aa[i]]);
//                } else {
//                    StdOut.printf("%s", points[aa[i]]);
//                }
            }
            Collections.sort(pl);
            pp.add(pl);
//            StdOut.println(pl);
//            StdOut.println("*********");
        }
        // TODO: merge duplicate lines
        for (ArrayList<Point> pl : pp) {
            for (int i = 0; i < pl.size(); i++) {
                if (i != pl.size() - 1) {
                    StdOut.printf("%s -> ", pl.get(i));
                } else {
                    StdOut.printf("%s\n", pl.get(i));
                }
//            StdOut.println(pl);
            }
        }
    }
}
