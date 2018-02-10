package sample.model.cell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.model.SizeConstants;

import static sample.model.SizeConstants.CELL_HEIGHT;
import static sample.model.SizeConstants.CELL_WIDTH;


public class Cell extends StackPane {

    private static final Color EMPTY_COLOR =  new Color(0.1294, 0.5216, 0.5333, 0.8);
    private static final Color FULL_COLOR =  new Color(0.9294, 0.1216, 0.5333, 0.8);

    private int number;
    private Color currentColor = EMPTY_COLOR;
    private Rectangle rectangle = new Rectangle(CELL_WIDTH,CELL_HEIGHT);

    private BooleanProperty updateProperty = new SimpleBooleanProperty();

    public Cell(int number,int x, int y) {

        this.number = number;
        // set positions which depends from
        int positionX = x * SizeConstants.CELL_WIDTH;
        int positionY = y * SizeConstants.CELL_HEIGHT;

        // reversed because setTranslate method set X and Y by coordinates
        setTranslateX(positionY);
        setTranslateY(positionX);

        setRectangle(currentColor);
        setDebugTitle();

        updateProperty.setValue(true);
    }

    Cell(int x, int y) {
        this(0,x,y);
    }

    private void setDebugTitle() {
        Label numberLabel = new Label(Integer.toString(this.number));
        getChildren().add(numberLabel);
    }


    private void setRectangle(Color color) {
        rectangle.setFill(color);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(1);

        getChildren().add(rectangle);
    }

    public static Color getEmptyColor() {
        return EMPTY_COLOR;
    }

    public static Color getFullColor() {
        return FULL_COLOR;
    }

    public void setColor(Color color) {
        rectangle.setFill(color);
        currentColor = color;
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


}
