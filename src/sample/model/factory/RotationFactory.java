package sample.model.factory;

import sample.model.coord.Coordinate;
import sample.model.figure.*;
import sample.model.figure.state.RotationMode;

public class RotationFactory implements FigureFactory {
    private BaseFigure figure;

    public RotationFactory(BaseFigure figure) {
        this.figure = figure;
    }

    @Override
    public BaseFigure create(Coordinate coordinate, RotationMode tempRotationMode) {

        // use for checking figure location in future after rotation
        BaseFigure tempFigure;
        if (this.figure instanceof FigureT) {
            // set current rotation mode, which will be changed to another
            tempFigure =  new FigureT(coordinate,tempRotationMode);
            System.out.println("FigureT");
        } else if (this.figure instanceof FigureI) {
            tempFigure =  new FigureI(coordinate,tempRotationMode);
            System.out.println("FigureI");
        } else if (this.figure instanceof FigureS) {
            tempFigure =  new FigureS(coordinate,tempRotationMode);
            System.out.println("FigureS");
        }else if (this.figure instanceof FigureZ) {
            tempFigure =  new FigureZ(coordinate,tempRotationMode);
            System.out.println("FigureZ");
        } else if (this.figure instanceof FigureJ) {
            tempFigure =  new FigureJ(coordinate,tempRotationMode);
            System.out.println("FigureJ");
        } else if (this.figure instanceof FigureL) {
            tempFigure =  new FigureL(coordinate,tempRotationMode);
            System.out.println("FigureL");
        } else {
            tempFigure =  new FigureO(coordinate,tempRotationMode);
            System.out.println("FigureO");
        }
        return tempFigure;
    }
}
