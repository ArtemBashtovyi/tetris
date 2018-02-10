package sample.model.figure;

public class FigureZ extends BaseFigure {

    private int[][] baseCells;

    private static final int[][] NORMAL_MODE = new int[][]{
            {0,1,1,0},
            {0,0,1,0},
            {0,0,1,0},
            {0,0,0,0}
    };

    private static final int[][] ROTATED_90_MODE = new int[][]{
            {0,0,1,0},
            {1,1,1,0},
            {0,0,0,0},
            {0,0,0,0}
    };

    private static final int[][] ROTATED_180_MODE = new int[][]{
            {0,1,0,0},
            {0,1,0,0},
            {0,1,1,0},
            {0,0,0,0}
    };

    private static final int[][] ROTATED_270_MODE = new int[][]{
            {0,0,0,0},
            {1,1,1,0},
            {1,0,0,0},
            {0,0,0,0}
    };


    public FigureZ(int x, int y) {
        super(x, y);
        baseCells = NORMAL_MODE;
    }


    @Override
    void changeMatrix() {
        if (baseCells == NORMAL_MODE) {
            this.baseCells = ROTATED_90_MODE;
        } else if (baseCells == ROTATED_90_MODE) {
            this.baseCells = ROTATED_180_MODE;
        } else if (baseCells == ROTATED_180_MODE) {
            this.baseCells = ROTATED_270_MODE;
        } else if (baseCells == ROTATED_270_MODE) {
            this.baseCells = NORMAL_MODE;
        }
    }

    @Override
    int[][] getFigureCells() {
        return baseCells;
    }

}
