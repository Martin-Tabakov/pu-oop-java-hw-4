package tiles;

import java.awt.*;

public class UnvisitedTile extends Tile{
    public UnvisitedTile(int placeCol, int placeRow) {
        super(placeCol, placeRow, tileColor.RED.color);
    }

}
