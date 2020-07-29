package ImageProcess.Filters;

public abstract class BaseFilter2D implements Filter {
    private double[][] f;
    private int width, height;

    BaseFilter2D(int width, int height) {
        this.width = width;
        this.height = height;
        this.f = new double[height][width];
    }

    BaseFilter2D(double[][] arr) {
        this.width = arr[0].length;
        this.height = arr.length;
        this.f = arr;
    }

    @Override
    public void multAllByValue(double v) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.f[i][j] *= v;
            }
        }
    }

    @Override
    public void addAllValue(double v) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.f[i][j] += v;
            }
        }
    }

    @Override
    public void multByValue(int row, int col, double v) {
        this.f[row][col] *= v;
    }

    @Override
    public void addValue(int row, int col, double v) {
        this.f[row][col] += v;
    }

    @Override
    public double getValue(int row, int col) {
        return this.f[row][col];
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void initFilterOnes() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.f[j][i] = 1;
            }
        }
    }

    protected void setArr(double[][] arr) {
        if (this.height != arr.length || this.width != arr[0].length) {
            try {
                throw new Exception("Error in filter dimensions");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.f = arr;
    }
}
