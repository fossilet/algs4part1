/*
 Algorithms, Part 1, Week 1, Coursera
 http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 Jun 25 2014
 */

public class Percolation {
    private int vtop;
    private int vbott;
    private int num; // size
    private boolean[][] sites;
    // uf1 is for solving backwash.
    private WeightedQuickUnionUF uf, uf1;

    public Percolation(int N) {
        // create N-by-N grid, with all sites blocked
        // 0 is blocked, and 1 is open.
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        num = N;
        vtop = num * num;
        vbott = num * num + 1;
        // The last two are virtual top and bottom sites.
        uf = new WeightedQuickUnionUF(num * num + 2);
        // Only use virtual top site.
        uf1 = new WeightedQuickUnionUF(num * num + 1);
        sites = new boolean[num][num];
        // initialize sites.
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                sites[i][j] = false;
            }
        }
    }

    private int xyton(int x, int y) {
        // Mapping two-dimensional array indices to one-dimensional array index.
        return x * num + y;
    }

    private void validateInd(int i, int j) {
        // Validate site indices, between 1 and N.
        if (i < 1 || j < 1 || i > num || j > num) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void open(int i, int j) {
        validateInd(i, j);
        // open site (row i, column j) if it is not already
        if (!sites[i - 1][j - 1]) {
            sites[i - 1][j - 1] = true;
            // adjacent open sites
            // normal coordiates, from 0 to N - 1.
            int in = i - 1;
            int jn = j - 1;
            int[][] adjSites = {{in - 1, jn}, {in + 1, jn},
                    {in, jn - 1}, {in, jn + 1}};
            for (int k = 0; k < 4; k++) {
                int x = adjSites[k][0];
                int y = adjSites[k][1];
                // connect to valid and open neighbors
                if (0 <= x && x <= num - 1 && 0 <= y && y <= num - 1 && sites[x][y]) {
                    uf.union(xyton(i - 1, j - 1), xyton(x, y));
                    uf1.union(xyton(i - 1, j - 1), xyton(x, y));
                }
            }
            // connect to respective virtual site if on top or bottom row.
            // This tricky part learn from:
            // https://github.com/agjacome/algs4part1-class
            if (i == 1) {
                uf.union(vtop, xyton(i - 1, j - 1));
                uf1.union(vtop, xyton(i - 1, j - 1));
            }
            if (i == num) {
                uf.union(vbott, xyton(i - 1, j - 1));
            }
        }
    }

    public boolean isOpen(int i, int j) {
        validateInd(i, j);
        // is site (row i, column j) open?
        return sites[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        validateInd(i, j);
        // is site (row i, column j) full?
        // test if the site is connected with the virtual top site
        // This is the critical part I failed to conceive by myself.
        return uf1.connected(vtop, xyton(i - 1, j - 1));
    }

    public boolean percolates() {
        // does the system percolate?
        return uf.connected(vtop, vbott);
    }

    public static void main(String[] args) {
    }
}
