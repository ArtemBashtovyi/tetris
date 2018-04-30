package sample.model.cell;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;

import static sample.model.figure.state.StateConstants.*;
import static sample.ui.UiConstants.*;

public class CellFactory {

    private static HashMap<Integer,DecoratedCell> decoratedCells = new HashMap<>();

    public static DecoratedCell createDecoratedCell(int state) {
        DecoratedCell decoratedCell = decoratedCells.get(state);

        if (decoratedCell == null) {
            decoratedCell = new DecoratedCell(getColorById(state));
            decoratedCells.put(state,decoratedCell);

        }
        return decoratedCell;
    }

    @Contract(pure = true)
    private static Color getColorById(int colorId) {
        Color currentColor;
        switch (colorId) {
            case -1 : currentColor = EMPTY_COLOR;break;
            case FIGURE_I : currentColor = FIGURE_I_COLOR;break;
            case FIGURE_T : currentColor = FIGURE_T_COLOR;break;
            case FIGURE_Z : currentColor = FIGURE_Z_COLOR;break;
            case FIGURE_O : currentColor = FIGURE_O_COLOR;break;
            case FIGURE_J : currentColor = FIGURE_J_COLOR;break;
            case FIGURE_L : currentColor = FIGURE_L_COLOR;break;
            case FIGURE_S : currentColor = FIGURE_S_COLOR;break;
            default: currentColor = FULL_COLOR;
        }
        return currentColor;
    }
}
