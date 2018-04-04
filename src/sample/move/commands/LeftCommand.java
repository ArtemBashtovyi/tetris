package sample.move.commands;

import sample.model.coord.Coordinate;
import sample.save.SaveManager;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class LeftCommand extends Command {

    public LeftCommand(SaveManager saveManager) {
        super(saveManager);
    }

    @Override
    Function<Coordinate, Integer> mapperFunc() {
        return coordinate -> coordinate.x;
    }

    @Override
    boolean isOffsetValid(List<Coordinate> filteredCoordinates) {
        for(Coordinate coordinate : filteredCoordinates) {

            int x = coordinate.x;
            int y = coordinate.y;

            if (y - 1 < 0) {
                return false;
            }

            if (saveManager.isExist(x,y-1)) {
                //System.out.println("filled block" + coordinate);
                return false;
            }
        }

        return true;
    }

    @Override
    Coordinate getOptionalElement(List<Coordinate> coordinates) {
        return coordinates
                .stream()
                .min(Comparator.comparingInt(value -> value.y)).get();
    }

}
