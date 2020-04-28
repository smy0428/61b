import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture pic;


    // current picture
    public SeamCarver(Picture picture) {
        // pic = picture;
        pic = new Picture(picture);
    }


    public Picture picture() {
        return pic;
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

        //int rgbCenter = pic.getRGB(x, y);
        int rgbLeft = pic.getRGB(leftX(x), leftY(y));
        int rgbRight = pic.getRGB(rightX(x), rightY(y));
        int rgbUp = pic.getRGB(upX(x), upY(y));
        int rgbDown = pic.getRGB(downX(x), downY(y));

        int xR = rgbR(rgbLeft) - rgbR(rgbRight);
        int xG = rgbG(rgbLeft) - rgbG(rgbRight);
        int xB = rgbB(rgbLeft) - rgbB(rgbRight);

        int yR = rgbR(rgbUp) - rgbR(rgbDown);
        int yG = rgbG(rgbUp) - rgbG(rgbDown);
        int yB = rgbB(rgbUp) - rgbB(rgbDown);

        double energy = xR * xR + xG * xG + xB * xB + yR * yR + yG * yG + yB * yB;

        return energy;
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
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        pic = SeamRemover.removeVerticalSeam(pic, seam);
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


    // helper method to generate the energy cost at certain level;
    private double[] energyCost(double[] prev, int y) {

        double[] curr = new double[width()];
        for (int x = 0; x < width(); x += 1) {
            curr[x] = energy(x, y) + minPrev(x, y);
        }

        return curr;
    }


    private double minPrev(int x, int y) {
        if (y == 0) {
            return 0;
        }
        double min = energy(x, y - 1);
        double d1 = energy(getLeft(x), y - 1);
        double d2 = energy(getRight(x), y - 1);
        min = Math.min(d1, min);
        min = Math.min(d2, min);

        return min;
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


    // helper method to get r, g, b in rgb respectively;
    private int rgbR(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int rgbG(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    private int rgbB(int rgb) {
        return (rgb) & 0xFF;
    }


    // helper method to get left and right pixel if it exists;
    private int getLeft(int i) {
        if (i == 0) {
            return 0;
        }
        return i - 1;
    }

    private int getRight(int i) {
        if (i == width()) {
            return i;
        }
        return i + 1;
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
    }

}
