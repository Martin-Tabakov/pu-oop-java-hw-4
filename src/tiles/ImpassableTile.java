package tiles;

import java.awt.*;

public class ImpassableTile extends Tile {


    public ImpassableTile(int placeCol, int placeRow) {
        super(placeCol, placeRow, tileColor.BLUE.color);
    }
}
