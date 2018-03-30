package sample.model.factory;

import sample.model.coord.Coordinate;
import sample.model.figure.*;
import sample.model.figure.state.RotationMode;

import java.util.Random;

public class RandomFactory implements FigureFactory {
    private static Random rnd = new Random(System.currentTimeMillis());

    @Override
    public BaseFigure create(Coordinate coordinate,RotationMode rotationMode) {
        int number = 1 + rnd.nextInt(7 - 1 + 1);
        switch (number) {
            case 1 : {
                return new FigureT(coordinate, rotationMode);
            }
            case 2 : {
                return new FigureZ(coordinate, rotationMode);
            }
            case 3 : {
                return new FigureO(coordinate, rotationMode);
            }
            case 4 : {
                return new FigureJ(coordinate, rotationMode);
            }
            case 5 : {
                return new FigureL(coordinate, rotationMode);
            }
            case 6 : {
                return new FigureS(coordinate, rotationMode);
            }
            default:return new FigureI(coordinate,rotationMode);
        }
    }
}
