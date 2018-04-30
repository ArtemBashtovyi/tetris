package sample.move.commands;

import sample.model.coord.Coordinate;
import sample.save.FieldSaver;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class RightCommand extends Command {
    private int fieldWidth;

    public RightCommand(FieldSaver fieldSaver, int fieldWidth) {
        super(fieldSaver);
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

            if (fieldSaver.isExist(x,y+1)) {
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
