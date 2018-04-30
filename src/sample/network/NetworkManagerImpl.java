package sample.network;

import sample.model.coord.Coordinate;

import java.util.ArrayList;
import java.util.List;

// TODO : impl receive coordinates in runtime via socket
public class NetworkManagerImpl implements NetworkManager,Observable {

    private List<Observer> observers;


    public NetworkManagerImpl() {
        observers = new ArrayList<>();
    }

    // TODO : impl synchronized

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservables(List<Coordinate> coordinates) {
        for (Observer observer : observers) {
            observer.update(coordinates);
        }
    }

    @Override
    public void updateField() {

    }
}
