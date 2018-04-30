package sample.ui.rate;

import javafx.scene.image.Image;
import sample.ui.rate.dialog.AllGamersDialog;
import sample.ui.rate.dialog.BaseDialog;

import java.util.Date;

public class AllGamersBuilder implements Builder {

    private AllGamersDialog dialog;

    public AllGamersBuilder() {
        dialog = new AllGamersDialog();
    }

    @Override
    public void setPointsCount() {
        dialog.setPoints("1000");
    }

    @Override
    public void setAvatar() {
        dialog.setAvatar(new Image("src/all_gamers.png"));
    }

    @Override
    public void setTitle() {
        dialog.setTitle("All GAMERS DIALOG");
    }


    @Override
    public BaseDialog create() {
        return dialog;
    }
}
