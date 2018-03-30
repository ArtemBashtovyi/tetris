package sample.ui.field;

import org.jetbrains.annotations.NotNull;
import sample.model.cell.Cell;
import sample.model.coord.Coordinate;
import sample.model.figure.BaseFigure;

import javax.xml.ws.BindingType;
import java.util.List;

public interface IFieldView {

    void updateFigure(@NotNull BaseFigure figure);

    void updateField();

    void setNewFigure();

    void showGameOverDialog();
}
