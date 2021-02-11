package tiles;

import utility.tileColor;

public class ImpassableTile extends Tile {

    public ImpassableTile(int placeCol, int placeRow) {
        super(placeCol, placeRow, tileColor.BLUE.color);
    }
}
