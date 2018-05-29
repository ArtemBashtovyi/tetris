package tetris.save;

import org.jetbrains.annotations.NotNull;
import tetris.model.coord.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FieldSaver {

    private static FieldSaver instance = null;

    // FIXME :IF ADDITIONALLY LOGIC WON'T EXIST,THAN FIX view to int type in constructor
    private int fieldYSize;
    private List<Coordinate> coordinates;

    public static synchronized FieldSaver getInstance(int fieldYSize) {
        if (instance == null) {
            instance = new FieldSaver(fieldYSize);
        }
        return instance;
    }

    private FieldSaver(int fieldYSize) {
        coordinates = new ArrayList<>();
        this.fieldYSize = fieldYSize;
    }

    public void addCell(Coordinate coordinate) {
        coordinates.add(coordinate);
    }

    public void removeCellsLine(int lineNumber) {
        //new Thread(() -> {
            List<Coordinate> line = coordinates.stream()
                    .filter(coordinate -> coordinate.x == lineNumber)
                    .collect(Collectors.toList());
            coordinates.removeAll(line);
       // }).start();

    }

    public boolean isLineFilled(int lineNumber) {
        int count = 0;
        for (Coordinate coordinate : coordinates) {
            if (coordinate.x == lineNumber) {
                count++;
            }
        }
        return count == fieldYSize;

    }

    public boolean isExist(int x,int y) {
        for (Coordinate coordinate : coordinates) {
            if (coordinate.x == x && coordinate.y == y) return true;
        }
        return false;
    }

    public boolean isLineEmpty(int lineNumber) {
        for (Coordinate coordinate : coordinates) {
            if (coordinate.x == lineNumber) return false;
        }
        return true;
    }

    @NotNull
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@NotNull List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
