package tetris.model.cell;

import java.io.Serializable;

public class DecoratedCell implements Serializable {

    private int colorId;

    public DecoratedCell(int color) {
        this.colorId = color;
    }

    public int getColor() {
        return colorId;
    }

    public void setColor(int color) {
        this.colorId = color;
    }

}
