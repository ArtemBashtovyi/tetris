package sample.ui.rate;

import sample.ui.rate.dialog.AllGamersDialog;
import sample.ui.rate.dialog.BaseDialog;

public class RateManager {

    BaseDialog createDialog(AllGamersBuilder builder) {
        builder.setAvatar();
        builder.setPointsCount();
        builder.setTitle();
        return builder.create();
    }

}
