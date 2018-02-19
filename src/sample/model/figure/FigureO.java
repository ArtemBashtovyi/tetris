package sample.model.figure;

import sample.model.coord.Coordinate;
import sample.model.figure.state.RotationMode;

import java.util.ArrayList;
import java.util.List;

import static sample.model.figure.state.StateConstants.FIGURE_O;

public class FigureO extends BaseFigure {

    private List<Coordinate> coordinates = new ArrayList<>();

    public FigureO(Coordinate topLeftCoordinate, RotationMode rotationMode) {
        super(topLeftCoordinate, rotationMode);
        setFigureMatrix(topLeftCoordinate,rotationMode);
    }

    @Override
    void setFigureMatrix(Coordinate topLeftCoordinate, RotationMode rotationMode) {
        coordinates.clear();
        switch (rotationMode) {
            case NORMAL:
            case ROTATED_90:
            case ROTATED_180:
            case ROTATED_270:
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y-2));
        }
    }

    @Override
    List<Coordinate> getFigureMatrix() {
        return coordinates;
    }

    @Override
    public int getState() {
        return FIGURE_O;
    }
}
