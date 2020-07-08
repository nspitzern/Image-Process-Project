import ImageProcess.ImageProcess;
import ImageProcess.Image;
import ImageProcess.FFT;

public class Main {
    public static void main(String[] args) {

//        Image img2 = new Image("src/images/ron.png");
//        Image img1 = new Image("src/images/dumbledore.png");
//
//        ImageProcess ip = new ImageProcess();
//
//        Image newImg = ip.hybrid(img1, img2);
//
//        newImg.save("src/images/hybrid.jpg");

        Image img = new Image("src/images/panda.jpg");

        ImageProcess ip = new ImageProcess();

        Image newImg = ip.RGB2GreyScale(img);

        newImg.save("src/images/grey.jpg");
    }
}
