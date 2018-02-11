package sample.model.cell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.Contract;
import sample.model.SizeConstants;

import java.util.Objects;

import static sample.model.SizeConstants.CELL_HEIGHT;
import static sample.model.SizeConstants.CELL_WIDTH;


public class Cell extends StackPane {

    private static final Color EMPTY_COLOR =  new Color(0.1294, 0.5216, 0.5333, 0.8);
    private static final Color FULL_COLOR =  new Color(0.9294, 0.1216, 0.5333, 0.8);


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
        int positionX = x * SizeConstants.CELL_WIDTH;
        int positionY = y * SizeConstants.CELL_HEIGHT;

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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
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
