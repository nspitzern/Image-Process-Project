package ImageProcess;

import ImageProcess.Filters.EdgeDetectionFilter;
import ImageProcess.Filters.PrewittFilter;
import ImageProcess.Filters.RobertsFilter;
import ImageProcess.Filters.SobelFilter;

public class ImageProcess {
    private Image img;

    public ImageProcess(Image img) {
        this.img = img;
    }

    public Image getImage() {
        return this.img;
    }

    public void loadImage(Image img) {
        this.img = img;
    }

    public void loadImage(String path) {
        this.img = new Image(path);
    }

    public ImageProcess negative() {
        this.img = Effects.negative(this.img);
        return this;
    }

    public ImageProcess brightness(double v) {
        this.img = Brightness.addBrightness(this.img, v);
        return this;
    }

    public ImageProcess multByValue(double v) {
        this.img = Brightness.multByValue(this.img, v);
        return this;
    }

    public ImageProcess blurImage(int filterWidth, int filterHeight) {
        this.img = FilterBased.blurImage(this.img, filterWidth, filterHeight);
        return this;
    }

    public ImageProcess gaussianBlur(int size, double sigma) {
        this.img = FilterBased.gaussianBlur(this.img, size, sigma);
        return this;
    }

    public ImageProcess RGB2GreyScale() {
        this.img = Colors.RGB2GreyScale(this.img);
        return this;
    }

    public ImageProcess sharpen() {
        this.img = FilterBased.sharpen(this.img);
        return this;
    }

    public ImageProcess edgeDetection(EdgeDetection.type type) {
        EdgeDetectionFilter filter;
        switch (type) {
            case PREWITT:
                filter = new PrewittFilter();
                break;
            case ROBERTS:
                filter = new RobertsFilter();
                break;
            case SOBEL:
                filter = new SobelFilter();
                break;
            default:
                return null;
        }
        this.img = EdgeDetection.edgeDetection(this.img, filter);
        return this;
    }

    public ImageProcess cannyEdgeDetection(double highThreshold, double lowThreshold) {
        this.img = EdgeDetection.cannyEdgeDetection(this.img, highThreshold, lowThreshold);
        return this;
    }

    public ImageProcess hybrid(Image other) {
        this.img = Effects.hybrid(this.img, other);
        return this;
    }

    public ImageProcess squareFocus(int squareX, int squareY, int squareWidth, int squareHeight) {
        this.img = Effects.squareFocus(this.img, squareX, squareY, squareWidth, squareHeight);
        return this;
    }

    public ImageProcess bilinearResize(int newWidth, int newHeight) {
        this.img = Resize.bilinearResize(this.img, newWidth, newHeight);
        return this;
    }

    public ImageProcess nearestNeighbourResize(int newWidth, int newHeight) {
        this.img = Resize.nearestNeighbourResize(this.img, newWidth, newHeight);
        return this;
    }

    /*****
     * Converts a colored image into a Sepia-colored image.
     * @a Sepia-colored image.
     */
    public ImageProcess sepia() {
        this.img = Effects.sepia(this.img);
        return this;
    }

    /*****
     * Flips an image 180 degrees around the Y axis.
     * @a flipped image.
     */
    public ImageProcess flip180Y() {
        this.img = Flips.flip180Y(this.img);
        return this;
    }

    /*****
     * Flips an image's red channel 180 degrees around the Y axis.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @a flipped image.
     */
    public ImageProcess flip180YColor(int color) {
        this.img = Flips.flip180YColor(this.img, color);
        return this;
    }

    /*****
     * Flips an image 180 degrees around the X axis.
     * @a flipped image.
     */
    public ImageProcess flip180X() {
        this.img = Flips.flip180X(this.img);
        return this;
    }

    /*****
     * Flips an image's red channel 180 degrees around the X axis.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @a flipped image.
     */
    public ImageProcess flip180XColor(int color) {
        this.img = Flips.flip180XColor(this.img, color);
        return this;
    }

    /*****
     * Flips an image's red channel 90 degrees.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @a flipped image.
     */
    public ImageProcess flip90Color(int color) {
        this.img = Flips.flip90Color(this.img, color);
        return this;
    }

    /*****
     * Flips an image's red channel 90 degrees.
     * @a flipped image.
     */
    public ImageProcess flip90() {
        this.img = Flips.flip90(this.img);
        return this;
    }

    public FFT FFT() {
        FFT fft = new FFT(this.img);
        fft.forward();
        return fft;
    }

    public Image inverseFFT(FFT fft) {
        fft.inverse();
        return fft.getImage();
    }

    public ImageProcess histogramEqualization() {
        this.img = Colors.histogramEqualization(this.img);
        return this;
    }

    /****
     * Enhance the image according to a logarithmic/linear/exponential function.
     * The type of the function depends on the power (power of 1 is linear, 0 < power < 1 is logarithmic,
     * and 1 < power is exponential).
     * @param power the power of the function
     * @param constant a constant
     * @an enhanced image
     */
    public ImageProcess imageEnhancement(double power, double constant) {
        this.img = Colors.imageEnhancement(this.img, power, constant);
        return this;
    }

    public ImageProcess LUT(double brightnessParam, double constant) {
        this.img = Colors.LUT(this.img, brightnessParam, constant);
        return this;
    }

    /*****
     *
     * @param swirlX the x position of the swirl center
     * @param swirlY the y position of the swirl center
     * @param swirlRadius the radius of the swirl
     * @param swirlTwists number of twists. positive is counter clockwise, negative is clockwise).
     *                    ! works best with numbers between 0 and 1.
     * @a twisted image
     */
    public ImageProcess swirl(double swirlX, double swirlY, double swirlRadius, double swirlTwists) {
        this.img = Effects.swirl(this.img, swirlX, swirlY, swirlRadius, swirlTwists);
        return this;
    }
}
