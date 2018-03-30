package sample.presenter;

import sample.model.coord.Coordinate;
import sample.model.factory.FigureFactory;
import sample.model.factory.RandomFactory;
import sample.model.factory.RotationFactory;
import sample.save.SaveManager;
import sample.model.figure.*;
import sample.model.figure.state.RotationMode;
import sample.ui.field.IFieldView;

import java.util.*;
import java.util.stream.Collectors;

public class FieldPresenter implements IFieldPresenter {

    private IFieldView controller;
    private BaseFigure figure;

    // saver dropped  blocks
    private SaveManager saveManager;
    // actual figure coordinates
    private List<Coordinate> coordinates;
    // current rotation mode
    private RotationMode rotationMode;

    private FigureFactory randomFactory;
    private FigureFactory rotationFactory;

    // App Field size
    private int fieldHeight;
    private int fieldWidth;


    public FieldPresenter(IFieldView controller, int fieldHeight, int fieldWidth) {
        this.controller = controller;
        this.saveManager = SaveManager.getInstance(fieldWidth);
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        randomFactory = new RandomFactory();
    }

    @Override
    public BaseFigure createFigure(Coordinate coordinate) {

        figure = randomFactory.create(coordinate,RotationMode.NORMAL);

        rotationMode = RotationMode.NORMAL;

        coordinates = figure.getCoordinates();
        return figure;
    }


    @Override
    public void moveFigureDown() {
        if (isCanMoveDown()) {
            figure.moveDown();

        } else {

            boolean isNeedToShiftLinesDown = false;

            // add current figure
            for (Coordinate coordinate : coordinates) {
                saveManager.addCell(coordinate);
                if (coordinate.x <= 1) {
                    System.out.println("GAME OVER");
                    controller.showGameOverDialog();
                    break;
                }
            }

            // set position which was last
            int lastRemovePosition = 0;
            for (int i = 0; i < fieldHeight;i++) {
                if (saveManager.isLineFilled(i)) {
                    saveManager.removeCellsLine(i);
                    System.out.println("removedLine" + i);
                    lastRemovePosition = i;
                }
            }

            System.out.println(lastRemovePosition +"= lastRemPosition");
            // If was delete at least one line
            if (lastRemovePosition != 0) {

                int shiftCapacity = 0;

                for (int i = lastRemovePosition; i > 0; i--) {
                    if (!saveManager.isLineEmpty(i)) {
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

        // Use coordinates which hasn't colored cell under themselves
        List<Coordinate> filteredCoordinates = coordinates.stream()
                .collect(Collectors.groupingBy(coordinate -> coordinate.y)) // group Coord for each Y in various collections
                .values()
                .stream()// stream of collections Stream<List<Coordinate>>
                .map(groupedCoordinates -> {
                    // return only most biggest value from the collection(with same Y coordinate items)
                    if (groupedCoordinates.size() > 1)  {
                        return Collections
                                .singletonList(groupedCoordinates
                                .stream()
                                .max(Comparator.comparingInt(coordinate -> coordinate.x)).get());
                    }
                        return groupedCoordinates;
                })
                // join all collection to one collection
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for(Coordinate coordinate : filteredCoordinates) {

            int x = coordinate.x;
            int y = coordinate.y;

            // FIXME : Second way to handle game over mode
            /*if (x + 1 == 1) {
                return false;
            }*/
            if (x + 1 >= fieldHeight) {
               // System.out.println("cell.x > baseMatrix.length");
                return false;
            }

            if (saveManager.isExist(x+1,y)) {
                //System.out.println("filled block" + coordinate);
                return false;
            }
        }


        return true;
    }

    private boolean isCanTurnLeft() {
        List<Coordinate> filteredCoordinates = coordinates.stream()
                .collect(Collectors.groupingBy(coordinate -> coordinate.x))
                .values()
                .stream()
                .map(groupedCoordinates -> {
                    if (groupedCoordinates.size() > 1)  {
                        return Collections
                                .singletonList(groupedCoordinates
                                        .stream()
                                        .min(Comparator.comparingInt(coordinate -> coordinate.y)).get());
                    }
                    return groupedCoordinates;
                })
                // join all collection to one collection
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for(Coordinate coordinate : filteredCoordinates) {

            int x = coordinate.x;
            int y = coordinate.y;

            if (y - 1 < 0) {
                return false;
            }

            if (saveManager.isExist(x,y-1)) {
                //System.out.println("filled block" + coordinate);
                return false;
            }
        }

        return true;
    }

    // check right collision
    private boolean isCanTurnRight() {
        List<Coordinate> filteredCoordinates = coordinates.stream()
                .collect(Collectors.groupingBy(coordinate -> coordinate.x))
                .values()
                .stream()
                .map(groupedCoordinates -> {
                    if (groupedCoordinates.size() > 1)  {
                        return Collections
                                .singletonList(groupedCoordinates
                                        .stream()
                                        .max(Comparator.comparingInt(coordinate -> coordinate.y)).get());
                    }
                    return groupedCoordinates;
                })
                // join all collection to one collection
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for(Coordinate coordinate : filteredCoordinates) {

            int x = coordinate.x;
            int y = coordinate.y;
            System.out.println("y+1");
            if (y + 1 >= fieldWidth) {
                return false;
            }

            if (saveManager.isExist(x,y+1)) {
                return false;
            }
        }

        return true;
    }

    private boolean isCanRotate() {
        BaseFigure tempFigure;

        RotationMode tempRotationMode = RotationMode.getNext(rotationMode);

        rotationFactory = new RotationFactory(figure);
        tempFigure = rotationFactory.create(figure.getTopLeftCoordinate(),tempRotationMode);

        List<Coordinate> newCoordinates = tempFigure.getCoordinates();
        System.out.println("TempFigure :: topCoordinate " + tempFigure.getTopLeftCoordinate());

        List<Coordinate> savedCoordinates = saveManager.getCoordinates();
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
        List<Coordinate> coordinates = saveManager.getCoordinates()
                .stream()
                .peek(coordinate -> {
                    if (coordinate.x < startLine)
                    coordinate.x = coordinate.x + capacity;
                })
                .collect(Collectors.toList());

        saveManager.setCoordinates(coordinates);
    }

    public List<Coordinate> getSavedCoordinates() {
        return saveManager.getCoordinates();
    }



}
