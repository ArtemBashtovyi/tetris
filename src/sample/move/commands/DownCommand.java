package sample.move.commands;

import sample.model.coord.Coordinate;
import sample.save.FieldSaver;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class DownCommand extends Command {

    public interface GameOverHandler {
        void onGameOver();
    }

    private int fieldHeight;
    private GameOverHandler gameOverHandler;

    public DownCommand(FieldSaver fieldSaver, int fieldHeight, GameOverHandler gameOverHandler) {
        super(fieldSaver);
        this.fieldHeight = fieldHeight;
        this.gameOverHandler = gameOverHandler;
    }

    @Override
    Function<Coordinate, Integer> mapperFunc() {
        return coordinate -> coordinate.y;
    }

    @Override
    boolean isOffsetValid(List<Coordinate> filteredCoordinates) {
        for(Coordinate coordinate : filteredCoordinates) {

            int x = coordinate.x;
            int y = coordinate.y;

            // FIXME : Second way to handle game over mode
            /*if (x + 1 == 1) {
                return false;
            }*/
            if (x + 1 >= fieldHeight) {
                // System.out.println("cell.x > baseMatrix.length");
                return false;
            }

            if (fieldSaver.isExist(x+1,y)) {
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
                .max(Comparator.comparingInt(value -> value.x)).get();
    }


}
