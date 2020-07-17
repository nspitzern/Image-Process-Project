package ImageProcess;

class Brightness {
    static Image addBrightness(Image img, double v) {
        Image newImg = img.copy();
        newImg.addAll(v);
        return newImg;
    }

    static Image multByValue(Image img, double v) {
        Image newImg = img.copy();
        newImg.multAll(v);
        return newImg;
    }
}
