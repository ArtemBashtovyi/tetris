package sample.ui.field;

import org.jetbrains.annotations.NotNull;
import sample.model.cell.VolatileCell;
import sample.model.figure.BaseFigure;

import java.util.ArrayList;

public interface IFieldView {

    void updateFigure(@NotNull BaseFigure figure);

    void updateField();

    void updateEnemyField(ArrayList<VolatileCell> coordinates);

    void setNewFigure();

    void showGameOverDialog();

    VolatileCell[][] getCurrentMatrix();


}
