package sample.mode.additions.field;

import sample.mode.FieldAddition;
import sample.model.cell.Cell;
import sample.save.SaveManager;

public class HardFieldAddition implements FieldAddition {

    @Override
    public Cell[][] getBaseField() {
        return new Cell[20][11];
    }
}
