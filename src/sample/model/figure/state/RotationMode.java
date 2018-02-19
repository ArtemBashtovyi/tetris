package sample.model.figure.state;


public enum RotationMode {
    NORMAL,
    ROTATED_90,
    ROTATED_180,
    ROTATED_270;

    public static RotationMode getNext(RotationMode previousMode) {
        switch (previousMode) {
            case NORMAL:      return ROTATED_90;
            case ROTATED_90:  return ROTATED_180;
            case ROTATED_180: return ROTATED_270;
            case ROTATED_270: return NORMAL;
            default: return NORMAL;
        }
    }
}
