package tiles;

import utility.Coordinate;

import java.awt.*;

public abstract class Tile {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public Coordinate boardPlacement;
    public Color fillColor;
    public String symbols = "";
    protected Color borderColor;
    protected Color letterColor;

    /**
     * Constructor for base class Tile
     *
     * @param placeCol  the column on which the tile is positioned
     * @param placeRow  the row on which the tile is positioned
     * @param fillColor the color used for drawing the tile
     */
    protected Tile(int placeCol, int placeRow, Color fillColor) {
        this.boardPlacement = new Coordinate(placeRow, placeCol);
        this.fillColor = fillColor;
        this.borderColor = fillColor.darker();
        this.letterColor = new Color(255, 50, 50);
    }

    /**
     * The horizontal position of the tile compared to the game board
     *
     * @return int - pixel displacement from left to right
     */
    public int getPixelPosCol() {
        return boardPlacement.getCol() * WIDTH + WIDTH;
    }

    /**
     * The horizontal position of the tile compared to the game board
     *
     * @return int - pixel displacement from top to bottom
     */
    public int getPixelPosRow() {
        return boardPlacement.getRow() * HEIGHT + HEIGHT;
    }

    /**
     * The horizontal position of the tile on the board
     *
     * @return the column number on which the tile is placed
     */
    public int getPlaceCol() {
        return boardPlacement.getCol();
    }

    /**
     * The vertical position of the tile on the board
     *
     * @return the column number on which the tile is placed
     */
    public int getPlaceRow() {
        return boardPlacement.getRow();
    }

    /**
     * Changes the fill color and border for this tile instance
     *
     * @param c Color obj
     */
    public void changeColor(Color c) {
        fillColor = c;
        borderColor = c.darker();
    }

    /**
     * Renders the tile on the game board
     *
     * @param g Graphic component
     */
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
