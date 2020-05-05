import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private boolean solvable;
    private BoardState endBoardState;

    private static class BoardState implements Comparable<BoardState> {
        private final Board board;
        private final int moves;
        private final int manhattan;
        private final BoardState previous;

        public BoardState(Board board) {
            this.board = board;
            this.previous = null;
            this.moves = 0;
            this.manhattan = board.manhattan() + moves;
        }

        public BoardState(Board board, BoardState previous) {
            this.board = board;
            this.previous = previous;
            this.moves = previous.moves + 1;
            this.manhattan = board.manhattan() + moves;
        }

        public int compareTo(BoardState other) {
            return this.manhattan - other.manhattan;
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<BoardState> boards = new MinPQ<BoardState>();
        boards.insert(new BoardState(initial));
        MinPQ<BoardState> twinBoards = new MinPQ<BoardState>();
        twinBoards.insert(new BoardState(initial.twin()));
        boolean twinSolvable = false;
        solvable = false;

        while (!solvable && !twinSolvable) {
            solvable = nextStep(boards);
            twinSolvable = nextStep(twinBoards);
        }
    }

    private boolean nextStep(MinPQ<BoardState> currentState) {
        BoardState searchNode = currentState.delMin();

        if (searchNode.manhattan - searchNode.moves == 0) {
            endBoardState = searchNode;
            return true;
        }
        for (Board neighbor : searchNode.board.neighbors()) {
            if (searchNode.previous == null || !neighbor.equals(searchNode.previous.board)) {
                currentState.insert(new BoardState(neighbor, searchNode));
            }
        }
        return false;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable()) return this.endBoardState.moves;
        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> solutions = new Stack<Board>();
        if (!isSolvable()) {
            solutions = null;
            return solutions;
        }

        BoardState current = endBoardState;
        while (current.previous != null) {
            solutions.push(current.board);
            current = current.previous;
        }
        solutions.push(current.board);
        return solutions;

    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
