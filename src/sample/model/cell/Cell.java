package sample.model.cell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.Contract;
import sample.ui.UiConstants;

import java.util.Objects;

import static sample.model.figure.state.StateConstants.*;
import static sample.ui.UiConstants.*;


public class Cell extends StackPane {

    private int x;
    private int y;

    private Color currentColor = EMPTY_COLOR;
    private Rectangle rectangle = new Rectangle(CELL_WIDTH,CELL_HEIGHT);

    // property for refresh ui state of rectangle
    private BooleanProperty updateProperty = new SimpleBooleanProperty();

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        // set positions which depends from
        int positionX = x * UiConstants.CELL_WIDTH;
        int positionY = y * UiConstants.CELL_HEIGHT;

        // reversed because setTranslate method set X and Y by coordinates
        setTranslateX(positionY);
        setTranslateY(positionX);

        setRectangle(currentColor);

        updateProperty.setValue(true);
    }


    private void setRectangle(Color color) {
        rectangle.setFill(color);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(1);

        getChildren().add(rectangle);
    }

    public void setColor(int colorId) {
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
        rectangle.setFill(currentColor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    public void update() {
        updateProperty.setValue(false);
        rectangle.visibleProperty().bind(updateProperty);
        updateProperty.setValue(true);
        rectangle.visibleProperty().bind(updateProperty);
    }
}
