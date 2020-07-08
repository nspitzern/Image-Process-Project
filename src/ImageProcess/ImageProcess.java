package ImageProcess;

import ImageProcess.Filters.*;

public class ImageProcess {

    public Image negative(Image img) {
        Image newImg = img.copy();
        for (int row = 0; row < newImg.getHeight(); row++) {
            for (int col = 0; col < newImg.getWidth(); col++) {
                newImg.setRed(row, col, 255 - (int)newImg.getRed(row, col));
                newImg.setGreen(row, col, 255 - (int)newImg.getGreen(row, col));
                newImg.setBlue(row, col, 255 - (int)newImg.getBlue(row, col));
            }
        }
        return newImg;
    }

    public Image brightness(Image img, double v) {
        Image newImg = img.copy();
        newImg.addAll(v);
        return newImg;
    }

    public Image multByValue(Image img, double v) {
        Image newImg = img.copy();
        newImg.multAll(v);
        return newImg;
    }

    public Image blurImage(Image img, int filterWidth, int filterHeight) {
        Filter f = new BlurFilter(filterWidth, filterHeight);

        return ImageProcessMath.conv2D(img, f);
    }

    public Image gaussianBlur(Image img, int size, double sigma) {
        if(size < 1 || size > img.getWidth() || size > img.getHeight()) {
            try {
                throw new Exception("Invalid filter dimensions");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Filter f = new GaussianFilter(size, sigma);

        return ImageProcessMath.conv2D(img, f);
    }

    public Image RGB2GreyScale(Image img) {
        Image newImg = img.copy();

        int width, height;
        width = newImg.getWidth();
        height = newImg.getHeight();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                for (int chan = 0; chan < img.getChannel(); chan++) {
//                    int grey = (int)(0.2126f * newImg.getRed(row, col) + 0.7152f * newImg.getGreen(row, col) + 0.0722f * newImg.getBlue(row, col));
                    int grey = (int)(0.299f * newImg.getRed(row, col) + 0.587f * newImg.getGreen(row, col) + 0.114f * newImg.getBlue(row, col));

                    newImg.setPixel(row, col, chan, grey);
                }
            }
        }

        return newImg;
    }

    public Image sharpen(Image img) {
        Filter f = new SharpFilter();

        Image newImg = ImageProcessMath.conv2D(img, f);
        newImg.limitImageColors();

        return newImg;
    }

    public Image edgeDetection(Image img, String type) {
        Image newImg = null;
        Image[] edges = null;
        switch (type) {
            case "sobel":
                edges = sobelEdgeDetection(img);
                newImg = edges[2];
                break;
            case "prewitt":
                edges = prewittEdgeDetection(img);
                newImg = edges[2];
                break;
            case "roberts":
                edges = robertsEdgeDetection(img);
                newImg = edges[2];
                break;
            default:
                Filter f = new EdgeDetectionFilter();

                newImg = ImageProcessMath.conv2D(img, f);
                newImg.limitImageColors();
                break;
        }
        return newImg;
    }

    private Image[] sobelEdgeDetection(Image img) {
        SobelFilter sobel = new SobelFilter();

        Image gxImg = ImageProcessMath.conv2D(img, sobel.getGx());
        Image gyImg = ImageProcessMath.conv2D(img, sobel.getGy());
        Image edges = img.copy();

        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                    double red = Math.pow(gxImg.getRed(row, col), 2) + Math.pow(gyImg.getRed(row, col), 2);
                    double green = Math.pow(gxImg.getRed(row, col), 2) + Math.pow(gyImg.getRed(row, col), 2);
                    double blue = Math.pow(gxImg.getRed(row, col), 2) + Math.pow(gyImg.getRed(row, col), 2);

                    edges.setRed(row, col, Math.sqrt(red));
                    edges.setGreen(row, col, Math.sqrt(green));
                    edges.setBlue(row, col, Math.sqrt(blue));
                }
            }
        return new Image[]{gxImg, gyImg, edges};
    }

    private Image[] prewittEdgeDetection(Image img) {
        PrewittFilter prewitt = new PrewittFilter();
        Image gxImg = ImageProcessMath.conv2D(img, prewitt.getGx());
        Image gyImg = ImageProcessMath.conv2D(img, prewitt.getGy());
        Image edges = img.copy();

        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                double red = Math.pow(gxImg.getRed(row, col), 2) + Math.pow(gyImg.getRed(row, col), 2);
                double green = Math.pow(gxImg.getRed(row, col), 2) + Math.pow(gyImg.getRed(row, col), 2);
                double blue = Math.pow(gxImg.getRed(row, col), 2) + Math.pow(gyImg.getRed(row, col), 2);

                edges.setRed(row, col, Math.sqrt(red));
                edges.setGreen(row, col, Math.sqrt(green));
                edges.setBlue(row, col, Math.sqrt(blue));
            }
        }
        return new Image[]{gxImg, gyImg, edges};
    }

    private Image[] robertsEdgeDetection(Image img) {
        RobertsFilter roberts = new RobertsFilter();
        Image gxImg = ImageProcessMath.conv2D(img, roberts.getGx());
        Image gyImg = ImageProcessMath.conv2D(img, roberts.getGy());
        Image edges = img.copy();

        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                double red = Math.pow(gxImg.getRed(row, col), 2) + Math.pow(gyImg.getRed(row, col), 2);
                double green = Math.pow(gxImg.getRed(row, col), 2) + Math.pow(gyImg.getRed(row, col), 2);
                double blue = Math.pow(gxImg.getRed(row, col), 2) + Math.pow(gyImg.getRed(row, col), 2);

                edges.setRed(row, col, Math.sqrt(red));
                edges.setGreen(row, col, Math.sqrt(green));
                edges.setBlue(row, col, Math.sqrt(blue));
            }
        }
        return new Image[]{gxImg, gyImg, edges};
    }

    public Image cannyEdgeDetection(Image img, double highThreshold, double lowThreshold) {
        img = RGB2GreyScale(img);

        img = gaussianBlur(img, 3, 1.5);

        Image[] sobel = sobelEdgeDetection(img);
        Image gx = sobel[0];
        Image gy = sobel[1];

        Image magnitudes = new Image(img.getWidth(), img.getHeight(), 1), angles = new Image(img.getWidth(), img.getHeight(), 1);

        // initialize magnitude and angles images
        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                    double mag = gx.getPixel(row, col, 0)*gx.getPixel(row, col, 0) + gy.getPixel(row, col, 0)*gy.getPixel(row, col, 0);
                    mag = Math.sqrt(mag);

                    double angle =  Math.atan2(gy.getPixel(row, col, 0), gx.getPixel(row, col, 0)) * 180 / Math.PI;
                    if (angle < 0) {
                        angle += 360;
                    }

                    magnitudes.setPixel(row, col, 0, mag);
                    angles.setPixel(row, col, 0, angle);
            }
        }

        Image edges = nonMaximumSuppression(magnitudes, angles);

        edges = applyThresholds(magnitudes, edges, highThreshold, lowThreshold);

        return edges;
    }

    private Image nonMaximumSuppression(Image magnitudes, Image angles) {
        Image newImg = magnitudes.copy();

        for (int row = 1; row < newImg.getHeight() - 1; row++) {
            for (int col = 1; col < newImg.getWidth() - 1; col++) {
                double currentPixel = magnitudes.getPixel(row, col, 0);

                if (angles.getPixel(row, col, 0) <= 22.5 || angles.getPixel(row, col, 0) > 157.5) { // 0 degrees
                    if (magnitudes.getPixel(row, col - 1, 0) >= currentPixel || magnitudes.getPixel(row, col + 1, 0) >= currentPixel) {
                        newImg.setPixel(row, col, 0 , 0);
                    }
                } else if (angles.getPixel(row, col, 0) <= 67.5) { // 45 degrees
                    if (magnitudes.getPixel(row + 1, col - 1, 0) >= currentPixel || magnitudes.getPixel(row - 1, col + 1, 0) >= currentPixel) {
                        newImg.setPixel(row, col, 0 , 0);
                    }
                } else if (angles.getPixel(row, col, 0) <= 112.5) { // 90 degrees
                    if (magnitudes.getPixel(row - 1, col, 0) >= currentPixel || magnitudes.getPixel(row + 1, col, 0) >= currentPixel) {
                        newImg.setPixel(row, col, 0 , 0);
                    }
                } else if (angles.getPixel(row, col, 0) <= 157.5) { // 135 degrees
                    if (magnitudes.getPixel(row - 1, col - 1, 0) >= currentPixel || newImg.getPixel(row + 1, col + 1, 0) >= currentPixel) {
                        newImg.setPixel(row, col, 0 , 0);
                    }
                }
            }
        }

        return newImg;
    }

    private Image applyThresholds(Image mags, Image edges, double highThreshold, double lowThreshold) {
        for (int row = 0; row < mags.getHeight(); row++) {
            for (int col = 0; col < mags.getWidth(); col++) {
                double currentPixel = mags.getPixel(row, col, 0);

                 if (currentPixel > highThreshold){
                    edges.setPixel(row, col, 0, 255);
                }
                // if current pixel gradient is lower than the low threshold - it is not an edge for sure
                else if(currentPixel < lowThreshold) {
                     edges.setPixel(row, col, 0, 0);
                } else if (currentPixel < highThreshold && currentPixel > lowThreshold){
                    // if current pixel is between the thresholds - check if it is near an edge pixel.
                    // if yes - it is also an edge (localization). otherwise - it isn't.

                    boolean isNeighbour = checkNeighbourPixels(mags, currentPixel, row, col, highThreshold);
                    if (!isNeighbour) {
                        edges.setPixel(row, col, 0, 0);
                    }
                }
            }
        }
        return edges;
    }

    private boolean checkNeighbourPixels(Image edges, double currentPixel, int currentRow, int currentCol, double high) {

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                // check if we exceed image boundaries
                if (0 > currentRow + i || currentRow + i >= edges.getHeight() ||
                        0 > currentCol + j || currentCol + j >= edges.getWidth()) {
                    continue;
                } else {
                    if (edges.getPixel(currentRow + i, currentCol + j, 0) > high) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Image hybrid(Image src, Image other) {
        Image lowFreqSrc = gaussianBlur(src, 11, 3);
        Image lowFreqOther = gaussianBlur(other, 11, 3);
        other.subImg(lowFreqOther);
        lowFreqSrc.addImg(other);

        return lowFreqSrc;
    }

    public Image squareFocus(Image img) {
        Image newImg = img.copy();

        Image filterImg = new Image(img.getWidth(), img.getHeight());

        for (int row = 0; row < filterImg.getHeight(); row++) {
            for (int col = 0; col < filterImg.getWidth(); col++) {
                for (int chan = 0; chan < img.getChannel(); chan++) {
                    filterImg.setPixel(row, col, chan, 0.5);
                }
            }
        }

        for (int row = filterImg.getHeight() / 4; row < 3 * (filterImg.getHeight() / 4); row++) {
            for (int col = filterImg.getWidth() / 4; col < 3 * (filterImg.getWidth() / 4); col++) {
                for (int chan = 0; chan < img.getChannel(); chan++) {
                    filterImg.setPixel(row, col, chan, 2);
                }
            }
        }

        newImg.multImg(filterImg);

        return newImg;
    }

    public Image bilinearResize(Image img, int newWidth, int newHeight) {
        try {
            if(newWidth < 0 || newHeight < 0) {
                throw new Exception("Invalid new image dimensions");
            } else {
                Image newImg = new Image(newWidth, newHeight, 3);
                int x, y;

                // Calculate scaling factor
                double row_scaling_factor = (double)newHeight/img.getHeight();
                double column_scaling_factor = (double)newWidth/img.getWidth();

                for (x = 0; x < newWidth; ++x) {
                    for (y = 0; y < newHeight; ++y) {
                        double r, g, b;

                        r = ImageProcessMath.bilinearInterpolation(img, x / column_scaling_factor, y / row_scaling_factor, 0);
                        g = ImageProcessMath.bilinearInterpolation(img, x / column_scaling_factor, y / row_scaling_factor, 1);
                        b = ImageProcessMath.bilinearInterpolation(img, x / column_scaling_factor, y / row_scaling_factor, 2);

                        newImg.setRed(y, x, r);
                        newImg.setGreen(y, x, g);
                        newImg.setBlue(y, x, b);
                    }
                }
                return newImg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Image nearestNeighbourResize(Image img, int newWidth, int newHeight) {
        Image resize = new Image(newWidth, newHeight, 3);

        //  float a = (-0.5 - (im.w-0.5))/(-0.5 - (w - 0.5));
        double a = (double) img.getWidth()/(double) newWidth;
        double a_y = (double)img.getHeight()/(double) newHeight;
        double b = -0.5 + 0.5 * a;

        //  float a_y = (-0.5 - (im.h-0.5))/(-0.5 - (h - 0.5));
        double b_y = -0.5 + 0.5 * a_y;

        for (int row = 0; row < resize.getHeight(); row++){
            for(int col = 0; col < resize.getWidth(); col++) {
                double c_pos = a * col + b;
                double r_pos = a_y * row + b_y;

                double red = ImageProcessMath.nearestNeighbourInterpolation(img, c_pos, r_pos, 0);
                resize.setRed(row, col, red);

                double green = ImageProcessMath.nearestNeighbourInterpolation(img, c_pos, r_pos, 1);
                resize.setGreen(row, col, green);

                double blue = ImageProcessMath.nearestNeighbourInterpolation(img, c_pos, r_pos, 2);
                resize.setBlue(row, col, blue);
            }
        }


        return resize;
    }

    /*****
     * Converts a colored image into a Sepia-colored image.
     * @param img an image.
     * @return a Sepia-colored image.
     */
    public Image sepia(Image img) {
        // newRed = 0.393*R + 0.769*G + 0.189*B
        // newGreen = 0.349*R + 0.686*G + 0.168*B
        // newBlue = 0.272*R + 0.534*G + 0.131*B
        Image newImg = img.copy();
        double red, green ,blue;

        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                red = img.getRed(row, col);
                green = img.getGreen(row, col);
                blue = img.getBlue(row, col);

                double newRed = 0.393 * red + 0.769 * green + 0.189 * blue;
                double newGreen = 0.349 * red + 0.686 * green + 0.168 * blue;
                double newBlue = 0.272 * red + 0.534 * green + 0.131 * blue;

                newImg.setRed(row, col, newRed);
                newImg.setGreen(row, col, newGreen);
                newImg.setBlue(row, col, newBlue);
            }
        }

        return newImg;
    }

    /*****
     * Flips an image 180 degrees around the Y axis.
     * @param img the original image.
     * @return a flipped image.
     */
    public Image flip180Y(Image img) {
        Image newImg = img.copy();

        newImg = flip180YColor(newImg, 0);
        newImg = flip180YColor(newImg, 1);
        newImg = flip180YColor(newImg, 2);

        return newImg;
    }

    /*****
     * Flips an image's red channel 180 degrees around the Y axis.
     * @param img the original image.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @return a flipped image.
     */
    public Image flip180YColor(Image img, int color) {
        Image newImg = img.copy();

        for (int row = 0; row < img.getHeight(); row++) {
            int leftX = 0, rightX = img.getWidth() - 1;

            for(; leftX < img.getWidth(); leftX++) {
                double red = img.getRed(row, leftX);
                double green = img.getGreen(row, leftX);
                double blue = img.getBlue(row, leftX);

                switch (color) {
                    case 0:
                        newImg.setRed(row, rightX, red);
                        break;
                    case 1:
                        newImg.setGreen(row, rightX, green);
                        break;
                    case 2:
                        newImg.setBlue(row, rightX, blue);
                        break;
                    default:
                        newImg.setRed(row, rightX, red);
                        break;
                }
                rightX--;
            }
        }
        return newImg;
    }

    /*****
     * Flips an image 180 degrees around the X axis.
     * @param img the original image.
     * @return a flipped image.
     */
    public Image flip180X(Image img) {
        Image newImg = img.copy();

        newImg = flip180XColor(newImg, 0);
        newImg = flip180XColor(newImg, 1);
        newImg = flip180XColor(newImg, 2);

        return newImg;
    }

    /*****
     * Flips an image's red channel 180 degrees around the X axis.
     * @param img the original image.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @return a flipped image.
     */
    public Image flip180XColor(Image img, int color) {
        Image newImg = img.copy();

        for (int col = 0; col < img.getWidth(); col++) {
            int upY = 0, downY = img.getHeight() - 1;

            for(; upY < img.getHeight(); upY++) {
                double red = img.getRed(upY, col);
                double green = img.getGreen(upY, col);
                double blue = img.getBlue(upY, col);

                switch (color) {
                    case 0:
                        newImg.setRed(downY, col, red);
                        break;
                    case 1:
                        newImg.setGreen(downY, col, green);
                        break;
                    case 2:
                        newImg.setBlue(downY, col, blue);
                        break;
                    default:
                        newImg.setRed(downY, col, red);
                        break;
                }
                downY--;
            }
        }
        return newImg;
    }

    /*****
     * Flips an image's red channel 90 degrees.
     * @param img the original image.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @return a flipped image.
     */
    public Image flip90Color(Image img, int color) {
        Image newImg = new Image(img.getHeight(), img.getWidth());

        for (int row = 0; row < img.getHeight(); row++) {

            for(int col = 0; col < img.getWidth() - 1; col++) {
                double red = img.getRed(row, col);
                double green = img.getGreen(row, col);
                double blue = img.getBlue(row, col);

                switch (color) {
                    case 0:
                        newImg.setRed(col, newImg.getWidth() - row - 1, red);
                        break;
                    case 1:
                        newImg.setGreen(col, newImg.getWidth() - row - 1, green);
                        break;
                    case 2:
                        newImg.setBlue(col, newImg.getWidth() - row - 1, blue);
                        break;
                    default:
                        newImg.setRed(col, newImg.getWidth() - row - 1, red);
                        break;
                }
            }
        }
        return newImg;
    }

    /*****
     * Flips an image's red channel 90 degrees.
     * @param img the original image.
     * @return a flipped image.
     */
    public Image flip90(Image img) {
        Image newImg = new Image(img.getHeight(), img.getWidth());

        Image redImg = flip90Color(img, 0);
        Image greenImg = flip90Color(img, 1);
        Image blueImg = flip90Color(img, 2);

        for (int row = 0; row < newImg.getHeight(); row++) {
            for (int col = 0; col < newImg.getWidth(); col++) {
                double red, green, blue;

                red = redImg.getRed(row, col);
                green = greenImg.getGreen(row, col);
                blue = blueImg.getBlue(row, col);

                newImg.setRed(row, col, red);
                newImg.setGreen(row, col, green);
                newImg.setBlue(row, col, blue);
            }
        }
        return newImg;
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
        Histogram hist = new Histogram(img);

        Histogram accumulativeHist = new Histogram(hist);

        double red, green, blue;

        for (int i = 1; i < 255; i++) {
            // get the values of the i-th value (Example: how much value of 231 are there in the red channel) of
            // the accumulative histogram and add to current i-th value of the histogram.
            red = accumulativeHist.getRed(i - 1) + hist.getRed(i);
            green = accumulativeHist.getGreen(i - 1) + hist.getGreen(i);
            blue = accumulativeHist.getBlue(i - 1) + hist.getBlue(i);

            accumulativeHist.setRed(i, red);
            accumulativeHist.setGreen(i, green);
            accumulativeHist.setBlue(i, blue);
        }

        int width = img.getWidth(), height = img.getHeight();

        // Normalize the accumulative histogram by the size of the image
        accumulativeHist.normalizeHist(width * height);

        // Initialize destination histogram with 1/255 values
        Histogram destinationHist = new Histogram();
        for (int i = 0; i < 255; i++) {
            destinationHist.setRed(i, (i + 1) / 255f);
            destinationHist.setGreen(i, (i + 1) / 255f);
            destinationHist.setBlue(i, (i + 1) / 255f);
        }

        Histogram colorVector = new Histogram();
        int src = 0, dst = 0;

        while (src < 255) {
            if(accumulativeHist.getRed(src) <= destinationHist.getRed(dst)) {
                colorVector.setRed(src, dst - 1);
                src++;
            } else {
                dst++;
            }
        }

        src = 0;
        dst = 0;

        while (src < 255) {
            if(accumulativeHist.getGreen(src) <= destinationHist.getGreen(dst)) {
                colorVector.setGreen(src, dst - 1);
                src++;
            } else {
                dst++;
            }
        }

        src = 0;
        dst = 0;

        while (src < 255) {
            if(accumulativeHist.getBlue(src) <= destinationHist.getBlue(dst)) {
                colorVector.setBlue(src, dst - 1);
                src++;
            } else {
                dst++;
            }
        }

        return applyHistogram(img, colorVector);
    }

    private Image applyHistogram(Image img, Histogram colorVector) {
        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                double red, green, blue;

                red = colorVector.getRed((int)img.getRed(row, col));
                green = colorVector.getGreen((int)img.getGreen(row, col));
                blue = colorVector.getBlue((int)img.getBlue(row, col));

                img.setRed(row, col, red);
                img.setGreen(row, col, green);
                img.setBlue(row, col, blue);
            }
        }

        return img;
    }

    public Image imageEnhancement(Image img, double power, double constant) {
        Image newImg = img.copy();

        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                for (int chan = 0; chan < img.getChannel(); chan++) {
                    double currPixel = img.getPixel(row, col, chan);

                    currPixel /= 255;

                    currPixel = constant * Math.pow(currPixel, power);

                    newImg.setPixel(row, col, chan, currPixel * 255);
                }
            }
        }
        return newImg;
    }
}
