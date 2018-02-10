package sample.ui.field;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Main;
import sample.model.cell.Cell;
import sample.model.figure.BaseFigure;
import sample.model.figure.FigureZ;

import static sample.model.SizeConstants.*;

public class FieldController implements Main.OnKeyListener {

    // count cells
    private int xCells = (int) (MAIN_SCREEN_HEIGHT / CELL_WIDTH - 3);
    private int yCells = (int) (MAIN_SCREEN_WIDTH  / CELL_WIDTH);

    private Cell baseMatrix[][] = new Cell[xCells][yCells];
    private BaseFigure figure;

    @FXML
    Pane layout;

    private int middlePosition = yCells/2-1;
    private static int cellCounter = 0;

    @FXML
    void initialize() {
        figure = new FigureZ(middlePosition,0);

        layout.setMaxHeight(MAIN_SCREEN_HEIGHT);
        layout.setMaxWidth(MAIN_SCREEN_WIDTH);

        for (int i = 0; i < baseMatrix.length;i++) {
            for (int j = 0; j < baseMatrix[i].length;j++) {
                baseMatrix[i][j] = new Cell(cellCounter++, i, j);
                layout.getChildren().add(baseMatrix[i][j]);
            }
        }

        setFigure(figure.getY(),figure.getX(),figure);
        startTimer();

    }


    private void startTimer() {
        Timeline everySecondsWorker = new Timeline(
                new KeyFrame(Duration.seconds(0.90),
                event -> moveFigure()));

        everySecondsWorker.setCycleCount(Timeline.INDEFINITE);
        everySecondsWorker.play();
    }

    private void moveFigure() {
        figure.moveDown();
        updateFigure(figure.getY(),figure.getX());
    }


    private void setFigure(int x,int y,BaseFigure baseFigure) {
        Cell[][] figureCells  = baseFigure.getCells();

        for (int i = 0; i < figureCells.length;i++) {
            for (int j = 0; j < figureCells[i].length;j++) {
                baseMatrix[x+i][y+j].setColor(figureCells[i][j].getColor());
            }
        }
    }

    private void updateField() {
        for (int i = 0; i < baseMatrix.length;i++) {
            for (int j = 0; j < baseMatrix[i].length;j++) {
                baseMatrix[i][j].update();
            }
        }
    }

    private void updateFigure(int x,int y) {
        Cell[][] figureCells = figure.getCells();
        clearField();

        for (int i = 0; i < figureCells.length;i++) {
            for (int j = 0; j < figureCells[i].length;j++) {
                baseMatrix[x+i][y+j].setColor(figureCells[i][j].getColor());
            }
        }
        updateField();
    }

    private void clearField() {
        for (Cell[] aBaseMatrix : baseMatrix) {
            for (Cell anABaseMatrix : aBaseMatrix) {
                anABaseMatrix.setColor(Cell.getEmptyColor());
            }
        }
    }

    // Callbacks
    @Override
    public void onUp() {
        figure.rotate();
        updateFigure(figure.getY(),figure.getX());
    }

    @Override
    public void onLeft() {
        figure.moveLeft();
        updateFigure(figure.getY(),figure.getX());
    }

    @Override
    public void onRight() {
        figure.moveRight();
        updateFigure(figure.getY(),figure.getX());
    }

    @Override
    public void onDown() {
        figure.moveDown();
        updateFigure(figure.getY(),figure.getX());
    }
}
