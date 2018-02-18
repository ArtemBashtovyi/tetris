package sample.ui.field;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import sample.Main;
import sample.model.field.CellsSaver;
import sample.model.field.FieldManager;
import sample.model.field.IFieldManager;
import sample.model.cell.Cell;
import sample.model.coord.Coordinate;
import sample.model.figure.BaseFigure;

import java.util.List;

import static sample.ui.UiConstants.*;

public class FieldController implements Main.OnKeyListener,IFieldController {

    // count cells
    private int xCells = (int) (MAIN_SCREEN_HEIGHT / CELL_WIDTH - 3);
    private int yCells = (int) (MAIN_SCREEN_WIDTH  / CELL_WIDTH);

    private Cell baseMatrix[][] = new Cell[xCells][yCells];
    private IFieldManager fieldManager;
    private Timeline timer;
    private CellsSaver cellsSaver;

    @FXML
    Pane layout;

    private final int middlePosition = yCells/2;

    @FXML
    void initialize() {

        cellsSaver = new CellsSaver(this);
        fieldManager = new FieldManager(middlePosition,this,cellsSaver);

        layout.setMaxHeight(MAIN_SCREEN_HEIGHT);
        layout.setMaxWidth(MAIN_SCREEN_WIDTH);

        System.out.println("Matrix[" + (xCells-1) + "," + (yCells-1) +"]");
        for (int i = 0; i < baseMatrix.length;i++) {
            for (int j = 0; j < baseMatrix[i].length;j++) {
                baseMatrix[i][j] = new Cell(i, j);
                layout.getChildren().add(baseMatrix[i][j]);
            }
        }

        for (int i = 0; i < baseMatrix[15].length-2;i++) {
            baseMatrix[baseMatrix.length-1][i].setColor(Cell.getFullColor());
            baseMatrix[baseMatrix.length-2][i].setColor(Cell.getFullColor());
            cellsSaver.addCell(new Coordinate(baseMatrix.length-1,i));
            cellsSaver.addCell(new Coordinate(baseMatrix.length-2,i));
        }

        for (int i = 0; i < baseMatrix[15].length-3;i++) {
            baseMatrix[baseMatrix.length-3][i].setColor(Cell.getFullColor());
            cellsSaver.addCell(new Coordinate(baseMatrix.length-3,i));
        }
        updateFigure(fieldManager.createFigure(0,middlePosition));
        startTimer();

    }

    private void startTimer() {
         timer = new Timeline(
                new KeyFrame(Duration.seconds(0.60),
                event -> onTimerTick()));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }


    private void onTimerTick() {
        fieldManager.moveFigureDown();
    }



    @Override
    public void updateFigure(@NotNull BaseFigure figure) {

        List<Coordinate> figureCells = figure.getCoordinates();

        cleanUpField();

       /* for (int i = 0; i < figureCells.length;i++) {
            for (int j = 0; j < figureCells[i].length;j++) {
                if (figureCells[i][j].getState() == 1) {
                    Cell cell = figureCells[i][j];
                    baseMatrix[cell.getX()][cell.getY()].setColor(cell.getColor());
                    cell = null;
                }
            }
        }*/

        /*for (int i = 0; i < figureCells.length;i++) {
            for (int j = 0; j < figureCells[i].length;j++) {
                if (figureCells[i][j].getState() == 1) {
                    baseMatrix[x + i][y + j].setColor(figureCells[i][j].getColor());
                }
            }
        }*/


        for(Coordinate cell : figureCells) {
            baseMatrix[cell.x][cell.y].setColor(Cell.getFullColor());
        }

        updateField();
    }


    @Override
    public void cleanUpField() {
        // get saved coordinates and set to base matrix color,another Cells have empty color

        for (int i = 0; i < baseMatrix.length;i++) {
            for (int j = 0; j < baseMatrix[i].length;j++) {

                // check if Cell exist in saved Cells

                if (cellsSaver.isExist(new Coordinate(i,j))) {
                    baseMatrix[i][j].setColor(Cell.getFullColor());
                } else baseMatrix[i][j].setColor(Cell.getEmptyColor());


            }
        }
    }

    @Override
    public Cell[][] getBaseCells() {
        return baseMatrix;
    }

    @Override
    public void setNewFigure() {
        fieldManager.createFigure(0,middlePosition);
    }

    @Override
    public void updateField() {
        for (Cell[] aBaseMatrix : baseMatrix) {
            for (Cell anABaseMatrix : aBaseMatrix) {
                anABaseMatrix.update();
            }
        }
    }

    // Callbacks
    @Override
    public void onUp() {
        fieldManager.rotate();
    }

    @Override
    public void onLeft() {
        fieldManager.moveFigureLeft();
    }

    @Override
    public void onRight() {
        fieldManager.moveFigureRight();
    }

    @Override
    public void onDown() {
        fieldManager.moveFigureDown();
    }
}
