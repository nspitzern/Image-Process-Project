package ImageProcess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private double arr[][][];
    private int width, height, chan;

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.chan = 3;
        this.arr = new double[height][width][this.chan];
    }

    public Image(int width, int height, int chan) {
        this.width = width;
        this.height = height;
        this.chan = chan;
        this.arr = new double[height][width][chan];
    }

    public Image(int width, int height, int chan, double[][][] a) {
        this.width = width;
        this.height = height;
        this.chan = chan;
        this.arr = new double[height][width][chan];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                for (int c = 0; c < chan; c++) {
                    this.arr[row][col][c] = a[row][col][c];
                }
            }
        }
    }

    public Image(double[][] arr) {
        this.height = arr.length;
        this.width = arr[0].length;
        this.chan = 3;
        this.arr = new double[this.height][this.width][this.chan];

        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                setRed(row, col, arr[row][col]);
                setGreen(row, col, arr[row][col]);
                setBlue(row, col, arr[row][col]);
            }
        }
    }

    public Image(double[][][] arr) {
        this.height = arr.length;
        this.width = arr[0].length;
        this.chan = arr[0][0].length;
        this.arr = new double[this.height][this.width][this.chan];

        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                for (int chan = 0; chan < arr[0][0].length; chan++) {
                    setPixel(row, col, chan, arr[row][col][chan]);
                }
            }
        }
    }

    public Image(String path) {
        loadImg(path);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getChannel() {
        return this.chan;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getRed(int row, int col) {
        return this.arr[row][col][0];
    }

    public double getGreen(int row, int col) {
        return this.arr[row][col][1];
    }

    public double getBlue(int row, int col) {
        return this.arr[row][col][2];
    }

    public double getPixel(int row, int col, int c) { return this.arr[row][col][c]; }

    public void setRed(int row, int col, double v) {
        this.arr[row][col][0] = limitColor(v);
    }

    public void setGreen(int row, int col, double v) {
        this.arr[row][col][1] = limitColor(v);
    }

    public void setBlue(int row, int col, double v) {
        this.arr[row][col][2] = limitColor(v);
    }

    public void setPixel(int row, int col, int chan, double v) {
        this.arr[row][col][chan] = limitColor(v);
    }

    public void save(String path) {
        BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

        try {
            Color c = null;
            if (this.chan == 1) {
                for (int row = 0; row < img.getHeight(); row++) {
                    for (int col = 0; col < img.getWidth(); col++) {
                        c = new Color((int)this.arr[row][col][0], (int)this.arr[row][col][0], (int)this.arr[row][col][0]);
                        img.setRGB(col, row, c.getRGB());
                    }
                }
            } else {
                for (int row = 0; row < img.getHeight(); row++) {
                    for (int col = 0; col < img.getWidth(); col++) {
                        c = new Color((int)this.arr[row][col][0], (int)this.arr[row][col][1], (int)this.arr[row][col][2]);
                        img.setRGB(col, row, c.getRGB());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File f = new File(path);
            ImageIO.write(img, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean loadImg(String path) {
        BufferedImage img  = null;
        try {
            File f = new File(path);
            img = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        this.height = img.getHeight();
        this.width = img.getWidth();
        this.chan = 3;
        this.arr = new double[this.height][this.width][this.chan];

        try {
            for (int row = 0; row < img.getHeight(); row++) {
                for (int col = 0; col < img.getWidth(); col++) {
                    Color c = new Color(img.getRGB(col, row));
                    this.arr[row][col][0] = c.getRed();
                    this.arr[row][col][1] = c.getGreen();
                    this.arr[row][col][2] = c.getBlue();
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addRed(int row, int col, double v) {
        double r = this.arr[row][col][0];
        this.arr[row][col][0] = (int)limitColor(r + v);
    }

    public void addBlue(int row, int col, double v) {
        double b = this.arr[row][col][2];
        this.arr[row][col][2] = (int)limitColor(b + v);
    }

    public void addGreen(int row, int col, double v) {
        double g = this.arr[row][col][1];
        this.arr[row][col][1] = (int)limitColor(g + v);
    }

    public void addAll(double v) {
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                addRed(row, col, v);
                addBlue(row, col, v);
                addGreen(row, col, v);
            }
        }
    }

    public void multRed(int row, int col, double v) {
        double r = this.arr[row][col][0];
        this.arr[row][col][0] = (int)limitColor(r * v);
    }

    public void multBlue(int row, int col, double v) {
        double b = this.arr[row][col][2];
        this.arr[row][col][2] = (int)limitColor(b * v);
    }

    public void multGreen(int row, int col, double v) {
        double g = this.arr[row][col][1];
        this.arr[row][col][1] = (int)limitColor(g * v);
    }

    public void multAll(double v) {
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                multRed(row, col, v);
                multBlue(row, col, v);
                multGreen(row, col, v);
            }
        }
    }

    public void addImg(Image otherImg) {
        try {
            if (otherImg.width != this.width || otherImg.height != this.height) {
                throw new Exception("Dimensions of images don't match!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                    addRed(row, col, otherImg.getRed(row, col));
                    addGreen(row, col, otherImg.getGreen(row, col));
                    addBlue(row, col, otherImg.getBlue(row, col));
            }
        }
    }

    public void subImg(Image otherImg) {
        try {
            if (otherImg.width != this.width || otherImg.height != this.height) {
                throw new Exception("Dimensions of images don't match!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                addRed(row, col, -otherImg.getRed(row, col));
                addGreen(row, col, -otherImg.getGreen(row, col));
                addBlue(row, col, -otherImg.getBlue(row, col));
            }
        }
    }

    public void multImg(Image otherImg) {
        try {
            if (otherImg.width != this.width || otherImg.height != this.height) {
                throw new Exception("Dimensions of images don't match!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                multRed(row, col, otherImg.getRed(row, col));
                multGreen(row, col, otherImg.getGreen(row, col));
                multBlue(row, col, otherImg.getBlue(row, col));
            }
        }
    }

    public Image copy() {
        return new Image(this.width, this.height, this.chan, this.arr);
    }

    private double limitColor(double value) {
        if(value > 255) {
            return 255;
        } else if(value < 0) {
            return 0;
        }
        return value;
    }

    public void limitImageColors() {
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                for (int c = 0; c < this.chan; c++) {
                    double v = this.arr[row][col][c];
                    this.arr[row][col][c] = limitColor(v);
                }
            }
        }
    }

    public Image createRandomNoise(int width, int height) {
        Image img = new Image(width, height);

        double red, green, blue;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                red = Math.random() * 256;
                green = Math.random() * 256;
                blue = Math.random() * 256;

                img.setRed(row, col, red);
                img.setGreen(row, col, green);
                img.setBlue(row, col, blue);
            }
        }
        return img;
    }

    public void show() {

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        try {
            for (int row = 0; row < bi.getHeight(); row++) {
                for (int col = 0; col < bi.getWidth(); col++) {
                    Color c = null;
                    if (this.chan == 1) {
                        c = new Color((int)this.arr[row][col][0], (int)this.arr[row][col][0], (int)this.arr[row][col][0]);
                    } else {
                        c = new Color((int)this.arr[row][col][0], (int)this.arr[row][col][1], (int)this.arr[row][col][2]);
                    }

                    bi.setRGB(col, row, c.getRGB());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(bi)));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public double getMaxValue() {
        return getMaxValue(0);
    }

    public double getMaxValue(int channel) {
        double max = 0;
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                if(this.arr[row][col][channel] > max) {
                    max = this.arr[row][col][channel];
                }
            }
        }
        return max;
    }

    public void normalizeImage(double val) {
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                for (int chan = 0; chan < this.chan; chan++) {
                    this.arr[row][col][chan] /= val;
                }
            }
        }
    }

    public void normalizeImage() {
        normalizeImage(255);
    }
}
