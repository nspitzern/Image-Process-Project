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

                double[] pixVals;
                double x_col_scale_factor, y_row_scale_factor;
                for (x = 0; x < newWidth; ++x) {
                    for (y = 0; y < newHeight; ++y) {
                        x_col_scale_factor = x / column_scaling_factor;
                        y_row_scale_factor = y / row_scaling_factor;

                        pixVals = ImageProcessMath.bilinearInterpolation(img, x_col_scale_factor, y_row_scale_factor, img.getChannel());

                        newImg.setRed(y, x, pixVals[0]);
                        newImg.setGreen(y, x, pixVals[1]);
                        newImg.setBlue(y, x, pixVals[2]);
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

        double a = (double) img.getWidth()/(double) newWidth;
        double a_y = (double)img.getHeight()/(double) newHeight;
        double b = -0.5 + 0.5 * a;
        double b_y = -0.5 + 0.5 * a_y;

        for (int row = 0; row < resize.getHeight(); row++){
            for(int col = 0; col < resize.getWidth(); col++) {
                double c_pos = a * col + b;
                double r_pos = a_y * row + b_y;
                double[] pixVals = ImageProcessMath.nearestNeighbourInterpolation(img, c_pos, r_pos, img.getChannel());

                resize.setRed(row, col, pixVals[0]);
                resize.setGreen(row, col, pixVals[1]);
                resize.setBlue(row, col, pixVals[2]);
            }
        }


        return resize;
    }
}
