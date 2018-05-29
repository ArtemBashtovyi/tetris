package tetris.model.factory;

import org.jetbrains.annotations.NotNull;
import tetris.model.coord.Coordinate;
import tetris.model.figure.*;
import tetris.model.figure.state.RotationMode;

import java.util.Random;

public class RandomFactory implements FigureFactory {

    private static Random rnd = new Random(System.currentTimeMillis());

    private BaseFigure nextFigure;


    public RandomFactory() {
    }

    @Override
    public BaseFigure create(Coordinate coordinate,RotationMode rotationMode) {
        /*BaseFigure result = getNextFigure(coordinate);
        setNextFigure(coordinate,RotationMode.getRandomRotationMode());*/
        return createFigure(coordinate,RotationMode.getRandomRotationMode());
    }

    public void setNextFigure(Coordinate coordinate,RotationMode rotationMode){
        this.nextFigure = createFigure(coordinate,rotationMode);
    }

    public BaseFigure getNextFigure(Coordinate coordinate) {
        if (nextFigure == null) {
            nextFigure = createFigure(coordinate,RotationMode.getRandomRotationMode());
        }
        return nextFigure;
    }


    @NotNull
    private BaseFigure createFigure(Coordinate coordinate,RotationMode rotationMode) {
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
