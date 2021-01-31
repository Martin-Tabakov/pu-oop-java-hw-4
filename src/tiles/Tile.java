package tiles;

import java.awt.*;
import java.text.AttributedCharacterIterator;

public abstract class Tile {

    protected int placeX;
    protected int placeY;

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    protected Color fillColor;
    protected Color borderColor;
    protected Color letterColor;

    protected String symbols = "";

    protected Tile(int placeX,int placeY ,Color fillColor) {
        this.placeX = placeX;
        this.placeY = placeY;
        this.fillColor = fillColor;
        this.borderColor = fillColor.darker();
        this.letterColor = new Color(255,50,50);
    }

    public int getPositionX() {
        return placeX * WIDTH;
    }

    public int getPositionY() {
        return placeY * HEIGHT+15;
    }

    public void render(Graphics g){
        g.setColor(borderColor);
        g.drawRect(getPositionX(),getPositionY(),WIDTH,HEIGHT);
        g.setColor(fillColor);
        g.fillRect(getPositionX()+1,getPositionY()+1,WIDTH-1,HEIGHT-1);
        g.setColor(letterColor);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString(symbols,getPositionX()+20,getPositionY()+35);
    }
}
