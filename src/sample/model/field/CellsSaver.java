package sample.model.field;

import org.jetbrains.annotations.NotNull;
import sample.model.coord.Coordinate;
import sample.ui.field.IFieldController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CellsSaver {

    // FIXME :IF ADDITIONALLY LOGIC WON'T EXIST,THAN FIX view to int type in constructor
    private int fieldYSize;
    private List<Coordinate> coordinates;

    public CellsSaver(@NotNull IFieldController view) {
        coordinates = new ArrayList<>();
        fieldYSize = view.getBaseCells()[0].length;
    }

    public void addCell(Coordinate coordinate) {
        coordinates.add(coordinate);
       // System.out.println("CellsSaver : addCell()  x=" + coordinate.x + " y=" + coordinate.y);
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
