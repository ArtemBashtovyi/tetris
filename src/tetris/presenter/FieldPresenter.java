package tetris.presenter;

import org.jetbrains.annotations.NotNull;
import tetris.model.cell.VolatileCell;
import tetris.model.coord.Coordinate;
import tetris.model.factory.FigureFactory;
import tetris.model.factory.RandomFactory;
import tetris.model.factory.RotationFactory;
import tetris.model.figure.state.StateConstants;
import tetris.move.MoveManager;
import tetris.move.commands.DownCommand;
import tetris.network.socket.SocketManager;
import tetris.save.FieldSaver;
import tetris.model.figure.*;
import tetris.model.figure.state.RotationMode;
import tetris.ui.field.IFieldView;

import java.util.*;
import java.util.stream.Collectors;

public class FieldPresenter implements IFieldPresenter,DownCommand.GameOverHandler {

    private IFieldView view;
    private BaseFigure figure;
    // App Field size
    private int fieldHeight;
    private int fieldWidth;


    // saver dropped  blocks
    private FieldSaver fieldSaver;
    // actual figure coordinates
    private List<Coordinate> coordinates;
    // current rotation mode
    private RotationMode rotationMode;

    private RandomFactory randomFactory;
    private FigureFactory rotationFactory;
    private MoveManager moveManager;
    private SocketManager socketManager;

    private tetris.network.command.Command startCommand;
    private tetris.network.command.Command stopCommand;

    public FieldPresenter(IFieldView view, @NotNull SocketManager socketManager, int fieldHeight, int fieldWidth) {
        this.view = view;
        this.fieldSaver = FieldSaver.getInstance(fieldWidth);
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        this.socketManager = socketManager;

       // socketManager.createSocket(this);

        randomFactory = new RandomFactory();
        moveManager = new MoveManager(fieldHeight,fieldWidth, fieldSaver,this);
    }


    @Override
    public BaseFigure createFigure(Coordinate coordinate) {

        figure = randomFactory.create(coordinate,RotationMode.NORMAL);
        System.out.println("--------Next Figure------" + randomFactory.getNextFigure(coordinate));
        rotationMode = RotationMode.NORMAL;

        coordinates = figure.getCoordinates();
        return figure;
    }

    @Override
    public void setCommands(tetris.network.command.Command startCommand, tetris.network.command.Command stopCommand) {
        this.startCommand = startCommand;
        this.stopCommand = stopCommand;

        startCommand.execute();
    }



    // FIXME : to move logic to another module
    @Override
    public void moveFigureDown() {
        if (isCanMoveDown()) {
            figure.moveDown();

        } else {

            boolean isNeedToShiftLinesDown = false;

            // add current figure
            for (Coordinate coordinate : coordinates) {
                fieldSaver.addCell(coordinate);
                if (coordinate.x <= 1) {
                    System.out.println("GAME OVER");
                    view.showGameOverDialog();
                    break;
                }
            }

            // set position which was last
            int lastRemovePosition = 0;
            for (int i = 0; i < fieldHeight;i++) {
                if (fieldSaver.isLineFilled(i)) {
                    fieldSaver.removeCellsLine(i);
                    System.out.println("removedLine" + i);
                    lastRemovePosition = i;
                }
            }

            System.out.println(lastRemovePosition +"= lastRemPosition");
            // If was delete at least one line
            if (lastRemovePosition != 0) {

                int shiftCapacity = 0;

                for (int i = lastRemovePosition; i > 0; i--) {
                    if (!fieldSaver.isLineEmpty(i)) {
                        isNeedToShiftLinesDown = true;
                        break;
                    }
                    shiftCapacity++;
                }

                System.out.println(shiftCapacity + "= shift capacity");
                if (isNeedToShiftLinesDown && shiftCapacity != 0) {
                    shiftLinesDown(lastRemovePosition, shiftCapacity);
                }

                // TODO : (not here) impl callbacks when figure can't move down,and finish game
            }

            view.setNewFigure();
        }

        view.updateFigure(figure);
    }

    @Override
    public void moveFigureLeft() {
        if (isCanTurnLeft()) {
            figure.moveLeft();
            view.updateFigure(figure);
        }
    }

    @Override
    public void moveFigureRight() {
        if (isCanTurnRight()) {
            figure.moveRight();
            view.updateFigure(figure);
        }
    }

    @Override
    public void rotate() {
        if (isCanRotate()) {
            // change next figure mode
            rotationMode = RotationMode.getNext(rotationMode);
            figure.rotate(rotationMode);
            view.updateFigure(figure);
            // set figure cell if change matrix (rotate stone)
        }
    }

    /**
     * @return false - if can't move down,some blocks stay on this positions
     *  true - move allowed
     */
    private boolean isCanMoveDown() {
        return moveManager.isCanMoveDown(coordinates);
    }


    private boolean isCanTurnLeft() {
        return moveManager.isCanMoveLeft(coordinates);
    }

    // check right collision
    private boolean isCanTurnRight() {
        return moveManager.isCanMoveRight(coordinates);
    }


    private boolean isCanRotate() {
        BaseFigure tempFigure;

        RotationMode tempRotationMode = RotationMode.getNext(rotationMode);

        rotationFactory = new RotationFactory(figure);
        tempFigure = rotationFactory.create(figure.getTopLeftCoordinate(),tempRotationMode);

        List<Coordinate> newCoordinates = tempFigure.getCoordinates();
        System.out.println("TempFigure :: topCoordinate " + tempFigure.getTopLeftCoordinate());

        List<Coordinate> savedCoordinates = fieldSaver.getCoordinates();
        // match future figure coordinates with used coordinates
        for (Coordinate coordinate : newCoordinates) {
            if (coordinate.y <= 0  || coordinate.y >= fieldWidth) {
                System.out.println("IsCanRotate false :: X < 0 or Y >= presenter width");
                return false;
            } else if (savedCoordinates.contains(coordinate)) {
                System.out.println("IsCanRotate false :: X=" + coordinate.x +",Y=" + coordinate.y +" filled");
                return false;
            }
        }

        return true;
    }

    private void shiftLinesDown(int startLine,int capacity) {

        // filter all positions cell above  empty line and move they  down
        List<Coordinate> coordinates = fieldSaver.getCoordinates()
                .stream()
                .peek(coordinate -> {
                    if (coordinate.x < startLine)
                    coordinate.x = coordinate.x + capacity;
                })
                .collect(Collectors.toList());

        fieldSaver.setCoordinates(coordinates);
    }

    public List<Coordinate> getSavedCoordinates() {
        return fieldSaver.getCoordinates();
    }

    @Override
    public void writeData(VolatileCell[][] matrix) {
        ArrayList<VolatileCell> listOfColoredCells = new ArrayList<>(24);

        for (VolatileCell[] aMatrix : matrix) {
            for (VolatileCell anAMatrix : aMatrix) {
                if (anAMatrix.getColor() != StateConstants.EMPTY_COLOR) {
                    listOfColoredCells.add(anAMatrix);
                }
            }
        }
        socketManager.writeData(listOfColoredCells);
    }


    @Override
    public void update(@NotNull ArrayList<VolatileCell> matrix) {
        view.updateEnemyField(matrix);
    }


    // Down Command Callback
    @Override
    public void onGameOver() {
        stopCommand.execute();
    }
}
