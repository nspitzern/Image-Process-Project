package ImageProcess;

import ImageProcess.Filters.EdgeDetectionFilter;
import ImageProcess.Filters.PrewittFilter;
import ImageProcess.Filters.RobertsFilter;
import ImageProcess.Filters.SobelFilter;

public class ImageProcess {

    public Image negative(Image img) {
        return Effects.negative(img);
    }

    public Image brightness(Image img, double v) {
        return Brightness.addBrightness(img, v);
    }

    public Image multByValue(Image img, double v) {
        return Brightness.multByValue(img, v);
    }

    public Image blurImage(Image img, int filterWidth, int filterHeight) {
        return FilterBased.blurImage(img, filterWidth, filterHeight);
    }

    public Image gaussianBlur(Image img, int size, double sigma) {
        return FilterBased.gaussianBlur(img, size, sigma);
    }

    public Image RGB2GreyScale(Image img) {
        return Colors.RGB2GreyScale(img);
    }

    public Image sharpen(Image img) {
        return FilterBased.sharpen(img);
    }

    public Image edgeDetection(Image img, EdgeDetection.type type) {
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
        return EdgeDetection.edgeDetection(img, filter);
    }

    public Image cannyEdgeDetection(Image img, double highThreshold, double lowThreshold) {
        return EdgeDetection.cannyEdgeDetection(img, highThreshold, lowThreshold);
    }

    public Image hybrid(Image src, Image other) {
        return Effects.hybrid(src, other);
    }

    public Image squareFocus(Image img, int squareX, int squareY, int squareWidth, int squareHeight) {
        return Effects.squareFocus(img, squareX, squareY, squareWidth, squareHeight);
    }

    public Image bilinearResize(Image img, int newWidth, int newHeight) {
        return Resize.bilinearResize(img, newWidth, newHeight);
    }

    public Image nearestNeighbourResize(Image img, int newWidth, int newHeight) {
        return Resize.nearestNeighbourResize(img, newWidth, newHeight);
    }

    /*****
     * Converts a colored image into a Sepia-colored image.
     * @param img an image.
     * @return a Sepia-colored image.
     */
    public Image sepia(Image img) {
        return Effects.sepia(img);
    }

    /*****
     * Flips an image 180 degrees around the Y axis.
     * @param img the original image.
     * @return a flipped image.
     */
    public Image flip180Y(Image img) {
        return Flips.flip180Y(img);
    }

    /*****
     * Flips an image's red channel 180 degrees around the Y axis.
     * @param img the original image.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @return a flipped image.
     */
    public Image flip180YColor(Image img, int color) {
        return Flips.flip180YColor(img, color);
    }

    /*****
     * Flips an image 180 degrees around the X axis.
     * @param img the original image.
     * @return a flipped image.
     */
    public Image flip180X(Image img) {
        return Flips.flip180X(img);
    }

    /*****
     * Flips an image's red channel 180 degrees around the X axis.
     * @param img the original image.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @return a flipped image.
     */
    public Image flip180XColor(Image img, int color) {
        return Flips.flip180XColor(img, color);
    }

    /*****
     * Flips an image's red channel 90 degrees.
     * @param img the original image.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @return a flipped image.
     */
    public Image flip90Color(Image img, int color) {
        return Flips.flip90Color(img, color);
    }

    /*****
     * Flips an image's red channel 90 degrees.
     * @param img the original image.
     * @return a flipped image.
     */
    public Image flip90(Image img) {
        return Flips.flip90(img);
    }

    public FFT FFT(Image img) {
        FFT fft = new FFT(img);
        fft.forward();
        return fft;
    }

    public Image inverseFFT(FFT fft) {
        fft.inverse();
        return fft.getImage();
    }

    public Image histogramEqualization(Image img) {
        return Colors.histogramEqualization(img);
    }

    /****
     * Enhance the image according to a logarithmic/linear/exponential function.
     * The type of the function depends on the power (power of 1 is linear, 0 < power < 1 is logarithmic,
     * and 1 < power is exponential).
     * @param img the image
     * @param power the power of the function
     * @param constant a constant
     * @return an enhanced image
     */
    public Image imageEnhancement(Image img, double power, double constant) {
        return Colors.imageEnhancement(img, power, constant);
    }

    public Image LUT(Image img, double brightnessParam, double constant) {
        return Colors.LUT(img, brightnessParam, constant);
    }

    /*****
     *
     * @param img the original image
     * @param swirlX the x position of the swirl center
     * @param swirlY the y position of the swirl center
     * @param swirlRadius the radius of the swirl
     * @param swirlTwists number of twists. positive is counter clockwise, negative is clockwise).
     *                    ! works best with numbers between 0 and 1.
     * @return a twisted image
     */
    public Image swirl(Image img, double swirlX, double swirlY, double swirlRadius, double swirlTwists) {
        return Effects.swirl(img, swirlX, swirlY, swirlRadius, swirlTwists);
    }
}
