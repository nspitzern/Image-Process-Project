package ImageProcess.Filters;

public class BlurFilter extends Filter2D{

    public BlurFilter(int width, int height) {
        super(width, height);
        super.initFilterOnes();;
        super.multAllByValue((double)1/(width * height));
    }
}
