package ImageProcess;

class Effects {
    static Image negative(Image img) {
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

    static Image hybrid(Image src, Image other) {
        Image lowFreqSrc = FilterBased.gaussianBlur(src, 11, 3);
        Image lowFreqOther = FilterBased.gaussianBlur(other, 11, 3);
        other.subImg(lowFreqOther);
        lowFreqSrc.addImg(other);

        return lowFreqSrc;
    }

    static Image squareFocus(Image img, int squareX, int squareY, int squareWidth, int squareHeight) {
        Image newImg = img.copy();

        Image filterImg = new Image(img.getWidth(), img.getHeight());

        for (int row = 0; row < filterImg.getHeight(); row++) {
            for (int col = 0; col < filterImg.getWidth(); col++) {
                for (int chan = 0; chan < img.getChannel(); chan++) {
                    filterImg.setPixel(row, col, chan, 0.5);
                }
            }
        }

        for (int row = squareY; row < squareY + squareHeight; row++) {
            for (int col = squareX; col < squareX + squareWidth; col++) {
                for (int chan = 0; chan < img.getChannel(); chan++) {
                    if (row < 0 || row >= img.getHeight() || col < 0 || col >= img.getWidth()) {
                        continue;
                    }
                    filterImg.setPixel(row, col, chan, 2);
                }
            }
        }

        newImg.multImg(filterImg);

        return newImg;
    }

    /*****
     * Converts a colored image into a Sepia-colored image.
     * @param img an image.
     * @return a Sepia-colored image.
     */
    static Image sepia(Image img) {
        Image newImg = img.copy();
        double red, green ,blue;

        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                red = img.getRed(row, col);
                green = img.getGreen(row, col);
                blue = img.getBlue(row, col);

                // newRed = 0.393*R + 0.769*G + 0.189*B
                double newRed = 0.393 * red + 0.769 * green + 0.189 * blue;
                // newGreen = 0.349*R + 0.686*G + 0.168*B
                double newGreen = 0.349 * red + 0.686 * green + 0.168 * blue;
                // newBlue = 0.272*R + 0.534*G + 0.131*B
                double newBlue = 0.272 * red + 0.534 * green + 0.131 * blue;

                newImg.setRed(row, col, newRed);
                newImg.setGreen(row, col, newGreen);
                newImg.setBlue(row, col, newBlue);
            }
        }

        return newImg;
    }

    /*****
     *
     * @param img the original image
     * @param swirlX the x position of the swirl center
     * @param swirlY the y position of the swirl center
     * @param swirlRadius the radius of the swirl
     * @param swirlTwists number of twists. positive is counter clockwise, negative is clockwise).
     *                    ! works best with numbers between 0 and 1.
     * @return a twisted image
     */
    static Image swirl(Image img, double swirlX, double swirlY, double swirlRadius, double swirlTwists) {
        Image newImg = img.copy();

        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {

                // compute the distance and angle from the swirl center:
                double pixelX = (double)col - swirlX;
                double pixelY = (double)row - swirlY;

                double pixelDistance = Math.sqrt((pixelX * pixelX) + (pixelY * pixelY));
                double pixelAngle = Math.atan2(pixelY, pixelX);

                // work out how much of a swirl to apply (1.0 in the center fading out to 0.0 at the radius):
                double swirlAmount = 1.0 - (pixelDistance / swirlRadius);
                if (swirlAmount > 0) {
                    double twistAngle = swirlTwists * swirlAmount * 2 * Math.PI;

                    // adjust the pixel angle and compute the adjusted pixel co-ordinates:
                    pixelAngle += twistAngle;
                    pixelX = Math.cos(pixelAngle) * pixelDistance;
                    pixelY = Math.sin(pixelAngle) * pixelDistance;
                }
                if ((int)(swirlX + pixelX) >= img.getWidth() || (int)(swirlY + pixelY) >= img.getHeight()) {
                    continue;
                }

                newImg.setRed(row, col, img.getRed((int)(swirlY + pixelY), (int)(swirlX + pixelX)));
                newImg.setGreen(row, col, img.getGreen((int)(swirlY + pixelY), (int)(swirlX + pixelX)));
                newImg.setBlue(row, col, img.getBlue((int)(swirlY + pixelY), (int)(swirlX + pixelX)));
            }
        }

        return newImg;
    }
}
