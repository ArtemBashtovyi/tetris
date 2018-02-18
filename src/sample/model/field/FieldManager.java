package sample.model.field;

import sample.model.cell.Cell;
import sample.model.coord.Coordinate;
import sample.model.figure.BaseFigure;
import sample.model.figure.FigureT;
import sample.model.figure.RotationMode;
import sample.ui.field.IFieldController;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FieldManager implements IFieldManager {


    private IFieldController controller;
    private BaseFigure figure;

    // saver static blocks, which were stop
    private CellsSaver cellsSaver;
    private List<Coordinate> coordinates;
    private RotationMode rotationMode;
    private List<Coordinate> usedCoordinates;
    private int fieldHeight;
    private int filedWidth;

    private Cell[][] baseMatrix;


    public FieldManager(IFieldController controller,CellsSaver cellsSaver,int fieldHeight,int filedWidth) {
        this.controller = controller;
        this.cellsSaver  = cellsSaver;
        this.fieldHeight = fieldHeight;
        this.filedWidth = filedWidth;
    }


    @Override
    public void moveFigureDown() {
        if (isCanMoveDown()) {
            figure.moveDown();
        } else {

            boolean isNeedToShiftLinesDown = false;

            // add current figure
            for (Coordinate coordinate : coordinates) {
                cellsSaver.addCell(coordinate);
            }

            // set position which was last
            int lastRemovePosition = 0;
            for (int i = 0; i < baseMatrix.length;i++) {
                if (cellsSaver.isLineFilled(i)) {
                    cellsSaver.removeCellsLine(i);
                    System.out.println("removedLine" + i);
                    lastRemovePosition = i;
                }
            }

            System.out.println(lastRemovePosition +"= lastRemPosition");
            // If was delete at least one line
            if (lastRemovePosition != 0) {

                int shiftCapacity = 0;

                for (int i = lastRemovePosition; i > 0; i--) {
                    if (!cellsSaver.isLineEmpty(i)) {
                        isNeedToShiftLinesDown = true;
                        break;
                    }
                    shiftCapacity++;
                }

                System.out.println(shiftCapacity + "= shift capacity");
                if (isNeedToShiftLinesDown && shiftCapacity != 0) {
                    shiftLinesDown(lastRemovePosition,shiftCapacity);
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
        } else {
            //figure.setCoordinates(backupCoordinates);
        }
    }

    @Override
    public BaseFigure createFigure(Coordinate coordinate) {
        // TODO: random create objects

        if (true) {
            figure = new FigureT(new Coordinate(coordinate.getX(),coordinate.getY()), RotationMode.NORMAL);
        }
        rotationMode = RotationMode.NORMAL;
        coordinates = figure.getCoordinates();
        baseMatrix  = controller.getBaseCells();
        return figure;
    }

    /**
     *
     *
     * @return false - if can't move down,some blocks stay on this positions
     *  true - move allowed
     */
    private boolean isCanMoveDown() {

        // temporary
        //Cell[][] baseMatrix = controller.getBaseCells();

        // Use coordinates which hasn't colored cell under themselves
        List<Coordinate> filteredCoordinates = coordinates.stream()
                .collect(Collectors.groupingBy(Coordinate::getY)) // group Coord for each Y in various collections
                .values()
                .stream()// stream of collections Stream<List<Coordinate>>
                .map(groupedCoordinates -> {
                    // return only most biggest value from the collection(with same Y coordinate items)
                    if (groupedCoordinates.size() > 1)  {
                        return Collections
                                .singletonList(groupedCoordinates
                                .stream()
                                .max(Comparator.comparingInt(Coordinate::getX)).get());
                    }
                        return groupedCoordinates;
                })
                // join all collection to one collection
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for(Coordinate coordinate : filteredCoordinates) {

            int x = coordinate.x;
            int y = coordinate.y;

            if (x + 1 == 1) {
                return false;
            }
            if (x + 1 >= baseMatrix.length) {
               // System.out.println("cell.x > baseMatrix.length");
                return false;
            }

            if (baseMatrix[x+1][y].isFilled()) {
                //System.out.println("filled block" + coordinate);
                return false;
            }
        }


        return true;
    }

    private boolean isCanTurnLeft() {
        List<Coordinate> filteredCoordinates = coordinates.stream()
                .collect(Collectors.groupingBy(Coordinate::getX))
                .values()
                .stream()
                .map(groupedCoordinates -> {
                    if (groupedCoordinates.size() > 1)  {
                        return Collections
                                .singletonList(groupedCoordinates
                                        .stream()
                                        .min(Comparator.comparingInt(Coordinate::getY)).get());
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

            if (baseMatrix[x][y-1].isFilled()) {
                //System.out.println("filled block" + coordinate);
                return false;
            }
        }

        return true;
    }

    private boolean isCanTurnRight() {
        List<Coordinate> filteredCoordinates = coordinates.stream()
                .collect(Collectors.groupingBy(Coordinate::getX))
                .values()
                .stream()
                .map(groupedCoordinates -> {
                    if (groupedCoordinates.size() > 1)  {
                        return Collections
                                .singletonList(groupedCoordinates
                                        .stream()
                                        .max(Comparator.comparingInt(Coordinate::getY)).get());
                    }
                    return groupedCoordinates;
                })
                // join all collection to one collection
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for(Coordinate coordinate : filteredCoordinates) {

            int x = coordinate.x;
            int y = coordinate.y;

            if (y + 1 >= baseMatrix[0].length) {
                return false;
            }

            if (baseMatrix[x][y+1].isFilled()) {
                //System.out.println("filled block" + coordinate);
                return false;
            }
        }

        return true;
    }

    private boolean isCanRotate() {
        BaseFigure tempFigure;
        RotationMode tempRotationMode = RotationMode.getNext(rotationMode);
        if (this.figure instanceof FigureT) {
            // set current rotation mode, which will be changed to another
            tempFigure =  new FigureT(figure.getTopLeftCoordinate(),tempRotationMode);
            System.out.println("FigureT");
        } else tempFigure = new FigureT(figure.getTopLeftCoordinate(),tempRotationMode);

        List<Coordinate> newCoordinates = tempFigure.getCoordinates();

        System.out.println("TempFigure :: topCoordinate " + tempFigure.getTopLeftCoordinate());

        tempFigure = null;

        List<Coordinate> savedCoordinates = cellsSaver.getCoordinates();

        for (Coordinate coordinate : newCoordinates) {
            if (coordinate.getX() <= 0 || coordinate.getY() >= baseMatrix[0].length) {
                System.out.println("IsCanRotate false :: X < 0 or Y >= baseMatrix[0].length");
                return false;
            } else if (savedCoordinates.contains(coordinate)) {
                System.out.println("IsCanRotate false :: X=" +coordinate.getX() +",Y=" + coordinate.getY() +" filled");
                return false;
            }
        }

        return true;
    }

    private void shiftLinesDown(int startLine,int capacity) {

        // filter all positions cell above  empty line and move they to down
        List<Coordinate> coordinates = cellsSaver.getCoordinates()
                .stream()
                .peek(coordinate -> {
                    if (coordinate.getX() < startLine)
                    coordinate.x = coordinate.getX() + capacity;
                })
                .collect(Collectors.toList());

        cellsSaver.setCoordinates(coordinates);
    }

}
