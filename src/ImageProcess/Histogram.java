package ImageProcess;

public class Histogram {
    private double[] red;
    private double[] green;
    private double[] blue;

    public Histogram() {
        this.red = new double[255];
        this.green = new double[255];
        this.blue = new double[255];
        initUniformHist();
    }

    public Histogram(Histogram hist) {
        this.red = new double[255];
        this.green = new double[255];
        this.blue = new double[255];

        for (int i = 0; i < 255; i++) {
            this.red[i] = hist.getRed(i);
            this.green[i] = hist.getGreen(i);
            this.blue[i] = hist.getBlue(i);
        }
    }

    public Histogram(Image img) {
        this.red = new double[255];
        this.green = new double[255];
        this.blue = new double[255];

        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                int redIdx, greenIdx, blueIdx;

                redIdx = (int)img.getRed(row, col);
                greenIdx = (int)img.getGreen(row, col);
                blueIdx = (int)img.getBlue(row, col);

                this.red[redIdx]++;
                this.green[greenIdx]++;
                this.blue[blueIdx]++;
            }
        }
    }

    /******
     * Initialize a uniform histogram.
     */
    public void initUniformHist() {
        for (int i = 0; i < 255; i++) {
            this.red[i] = 1;
            this.green[i] = 1;
            this.blue[i] = 1;
        }
    }

    public double[] getRed() {
        return this.red;
    }

    public double[] getGreen() {
        return this.green;
    }

    public double[] getBlue() {
        return this.blue;
    }

    public void setRed(double[] red) {
        this.red = nonNegArr(red);
    }

    public void setGreen(double[] green) {
        this.green = nonNegArr(green);
    }

    public void setBlue(double[] blue) {
        this.blue = nonNegArr(blue);
    }

    public double getRed(int i) {
        return this.red[i];
    }

    public double getGreen(int i) {
        return this.green[i];
    }

    public double getBlue(int i) {
        return this.blue[i];
    }

    public void setRed(int i, double red) {
        this.red[i] = nonNeg(red);
    }

    public void setGreen(int i, double green) {
        this.green[i] = nonNeg(green);
    }

    public void setBlue(int i, double blue) {
        this.blue[i] = nonNeg(blue);
    }

    private double nonNeg(double v) {
        return Math.max(0, v);
    }

    private double[] nonNegArr(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = nonNeg(arr[i]);
        }
        return arr;
    }

    public void normalizeHist(double val) {
        for (int i = 0; i < 255; i++) {
            this.red[i] /= val;
            this.green[i] /= val;
            this.blue[i] /= val;
        }
    }

    public void normalizeHist() {
        for (int i = 0; i < 255; i++) {
            this.red[i] /= 255;
            this.green[i] /= 255;
            this.blue[i] /= 255;
        }
    }
}
