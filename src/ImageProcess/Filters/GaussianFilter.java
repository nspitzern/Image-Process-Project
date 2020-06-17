package ImageProcess.Filters;

public class GaussianFilter implements Filter {
    private Filter2D f;

    public GaussianFilter(int size, double sigma) {
        size = (size % 2 == 0) ? size + 1 : size;
        // make the filter size odd
        size = size + 1;
        double[][] arr = new double[size][size];

        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                int x = col - size / 2;
                int y = row - size / 2;

                double val = Math.exp(-(x*x + y*y)/ (2 * sigma * sigma));
                val = val / (2 * Math.PI * sigma * sigma);

                arr[row][col] = val;
            }
        }

        arr = l1Norm(size, size, arr);

        this.f = new Filter2D(arr);
    }

    private double[][] l1Norm(int width, int height, double[][] arr) {
        int total = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                total += arr[row][col];
            }
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double pixel = arr[row][col];

                if(total != 0) {
                    pixel /= total;
                    arr[row][col] = pixel;
                } else {
                    arr[row][col] = 1.0f / (width * height);
                }
            }
        }

        return arr;
    }

    @Override
    public void multAllByValue(double v) {
        this.f.multAllByValue(v);
    }

    @Override
    public void addAllValue(double v) {
        this.f.addAllValue(v);
    }

    @Override
    public void multByValue(int row, int col, double v) {
        this.f.multByValue(row, col, v);
    }

    @Override
    public void addValue(int row, int col, double v) {
        this.f.addValue(row, col, v);
    }

    @Override
    public double getValue(int row, int col) {
        return this.f.getValue(row, col);
    }

    @Override
    public int getWidth() {
        return this.f.getWidth();
    }

    @Override
    public int getHeight() {
        return this.f.getHeight();
    }
}
