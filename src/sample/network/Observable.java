package sample.network;

import sample.model.coord.Coordinate;

import java.util.List;

public interface Observable {

    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void notifyAllObservables(List<Coordinate> coordinates);
}
