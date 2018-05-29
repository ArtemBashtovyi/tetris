package tetris.ui.rate;

import tetris.ui.rate.dialog.BaseDialog;

public class RateManager {

    BaseDialog createDialog(AllGamersBuilder builder) {
        builder.setAvatar();
        builder.setPointsCount();
        builder.setTitle();
        return builder.create();
    }

}
