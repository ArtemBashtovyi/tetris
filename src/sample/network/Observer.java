package sample.network;

import sample.model.coord.Coordinate;

import java.util.List;

public interface Observer {
    void update(List<Coordinate> allCoordinates);
}
