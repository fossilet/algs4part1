/**
 * Percolation stats.
 * Created by tux on 6/26/14.
 */

public class PercolationStats {

    private double[] fractions;

    // Perform one computational experiments on an N-by-N grid
    private double Compute(int N) {
        int x;
        int y;
        Percolation perc = new Percolation(N);
        while (!perc.percolates()) {
            x = StdRandom.uniform(1, N + 1);
            y = StdRandom.uniform(1, N + 1);
            perc.open(x, y);
//            System.out.printf("%d %d\n----\n", x, y);
        }
        // Calculate threshold.
        int open_num = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (perc.isOpen(i + 1, j + 1)) {
                    open_num += 1;
                }
            }
        }
        double frac;
        frac = ((double) open_num) / (N * N);
        return frac;
    }

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) throws IllegalArgumentException {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        fractions = new double[T];
        for (int i = 0; i < T; i++) {
            fractions[i] = Compute(N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (double fraction : fractions) {
            sum += fraction;
        }
        return sum / fractions.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sum = 0;
        double m = mean();
        for (double fraction : fractions) {
            sum += Math.pow(m - fraction, 2);
        }
        return Math.pow(sum / (fractions.length - 1), 0.5);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        double m = mean();
        double dv = stddev();
        return m - 1.96 * dv / Math.pow(fractions.length, 0.5);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        double m = mean();
        double dv = stddev();
        return m + 1.96 * dv / Math.pow(fractions.length, 0.5);
    }

    // test client, described below
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[0]);
        PercolationStats percst = new PercolationStats(N, T);
        System.out.printf("mean                    = %.16f\n", percst.mean());
        System.out.printf("stddev                  = %.16f\n", percst.stddev());
        System.out.printf("95%% confidence interval = %.16f, %.16f\n",
                percst.confidenceLo(), percst.confidenceHi());
    }
}
