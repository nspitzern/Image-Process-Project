package ImageProcess;

import ImageProcess.Filters.*;

public class ImageProcess {
//    private Image img;
//
//    public ImageProcess(Image img) {
//        this.img = img;
//    }

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

                int grey = (int)(0.299f * newImg.getRed(row, col) + 0.587f * newImg.getGreen(row, col) + 0.114f * newImg.getBlue(row, col));

                newImg.setPixel(row, col, grey);
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

    public Image[] prewittEdgeDetection(Image img) {
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
                filterImg.setPixel(row, col, 0.5);
            }
        }

        for (int row = filterImg.getHeight() / 4; row < 3 * (filterImg.getHeight() / 4); row++) {
            for (int col = filterImg.getWidth() / 4; col < 3 * (filterImg.getWidth() / 4); col++) {
                filterImg.setPixel(row, col, 2);
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
}
