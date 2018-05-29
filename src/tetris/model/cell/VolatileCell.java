package tetris.model.cell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tetris.ui.ColorFactory;
import tetris.ui.UiConstants;

import java.io.Serializable;
import java.util.Objects;

import static tetris.ui.UiConstants.CELL_HEIGHT;
import static tetris.ui.UiConstants.CELL_WIDTH;


public class VolatileCell extends StackPane implements Serializable {

    private Integer x;
    private Integer y;
    private Integer state;
    transient private Rectangle rectangle = new Rectangle(CELL_WIDTH,CELL_HEIGHT);

    // property for refresh ui state of rectangle
    transient private BooleanProperty updateProperty = new SimpleBooleanProperty();

    public VolatileCell(int x, int y,int state) {
        this.x = x;
        this.y = y;
        this.state = state;

        // set positions which depends from
        int positionX = x * UiConstants.CELL_WIDTH;
        int positionY = y * UiConstants.CELL_HEIGHT;

        // reversed because setTranslate method set X and Y by coordinates
        setTranslateX(positionY);
        setTranslateY(positionX);

        setRectangle(ColorFactory.getColorById(state));
        // init update property
        updateProperty.setValue(true);

    }

    public void updateColor(Integer state) {
        this.state = state;
        rectangle.setFill(ColorFactory.getColorById(state));
    }

    public int getColor() {
        return state;
    }

    private void setRectangle(Color color) {
        rectangle.setFill(color);
        /*rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(1);*/
        getChildren().add(rectangle);
    }

    // change UI-color of Cell in runtime
    public void update() {
        updateProperty.setValue(false);
        rectangle.visibleProperty().bind(updateProperty);
        updateProperty.setValue(true);
        rectangle.visibleProperty().bind(updateProperty);
    }

    public int getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolatileCell cell = (VolatileCell) o;
        return Objects.equals(x, cell.x) &&
                Objects.equals(y, cell.y) &&
                Objects.equals(state, cell.state);
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y, state);
    }
}
