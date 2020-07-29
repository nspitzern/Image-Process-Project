package ImageProcess.Filters;

public class LaplacianFilter extends BaseFilter2D {

    public LaplacianFilter() {
        super(3, 3);
        double[][] arr = {{0, 1, 0}, {1, -4 ,1}, {0, 1, 0}};
        super.setArr(arr);
    }
}
