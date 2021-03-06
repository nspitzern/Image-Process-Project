package ImageProcess;

import ImageProcess.Filters.BlurFilter;
import ImageProcess.Filters.IFilter;
import ImageProcess.Filters.GaussianFilter;
import ImageProcess.Filters.SharpFilter;

class FilterBased {
    static Image blurImage(Image img, int filterWidth, int filterHeight) {
        IFilter f = new BlurFilter(filterWidth, filterHeight);

        return ImageProcessMath.conv2D(img, f);
    }

    static Image gaussianBlur(Image img, int size, double sigma) {
        if(size < 1 || size > img.getWidth() || size > img.getHeight()) {
            try {
                throw new Exception("Invalid filter dimensions");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IFilter f = new GaussianFilter(size, sigma);

        return ImageProcessMath.conv2D(img, f);
    }

    static Image sharpen(Image img) {
        IFilter f = new SharpFilter();

        Image newImg = ImageProcessMath.conv2D(img, f);
        newImg.limitImageColors();

        return newImg;
    }
}
