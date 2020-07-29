package ImageProcess;

import ImageProcess.Filters.Filter;

public class ImageProcessMath {

    static Image conv2D(Image img, Filter filter) {
        int imgWidth, imgHeight, filterWidth, filterHeight;
        imgWidth = img.getWidth();
        imgHeight = img.getHeight();
        filterHeight = filter.getHeight();
        filterWidth = filter.getWidth();

        Image newImg = new Image(img.getWidth(), img.getHeight(), img.getChannel());

        try {
            for (int imgRow = 0; imgRow < imgHeight; imgRow++) {
                for (int imgCol = 0; imgCol < imgWidth; imgCol++) {
                    double redSum = 0, greenSum = 0, blueSum = 0;

                    // Go over the filter
                    for (int fRow = 0; fRow < filterHeight; fRow++) {

                        for (int fCol = 0; fCol < filterWidth; fCol++) {
                            // Calculate filter value, and image x,y indices
                            double fval = filter.getValue(fRow, fCol);
                            int imgX = imgCol + fCol;
                            int imgY = imgRow + fRow;

                            // if the index is outside of the original image - go to the next iteration
                            if (imgX < 0 || imgY < 0 || imgX >= imgWidth || imgY >= imgHeight) {
                                continue;
                            }

                            redSum += (int) img.getRed(imgY, imgX) * fval;
                            greenSum += (int) img.getGreen(imgY, imgX) * fval;
                            blueSum += (int) img.getBlue(imgY, imgX) * fval;
                        }
                    }

                    newImg.setRed(imgRow, imgCol, (int)redSum);
                    newImg.setGreen(imgRow, imgCol, (int)greenSum);
                    newImg.setBlue(imgRow, imgCol, (int)blueSum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newImg;
    }

    static double[] bilinearInterpolation(Image img, double x, double y, int chans) {
        double lowX, lowY, highX, highY;
        // calculate 4 surrounding pixels in original image
        lowX = x;
        lowY = y;
        highX = x + 1;
        highY = y + 1;

        double q1, q2;
        double d3, d4;
        double[] q = new double[chans];

        for (int chan = 0; chan < chans; chan++) {
            // prevent getting out of the image bound
            if (highX >= img.getWidth() || highY >= img.getHeight()) {
                q[chan] = 0;
            } else {
                // calculate ratio of x points and y points
                double d1, d2;
                d1 = (x - lowX) / (highX - lowX);
                d2 = (highX - x) / (highX - lowX);

                // interpolate low pixel
                q1 = img.getPixel((int) lowY, (int) lowX, chan) * d2 + img.getPixel((int) lowY, (int) highX, chan) * d1;

                // interpolate high pixel
                q2 = img.getPixel((int) highY, (int) lowX, chan) * d2 + img.getPixel((int) highY, (int) highX, chan) * d1;

                d3 = (y - lowY) / (highY - lowY);
                d4 = (highY - y) / (highY - lowY);

                // interpolate middle pixel
                q[chan] = q1 * d4 + q2 * d3;
            }
        }

        return q;
    }

    static double[] nearestNeighbourInterpolation(Image img, double x, double y, int chans) {
        double[] pixVal = new double[chans];

        for (int chan = 0; chan < chans; chan++) {
            int curr_x = (int)Math.round(x), curr_y = (int)Math.round(y);

            if (curr_x >= img.getWidth() || curr_y >= img.getHeight()) {
                pixVal[chan] = 0;
            }

            pixVal[chan] = img.getPixel(curr_y,  curr_x, chan);
        }


        return pixVal;
    }
}
