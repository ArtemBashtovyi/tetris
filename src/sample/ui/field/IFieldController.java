package sample.ui.field;

import org.jetbrains.annotations.NotNull;
import sample.model.cell.Cell;
import sample.model.figure.BaseFigure;

import javax.xml.ws.BindingType;

public interface IFieldController {

    void updateFigure(@NotNull BaseFigure figure);

    void updateField();

    void cleanUpField();

    Cell[][] getBaseCells();

    void setNewFigure();
}
