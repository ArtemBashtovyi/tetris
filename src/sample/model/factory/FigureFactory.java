package sample.model.factory;

import sample.model.coord.Coordinate;
import sample.model.figure.BaseFigure;
import sample.model.figure.state.RotationMode;

public interface FigureFactory {
    // setting base coordinate
    BaseFigure create(Coordinate coordinate, RotationMode rotationMode);
}
