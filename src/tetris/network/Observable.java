package tetris.network;

import tetris.model.cell.VolatileCell;
import tetris.network.socket.callbacks.IOCallbacks;

import java.util.ArrayList;

public interface Observable extends IOCallbacks {

    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void notifyAllObservables(ArrayList<VolatileCell> filledCells);
}
