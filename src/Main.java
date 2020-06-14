import ImageProcess.ImageProcess;
import ImageProcess.Image;

public class Main {
    public static void main(String[] args) {

        Image img = new Image("panda.jpg");

        ImageProcess ip = new ImageProcess(img);

        Image newImg = ip.sharpen();

        newImg.save("sharp.jpg");
    }
}
