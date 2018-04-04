package sample.model.figure.state;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    private static final List<RotationMode> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static RotationMode getRandomRotationMode()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
