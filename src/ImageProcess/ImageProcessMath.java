package ImageProcess;

import ImageProcess.Filters.Filter;
import ImageProcess.Filters.Filter2D;

class ImageProcessMath {
//    static BufferedImage conv2D(BufferedImage img, Filter2D filter) {
//        int imgWidth, imgHeight, filterWidth, filterHeight;
//        imgWidth = img.getWidth();
//        imgHeight = img.getHeight();
//        filterHeight = filter.getHeight();
//        filterWidth = filter.getWidth();
//
//        BufferedImage newImg = img;
//
//        try {
//            for (int imgRow = 0; imgRow < imgHeight; imgRow++) {
//                for (int imgCol = 0; imgCol < imgWidth; imgCol++) {
//
//                    int pixelRGB = newImg.getRGB(imgCol, imgRow);
//                    Pixel p = new Pixel(pixelRGB);
//                    Pixel convSum = new Pixel(0, 0, 0);
//
//                    // Go over the filter
//                    for (int fRow = 0; fRow < filterHeight; fRow++) {
//
//                        for (int fCol = 0; fCol < filterWidth; fCol++) {
//                            // Calculate filter value, and image x,y indices
//                            double fval = filter.getValue(fRow, fCol);
//                            int imgX = imgCol + fCol - filterWidth / 2;
//                            int imgY = imgRow + fRow - filterHeight / 2;
//
//                            // if the index is outside of the original image - go to the next iteration
//                            if (imgX < 0 || imgY < 0 || imgX >= imgWidth || imgY >= imgHeight) {
//                                continue;
//                            }
//
//                            convSum.setRed(convSum.getRed() + (int) (new Pixel(img.getRGB(imgX, imgY)).getRed() * fval));
//                            convSum.setGreen(convSum.getGreen() + (int) (new Pixel(img.getRGB(imgX, imgY)).getGreen() * fval));
//                            convSum.setBlue(convSum.getBlue() + (int) (new Pixel(img.getRGB(imgX, imgY)).getBlue() * fval));
//                        }
//                    }
//
//                    newImg.setRGB(imgCol, imgRow, convSum.getRGB());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return newImg;
//    }

    static Image conv2D(Image img, Filter filter) {
        int imgWidth, imgHeight, filterWidth, filterHeight;
        imgWidth = img.getWidth();
        imgHeight = img.getHeight();
        filterHeight = filter.getHeight();
        filterWidth = filter.getWidth();

        Image newImg = img.copy();

        try {
            for (int imgRow = 0; imgRow < imgHeight; imgRow++) {
                for (int imgCol = 0; imgCol < imgWidth; imgCol++) {
                    double redSum = 0, greenSum = 0, blueSum = 0;

                    // Go over the filter
                    for (int fRow = 0; fRow < filterHeight; fRow++) {

                        for (int fCol = 0; fCol < filterWidth; fCol++) {
                            // Calculate filter value, and image x,y indices
                            double fval = filter.getValue(fRow, fCol);
                            int imgX = imgCol + fCol - filterWidth / 2;
                            int imgY = imgRow + fRow - filterHeight / 2;

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
}
