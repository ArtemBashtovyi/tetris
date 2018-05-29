package tetris.model.figure;

import tetris.model.coord.Coordinate;
import tetris.model.figure.state.RotationMode;
import tetris.model.figure.state.StateConstants;

import java.util.ArrayList;
import java.util.List;

import static tetris.model.figure.state.StateConstants.FIGURE_L;

public class FigureL extends BaseFigure {

    private List<Coordinate> coordinates = new ArrayList<>();

    public FigureL(Coordinate topLeftCoordinate, RotationMode rotationMode) {
        super(topLeftCoordinate, rotationMode);
        setFigureMatrix(topLeftCoordinate,rotationMode);
    }

    @Override
    void setFigureMatrix(Coordinate topLeftCoordinate, RotationMode rotationMode) {
        coordinates.clear();
        switch (rotationMode) {
            case NORMAL:{
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y));
                coordinates.add(new Coordinate(topLeftCoordinate.x+2,topLeftCoordinate.y));
                coordinates.add(new Coordinate(topLeftCoordinate.x+2,topLeftCoordinate.y+1));
                System.out.println("NORMAL_INSTALLED");
                break;
            }
            case ROTATED_90:{
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y+2));
                System.out.println("ROTATED_90_INSTALLED");
                break;
            }
            case ROTATED_180:{
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+2,topLeftCoordinate.y+1));
                System.out.println("ROTATED_180_INSTALLED");
                break;
            }
            default:
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y+2));
                coordinates.add(new Coordinate(topLeftCoordinate.x-1,topLeftCoordinate.y+2));
                System.out.println("ROTATED_270_INSTALLED");
        }
    }

    @Override
    List<Coordinate> getFigureMatrix() {
        for (Coordinate coordinate : coordinates) {
            coordinate.setState(StateConstants.FIGURE_L);
        }
        return coordinates;
    }

    @Override
    public String toString() {
        return super.toString() + " L";

    }

    @Override
    public int getState() {
        return FIGURE_L;
    }
}
