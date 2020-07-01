package ImageProcess;

public class FFT {
    private Image img;
    private double[][] real;
    private double[][] imagine;
    private double[][] amps;
    private double[][] dataOut;

    FFT(Image img) {
        this.img = img;
        this.real = new double[img.getHeight()][img.getWidth()];
        this.imagine = new double[img.getHeight()][img.getWidth()];
        this.amps = new double[img.getHeight()][img.getWidth()];
        this.dataOut = new double[img.getHeight()][img.getWidth()];
    }

    public double[][] getAmps() {
        return this.amps;
    }

    public double[][] getImagine() {
        return this.imagine;
    }

    public double[][] getReal() {
        return this.real;
    }

    void forward() {
        int width = this.img.getWidth(), height = this.img.getHeight();

        for (int imgRow = 0; imgRow < height; imgRow++) {
            for (int imgCol = 0; imgCol < width; imgCol++) {
                // Two inner loops iterate on input data.
                for (int ySpace = 0; ySpace < height; ySpace++) {
                    for (int xSpace = 0; xSpace < width; xSpace++) {

                        double val = 2 * Math.PI * ((1.0 * imgCol * xSpace / width) + (1.0 * imgRow * ySpace / height));
                        this.real[imgRow][imgCol] += (this.img.getPixel(ySpace, xSpace, 0) * Math.cos(val)) / Math.sqrt(width * height);

                        this.imagine[imgRow][imgCol] -= (this.img.getPixel(ySpace, xSpace, 0) * Math.sin(val)) / Math.sqrt(width * height);
                    }
                }
                this.amps[imgRow][imgCol] = Math.sqrt(this.real[imgRow][imgCol] * this.real[imgRow][imgCol] +
                        this.imagine[imgRow][imgCol] * this.imagine[imgRow][imgCol]);

            }
        }
    }

    void inverse() {
        int width = this.img.getWidth(), height = this.img.getHeight();

        for (int imgRow = 0; imgRow < height; imgRow++) {
            for (int imgCol = 0; imgCol < width; imgCol++) {
                // Two inner loops iterate on input data.
                for (int ySpace = 0; ySpace < height; ySpace++) {
                    for (int xSpace = 0; xSpace < width; xSpace++) {

                        double val = 2 * Math.PI * ((1.0 * imgCol * xSpace / width) + (1.0 * imgRow * ySpace / height));
                        this.dataOut[ySpace][xSpace] += (this.real[imgRow][imgCol] * Math.cos(val) -
                                this.imagine[imgRow][imgCol] * Math.sin(val)) / Math.sqrt(width * height);
                    }
                }
            }
        }
    }

    public Image getImage() {
        int width = this.img.getWidth(), height = this.img.getHeight();

        Image newImg = new Image(width, height, 3);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                newImg.setRed(row, col, this.dataOut[row][col]);
                newImg.setGreen(row, col, this.dataOut[row][col]);
                newImg.setBlue(row, col, this.dataOut[row][col]);
            }
        }

        return newImg;
    }

    public Image getFrequenciesImage() {
        int width = this.img.getWidth(), height = this.img.getHeight();

        Image newImg = new Image(width, height, 3);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                newImg.setRed(row, col, this.amps[row][col]);
                newImg.setGreen(row, col, this.amps[row][col]);
                newImg.setBlue(row, col, this.amps[row][col]);
            }
        }

        return newImg;
    }
}
