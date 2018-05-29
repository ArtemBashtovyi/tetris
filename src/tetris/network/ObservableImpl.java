package tetris.network;

import org.jetbrains.annotations.NotNull;
import tetris.model.cell.VolatileCell;

import java.util.ArrayList;
import java.util.List;

public class ObservableImpl implements Observable{

    private List<Observer> observers =  new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservables(ArrayList<VolatileCell> filledCells) {
        for (Observer observer : observers) {
            observer.update(filledCells);
        }
    }

    // when data receive notify all subscribers
    @Override
    public void onDataReceive(@NotNull ArrayList<VolatileCell> matrix) {
        notifyAllObservables(matrix);
    }
}
