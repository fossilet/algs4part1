/**
 * Percolation stats.
 * Created by tux on 6/26/14.
 */

public class PercolationStats {

    private double[] fractions;
    private double mean_val;
    private double stddev_val;

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
        mean_val = sum / fractions.length;
        return mean_val;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sum = 0;
        for (double fraction : fractions) {
            sum += Math.pow(mean_val - fraction, 2);
        }
        stddev_val = Math.pow(sum / (fractions.length - 1), 0.5);
        return stddev_val;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean_val - 1.96 * stddev_val / Math.pow(fractions.length, 0.5);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean_val + 1.96 * stddev_val / Math.pow(fractions.length, 0.5);
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
