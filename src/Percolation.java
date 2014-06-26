/*
 Algorithms, Princeton Unviersity
Jun 25 2014
 */

public class Percolation {
    private int vtop;
    private int vbott;
    private int num; // size
    private int[][] sites;
    private WeightedQuickUnionUF uf;

    private int xyton(int x, int y) {
        // Mapping two-dimensional array indices to one-dimensional array index.
        return x*num+y;
    }

    private void val_ind(int i, int j) {
        // Validate site indices, between 1 and N.
        if (i < 1 || j < 1 || i > num || j > num) {
            throw new IndexOutOfBoundsException();
        }
    }

    public Percolation(int N) throws IllegalArgumentException {
        // create N-by-N grid, with all sites blocked
        // 0 is blocked, and 1 is open.
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        num = N;
        vtop = num*num;
        vbott = num*num + 1;
        // The last two are virtual top and bottom sites.
        uf = new WeightedQuickUnionUF(num * num + 2);
        sites = new int[num][num];
        // initialize sites.
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                sites[i][j] = 0;
            }
        }
    }

    public void open(int i, int j) throws IndexOutOfBoundsException {
        val_ind(i, j);
        // open site (row i, column j) if it is not already
        if (sites[i - 1][j - 1] == 0) {
            sites[i - 1][j - 1] = 1;
            // connect to respective virtual site if on top or bottom row.
            if (i == 1) {
                uf.union(vtop, xyton(i - 1, j - 1));
            }
            if (i == num) {
                uf.union(vbott, xyton(i - 1, j - 1));
            }
            // adjacent open sites
            // normal coordiates, from 0 to N - 1.
            int in = i - 1;
            int jn = j - 1;
            int[][] adj_sites = {{in - 1, jn}, {in + 1, jn}, {in, jn - 1}, {in, jn + 1}};
            for (int k = 0; k < 4; k++) {
                int x = adj_sites[k][0];
                int y = adj_sites[k][1];
                // connect to valid and open neighbors
                if (0 < x && x < num - 1 && 0 < y && y < num - 1 && sites[x][y] == 1) {
                    uf.union(xyton(i - 1, j - 1), xyton(x, y));
                }
            }
        }
    }

    public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {
        val_ind(i, j);
        // is site (row i, column j) open?
        return sites[i - 1][j - 1] == 1;
    }

    public boolean isFull(int i, int j) throws IndexOutOfBoundsException {
        val_ind(i, j);
        // is site (row i, column j) full?
        // test if the site is connected with the virtual top site
        return uf.connected(vtop, xyton(i-1, j-1));
    }

    public boolean percolates() throws IndexOutOfBoundsException {
        // does the system percolate?
        // whether the virtual top site is connected to the virtual bottom site.
        return uf.connected(vtop, vbott);
    }

    public static void main(String[] args) {
    }
}
