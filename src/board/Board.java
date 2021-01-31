package board;

import tiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Board extends JFrame implements MouseListener {

    private final int boardSize;
    private final int totalImpassableTiles;
    private final int totalDestinationTiles;

    private Tile[][] tiles;

    public Board(int dimension, int totalDestinationTiles, int totalImpassableTiles) {
        this.boardSize = dimension;
        this.totalDestinationTiles = totalDestinationTiles;
        this.totalImpassableTiles = totalImpassableTiles;

        setTiles();

        this.setSize(Tile.WIDTH * (boardSize + 2), Tile.HEIGHT * (boardSize + 2) + 15);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addMouseListener(this);
    }

    private void setTiles() {
        this.tiles = new Tile[boardSize][boardSize];
        setStartTile();
        setDestinationTiles();
        setImpassableTiles();
        setUnvisitedTiles();
    }

    private void setStartTile() {
        Random random = new Random();
        int row = random.nextInt(2);
        int col = random.nextInt(2);

        this.tiles[(row == 0) ? 0 : boardSize - 1][(col == 0) ? 0 : boardSize - 1] = new StartTile((col == 0) ? 1 : boardSize, (row == 0) ? 1 : boardSize);
    }

    private void setDestinationTiles() {
        Random random = new Random();
        int remainingTiles = totalDestinationTiles;

        while (remainingTiles > 0) {
            int row = random.nextInt(boardSize);
            int col = random.nextInt(boardSize);

            if (tiles[row][col] == null) {
                tiles[row][col] = new DestinationTile(col+1, row+1);
                remainingTiles--;
            }
        }

    }

    private void setImpassableTiles() {
        Random random = new Random();
        int remainingTiles = totalImpassableTiles;

        while (remainingTiles > 0) {
            int row = random.nextInt(boardSize);
            int col = random.nextInt(boardSize);

            if (tiles[row][col] == null) {
                tiles[row][col] = new ImpassableTile(col+1, row+1);
                remainingTiles--;
            }
        }

    }

    private void setUnvisitedTiles() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] == null) tiles[i][j] = new UnvisitedTile(j + 1, i + 1);
            }
        }
    }

    private void paintBoard(Graphics g) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] != null)
                    tiles[i][j].render(g);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintBoard(g);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
