package tetris.network;

import tetris.model.cell.VolatileCell;

import java.util.ArrayList;

public interface Observer {
    void update(ArrayList<VolatileCell> filledCells);
}
