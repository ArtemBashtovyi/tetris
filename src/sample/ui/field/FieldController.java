package sample.ui.field;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import sample.Main;
import sample.field.CellsSaver;
import sample.field.IFieldManager;
import sample.field.FieldManager;
import sample.model.cell.Cell;
import sample.model.figure.BaseFigure;

import static sample.model.SizeConstants.*;

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

    private final int middlePositionX = yCells/2-1;

    @FXML
    void initialize() {

        cellsSaver = new CellsSaver();
        fieldManager = new FieldManager(yCells,middlePositionX,this,cellsSaver);

        layout.setMaxHeight(MAIN_SCREEN_HEIGHT);
        layout.setMaxWidth(MAIN_SCREEN_WIDTH);

        for (int i = 0; i < baseMatrix.length;i++) {
            for (int j = 0; j < baseMatrix[i].length;j++) {
                baseMatrix[i][j] = new Cell(i, j);
                layout.getChildren().add(baseMatrix[i][j]);
            }
        }

        for (int i = 0; i < baseMatrix[15].length;i++) {
            baseMatrix[15][i].setColor(Cell.getFullColor());
            cellsSaver.addCell(baseMatrix[15][i]);
        }

        updateFigure(fieldManager.createFigure(middlePositionX,0));
        startTimer();

    }

    private void startTimer() {
         timer = new Timeline(
                new KeyFrame(Duration.seconds(0.90),
                event -> onTimerTick()));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }


    private void onTimerTick() {
        fieldManager.moveFigureDown();
    }



    @Override
    public void updateFigure(@NotNull BaseFigure figure) {
        int x = figure.getY();
        int y = figure.getX();

        Cell[][] figureCells = figure.getCells();

        cleanUpField();

        for (int i = 0; i < figureCells.length;i++) {
            for (int j = 0; j < figureCells[i].length;j++) {
                if (figureCells[i][j].getState() == 1) {
                    
                    baseMatrix[x + i][y + j].setColor(figureCells[i][j].getColor());
                }
            }
        }

        updateField();
    }


    @Override
    public void cleanUpField() {
        for (Cell[] aBaseMatrix : baseMatrix) {
            for (Cell anABaseMatrix : aBaseMatrix) {

                // check if Cell exist in saved Cells
                int savedCellIndex = cellsSaver.indexOf(anABaseMatrix);
                if (savedCellIndex != -1) {
                    anABaseMatrix.setColor(cellsSaver.getCell(savedCellIndex).getColor());
                } else anABaseMatrix.setColor(Cell.getEmptyColor());
            }
        }
    }

    @Override
    public Cell[][] getBaseCells() {
        return baseMatrix;
    }

    @Override
    public void setNewFigure() {
        fieldManager.createFigure(middlePositionX,0);
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
