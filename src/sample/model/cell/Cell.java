package sample.model.cell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.Contract;
import sample.ui.UiConstants;

import java.util.Objects;

import static sample.ui.UiConstants.*;


public class Cell extends StackPane {

    private int state = 0;
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

    @Contract(pure = true)
    public static Color getEmptyColor() {
        return EMPTY_COLOR;
    }

    @Contract(pure = true)
    public static Color getFullColor() {
        return FULL_COLOR;
    }

    public void setColor(Color color) {
        rectangle.setFill(color);
        currentColor = color;
        if (currentColor.equals(EMPTY_COLOR)) {
            state = 0;
        } else state = 1;
    }

    public Color getColor() {
        return currentColor;
    }

    public void update() {
        updateProperty.setValue(false);
        rectangle.visibleProperty().bind(updateProperty);
        updateProperty.setValue(true);
        rectangle.visibleProperty().bind(updateProperty);
    }


    public boolean isFilled() {
        return currentColor.equals(FULL_COLOR);
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
}
