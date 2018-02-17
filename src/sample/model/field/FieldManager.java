package sample.model.field;

import sample.model.cell.Cell;
import sample.model.coord.Coordinate;
import sample.model.figure.BaseFigure;
import sample.model.figure.FigureT;
import sample.ui.field.IFieldController;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FieldManager implements IFieldManager {


    private int middlePosition;

    private IFieldController controller;
    private BaseFigure figure;

    // saver static blocks, which were stop
    private CellsSaver cellsSaver;
    private List<Coordinate> coordinates;


    private Cell[][] baseMatrix;

    public FieldManager(int middlePosition,IFieldController controller,CellsSaver cellsSaver) {
        this.controller = controller;
        this.middlePosition = middlePosition;
        this.cellsSaver = cellsSaver;
    }


    @Override
    public void moveFigureDown() {
        if (isCanMoveDown()) {
            figure.moveDown();
        } else {

            Cell[][] baseCells = controller.getBaseCells();

            boolean isNeedToShiftLinesDown = false;

            // add current figure
            for (Coordinate coordinate : coordinates) {
                cellsSaver.addCell(coordinate);
            }

            int lastRemovePosition = 0;
            for (int i = 0; i < baseCells.length;i++) {
                if (cellsSaver.isLineFilled(i)) {
                    cellsSaver.removeCellsLine(i);
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

                if (isNeedToShiftLinesDown && shiftCapacity != 0) {
                    System.out.println("-----need Shift Down on lastRemovePos = "
                            + lastRemovePosition  + " capacity= " + shiftCapacity);
                    shiftLinesDown(lastRemovePosition,shiftCapacity);
                }
            }

            controller.setNewFigure();
        }

        controller.updateFigure(figure);
    }

    @Override
    public void moveFigureLeft() {
        //if (!isCollision()) {
            figure.moveLeft();
            controller.updateFigure(figure);
       // }
    }

    @Override
    public void moveFigureRight() {
        //if (!isCollision()) {
            figure.moveRight();
            controller.updateFigure(figure);
       // }
    }

    @Override
    public void rotate() {
        figure.rotate();
        controller.updateFigure(figure);
        // set figure cell if change matrix (rotate stone)

    }

    @Override
    public BaseFigure createFigure(int x, int y) {
        // TODO: random create objects

        if (true) {
            figure = new FigureT(new Coordinate(x,y));
        }
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
        return true;
    }

    private boolean isCanTurnRight() {
        return true;
    }

    private void shiftLinesDown(int startLine,int capacity) {

        // filter all positions cell above  empty line and move they to down
        List<Coordinate> coordinates = cellsSaver.getCoordinates()
                .stream()
                .filter(coordinate -> coordinate.getX() < startLine)
                .peek(coordinate -> coordinate.x = coordinate.getX() + capacity)
                .collect(Collectors.toList());

        cellsSaver.setCoordinates(coordinates);
    }

}
