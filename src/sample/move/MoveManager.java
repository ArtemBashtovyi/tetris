package sample.move;

import sample.model.coord.Coordinate;
import sample.move.commands.Command;
import sample.move.commands.DownCommand;
import sample.move.commands.LeftCommand;
import sample.move.commands.RightCommand;
import sample.save.FieldSaver;

import java.util.List;

public class MoveManager {

    private Command rightCommand;
    private Command leftCommand;
    private Command downCommand;


    public MoveManager(int fieldHeight, int fieldWidth, FieldSaver fieldSaver, DownCommand.GameOverHandler gameOverHandler) {
        rightCommand = new RightCommand(fieldSaver,fieldWidth);
        leftCommand = new LeftCommand(fieldSaver);
        downCommand = new DownCommand(fieldSaver,fieldHeight,gameOverHandler);
    }

    public boolean isCanMoveDown(List<Coordinate> coordinates) {
        return downCommand.isCanDoAction(coordinates);
    }
    public boolean isCanMoveLeft(List<Coordinate> coordinates) {
        return leftCommand.isCanDoAction(coordinates);
    }
    public boolean isCanMoveRight(List<Coordinate> coordinates){
        return rightCommand.isCanDoAction(coordinates);
    }
}
