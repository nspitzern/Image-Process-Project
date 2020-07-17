package ImageProcess;

class Resize {
    static Image bilinearResize(Image img, int newWidth, int newHeight) {
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

    static Image nearestNeighbourResize(Image img, int newWidth, int newHeight) {
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
}
