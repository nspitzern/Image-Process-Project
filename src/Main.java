import ImageProcess.ImageProcess;
import ImageProcess.Image;

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

        Image newImg = ip.flip180X(img);

        newImg.save("src/images/flip180.jpg");
    }
}
