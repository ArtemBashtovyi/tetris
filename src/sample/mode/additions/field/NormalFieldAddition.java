package sample.mode.additions.field;

import sample.mode.FieldAddition;
import sample.model.cell.VolatileCell;

public class NormalFieldAddition implements FieldAddition {

    @Override
    public VolatileCell[][] getBaseField() {
        return new VolatileCell[18][14];
    }
}
