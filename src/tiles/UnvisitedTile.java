package tiles;

import utility.tileColor;

public class UnvisitedTile extends Tile {
    public UnvisitedTile(int placeCol, int placeRow) {
        super(placeCol, placeRow, tileColor.RED.color);
    }

}
