package tetris.ui.rate.dialog;

import javafx.scene.image.Image;

public class AllGamersDialog implements BaseDialog {

    private String points;
    private Image avatar;
    private String title;

    @Override
    public void showInfo() {
        System.out.println("ALL GAMERS");
    }

    public String getPoints() {
        return points;
    }

    public Image getAvatar() {
        return avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }


}
