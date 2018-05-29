package tetris.ui.field;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import tetris.model.figure.state.StateConstants;
import tetris.model.cell.VolatileCell;
import tetris.network.Observable;
import tetris.network.ObservableImpl;
import tetris.network.command.CloseCommand;
import tetris.network.command.Command;
import tetris.network.command.CreateCommand;
import tetris.network.socket.SocketManager;
import tetris.presenter.FieldPresenter;
import tetris.presenter.IFieldPresenter;
import tetris.model.coord.Coordinate;
import tetris.model.figure.BaseFigure;
import tetris.ui.OnKeyListener;

import java.util.ArrayList;
import java.util.List;

import static tetris.ui.UiConstants.*;

public class FieldView implements OnKeyListener,IFieldView {

    private static final int ENEMY_MATRIX_START_POSITION = 4;

    // count cells
    private int xCells = (int) (FIELD_HEIGHT / CELL_WIDTH - 3);
    private int yCells = (int) (FIELD_WIDTH  / CELL_WIDTH);

    private VolatileCell baseMatrix[][] = new VolatileCell[xCells][yCells];
    private VolatileCell enemyMatrix[][] = new VolatileCell[xCells][yCells];

    private IFieldPresenter presenter;
    private Timeline timer;
    private SocketManager socketManager;


    @FXML
    Pane layout;

    private final int middlePosition = yCells/2;

    private void initGui() {
        layout.setMaxHeight(MAIN_SCREEN_HEIGHT);
        layout.setMaxWidth(MAIN_SCREEN_WIDTH);

        for (int i = 0; i < baseMatrix.length;i++) {
            for (int j = 0; j < baseMatrix[i].length;j++) {
                baseMatrix[i][j] = new VolatileCell(i, j, StateConstants.EMPTY_COLOR);
                layout.getChildren().add(baseMatrix[i][j]);
            }
        }

        //draw enemy field
        for (int i = 0; i < enemyMatrix.length;i++) {
            for (int j = 0; j < enemyMatrix[i].length;j++) {
                // set offset between enemy and current matrix
                enemyMatrix[i][j] = new VolatileCell(i, j + (yCells + ENEMY_MATRIX_START_POSITION)
                        ,StateConstants.EMPTY_COLOR);
                layout.getChildren().add(enemyMatrix[i][j]);
            }
        }

        updateFigure(presenter.createFigure(new Coordinate(1,middlePosition)));
        startTimer();
    }

    // init GUI only after socket connection will be created
    public void init(SocketManager socketManager) {
        this.socketManager = socketManager;
        presenter = new FieldPresenter(this,socketManager,baseMatrix.length,baseMatrix[0].length);
        //create command for start
        Observable observable = new ObservableImpl();

        Command createCommand = new CreateCommand(socketManager,observable);
        Command closeCommand = new CloseCommand(socketManager);

        presenter.setCommands(createCommand,closeCommand);

        observable.addObserver(presenter);
        initGui();
    }

    private void startTimer() {
         timer = new Timeline(
                new KeyFrame(Duration.seconds(1.90),
                event -> onTimerTick()));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }


    private void onTimerTick() {
        presenter.moveFigureDown();
    }

    private void cleanUpField(List<Coordinate> coordinates) {
        // get saved coordinates and set to base matrix color,another Cells have empty color

        for (int i = 0; i < baseMatrix.length;i++) {
            for (int j = 0; j < baseMatrix[i].length;j++) {
                int position = coordinates.indexOf(new Coordinate(i,j));

                // if VolatileCell exist in fieldSaver than - set color of current cell
                if ( position != -1) {
                    baseMatrix[i][j].updateColor(coordinates.get(position).getState());
                } else
                    baseMatrix[i][j].updateColor(StateConstants.EMPTY_COLOR);
            }
        }
    }

    private void cleanUpEnemyField() {
        for (VolatileCell[] arrayOfCells : enemyMatrix) {
            for (VolatileCell cell : arrayOfCells) {
                cell.updateColor(StateConstants.EMPTY_COLOR);
            }
        }
        System.out.println("\n");
    }

    @Override
    public void updateFigure(@NotNull BaseFigure figure) {
        List<Coordinate> figureCells = figure.getCoordinates();
        cleanUpField(presenter.getSavedCoordinates());

        for(Coordinate cell : figureCells) {
            baseMatrix[cell.x][cell.y].updateColor(figure.getState());
        }

        updateField();
        presenter.writeData(baseMatrix);
    }

    @Override
    public void setNewFigure() {
        presenter.createFigure(new Coordinate(1,middlePosition));
    }

    @Override
    public void updateField() {
        for (VolatileCell[] aBaseMatrix : baseMatrix) {
            for (VolatileCell anABaseMatrix : aBaseMatrix) {
                anABaseMatrix.update();
            }
        }
    }

    @Override
    public void updateEnemyField(@NotNull ArrayList<VolatileCell> coordinates) {
        cleanUpEnemyField();
        for (VolatileCell cell : coordinates) {
            enemyMatrix[cell.getX()][cell.getY()].updateColor(cell.getColor());
            enemyMatrix[cell.getX()][cell.getY()].update();
        }
    }

    @Override
    public VolatileCell[][] getCurrentMatrix() {
        return baseMatrix;
    }

    // Callbacks
    @Override
    public void onUp() {
        presenter.rotate();
    }

    @Override
    public void onLeft() {
        presenter.moveFigureLeft();
    }

    @Override
    public void onRight() {
        presenter.moveFigureRight();
    }

    @Override
    public void onDown() {
        presenter.moveFigureDown();
    }

    @Override
    public void showGameOverDialog() {

        System.out.println("Game over");
        timer.stop();

    }
}
