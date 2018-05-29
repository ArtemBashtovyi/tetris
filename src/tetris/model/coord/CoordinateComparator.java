package tetris.model.coord;

import java.util.Comparator;

/**
 *  Comparator for computation left top figure square
 */
public class CoordinateComparator implements Comparator<Coordinate> {

    @Override
    public int compare(Coordinate o1, Coordinate o2) {
        if (o1.x < o2.x) {
            return -1;
        } else if (o1.x > o2.x) {
            return 1;
        } else
            return o1.y - o2.y;
    }
}
