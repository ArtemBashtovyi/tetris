package sample.model.cell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import sample.ui.UiConstants;

import java.util.Objects;

import static sample.ui.UiConstants.CELL_HEIGHT;
import static sample.ui.UiConstants.CELL_WIDTH;


public class VolatileCell extends StackPane {

    private int x;
    private int y;
    private DecoratedCell decoratedCell;
    private Rectangle rectangle = new Rectangle(CELL_WIDTH,CELL_HEIGHT);

    // property for refresh ui state of rectangle
    private BooleanProperty updateProperty = new SimpleBooleanProperty();

    public VolatileCell(int x, int y,@NotNull DecoratedCell decoratedCell) {

        this.x = x;
        this.y = y;

        // set positions which depends from
        int positionX = x * UiConstants.CELL_WIDTH;
        int positionY = y * UiConstants.CELL_HEIGHT;

        // reversed because setTranslate method set X and Y by coordinates
        setTranslateX(positionY);
        setTranslateY(positionX);

        setRectangle(decoratedCell.getColor());
        // init update property
        updateProperty.setValue(true);
    }

    public void updateColor(DecoratedCell decoratedCell) {
        this.decoratedCell = decoratedCell;
        rectangle.setFill(decoratedCell.getColor());
    }

    private void setRectangle(Color color) {

        rectangle.setFill(color);
        /*rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(1);*/

        getChildren().add(rectangle);
    }
    // change color of Cell in runtime
    public void update() {
        updateProperty.setValue(false);
        rectangle.visibleProperty().bind(updateProperty);
        updateProperty.setValue(true);
        rectangle.visibleProperty().bind(updateProperty);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolatileCell volatileCell = (VolatileCell) o;
        return x == volatileCell.x &&
                y == volatileCell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
