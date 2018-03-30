package sample.mode.hard;

import sample.mode.BaseFactory;
import sample.mode.FieldAddition;
import sample.mode.SpeedAddition;
import sample.mode.additions.field.HardFieldAddition;
import sample.mode.additions.speed.HighSpeedAddition;


public class HardModeFactory implements BaseFactory {

    @Override
    public FieldAddition createFieldAddition() {
        return new HardFieldAddition();
    }

    @Override
    public SpeedAddition createSpeedAddition() {
        return new HighSpeedAddition();
    }
}
