package sample.model.figure;


import sample.model.cell.Cell;
import sample.model.cell.CellMapper;

public abstract class BaseFigure {

    private int x;
    private int y;

    BaseFigure(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        this.x--;
        /*if (isCollision()) {
            this.x++;
        }*/
    }

    public void moveRight() {
        this.x++;
        /*if (isCollision()) {
            this.x--;
        }*/
    }

    public void moveDown() {
        this.y++;
    }

    public Cell[][] getCells() {
        return CellMapper.getCells(getFigureCells());
    }

    public void rotate() {
        changeMatrix();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


    abstract void changeMatrix();
    abstract int[][] getFigureCells();
}
