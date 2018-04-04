package sample.ui.field;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import sample.App;
import sample.model.cell.CellFactory;
import sample.model.cell.VolatileCell;
import sample.presenter.FieldPresenter;
import sample.presenter.IFieldPresenter;
import sample.model.coord.Coordinate;
import sample.model.figure.BaseFigure;

import java.util.List;

import static sample.ui.UiConstants.*;

public class FieldView implements App.OnKeyListener,IFieldView {

    // count cells
    private int xCells = (int) (FIELD_HEIGHT / CELL_WIDTH - 3);
    private int yCells = (int) (FIELD_WIDTH  / CELL_WIDTH);

    private VolatileCell baseMatrix[][] = new VolatileCell[xCells][yCells];

    private IFieldPresenter fieldManager;
    private Timeline timer;

    @FXML
    Pane layout;

    private final int middlePosition = yCells/2;

    @FXML
    void initialize() {

        fieldManager = new FieldPresenter(this,baseMatrix.length,baseMatrix[0].length);

        layout.setMaxHeight(MAIN_SCREEN_HEIGHT);
        layout.setMaxWidth(MAIN_SCREEN_WIDTH);

        System.out.println("Matrix[" + (xCells-1) + "," + (yCells-1) +"]");

        for (int i = 0; i < baseMatrix.length;i++) {
            for (int j = 0; j < baseMatrix[i].length;j++) {
                baseMatrix[i][j] = new VolatileCell(i, j, CellFactory.createDecoratedCell(-1));
                layout.getChildren().add(baseMatrix[i][j]);
            }
        }

        updateFigure(fieldManager.createFigure(new Coordinate(1,middlePosition)));
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

        cleanUpField(fieldManager.getSavedCoordinates());

        for(Coordinate cell : figureCells) {
            baseMatrix[cell.x][cell.y].updateColor(CellFactory.createDecoratedCell(figure.getState()));
        }

        updateField();
    }



    private void cleanUpField(List<Coordinate> coordinates) {
        // get saved coordinates and set to base matrix color,another Cells have empty color

        for (int i = 0; i < baseMatrix.length;i++) {
            for (int j = 0; j < baseMatrix[i].length;j++) {
                int position = coordinates.indexOf(new Coordinate(i,j));

                // if VolatileCell exist in saveManager than - set color of current cell
                if ( position != -1) {
                    baseMatrix[i][j].updateColor(CellFactory.createDecoratedCell(coordinates.get(position).getState()));
                } else
                    baseMatrix[i][j].updateColor(CellFactory.createDecoratedCell(-1));
            }
        }
    }

    @Override
    public void setNewFigure() {
        fieldManager.createFigure(new Coordinate(1,middlePosition));
    }

    @Override
    public void updateField() {
        for (VolatileCell[] aBaseMatrix : baseMatrix) {
            for (VolatileCell anABaseMatrix : aBaseMatrix) {
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

    @Override
    public void showGameOverDialog() {
        System.out.println("Game over");
        timer.stop();
    }
}
