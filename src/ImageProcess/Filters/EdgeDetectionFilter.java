package ImageProcess.Filters;

public class EdgeDetectionFilter extends Filter2D implements Filter{

    public EdgeDetectionFilter() {
        super(3, 3);
        double[][] arr = {{0, 1, 0}, {1, -4 ,1}, {0, 1, 0}};
        super.setArr(arr);
    }
}
