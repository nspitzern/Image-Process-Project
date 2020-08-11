import ImageProcess.ImageProcess;
import ImageProcess.Image;
import ImageProcess.FFT;

public class Main {
    public static void main(String[] args) {
        try {
            String imgPath = args[0];
            String imgSavePath = args[1];

            Image img = new Image(imgPath);

            ImageProcess ip = new ImageProcess(img);

            ip.flip180XColor(2).blurImage(3, 3).sharpen();

            Image newImg = ip.getImage();

            newImg.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
