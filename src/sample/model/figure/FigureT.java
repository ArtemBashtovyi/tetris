package sample.model.figure;

import sample.model.coord.Coordinate;
import sample.model.figure.state.RotationMode;
import sample.model.figure.state.StateConstants;

import java.util.ArrayList;
import java.util.List;


public class FigureT extends BaseFigure {

    private List<Coordinate> coordinates = new ArrayList<>();

    public FigureT(Coordinate topLeftCoordinate,RotationMode rotationMode) {
        super(topLeftCoordinate,rotationMode);
        setFigureMatrix(topLeftCoordinate,rotationMode);
    }

    @Override
    void setFigureMatrix(Coordinate topLeftCoordinate,RotationMode rotationMode) {
        coordinates.clear();
        switch (rotationMode) {
            case NORMAL:{
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+2,topLeftCoordinate.y));
                System.out.println("NORMAL_INSTALLED");
                break;
            }
            case ROTATED_90:{
                coordinates.add(new Coordinate(topLeftCoordinate.x-1,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y+1));
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y+1));
                System.out.println("ROTATED_90_INSTALLED");
                break;
            }
            case ROTATED_180:{
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y-2));
                System.out.println("ROTATED_180_INSTALLED");
                break;
            }
            default:
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y-2));
                System.out.println("ROTATED_270_INSTALLED");
        }
    }

    @Override
    List<Coordinate> getFigureMatrix() {
        return coordinates;
    }

    @Override
    public int getState() {
        return StateConstants.FIGURE_T;
    }


}
