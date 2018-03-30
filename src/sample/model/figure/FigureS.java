package sample.model.figure;

import javafx.scene.Parent;
import sample.model.coord.Coordinate;
import sample.model.figure.state.RotationMode;

import java.util.ArrayList;
import java.util.List;

import static sample.model.figure.state.StateConstants.FIGURE_S;

public class FigureS extends BaseFigure{

    private List<Coordinate> coordinates = new ArrayList<>();


    public FigureS(Coordinate topLeftCoordinate, RotationMode rotationMode) {
        super(topLeftCoordinate, rotationMode);
        setFigureMatrix(topLeftCoordinate,rotationMode);
    }

    @Override
    void setFigureMatrix(Coordinate topLeftCoordinate, RotationMode rotationMode) {
        coordinates.clear();
        switch (rotationMode) {
            case ROTATED_180:
            case NORMAL: {
                coordinates.add(new Coordinate(topLeftCoordinate.x , topLeftCoordinate.y-1));
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x-1 , topLeftCoordinate.y ));
                coordinates.add(new Coordinate(topLeftCoordinate.x-1, topLeftCoordinate.y+1));
                System.out.println("NORMAL_INSTALLED");
                break;
            }
            default:
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+2,topLeftCoordinate.y+1));
                System.out.println("ROTATED_270_INSTALLED");
        }
    }

    @Override
    List<Coordinate> getFigureMatrix() {
        return coordinates;
    }

    @Override
    public int getState() {
        return FIGURE_S;
    }
}
