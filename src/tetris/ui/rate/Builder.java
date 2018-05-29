package tetris.ui.rate;

import tetris.ui.rate.dialog.BaseDialog;

public interface Builder {

    void setPointsCount();
    void setAvatar();
    void setTitle();

    BaseDialog create();
}
