package ImageProcess;

import ImageProcess.Filters.Filter;
import ImageProcess.Filters.Filter2D;

class ImageProcessMath {

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

    static double bilinearInterpolation(Image img, double x, double y, int chan) {
        double lowX, lowY, highX, highY;
        // calculate 4 surrounding pixels in original image
        lowX = x;
        lowY = y;
        highX = x + 1;
        highY = y + 1;

        // prevent getting out of the image bound
        if (highX >= img.getWidth() || highY >= img.getHeight()) {
            return 0;
        }

        // calculate ratio of x points and y points
        double d1, d2;
        d1 = (x - lowX) / (highX - lowX);
        d2 = (highX - x) / (highX - lowX);

        double q1, q2;
        // interpolate low pixel
        q1 = img.getPixel((int)lowY, (int)lowX, chan) * d2 + img.getPixel((int)lowY, (int)highX, chan) * d1;

        // interpolate high pixel
        q2 = img.getPixel((int)highY, (int)lowX, chan) * d2 + img.getPixel((int)highY, (int)highX, chan) * d1;

        double d3, d4;
        d3 = (y - lowY) / (highY - lowY);
        d4 = (highY - y) / (highY - lowY);

        double q;
        // interpolate middle pixel
        q = q1 * d4 + q2 * d3;

        return q;
    }

    static double nearestNeighbourInterpolation(Image img, double x, double y, int chan) {
        int curr_x = (int)Math.round(x), curr_y = (int)Math.round(y);

        if (curr_x >= img.getWidth() || curr_y >= img.getHeight()) {
            return 0;
        }

        return img.getPixel(curr_y,  curr_x, chan);
    }
}
