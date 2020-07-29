package ImageProcess.Filters;

public class SharpFilter extends BaseFilter2D {

    public SharpFilter() {
        super(3, 3);
        double[][] arr = {{0, -1, 0}, {-1, 5 ,-1}, {0, -1, 0}};
        super.setArr(arr);
    }
}
