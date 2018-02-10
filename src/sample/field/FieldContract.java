package sample.field;

import sample.model.cell.Cell;
import sample.model.figure.BaseFigure;

public interface FieldContract {

    void moveFigure();

    Cell[][] getFigureCells();

    BaseFigure getFigure();

}
