package tetris.presenter;

import tetris.model.cell.VolatileCell;
import tetris.model.coord.Coordinate;
import tetris.model.figure.BaseFigure;
import tetris.network.Observer;
import tetris.network.command.Command;

import java.util.List;

public interface IFieldPresenter extends Observer{

    void moveFigureDown();

    void moveFigureLeft();

    void moveFigureRight();

    void rotate();

    void setCommands(Command startCommand,Command stopCommand);

    BaseFigure createFigure(Coordinate coordinate);

    List<Coordinate> getSavedCoordinates();

    void writeData(VolatileCell[][] matrix);
}
