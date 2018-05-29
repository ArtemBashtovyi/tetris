package tetris.model.factory;

import tetris.model.coord.Coordinate;
import tetris.model.figure.BaseFigure;
import tetris.model.figure.state.RotationMode;

public interface FigureFactory {
    // setting base coordinate
    BaseFigure create(Coordinate coordinate, RotationMode rotationMode);
}
