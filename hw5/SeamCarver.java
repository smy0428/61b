import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {

    private Picture pic;
    private double[][] energy;


    // current picture
    public SeamCarver(Picture picture) {
        // pic = picture;
        pic = new Picture(picture);
        energy = energyInitial();

    }


    public Picture picture() {
        // in order to protect
        return new Picture(pic);
    }


    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateColumnIndex(x);
        validateRowIndex(y);

        return energy[y][x];
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

        rotate();
        int[] result = findVerticalSeam();
        rotate();
        return result;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        double[][] energies = new double[height()][width()];
        int[][] parent = new int[height()][width()];
        int flag = -1;


        for (int i = 0; i < height(); i += 1) {
            for (int j = 0; j < width(); j += 1) {

                if (i == 0) {
                    energies[i][j] = energy(j, i);
                    parent[i][j] = flag;


                } else {
                    double prevMin = energies[i - 1][j];
                    int temp = j;

                    if (j > 0) {
                        double left = energies[i - 1][j - 1];
                        if (Double.compare(left, prevMin) < 0) {
                            prevMin = left;
                            temp = j - 1;
                        }
                    }

                    if (j < width() - 1) {
                        double right = energies[i - 1][j + 1];
                        if (Double.compare(right, prevMin) < 0) {
                            prevMin = right;
                            temp = j + 1;
                        }
                    }

                    // record the min energy and parents
                    energies[i][j] = energy(j, i) + prevMin;
                    parent[i][j] = temp;
                }
            }
        }

        // get the MIN point at the bottom line
        double min = Double.MAX_VALUE;
        int index = 0;
        for (int k = 0; k < width(); k += 1) {
            double curr = energies[height() - 1][k];
            if (Double.compare(curr, min) < 0) {
                min = curr;
                index = k;
            }
        }

        // loop through the parent to get the int[]
        int[] result = new int[height()];
        int h = height() - 1;
        while (h >= 0) {
            result[h] = index;
            index = parent[h][index];
            h -= 1;
        }

        return result;
    }


    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        pic = SeamRemover.removeHorizontalSeam(pic, seam);
        energy = energyInitial();
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        pic = SeamRemover.removeVerticalSeam(pic, seam);
        energy = energyInitial();
    }

    private void validateRowIndex(int row) {
        if (row < 0 || row >= height()) {
            throw new IndexOutOfBoundsException(
                    "row index must be between 0 and " + (height() - 1) + ": " + row);
        }
    }

    private void validateColumnIndex(int col) {
        if (col < 0 || col >= width()) {
            throw new IndexOutOfBoundsException(
                    "column index must be between 0 and " + (width() - 1) + ": " + col);
        }
    }


    // helper method to get the position
    private int leftX(int col) {
        return (col - 1 + width()) % width();
    }

    private int rightX(int col) {
        return (col + 1) % width();
    }

    private int upX(int col) {
        return col;
    }

    private int downX(int col) {
        return col;
    }

    private int leftY(int row) {
        return row;
    }

    private int rightY(int row) {
        return row;
    }

    private int upY(int row) {
        return (row - 1 + height()) % height();
    }

    private int downY(int row) {
        return (row + 1 + height()) % height();
    }


    // to transpose the image fit for the horizontalSeam
    private void rotate() {
        Picture var = new Picture(height(), width());
        for (int i = 0; i < width(); i += 1) {
            for (int j = 0; j < height(); j += 1) {
                var.set(j, i, pic.get(i, j));
            }
        }

        pic = var;
        energy = energyInitial();
    }


    // initial generate the energy matrix
    private double[][] energyInitial() {

        int h = pic.height();
        int w = pic.width();

        double[][] result = new double[h][w];
        for (int i = 0; i < h; i += 1) {
            for (int j = 0; j < w; j += 1) {
                Color rgbLeft = pic.get(leftX(j), leftY(i));
                Color rgbRight = pic.get(rightX(j), rightY(i));
                Color rgbUp = pic.get(upX(j), upY(i));
                Color rgbDown = pic.get(downX(j), downY(i));

                int xR = rgbLeft.getRed() - rgbRight.getRed();
                int xG = rgbLeft.getGreen() - rgbRight.getGreen();
                int xB = rgbLeft.getBlue() - rgbRight.getBlue();

                int yR = rgbUp.getRed() - rgbDown.getRed();
                int yG = rgbUp.getGreen() - rgbDown.getGreen();
                int yB = rgbUp.getBlue() - rgbDown.getBlue();

                double curr = xR * xR + xG * xG + xB * xB + yR * yR + yG * yG + yB * yB;

                result[i][j] = curr;
            }
        }
        return result;
    }
}
