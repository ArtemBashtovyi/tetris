package sample.model.figure;


import sample.model.coord.Coordinate;
import sample.model.coord.CoordinateComparator;
import sample.model.figure.state.RotationMode;

import java.io.Serializable;
import java.util.List;

public abstract class BaseFigure implements Serializable {


    private static final CoordinateComparator COMPARATOR = new CoordinateComparator();

    private List<Coordinate> coordinates;
    private Coordinate topLeftCoordinate;

    BaseFigure(Coordinate topLeftCoordinate,RotationMode rotationMode) {
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

    public void rotate(RotationMode rotationMode) {
        // change figure matrix
        setFigureMatrix(topLeftCoordinate,rotationMode);
        //set new changed figure matrix
        coordinates = getFigureMatrix();
        // set left top point
        setTopLeftCoordinate();
    }

    // get current coordinates of figure
    public List<Coordinate> getCoordinates() {
        coordinates = getFigureMatrix();
        return coordinates;
    }

    // get current top left coordinate
    public Coordinate getTopLeftCoordinate() {
        return topLeftCoordinate;
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

    abstract void setFigureMatrix(Coordinate topLeftCoordinate,RotationMode rotationMode);
    abstract List<Coordinate> getFigureMatrix();
    public abstract int getState();


    @Override
    public String toString() {
        return "Figure{}" ;
    }
}
