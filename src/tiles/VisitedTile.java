package tiles;

import java.awt.*;

public class VisitedTile extends Tile{
    public VisitedTile(int placeCol, int placeRow) {
        super(placeCol, placeRow, tileColor.YELLOW.color);
    }
}
