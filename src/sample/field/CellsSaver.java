package sample.field;

import org.jetbrains.annotations.NotNull;
import sample.model.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class CellsSaver{
    private List<Cell> cells;

    public CellsSaver() {
        cells = new ArrayList<>();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
        System.out.println("CellsSaver : addCell()  x=" + cell.getX() + " y=" + cell.getY());
    }

    public void removeCellsLine(int lineNumber) {

        for (Cell cell : cells) {
            if (cell.getX() == lineNumber) cells.remove(cell);
        }
    }

    public int indexOf(Cell cell) {
        return cells.indexOf(cell);
    }

    @NotNull
    public Cell getCell(int index) {
        return cells.get(index);
    }

    @NotNull
    public List<Cell> getCells() {
        return cells;
    }

}
