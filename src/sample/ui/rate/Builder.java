package sample.ui.rate;

import javafx.scene.image.Image;
import sample.ui.rate.dialog.BaseDialog;

import java.util.Date;

public interface Builder {

    void setPointsCount();
    void setAvatar();
    void setTitle();

    BaseDialog create();
}
