package ImageProcess.Filters;

public class PrewittFilter extends EdgeDetectionFilter {

    public PrewittFilter() {
        super(new double[][]{{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}}, new double[][]{{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}});
    }

    public void multAllByValue(EdgeDetectionFilter.direction dir, double v) {
        super.multAllByValue(v, dir);
    }

    public void addAllValue(EdgeDetectionFilter.direction dir, double v) {
        super.addAllValue(v, dir);
    }

    public void multByValue(EdgeDetectionFilter.direction dir, int row, int col, double v) {
        super.multByValue(row, col, v, dir);
    }

    public void addValue(EdgeDetectionFilter.direction dir, int row, int col, double v) {
        super.addValue(row, col, v, dir);
    }

    public double getValue(EdgeDetectionFilter.direction dir, int row, int col) {
        return super.getValue(row, col, dir);
    }

    public int getWidth(EdgeDetectionFilter.direction dir) {
        return super.getWidth(dir);
    }

    public int getHeight(EdgeDetectionFilter.direction dir) {
        return super.getHeight(dir);
    }

    public IFilter getGx() {
        return super.getGx();
    }

    public IFilter getGy() {
        return super.getGy();
    }
}
