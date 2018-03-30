package sample.mode.normal;

import sample.mode.BaseFactory;
import sample.mode.FieldAddition;
import sample.mode.SpeedAddition;
import sample.mode.additions.field.NormalFieldAddition;
import sample.mode.additions.speed.NormalSpeedAddition;

public class NormalModeFactory implements BaseFactory {

    @Override
    public FieldAddition createFieldAddition() {
        return new NormalFieldAddition();
    }

    @Override
    public SpeedAddition createSpeedAddition() {
        return new NormalSpeedAddition();
    }
}
