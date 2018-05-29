package tetris.ui;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;

import static tetris.model.figure.state.StateConstants.*;
import static tetris.ui.UiConstants.*;
import static tetris.ui.UiConstants.EMPTY_COLOR;

public class ColorFactory {

    @Contract(pure = true)
    public static Color getColorById(int colorId) {
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
