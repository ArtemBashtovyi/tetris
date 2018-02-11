package sample.field;

import sample.model.cell.Cell;
import sample.model.figure.BaseFigure;
import sample.model.figure.FigureZ;
import sample.ui.field.IFieldController;

import java.util.List;

public class FieldManager implements IFieldManager {

    // set default 4x4 block
    private static final int DEFAULT_X_SIZE_FIGURE = 4;
    private static final int DEFAULT_Y_SIZE_FIGURE = 4;

    private Cell[][] figureCells = new Cell[DEFAULT_X_SIZE_FIGURE][DEFAULT_Y_SIZE_FIGURE];


    private int[] countColoredCellsInLine;
    private int xMiddlePosition;

    private IFieldController controller;
    private BaseFigure figure;

    // saver static blocks, which were stop
    private CellsSaver cellsSaver;


    public FieldManager(int countCellsInLine,int xMiddlePosition,IFieldController controller,CellsSaver cellsSaver) {
        this.countColoredCellsInLine = new int[countCellsInLine];
        this.controller = controller;
        this.xMiddlePosition = xMiddlePosition;
        this.cellsSaver = cellsSaver;
    }


    @Override
    public void moveFigureDown() {
        if (isCanMoveDown()) {
            figure.moveDown();
            controller.updateFigure(figure);
        } else {

            Cell[][] baseCells = controller.getBaseCells();
            int x = figure.getX();
            int y = figure.getY();
            boolean isNeedToShiftLinesDown = false;

            for (int i = 0; i < figureCells.length;i++) {
                for (int j = 0; j < figureCells[i].length;j++){
                    if (figureCells[i][j].getState() == 1) {
                        cellsSaver.addCell(baseCells[x+i][y+j]);
                        countColoredCellsInLine[figure.getX()]++;

                    }
                }

            }
            controller.setNewFigure();
        }
    }

    @Override
    public void moveFigureLeft() {
        //if (!isCollision()) {
            figure.moveLeft();
            controller.updateFigure(figure);
       // }
    }

    @Override
    public void moveFigureRight() {
        //if (!isCollision()) {
            figure.moveRight();
            controller.updateFigure(figure);
       // }
    }

    @Override
    public void rotate() {
        figure.rotate();
        controller.updateFigure(figure);
        // set figure cell if change matrix (rotate stone)
        figureCells = figure.getCells();
    }

    @Override
    public BaseFigure createFigure(int x, int y) {
        // TODO: random create objects
        if (true) {
            figure = new FigureZ(x,y);
        }
        figureCells = figure.getCells();
        return figure;
    }

    public void onFigureFinish() {
        figure = createFigure(xMiddlePosition,0);
    }

    /**
     *  use +1+y  for predict conclusion of offset block to future position
     *
     * @return false - if can't move down,some blocks stay on this positions
     *  true - move allowed
     */
    private boolean isCanMoveDown() {
        int x = figure.getX();
        int y = figure.getY();

        Cell[][] baseMatrix = controller.getBaseCells();
        List<Cell> savedCells = cellsSaver.getCells();
        /*for (Cell cell : savedCells ) {
            if (figureCell)

        }*/

        for (int i = 0; i < DEFAULT_X_SIZE_FIGURE; i++) {
            for (int j = 0; j < DEFAULT_Y_SIZE_FIGURE;j++) {
                if (y+j+1 <= -1
                        || (baseMatrix[i+x+1][j+y].getState() == 1
                        && figureCells[i][j].getState() == 1 )) {
                    System.out.println("false- moveDown");
                    return false;
                }
            }
        }


        System.out.println("true- moveDown");
        return true;
    }

    public void isCanTurnLeft() {

    }


    public void setCountColoredCells(int lineNumber,int value) {
        if (lineNumber >= 0 && lineNumber <= countColoredCellsInLine.length)
        this.countColoredCellsInLine[lineNumber] = value;
    }

    public int getCountColoredCells(int lineNumber) {
        return this.countColoredCellsInLine[lineNumber];
    }
}
