package ImageProcess.Filters;

public interface Filter {
    void multAllByValue(double v);

    void addAllValue(double v);

    void multByValue(int row, int col, double v);

    void addValue(int row, int col, double v);

    double getValue(int row, int col);

    int getWidth();

    int getHeight();

    void initFilterOnes();
}
