package sample.model.figure;

import sample.model.coord.Coordinate;

import java.util.ArrayList;
import java.util.List;

import static sample.model.figure.BaseFigure.RotationMode.*;

public class FigureT extends BaseFigure {

    private RotationMode rotationMode;
    private List<Coordinate> coordinates = new ArrayList<>();

    public FigureT(Coordinate topLeftCoordinate) {
        super(topLeftCoordinate);
        this.rotationMode = ROTATED_270;
        setFigureMatrix(topLeftCoordinate);
    }

    @Override
    void setFigureMatrix(Coordinate topLeftCoordinate) {
        /*System.out.println("topLeftCoordinate = " + topLeftCoordinate);*/
        coordinates.clear();
        switch (rotationMode) {
            case NORMAL:{
                this.rotationMode = ROTATED_90;
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+2,topLeftCoordinate.y));
                System.out.println("ROTATED_90");
                break;
            }
            case ROTATED_90:{
                this.rotationMode = ROTATED_180;
                coordinates.add(new Coordinate(topLeftCoordinate.x-1,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y+1));
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y+1));
                System.out.println("ROTATED_180");
                break;
            }
            case ROTATED_180:{
                this.rotationMode = ROTATED_270;
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y-2));
                System.out.println("ROTATED_270");
                break;
            }
            default:
                this.rotationMode = NORMAL;
                coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y+1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y));
                /*coordinates.add(topLeftCoordinate);
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x+1,topLeftCoordinate.y-1));
                coordinates.add(new Coordinate(topLeftCoordinate.x,topLeftCoordinate.y-2));*/
                System.out.println("NORMAL");
        }
    }

    @Override
    List<Coordinate> getFigureMatrix() {
        return coordinates;
    }


}
