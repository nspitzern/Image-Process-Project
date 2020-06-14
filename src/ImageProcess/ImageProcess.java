package ImageProcess;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcess {
    private BufferedImage img;

    private Image imgI;

    public ImageProcess(BufferedImage img) {
        this.img = img;
    }

    public ImageProcess(Image img) {
        this.imgI = img;
    }

    public Image negative() {
        Image newImg = this.imgI.copy();
        for (int row = 0; row < newImg.getHeight(); row++) {
            for (int col = 0; col < newImg.getWidth(); col++) {
                newImg.setRed(row, col, 255 - (int)newImg.getRed(row, col));
                newImg.setGreen(row, col, 255 - (int)newImg.getGreen(row, col));
                newImg.setBlue(row, col, 255 - (int)newImg.getBlue(row, col));
            }
        }
        return newImg;
    }

    public Image brightness(double v) {
        Image newImg = this.imgI.copy();
        newImg.addAll(v);
        return newImg;
    }

    public Image multByValue(double v) {
        Image newImg = this.imgI.copy();
        newImg.multAll(v);
        return newImg;
    }

    public Image blurImage(int filterWidth, int filterHeight) {
        Filter2D f = new Filter2D(filterWidth, filterHeight);
        f.multByValue((double)1 / (filterWidth * filterHeight));

        return ImageProcessMath.conv2D(this.imgI, f);
    }

    public Image RGB2GreyScale() {
        Image newImg = this.imgI.copy();

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

    public Image sharpen() {
        double[][] arr = {{0, -1, 0}, {-1, 5 ,-1}, {0, -1, 0}};
        Filter2D f = new Filter2D(arr);

        Image newImg = ImageProcessMath.conv2D(this.imgI, f);
        newImg.limitImageColors();

        return newImg;
    }
}
