package sample.ui.rate;

import javafx.scene.image.Image;
import sample.ui.rate.dialog.BaseDialog;
import sample.ui.rate.dialog.UkrGamersDialog;

public class UkrGamersBuilder implements Builder {

    private UkrGamersDialog dialog;

    public UkrGamersBuilder() {
        this.dialog = new UkrGamersDialog();
    }

    @Override
    public void setPointsCount() {
        dialog.setPoints("100");
    }

    @Override
    public void setAvatar() {
        dialog.setAvatar(new Image("src/avatar_ukr_gamers.png"));
    }

    @Override
    public void setTitle() {
        dialog.setTitle("UKR GAMERS");
    }

    @Override
    public BaseDialog create() {
        return dialog;
    }
}
