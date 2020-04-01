package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF unionGrid;
    private int[][] openingGrid;
    private int openings;
    private int size;


    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            size = N;
            openings = 0;
            unionGrid = new WeightedQuickUnionUF(N * N + 2);
            openingGrid = new int[N][N];
            for (int i = 0; i < N; i += 1) {
                // connect the 0th row to the top point
                unionGrid.union(i, N * N);
                // connect the (N-1)th row to the bottom point
                //unionGrid.union(i + (N - 1) * N, N * N + 1);
                for (int j = 0; j < N; j += 1) {
                    openingGrid[i][j] = 0;
                }
            }
        }
    }


    public void open(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            if (!isOpen(row, col)) {
                openingGrid[row][col] = 1;
                openings += 1;
                connect(row, col);
                if (row == size - 1 && isFull(row, col)) {
                    int id = row * size + col;
                    unionGrid.union(size * size + 1, id);
                }
            }
        }
    }


    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            return openingGrid[row][col] > 0;
        }
    }


    public boolean isFull(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        } else if (!isOpen(row, col)) {
            return false;
        } else {
            int id = row * size + col;
            return unionGrid.connected(id, size * size);
        }
    }


    private void connect(int row, int col) {
        int id = row * size + col;
        if (row > 0 && isOpen(row - 1, col)) {
            unionGrid.union(id, id - size);
        }

        if (row < size - 1 && isOpen(row + 1, col)) {
            unionGrid.union(id, id + size);
        }

        if (col > 0 && isOpen(row, col - 1)) {
            unionGrid.union(id, id - 1);
        }

        if (col < size - 1 && isOpen(row, col + 1)) {
            unionGrid.union(id, id + 1);
        }
    }


    public int numberOfOpenSites() {
        return openings;
    }


    public boolean percolates() {
        return unionGrid.connected(size * size, size * size + 1);
    }


    public static void main(String[] args) {

    }

}
