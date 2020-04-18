/* *****************************************************************************
 *  Name:              Matt Edmond
 *  Coursera User ID:  123456
 *  Last modified:     4/8/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int width;
    private final int count;
    private final WeightedQuickUnionUF uf;
    private boolean[] grid;
    private int openCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        width = n;
        grid = new boolean[width * width];
        uf = new WeightedQuickUnionUF(width * width + 2);
        count = uf.count();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        openSite(row, col);
        connectToVirtual(row, col);
        connectLeft(row, col);
        connectRight(row, col);
        connectTop(row, col);
        connectBottom(row, col);
    }

    private void openSite(int row, int col) {
        if (!isOpen(row, col)) {
            grid[xyTo1D(row, col)] = true;
            openCount++;
        }
    }

    private void connectToVirtual(int row, int col) {
        if (row == 1) {
            uf.union(count - 2, xyTo1D(row, col));
        }
        if (row == width) {
            uf.union(count - 1, xyTo1D(row, col));
        }
    }

    private void connectRight(int row, int col) {
        if (col < width && isOpen(row, col + 1)) {
            uf.union(xyTo1D(row, col + 1), xyTo1D(row, col));
        }
    }

    private void connectLeft(int row, int col) {
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(xyTo1D(row, col - 1), xyTo1D(row, col));
        }
    }

    private void connectTop(int row, int col) {
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        }
    }

    private void connectBottom(int row, int col) {
        if (row < width && isOpen(row + 1, col)) {
            uf.union(xyTo1D(row + 1, col), xyTo1D(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[xyTo1D(row, col)];
    }

    private void validate(int row, int col) {
        if (row < 1 || row > width || col < 1 || col > width) {
            throw new IllegalArgumentException("row or col not in range");
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf.find(xyTo1D(row, col)) == uf.find(count - 2);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(count - 1) == uf.find(count - 2);
    }

    // convert row, col to 1d array
    private int xyTo1D(int row, int col) {
        return (row - 1) * width + (col - 1);
    }

}


