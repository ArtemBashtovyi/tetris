package sample.model.figure;

import sample.model.coord.Coordinate;
import sample.model.figure.state.RotationMode;
import sample.model.figure.state.StateConstants;

import java.util.ArrayList;
import java.util.List;

public class FigureI extends BaseFigure {

    private List<Coordinate> coordinates = new ArrayList<>();

    public FigureI(Coordinate topLeftCoordinate,RotationMode rotationMode) {
        super(topLeftCoordinate,rotationMode);
        setFigureMatrix(topLeftCoordinate,rotationMode);
    }

    @Override
    void setFigureMatrix(Coordinate topLeftCoordinate, RotationMode rotationMode) {
        coordinates.clear();
        switch (rotationMode) {
            case ROTATED_180:
            case NORMAL: {
                coordinates.add(new Coordinate(topLeftCoordinate.x, topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x , topLeftCoordinate.y+2));
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x, topLeftCoordinate.y+3));
                System.out.println("NORMAL_INSTALLED");
                break;
            }
            case ROTATED_90:
            default: {
                coordinates.add(new Coordinate(topLeftCoordinate.x, topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1, topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+2,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+3, topLeftCoordinate.y+1));
                System.out.println("ROTATED_90_INSTALLED");
            }
        }
    }

    @Override
    List<Coordinate> getFigureMatrix() {
        return coordinates;
    }

    @Override
    public int getState() {
        return StateConstants.FIGURE_I;
    }
}
