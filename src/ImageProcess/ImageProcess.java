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

    public Image gaussianBlur(Image img, double sigma) {
        Filter f = new GaussianFilter(sigma);

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

    public Image edgeDetection(Image img) {
        Filter f = new EdgeDetectionFilter();

        Image newImg = ImageProcessMath.conv2D(img, f);
        newImg.limitImageColors();

        return newImg;
    }

    public Image hybrid(Image src, Image other) {
        Image lowFreqSrc = gaussianBlur(src, 2);
        Image lowFreqOther = gaussianBlur(other, 2);
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
}
