package tetris.ui.rate.dialog;

import javafx.scene.image.Image;

public class UkrGamersDialog implements BaseDialog {

    private String points;
    private Image avatar;
    private String title;

    @Override
    public void showInfo() {
        System.out.println("UKR GAMERS");
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
