package tetris.move;

import tetris.model.coord.Coordinate;
import tetris.move.commands.Command;
import tetris.move.commands.DownCommand;
import tetris.move.commands.LeftCommand;
import tetris.move.commands.RightCommand;
import tetris.save.FieldSaver;

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
