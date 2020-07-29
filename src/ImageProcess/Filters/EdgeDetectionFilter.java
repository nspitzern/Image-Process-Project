package ImageProcess.Filters;

public abstract class EdgeDetectionFilter {
    public enum direction {
        GX, GY
    }

    private Filter gx;
    private Filter gy;

    public EdgeDetectionFilter(double[][] gx, double[][] gy) {
        this.gx = new GeneralFilter(gx);
        this.gy = new GeneralFilter(gy);
    }

    public double getValue(int row, int col, direction dir) {
        if (dir == direction.GX) {
            return this.gx.getValue(row, col);
        }
        return this.gy.getValue(row, col);
    }

    public int getWidth(direction dir) {
        if (dir == direction.GX) {
            return this.gx.getWidth();
        }
        return this.gy.getWidth();
    }

    public int getHeight(direction dir) {
        if (dir == direction.GX) {
            return this.gx.getHeight();
        }
        return this.gy.getHeight();
    }

    public void multAllByValue(double v, direction dir) {
        if (dir == direction.GX) {
            this.gx.multAllByValue(v);
        } else {
            this.gy.multAllByValue(v);
        }
    }

    public void addAllValue(double v, direction dir) {
        if (dir == direction.GX) {
            this.gx.addAllValue(v);
        } else {
            this.gy.addAllValue(v);
        }
    }

    public void multByValue(int row, int col, double v, direction dir) {
        if (dir == direction.GX) {
            this.gx.multByValue(row, col, v);
        } else {
            this.gy.multByValue(row, col, v);
        }
    }

    public void addValue(int row, int col, double v, direction dir) {
        if (dir == direction.GX) {
            this.gx.addValue(row, col, v);
        } else {
            this.gy.addValue(row, col, v);
        }
    }

    public Filter getGx() {
        return this.gx;
    }

    public Filter getGy() {
        return this.gy;
    }
}
