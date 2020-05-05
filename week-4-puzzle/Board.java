import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {

    private final int[][] startBoard;
    private final int width;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        width = tiles.length;
        this.startBoard = copy(tiles);
    }

    private int[][] copy(int[][] tiles) {
        int[][] copy = new int[width][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    // string representation of this board
    public String toString() {
        StringBuilder toString = new StringBuilder(String.valueOf(width));
        for (int i = 0; i < width; i++) {
            toString.append("\n");
            for (int j = 0; j < width; j++) {
                toString.append(" " + startBoard[i][j]);
            }
        }
        return toString.toString();
    }

    // board dimension n
    public int dimension() {
        return width;
    }

    // number of tiles out of place
    public int hamming() {
        int wrongPosition = 0;
        int target = 0;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < width; col++) {
                target++;
                int current = startBoard[row][col];
                if (current != target && current != 0) {
                    wrongPosition++;
                }
            }
        }
        return wrongPosition;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanSum = 0;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < width; col++) {
                int current = startBoard[row][col];
                if (current != 0) {
                    int[] goal = oneDToXy(current);
                    manhattanSum += Math.abs(row - goal[0]) + Math
                            .abs(col - goal[1]);
                }
            }
        }
        return manhattanSum;
    }

    private int[] oneDToXy(int oneD) {
        oneD--;
        int[] xy = new int[2];
        xy[0] = oneD / width;
        xy[1] = oneD % width;
        return xy;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || !(y.getClass() == this.getClass()) || ((Board) y).dimension() != this
                .dimension())
            return false;
        return Arrays.deepEquals(((Board) y).startBoard, this.startBoard);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<Board>();

        int[] openLocation = openLocation();

        if (openLocation[0] > 0) neighbors.add(new Board(
                swap(openLocation[0], openLocation[1], openLocation[0] - 1, openLocation[1])));
        if (openLocation[0] < width - 1) neighbors.add(new Board(
                swap(openLocation[0], openLocation[1], openLocation[0] + 1, openLocation[1])));
        if (openLocation[1] > 0) neighbors.add(new Board(
                swap(openLocation[0], openLocation[1], openLocation[0], openLocation[1] - 1)));
        if (openLocation[1] < width - 1) neighbors.add(new Board(
                swap(openLocation[0], openLocation[1], openLocation[0], openLocation[1] + 1)));

        return neighbors;
    }

    private int[][] swap(int aRow, int aCol, int bRow, int bCol) {
        int[][] copy = copy(startBoard);
        int tempTile = copy[aRow][aCol];
        copy[aRow][aCol] = copy[bRow][bCol];
        copy[bRow][bCol] = tempTile;
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (startBoard[0][0] != 0) {
            if (startBoard[0][1] != 0) {
                return new Board(swap(0, 0, 0, 1));
            }
            else return new Board(swap(0, 0, 1, 0));
        }
        else return new Board(swap(0, 1, 1, 0));
    }

    private int[] openLocation() {
        int[] open = new int[2];
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < width; col++) {
                if (startBoard[row][col] == 0) {
                    open[0] = row;
                    open[1] = col;
                }
            }
        }
        return open;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            for (Board b : initial.neighbors()) {
                StdOut.println(b);
            }
        }
    }
}
