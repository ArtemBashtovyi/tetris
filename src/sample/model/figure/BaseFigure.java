package sample.model.figure;


import sample.model.coord.Coordinate;
import sample.model.coord.CoordinateComparator;

import java.util.List;

public abstract class BaseFigure {

    private static final CoordinateComparator COMPARATOR = new CoordinateComparator();

    enum RotationMode {
        NORMAL,
        ROTATED_90,
        ROTATED_180,
        ROTATED_270
    }

    private List<Coordinate> coordinates;
    private Coordinate topLeftCoordinate;

    BaseFigure(Coordinate topLeftCoordinate) {
        this.topLeftCoordinate = topLeftCoordinate;
    }

    public void moveLeft() {
        for (Coordinate cell : coordinates) {
            cell.y--;
        }
    }

    public void moveRight() {
        for (Coordinate cell : coordinates) {
            cell.y++;
        }
    }

    public void moveDown() {
        for (Coordinate cell : coordinates) {
            cell.x++;
        }
    }


    public void rotate() {
        // change figure matrix
        setFigureMatrix(topLeftCoordinate);
        //set new changed figure matrix
        coordinates = getFigureMatrix();
        // set left top point
        setTopLeftCoordinate();
    }

    public List<Coordinate> getCoordinates() {
        coordinates = getFigureMatrix();
        return coordinates;
    }

    // computation of left top coordinate figure
    private void setTopLeftCoordinate() {
        coordinates.sort(COMPARATOR);
        System.out.print("All coordinates : ");
        for (Coordinate coordinate : coordinates) {
            System.out.print(coordinate + " ");
        }

        if (!coordinates.isEmpty()) {
            topLeftCoordinate = coordinates.get(0);
        }

    }

    abstract void setFigureMatrix(Coordinate topLeftCoordinate);
    abstract List<Coordinate> getFigureMatrix();
}
