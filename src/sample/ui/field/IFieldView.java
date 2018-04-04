package sample.ui.field;

import org.jetbrains.annotations.NotNull;
import sample.model.figure.BaseFigure;

public interface IFieldView {

    void updateFigure(@NotNull BaseFigure figure);

    void updateField();

    void setNewFigure();

    void showGameOverDialog();

}
