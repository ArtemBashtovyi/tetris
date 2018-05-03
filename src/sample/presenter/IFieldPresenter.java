package sample.presenter;

import sample.model.cell.VolatileCell;
import sample.model.coord.Coordinate;
import sample.model.figure.BaseFigure;

import java.util.ArrayList;
import java.util.List;

public interface IFieldPresenter {


    void moveFigureDown();

    void moveFigureLeft();

    void moveFigureRight();

    void rotate();

    BaseFigure createFigure(Coordinate coordinate);

    List<Coordinate> getSavedCoordinates();

    void writeData(VolatileCell[][] matrix);
}
