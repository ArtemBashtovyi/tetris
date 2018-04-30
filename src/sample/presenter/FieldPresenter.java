package sample.presenter;

import sample.model.coord.Coordinate;
import sample.model.factory.FigureFactory;
import sample.model.factory.RandomFactory;
import sample.model.factory.RotationFactory;
import sample.move.MoveManager;
import sample.move.commands.DownCommand;
import sample.save.FieldSaver;
import sample.model.figure.*;
import sample.model.figure.state.RotationMode;
import sample.ui.field.IFieldView;

import java.util.*;
import java.util.stream.Collectors;

public class FieldPresenter implements IFieldPresenter,DownCommand.GameOverHandler {

    private IFieldView controller;
    private BaseFigure figure;

    // saver dropped  blocks
    private FieldSaver fieldSaver;
    // actual figure coordinates
    private List<Coordinate> coordinates;
    // current rotation mode
    private RotationMode rotationMode;

    private RandomFactory randomFactory;
    private FigureFactory rotationFactory;

    // App Field size
    private int fieldHeight;
    private int fieldWidth;

    private MoveManager moveManager;

    public FieldPresenter(IFieldView controller, int fieldHeight, int fieldWidth) {
        this.controller = controller;
        this.fieldSaver = FieldSaver.getInstance(fieldWidth);
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
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
                    controller.showGameOverDialog();
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

            controller.setNewFigure();
        }

        controller.updateFigure(figure);
    }

    @Override
    public void moveFigureLeft() {
        if (isCanTurnLeft()) {
            figure.moveLeft();
            controller.updateFigure(figure);
        }
    }

    @Override
    public void moveFigureRight() {
        if (isCanTurnRight()) {
            figure.moveRight();
            controller.updateFigure(figure);
        }
    }

    @Override
    public void rotate() {
        if (isCanRotate()) {
            // change next figure mode
            rotationMode = RotationMode.getNext(rotationMode);
            figure.rotate(rotationMode);
            controller.updateFigure(figure);
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


    // FIXME : make Command Pattern
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
    public void onGameOver() {

    }
}
