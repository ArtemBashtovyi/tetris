package sample.move.commands;

import sample.model.coord.Coordinate;
import sample.save.SaveManager;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class RightCommand extends Command {
    private int fieldWidth;

    public RightCommand(SaveManager saveManager,int fieldWidth) {
        super(saveManager);
        this.fieldWidth = fieldWidth;

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
            System.out.println("y+1");
            if (y + 1 >= fieldWidth) {
                return false;
            }

            if (saveManager.isExist(x,y+1)) {
                return false;
            }
        }
        return true;
    }

    @Override
    Coordinate getOptionalElement(List<Coordinate> coordinates) {
        return coordinates
                .stream()
                .max(Comparator.comparingInt(value -> value.y)).get();
    }

}
