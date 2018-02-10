package sample.model.figure;


import sample.model.cell.Cell;
import sample.model.cell.CellMapper;

public abstract class BaseFigure {

    private int x;
    private int y;

    private Cell[][] cells = new Cell[4][4];

    public BaseFigure(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        if (isCollision()) {
            this.x--;
        }
    }

    public void moveRight() {
        if (isCollision()) {
            this.x++;
        }
    }

    public void moveDown() {
        if (isCollision()) {
            this.y++;
        }
    }

    private boolean isCollision() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4;j++) {

            }
        }
        return true;
    }
    public Cell[][] getCells() {
        cells = CellMapper.getCells(getFigureCells());
        return cells;
    }

    public void rotate() {
        if (isCollision()) {
            changeMatrix();
        }
    }

    abstract void changeMatrix();
    abstract int[][] getFigureCells();

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
