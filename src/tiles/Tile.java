package tiles;

import java.awt.*;

public abstract class Tile {

    public Coordinate boardPlacement;

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    public Color fillColor;
    protected Color borderColor;
    protected Color letterColor;

    public String symbols = "";

    protected Tile(int placeCol, int placeRow, Color fillColor) {
        this.boardPlacement = new Coordinate(placeRow,placeCol);
        this.fillColor = fillColor;
        this.borderColor = fillColor.darker();
        this.letterColor = new Color(255, 50, 50);
    }

    public int getPixelPosCol() {
        return boardPlacement.getCol() * WIDTH+WIDTH;
    }

    public int getPixelPosRow() {
        return boardPlacement.getRow() * HEIGHT+HEIGHT;
    }

    public int getPlaceCol() {
        return boardPlacement.getCol();
    }

    public int getPlaceRow() {
        return boardPlacement.getRow();
    }

    public void changeColor(Color c) {
        fillColor = c;
        borderColor = c.darker();
    }

    public void render(Graphics g) {
        g.setColor(borderColor);
        g.drawRect(getPixelPosCol(), getPixelPosRow(), WIDTH, HEIGHT);
        g.setColor(fillColor);
        g.fillRect(getPixelPosCol() + 1, getPixelPosRow() + 1, WIDTH - 1, HEIGHT - 1);
        g.setColor(letterColor);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString(symbols, getPixelPosCol() + 20, getPixelPosRow() + 35);
    }
}
