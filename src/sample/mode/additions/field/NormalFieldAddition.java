package sample.mode.additions.field;

import sample.mode.FieldAddition;
import sample.model.cell.Cell;
import sample.save.SaveManager;

public class NormalFieldAddition implements FieldAddition {

    @Override
    public Cell[][] getBaseField() {
        return new Cell[18][14];
    }
}
