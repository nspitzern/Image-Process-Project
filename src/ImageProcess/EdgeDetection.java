package ImageProcess;

import ImageProcess.Filters.*;

class EdgeDetection {
    static Image edgeDetection(Image img, String type) {
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

    private static Image[] sobelEdgeDetection(Image img) {
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

    private static Image[] prewittEdgeDetection(Image img) {
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

    private static Image[] robertsEdgeDetection(Image img) {
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

    static Image cannyEdgeDetection(Image img, double highThreshold, double lowThreshold) {
        img = Colors.RGB2GreyScale(img);

        img = FilterBased.gaussianBlur(img, 3, 1.5);

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

                magnitudes.setPixel(row, col, 0, mag);
                angles.setPixel(row, col, 0, angle);
            }
        }

        nonMaximumSuppression(magnitudes, angles);

        Image edges = applyThresholds(magnitudes, highThreshold, lowThreshold);

        return edges;
    }

    private static void nonMaximumSuppression(Image magnitudes, Image angles) {
        for (int row = 1; row < magnitudes.getHeight() - 1; row++) {
            for (int col = 1; col < magnitudes.getWidth() - 1; col++) {
                double currentPixel = magnitudes.getPixel(row, col, 0);

                if ((angles.getPixel(row, col, 0) <= 22.5 && angles.getPixel(row, col, 0) >= -22.5) ||
                        (angles.getPixel(row, col, 0) < -157.5 && angles.getPixel(row, col, 0) >= -180)) { // 0 degrees
                    if (magnitudes.getPixel(row, col - 1, 0) > currentPixel || magnitudes.getPixel(row, col + 1, 0) > currentPixel) {
                        magnitudes.setPixel(row, col, 0 , 0);
                    }
                } else if ((angles.getPixel(row, col, 0) >= 22.5 && angles.getPixel(row, col, 0) <= 67.5) ||
                        (angles.getPixel(row, col, 0) >= -157.5 && angles.getPixel(row, col, 0) < -112.5)) { // 45 degrees
                    if (magnitudes.getPixel(row - 1, col - 1, 0) > currentPixel || magnitudes.getPixel(row + 1, col + 1, 0) > currentPixel) {
                        magnitudes.setPixel(row, col, 0 , 0);
                    }
                } else if ((angles.getPixel(row, col, 0) >= 67.5 && angles.getPixel(row, col, 0) <= 112.5) ||
                        (angles.getPixel(row, col, 0) < -67.5 && angles.getPixel(row, col, 0) >= -112.5)) { // 90 degrees
                    if (magnitudes.getPixel(row - 1, col, 0) > currentPixel || magnitudes.getPixel(row + 1, col, 0) > currentPixel) {
                        magnitudes.setPixel(row, col, 0 , 0);
                    }
                } else if ((angles.getPixel(row, col, 0) >= 112.5 && angles.getPixel(row, col, 0) <= 157.5) ||
                        (angles.getPixel(row, col, 0) < -22.5 && angles.getPixel(row, col, 0) >= -67.5)) { // 135 degrees
                    if (magnitudes.getPixel(row + 1, col - 1, 0) > currentPixel || magnitudes.getPixel(row - 1, col + 1, 0) > currentPixel) {
                        magnitudes.setPixel(row, col, 0 , 0);
                    }
                }
            }
        }
    }

    private static Image applyThresholds(Image edges, double highThreshold, double lowThreshold) {
        double max = edges.getMaxValue();

        edges.normalizeImage(max);

        max = edges.getMaxValue();

        highThreshold = max * highThreshold;
        lowThreshold = lowThreshold * highThreshold;

        for (int row = 0; row < edges.getHeight(); row++) {
            for (int col = 0; col < edges.getWidth(); col++) {
                double currentPixel = edges.getPixel(row, col, 0);

                if (currentPixel > highThreshold){
                    edges.setPixel(row, col, 0, 255);
                }
                // if current pixel gradient is lower than the low threshold - it is not an edge for sure
                else if(currentPixel < lowThreshold) {
                    edges.setPixel(row, col, 0, 0);
                } else if (currentPixel < highThreshold && currentPixel > lowThreshold){
                    // if current pixel is between the thresholds - check if it is near an edge pixel.
                    // if yes - it is also an edge (localization). otherwise - it isn't.

                    boolean isNeighbour = checkNeighbourPixels(edges, row, col, highThreshold);
                    if (isNeighbour) {
                        edges.setPixel(row, col, 0, 255);
                    } else {
                        edges.setPixel(row, col, 0, 0);
                    }
                }
            }
        }
        return edges;
    }

    private static boolean checkNeighbourPixels(Image mags, int currentRow, int currentCol, double high) {

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                // check if we exceed image boundaries
                if (0 <= currentRow + i && currentRow + i < mags.getHeight() &&
                        0 <= currentCol + j && currentCol + j < mags.getWidth()) {
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    if (mags.getPixel(currentRow + i, currentCol + j, 0) > high) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
