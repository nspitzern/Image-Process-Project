package ImageProcess.Filters;

public class BlurFilter implements Filter{
    private Filter2D f;

    public BlurFilter(int width, int height) {
        this.f = new Filter2D(width, height);
        this.f.initFilterOnes();
        this.f.multAllByValue((double)1/(width * height));
    }

    @Override
    public void multAllByValue(double v) {
        this.f.multAllByValue(v);
    }

    @Override
    public void addAllValue(double v) {
        this.f.addAllValue(v);
    }

    @Override
    public void multByValue(int row, int col, double v) {
        this.f.multByValue(row, col, v);
    }

    @Override
    public void addValue(int row, int col, double v) {
        this.f.addValue(row, col, v);
    }

    @Override
    public double getValue(int row, int col) {
        return this.f.getValue(row, col);
    }

    @Override
    public int getWidth() {
        return this.f.getWidth();
    }

    @Override
    public int getHeight() {
        return this.f.getHeight();
    }
}
