package ImageProcess;

class Colors {
    static Image RGB2GreyScale(Image img) {
        Image newImg = new Image(img.getWidth(), img.getHeight(), img.getChannel());

        int width, height, channel;
        width = newImg.getWidth();
        height = newImg.getHeight();
        channel = newImg.getChannel();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                for (int chan = 0; chan < channel; chan++) {
                    double grey = (0.299f * img.getRed(row, col) + 0.587f * img.getGreen(row, col) + 0.114f * img.getBlue(row, col));

                    newImg.setPixel(row, col, chan, grey);
                }
            }
        }

        return newImg;
    }

    static Image histogramEqualization(Image img) {
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

    private static Image applyHistogram(Image img, Histogram colorVector) {
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

    /****
     * Enhance the image according to a logarithmic/linear/exponential function.
     * The type of the function depends on the power (power of 1 is linear, 0 < power < 1 is logarithmic,
     * and 1 < power is exponential).
     * @param img the image
     * @param power the power of the function
     * @param constant a constant
     * @return an enhanced image
     */
    static Image imageEnhancement(Image img, double power, double constant) {
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

    static Image LUT(Image img, double brightnessParam, double constant) {
        Image newImg = new Image(img.getWidth(), img.getHeight(), img.getChannel());
        for (int chan = 0; chan < img.getChannel(); chan++) {
            double fMean = 0;

            // calculate the mean of the image in the current channel
            for (int row = 0; row < img.getHeight(); row++) {
                for (int col = 0; col < img.getWidth(); col++) {
                    fMean += img.getPixel(row, col, chan);
                }
            }

            fMean /= (img.getHeight() * img.getWidth());

            for (int row = 0; row < img.getHeight(); row++) {
                for (int col = 0; col < img.getWidth(); col++) {
                    double newVal = ((img.getPixel(row, col, chan) - fMean + brightnessParam) / (1 - (constant / 100))) + fMean;
                    newImg.setPixel(row, col, chan, newVal);
                }
            }
        }
        return  newImg;
    }
}
