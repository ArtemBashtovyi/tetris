package sample.model.field;

import sample.model.figure.BaseFigure;

public interface IFieldManager {


    void moveFigureDown();

    void moveFigureLeft();

    void moveFigureRight();

    void rotate();

    BaseFigure createFigure(int x,int y);


}
