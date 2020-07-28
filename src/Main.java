import ImageProcess.ImageProcess;
import ImageProcess.Image;

public class Main {
    public static void main(String[] args) {
        try {
            String imgPath = args[0];
            String imgSavePath = args[1];

            Image img = new Image(imgPath);

            ImageProcess ip = new ImageProcess();

            Image newImg = ip.nearestNeighbourResize(img, 100, 200);

            newImg.save(imgSavePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
