package sample.move.commands;

import sample.model.coord.Coordinate;
import sample.save.SaveManager;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public abstract class Command {

    protected SaveManager saveManager;

    public Command(SaveManager saveManager) {
        this.saveManager = saveManager;
    }

    public boolean isCanDoAction(List<Coordinate> coordinates) {
        boolean result;
        List<Coordinate> filteredCoordinates = coordinates.stream()
                .collect(Collectors.groupingBy(mapperFunc()))
                .values()
                .stream()
                .map(groupedCoordinates -> {
                    if (groupedCoordinates.size() > 1)  {
                        return Collections
                                .singletonList(getOptionalElement(groupedCoordinates));
                    }
                    return groupedCoordinates;
                })
                // join all collections to one collection
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        result = isOffsetValid(filteredCoordinates);
        return result;
    }


    abstract Function<Coordinate,Integer> mapperFunc();
    // offset logic
    abstract boolean isOffsetValid(List<Coordinate> filteredCoordinates);

    abstract Coordinate getOptionalElement(List<Coordinate> coordinates);

}
