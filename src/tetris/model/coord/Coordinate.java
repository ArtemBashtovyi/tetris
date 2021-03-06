package tetris.model.coord;

import java.io.Serializable;
import java.util.Objects;

public class Coordinate implements Serializable {

    public int x;
    public int y;
    private int state;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "Coord[" +
                "x=" + x +
                ", y=" + y +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
