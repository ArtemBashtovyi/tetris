package sample.model.cell;

import com.sun.istack.internal.NotNull;
import sample.model.cell.Cell;

public class CellMapper {

    public static Cell[][] getCells(@NotNull int[][] base) {

        Cell[][] cells = new Cell[base.length][base[0].length];
        for (int i = 0; i < base.length;i ++) {
            for (int j = 0;j < base[i].length; j++) {

                // Reversed j and i to Cell object,because Cell has x and y by coordinates
                Cell cell = new Cell(j,i);

                if (base[i][j] == 0) {
                    cell.setColor(Cell.getEmptyColor());
                } else cell.setColor(Cell.getFullColor());

                cells[i][j] = cell;
            }
        }
        return cells;
    }



}
