package sample.model.field;

import sample.model.coord.Coordinate;
import sample.model.figure.*;
import sample.model.figure.state.RotationMode;
import sample.ui.field.IFieldController;

import java.util.*;
import java.util.stream.Collectors;

public class FieldManager implements IFieldManager {

    private IFieldController controller;
    private BaseFigure figure;

    // saver dropped  blocks
    private CellsSaver cellsSaver;
    // actual figure coordinates
    private List<Coordinate> coordinates;
    // current rotation mode
    private RotationMode rotationMode;

    // Main Field size
    private int fieldHeight;
    private int fieldWidth;


    public FieldManager(IFieldController controller,CellsSaver cellsSaver,int fieldHeight,int fieldWidth) {
        this.controller = controller;
        this.cellsSaver  = cellsSaver;
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
    }

    @Override
    public BaseFigure createFigure(Coordinate coordinate) {
        // TODO: random create objects
        Random rnd = new Random(System.currentTimeMillis());
        int number = 1 + rnd.nextInt(7 - 1 + 1);

        switch (number) {
            case 1 : {
                figure = new FigureT(coordinate, RotationMode.NORMAL);
                break;
            }
            case 2 : {
                figure = new FigureZ(coordinate, RotationMode.NORMAL);
                break;
            }
            case 3 : {
                figure = new FigureO(coordinate, RotationMode.NORMAL);
                break;
            }
            case 4 : {
                figure = new FigureJ(coordinate, RotationMode.NORMAL);
                break;
            }
            case 5 : {
                figure = new FigureL(coordinate, RotationMode.NORMAL);
                break;
            }
            case 6 : {
                figure = new FigureS(coordinate, RotationMode.NORMAL);
                break;
            }
            default:figure = new FigureI(coordinate,RotationMode.NORMAL);
        }

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
                cellsSaver.addCell(coordinate);
            }

            // set position which was last
            int lastRemovePosition = 0;
            for (int i = 0; i < fieldHeight;i++) {
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
        }
    }

    /**
     *
     *
     * @return false - if can't move down,some blocks stay on this positions
     *  true - move allowed
     */
    private boolean isCanMoveDown() {

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

            /*if (x + 1 == 1) {
                return false;
            }*/
            if (x + 1 >= fieldHeight) {
               // System.out.println("cell.x > baseMatrix.length");
                return false;
            }

            if (cellsSaver.isExist(x+1,y)) {
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

            if (cellsSaver.isExist(x,y-1)) {
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
            System.out.println("y+1");
            if (y + 1 >= fieldWidth) {
                return false;
            }

            if (cellsSaver.isExist(x,y+1)) {
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
        } else if (this.figure instanceof FigureI) {
            tempFigure =  new FigureI(figure.getTopLeftCoordinate(),tempRotationMode);
            System.out.println("FigureI");
        } else if (this.figure instanceof FigureS) {
            tempFigure =  new FigureS(figure.getTopLeftCoordinate(),tempRotationMode);
            System.out.println("FigureS");
        }else if (this.figure instanceof FigureZ) {
            tempFigure =  new FigureZ(figure.getTopLeftCoordinate(),tempRotationMode);
            System.out.println("FigureZ");
        } else if (this.figure instanceof FigureJ) {
            tempFigure =  new FigureJ(figure.getTopLeftCoordinate(),tempRotationMode);
            System.out.println("FigureJ");
        } else if (this.figure instanceof FigureL) {
            tempFigure =  new FigureL(figure.getTopLeftCoordinate(),tempRotationMode);
            System.out.println("FigureL");
        } else {
            tempFigure =  new FigureO(figure.getTopLeftCoordinate(),tempRotationMode);
            System.out.println("FigureO");
        }

        List<Coordinate> newCoordinates = tempFigure.getCoordinates();
        System.out.println("TempFigure :: topCoordinate " + tempFigure.getTopLeftCoordinate());

        tempFigure = null;

        List<Coordinate> savedCoordinates = cellsSaver.getCoordinates();
        // match future figure coordinates with used coordinates
        for (Coordinate coordinate : newCoordinates) {
            if (coordinate.getY() <= 0  || coordinate.getY() >= fieldWidth) {
                System.out.println("IsCanRotate false :: X < 0 or Y >= field width");
                return false;
            } else if (savedCoordinates.contains(coordinate)) {
                System.out.println("IsCanRotate false :: X=" +coordinate.getX() +",Y=" + coordinate.getY() +" filled");
                return false;
            }
        }

        return true;
    }

    private void shiftLinesDown(int startLine,int capacity) {

        // filter all positions cell above  empty line and move they  down
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
