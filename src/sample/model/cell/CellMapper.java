package sample.model.cell;

import com.sun.istack.internal.NotNull;

public class CellMapper {

    /**
     * @param base - base matrix which need to perform
     * @param x - x start coordinate
     * @param y - y start coordinate
     * @return - graphic Cells
     */

    public static Cell[][] getCells(@NotNull int[][] base,int x,int y) {
        int tempX = x;
        Cell[][] cells = new Cell[base.length][base[0].length];
        for (int i = 0; i < base.length;i ++) {
            int tempY = y;
            for (int j = 0;j < base[i].length; j++) {

                // Reversed j and i to Cell object,because Cell has x and y by coordinates
                Cell cell = new Cell(tempX,tempY);

                if (base[i][j] == 0) {
                    cell.setColor(Cell.getEmptyColor());
                } else cell.setColor(Cell.getFullColor());

                cells[i][j] = cell;
                tempY++;
            }
            tempX++;
        }
        return cells;
    }



}
