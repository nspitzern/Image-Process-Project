package ImageProcess;

public class Filter2D {
    private double[][] f;
    private int width, height;

    public Filter2D(int width, int height) {
        this.width = width;
        this.height = height;
        this.f = new double[height][width];
        initFilterOnes();
    }

    public Filter2D(double[][] arr) {
        this.width = arr[0].length;
        this.height = arr.length;
        this.f = arr;
    }

    public void multByValue(double v) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                f[i][j] *= v;
            }
        }
    }

    public double getValue(int row, int col) {
        return this.f[row][col];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private void initFilterOnes() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.f[j][i] = 1;
            }
        }
    }

    private void initFilterZeros() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.f[j][i] = 0;
            }
        }
    }
}
