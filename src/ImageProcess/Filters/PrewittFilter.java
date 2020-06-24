package ImageProcess.Filters;

public class PrewittFilter {
    private Filter2D gx, gy;

    public PrewittFilter() {
        double[][] gxArr = {{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
        double[][] gyArr = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};
        this.gx = new Filter2D(gxArr);
        this.gy = new Filter2D(gyArr);
    }

    public void multAllByValue(char dir, double v) {
        try {
            if (dir == 'x') {
                this.gx.multAllByValue(v);
            } else if (dir == 'y') {
                this.gy.multAllByValue(v);
            } else {
                throw new Exception("Sobel Filter - Undefined direction");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAllValue(char dir, double v) {
        try {
            if (dir == 'x') {
                this.gx.addAllValue(v);
            } else if (dir == 'y') {
                this.gy.addAllValue(v);
            } else {
                throw new Exception("Sobel Filter - Undefined direction");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void multByValue(char dir, int row, int col, double v) {
        try {
            if (dir == 'x') {
                this.gx.multByValue(row, col, v);
            } else if (dir == 'y') {
                this.gy.multByValue(row ,col, v);
            } else {
                throw new Exception("Sobel Filter - Undefined direction");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addValue(char dir, int row, int col, double v) {
        try {
            if (dir == 'x') {
                this.gx.addValue(row, col, v);
            } else if (dir == 'y') {
                this.gy.addValue(row, col, v);
            } else {
                throw new Exception("Sobel Filter - Undefined direction");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getValue(char dir, int row, int col) {
        try {
            if (dir == 'x') {
                return this.gx.getValue(row, col);
            } else if (dir == 'y') {
                return this.gy.getValue(row, col);
            } else {
                throw new Exception("Sobel Filter - Undefined direction");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getWidth(char dir) {
        try {
            if (dir == 'x') {
                return this.gx.getWidth();
            } else if (dir == 'y') {
                return this.gy.getWidth();
            } else {
                throw new Exception("Sobel Filter - Undefined direction");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getHeight(char dir) {
        try {
            if (dir == 'x') {
                return this.gx.getHeight();
            } else if (dir == 'y') {
                return this.gy.getHeight();
            } else {
                throw new Exception("Sobel Filter - Undefined direction");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Filter2D getGx() {
        return this.gx;
    }

    public Filter2D getGy() {
        return this.gy;
    }
}
