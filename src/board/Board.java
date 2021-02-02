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

    private Tile activeTile = null;
    private Tile selectedTile = null;
    private DestinationTile winningTile = null;

    private Tile[][] tiles;

    public Board(int dimension, int totalDestinationTiles, int totalImpassableTiles) {
        this.boardSize = dimension;
        this.totalDestinationTiles = totalDestinationTiles;
        this.totalImpassableTiles = totalImpassableTiles;

        setTiles();

        this.setSize(Tile.WIDTH * (boardSize + 2), Tile.HEIGHT * (boardSize + 2));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addMouseListener(this);
    }

    //region Setting Up The board
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
        int rowPlacement = (row == 0) ? 0 : boardSize - 1;
        int colPlacement = (col == 0) ? 0 : boardSize - 1;

        this.tiles[rowPlacement][colPlacement] = new StartTile(colPlacement, rowPlacement);
        activeTile = tiles[rowPlacement][colPlacement];
    }

    private void setDestinationTiles() {
        Random random = new Random();
        int remainingTiles = totalDestinationTiles;

        while (remainingTiles > 0) {
            int row = random.nextInt(boardSize);
            int col = random.nextInt(boardSize);

            if (tiles[row][col] == null) {
                tiles[row][col] = new DestinationTile(col, row);

                if (remainingTiles == 1) winningTile = (DestinationTile) tiles[row][col];

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
                tiles[row][col] = new ImpassableTile(col, row);
                remainingTiles--;
            }
        }

    }

    private void setUnvisitedTiles() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] == null) tiles[i][j] = new UnvisitedTile(j, i);
            }
        }
    }
//endregion

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

    private Coordinate getClickedTilePosition(MouseEvent e) {
        System.out.println("Column: " + e.getX() / Tile.WIDTH + " Row: " + e.getY() / Tile.HEIGHT);
        return new Coordinate(e.getY() / Tile.HEIGHT-1, e.getX() / Tile.WIDTH-1);
    }

    private boolean isWithinBoard(Coordinate position) {
        return (position.getRow() >= 0 && position.getRow() < boardSize) &&
                (position.getCol() >= 0 && position.getCol() < boardSize);
    }

    private boolean isClickedOnAdjacentTile(Coordinate position) {
        int horizontalDiff = Math.abs(activeTile.getPlaceCol() - position.getCol());
        int verticalDiff = Math.abs(activeTile.getPlaceRow() - position.getRow());

        return horizontalDiff == 1 && verticalDiff == 0 || horizontalDiff == 0 && verticalDiff == 1;
    }

    private Tile getTileOnCoordinate(Coordinate clickedPos) {
        return tiles[clickedPos.getRow()][clickedPos.getCol()];
    }

    private boolean hasClickedOnUnvisitedAdj(boolean isWithinBoard, boolean isClickedOnAdjacentTile, Coordinate clickedPos) {
        if (!isWithinBoard) return false;
        String tileName = getTileOnCoordinate(clickedPos).getClass().getSimpleName();
        return isClickedOnAdjacentTile &&
                tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile");
    }

    private void changeTile(Tile newTile) {
        tiles[newTile.getPlaceRow()][newTile.getPlaceCol()] = newTile;
    }

    private boolean canMove() {
        return canMoveUp() || canMoveDown() || canMoveLeft() || canMoveRight();
    }

    private boolean canMoveUp() {

        if(activeTile.getPlaceRow()-1<0) return false;
        String tileName =tiles[activeTile.getPlaceRow()-1][activeTile.getPlaceCol()].getClass().getSimpleName();
        return tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile");
    }

    private boolean canMoveDown() {
        if(activeTile.getPlaceRow()+1>=boardSize) return false;
        String tileName = tiles[activeTile.getPlaceRow()+1][activeTile.getPlaceCol()].getClass().getSimpleName();
        return tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile");
    }

    private boolean canMoveLeft() {
        if(activeTile.getPlaceCol()-1<0) return false;
        String tileName = tiles[activeTile.getPlaceRow()][activeTile.getPlaceCol()-1].getClass().getSimpleName();
        return tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile");
    }

    private boolean canMoveRight() {
        if(activeTile.getPlaceCol()+1>=boardSize) return false;
        String tileName =  tiles[activeTile.getPlaceRow()][activeTile.getPlaceCol()+1].getClass().getSimpleName();
        return tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile");
    }
//region MouseEvents

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        Coordinate clickedTilePosition = getClickedTilePosition(e);
        boolean isWithinBoard = isWithinBoard(clickedTilePosition);
        boolean isClickedOnAdjacentTile = isClickedOnAdjacentTile(clickedTilePosition);
        boolean isMovePossible = hasClickedOnUnvisitedAdj(isWithinBoard, isClickedOnAdjacentTile, clickedTilePosition);


        if (selectedTile == null) {
            //No tile has been marked with question mark

            if (isMovePossible) {
                selectedTile = getTileOnCoordinate(clickedTilePosition);
                selectedTile.changeColor(tileColor.YELLOW.color);
                selectedTile.symbols = "?";
            }
        } else {
            //Already have a selected tile, marked with a question mark
            //Check if it becomes yellow and new active or blue and preserve the same active tile
            if (Coordinate.isEqual(selectedTile.boardPlacement, clickedTilePosition)) {
                //Clicked on the same position, already marked with a question mark
                System.out.println("Clicked on the same position, already marked with a question mark");
                Random random = new Random();
                if (random.nextInt(5) < 4) {
                    System.out.println("Making tile active and yellow");
                    changeTile(new VisitedTile(clickedTilePosition.getCol(), clickedTilePosition.getRow()));
                    activeTile = selectedTile;
                } else {
                    System.out.println("Making tile impassable");
                    changeTile(new ImpassableTile(clickedTilePosition.getCol(), clickedTilePosition.getRow()));
                }
                selectedTile = null;
            } else {
                System.out.println("Clicked on position, different from the marked with a question mark");
            }
        }
        repaint();
        if (!canMove()) {
            System.out.println("You are stuck!");
            return;
        }

        if (Coordinate.isEqual(activeTile.boardPlacement, winningTile.boardPlacement)) {
            System.out.println("Game won");
            return;
        }


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
    //endregion
}
