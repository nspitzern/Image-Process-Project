package ImageProcess;

class Flips {
    /*****
     * Flips an image 180 degrees around the Y axis.
     * @param img the original image.
     * @return a flipped image.
     */
    static Image flip180Y(Image img) {
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
    static Image flip180YColor(Image img, int color) {
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
    static Image flip180X(Image img) {
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
    static Image flip180XColor(Image img, int color) {
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

    /*****
     * Flips an image's red channel 90 degrees.
     * @param img the original image.
     * @param color specific channel. 0 - red, 1 - green, 2 - blue. By default we flip the red channel.
     * @return a flipped image.
     */
    static Image flip90Color(Image img, int color) {
        Image newImg = new Image(img.getHeight(), img.getWidth());

        for (int row = 0; row < img.getHeight(); row++) {

            for(int col = 0; col < img.getWidth() - 1; col++) {
                double red = img.getRed(row, col);
                double green = img.getGreen(row, col);
                double blue = img.getBlue(row, col);

                switch (color) {
                    case 0:
                        newImg.setRed(col, newImg.getWidth() - row - 1, red);
                        break;
                    case 1:
                        newImg.setGreen(col, newImg.getWidth() - row - 1, green);
                        break;
                    case 2:
                        newImg.setBlue(col, newImg.getWidth() - row - 1, blue);
                        break;
                    default:
                        newImg.setRed(col, newImg.getWidth() - row - 1, red);
                        break;
                }
            }
        }
        return newImg;
    }

    /*****
     * Flips an image's red channel 90 degrees.
     * @param img the original image.
     * @return a flipped image.
     */
    static Image flip90(Image img) {
        Image newImg = new Image(img.getHeight(), img.getWidth());

        Image redImg = flip90Color(img, 0);
        Image greenImg = flip90Color(img, 1);
        Image blueImg = flip90Color(img, 2);

        for (int row = 0; row < newImg.getHeight(); row++) {
            for (int col = 0; col < newImg.getWidth(); col++) {
                double red, green, blue;

                red = redImg.getRed(row, col);
                green = greenImg.getGreen(row, col);
                blue = blueImg.getBlue(row, col);

                newImg.setRed(row, col, red);
                newImg.setGreen(row, col, green);
                newImg.setBlue(row, col, blue);
            }
        }
        return newImg;
    }
}
