package sample.save;

import org.jetbrains.annotations.NotNull;
import sample.model.coord.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SaveManager {

    private static SaveManager instance = null;

    // FIXME :IF ADDITIONALLY LOGIC WON'T EXIST,THAN FIX view to int type in constructor
    private int fieldYSize;
    private List<Coordinate> coordinates;

    public static synchronized SaveManager getInstance(int fieldYSize) {
        if (instance == null) {
            instance = new SaveManager(fieldYSize);
        }
        return instance;
    }

    private SaveManager(int fieldYSize) {
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

    public boolean isExist(Coordinate cell) {

        for (Coordinate coordinate : coordinates) {
            if (coordinate.equals(cell)) return true;
        }

        return false;
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
    public Coordinate getCell(int index) {
        return coordinates.get(index);
    }

    @NotNull
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@NotNull List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
