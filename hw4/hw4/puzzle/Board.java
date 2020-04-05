package hw4.puzzle;


import java.util.ArrayList;

public class Board implements WorldState {
    private int[][] grid;
    private int size;
    private int hamming;
    private int manhattan;
    private ArrayList<WorldState> boardNeighbors = new ArrayList<>();
    private Position blank;

    public Board(int[][] tiles) {
        size = tiles.length;
        grid = new int[size][size];
        hamming = 0;
        manhattan = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                int curr = tiles[i][j];
                grid[i][j] = curr;

                if (curr == 0) {
                    blank = new Position(i, j);
                } else {
                    int expRow = (curr - 1) / size;
                    int expCol = (curr - 1) % size;
                    int diff = Math.abs(i - expRow) + Math.abs(j - expCol);
                    if (diff > 0) {
                        hamming += 1;
                        manhattan += diff;
                    }
                }
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i >= size || j >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            return grid[i][j];
        }
    }


    public int size() {
        return size;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        for (Position p: blank.neighborP()) {
            boardNeighbors.add(swap(p));
        }
        return boardNeighbors;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        } else {
            Board yy = (Board) y;
            if (size != yy.size()) {
                return false;
            }
            for (int i = 0; i < size; i += 1) {
                for (int j = 0; j < size; j += 1) {
                    if (tileAt(i, j) != yy.tileAt(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        int code = 0;
        for (int i = 0; i < size; i += 1) {
            code = code * 10;
            code += tileAt(i, 0);
        }
        return code;
    }

    // helper class
    private class Position {
        int row;
        int col;

        Position(int i, int j) {
            row = i;
            col = j;
        }

        public int getR() {
            return row;
        }

        public int getC() {
            return col;
        }

        public boolean check() {
            return row >= 0 && col >= 0 && row < size && col < size;
        }

        public Iterable<Position> neighborP() {
            ArrayList<Position> neighbors = new ArrayList<>();
            ArrayList<Position> ps = new ArrayList<>();
            ps.add(new Position(row - 1, col));
            ps.add(new Position(row, col + 1));
            ps.add(new Position(row + 1, col));
            ps.add(new Position(row, col - 1));
            for (Position p : ps) {
                if (p.check()) {
                    neighbors.add(p);
                }
            }
            return neighbors;
        }
    }

    // helper method
    private Board swap(Position p) {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            System.arraycopy(grid[i], 0, copy[i], 0, size);
        }
        // exchange the value;
        int i = p.getR();
        int j = p.getC();
        int temp = copy[i][j];
        copy[i][j] = 0;
        copy[blank.getR()][blank.getC()] = temp;

        return new Board(copy);
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
