package ui;

import tiles.*;
import utility.Coordinate;
import utility.tileColor;

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

    /**
     * Creates all tiles and places them on the board
     */
    private void setTiles() {
        this.tiles = new Tile[boardSize][boardSize];
        setStartTile();
        setDestinationTiles();
        setImpassableTiles();
        setUnvisitedTiles();
    }

    /**
     * Creates the start tile and places it on the board
     */
    private void setStartTile() {
        Random random = new Random();
        int row = random.nextInt(2);
        int col = random.nextInt(2);
        int rowPlacement = (row == 0) ? 0 : boardSize - 1;
        int colPlacement = (col == 0) ? 0 : boardSize - 1;

        this.tiles[rowPlacement][colPlacement] = new StartTile(colPlacement, rowPlacement);
        activeTile = tiles[rowPlacement][colPlacement];
    }

    /**
     * Creates all destination tiles and places them on the board
     */
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

    /**
     * Creates all impassable tiles and places them on the board
     */
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

    /**
     * Fills the rest of the board with unvisited tiles
     */
    private void setUnvisitedTiles() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] == null) tiles[i][j] = new UnvisitedTile(j, i);
            }
        }
    }
//endregion

    /**
     * Paints all tiles on the board
     *
     * @param g Graphic component
     */
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
     * Returns the board position of a tile in the form of a Coordinate instance
     *
     * @param e MouseEvent
     * @return Coordinate containing the column and row on which the tile is positioned
     */
    private Coordinate getClickedTilePosition(MouseEvent e) {
        System.out.println("Column: " + e.getX() / Tile.WIDTH + " Row: " + e.getY() / Tile.HEIGHT);
        return new Coordinate(e.getY() / Tile.HEIGHT - 1, e.getX() / Tile.WIDTH - 1);
    }

    /**
     * Checks whether a coordinate is within the boards boundaries
     *
     * @param position Coordinate containing a position
     * @return true if the coordinates are within the board boundaries
     */
    private boolean isWithinBoard(Coordinate position) {
        return (position.getRow() >= 0 && position.getRow() < boardSize) &&
                (position.getCol() >= 0 && position.getCol() < boardSize);
    }

    /**
     * Checks whether a coordinate is adjacent to the activeTile
     *
     * @param position Coordinate
     * @return true if the coordinate is adjacent, otherwise false
     */
    private boolean isClickedOnAdjacentTile(Coordinate position) {
        int horizontalDiff = Math.abs(activeTile.getPlaceCol() - position.getCol());
        int verticalDiff = Math.abs(activeTile.getPlaceRow() - position.getRow());

        return horizontalDiff == 1 && verticalDiff == 0 || horizontalDiff == 0 && verticalDiff == 1;
    }

    /**
     * Gets the tile, which is positioned on a coordinate
     *
     * @param clickedPos the coordinate of the tile
     * @return tile
     */
    private Tile getTileOnCoordinate(Coordinate clickedPos) {
        return tiles[clickedPos.getRow()][clickedPos.getCol()];
    }

    /**
     * Checks whether a click is made on a valid tile for making a new move
     *
     * @param isWithinBoard           value showing if the click is made within the board
     * @param isClickedOnAdjacentTile value showing if the click is made on adjacent tile
     * @param clickedPos              the coordinate on the board where the click is made
     * @return true if the tile on the clickedPos is viable for next move
     */
    private boolean hasClickedOnUnvisitedAdj(boolean isWithinBoard, boolean isClickedOnAdjacentTile, Coordinate clickedPos) {
        if (!isWithinBoard) return false;
        String tileName = getTileOnCoordinate(clickedPos).getClass().getSimpleName();
        return isClickedOnAdjacentTile &&
                (tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile"));
    }

    /**
     * Changes the current tile on the board with a new one
     *
     * @param newTile tile to be placed on the board
     */
    private void changeTile(Tile newTile) {
        tiles[newTile.getPlaceRow()][newTile.getPlaceCol()] = newTile;
    }

    /**
     * Checks whether a move can be made from the activeTile
     *
     * @return true if a move can be made, otherwise - false
     */
    private boolean canMove() {
        return canMoveUp() || canMoveDown() || canMoveLeft() || canMoveRight();
    }

    /**
     * Checks whether the tile located on top of the activeTile is viable for a move
     *
     * @return true if move can be made on the tile
     */
    private boolean canMoveUp() {

        if (activeTile.getPlaceRow() - 1 < 0) return false;
        String tileName = tiles[activeTile.getPlaceRow() - 1][activeTile.getPlaceCol()].getClass().getSimpleName();
        return tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile");
    }

    /**
     * Checks whether the tile located lower from the activeTile is viable for a move
     *
     * @return true if move can be made on the tile
     */
    private boolean canMoveDown() {
        if (activeTile.getPlaceRow() + 1 >= boardSize) return false;
        String tileName = tiles[activeTile.getPlaceRow() + 1][activeTile.getPlaceCol()].getClass().getSimpleName();
        return tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile");
    }

    /**
     * Checks whether the tile located to the right of the activeTile is viable for a move
     *
     * @return true if move can be made on the tile
     */
    private boolean canMoveLeft() {
        if (activeTile.getPlaceCol() - 1 < 0) return false;
        String tileName = tiles[activeTile.getPlaceRow()][activeTile.getPlaceCol() - 1].getClass().getSimpleName();
        return tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile");
    }

    /**
     * Checks whether the tile located to the left of the activeTile is viable for a move
     *
     * @return true if move can be made on the tile
     */
    private boolean canMoveRight() {
        if (activeTile.getPlaceCol() + 1 >= boardSize) return false;
        String tileName = tiles[activeTile.getPlaceRow()][activeTile.getPlaceCol() + 1].getClass().getSimpleName();
        return tileName.equals("UnvisitedTile") || tileName.equals("DestinationTile");
    }

    /**
     * Changes a tile color and adds a question mark to be printed within the tile
     *
     * @param position The position of the tile on the game board
     */
    private void markTileWithQuestionMark(Coordinate position) {
        selectedTile = getTileOnCoordinate(position);
        selectedTile.changeColor(tileColor.YELLOW.color);
        selectedTile.symbols = "?";
    }

    /**
     * Changes the active tile if possible - a click is made on tile marked with question mark and has random chance of 80%.
     *
     * @param clickedTilePosition the coordinate of the made click
     */
    private void changeActiveTile(Coordinate clickedTilePosition) {
        if (Coordinate.isEqual(selectedTile.boardPlacement, clickedTilePosition)) {
            System.out.println("Clicked on the same position, already marked with a question mark");
            Random random = new Random();
            if (random.nextInt(5) < 4) {
                System.out.println("Making tile active and yellow");
                changeTile(new VisitedTile(clickedTilePosition.getCol(), clickedTilePosition.getRow()));
                activeTile = selectedTile;
            } else changeTile(new ImpassableTile(clickedTilePosition.getCol(), clickedTilePosition.getRow()));
            selectedTile = null;
        } else System.out.println("Clicked on position, different from the marked with a question mark");
    }

    /**
     * Checks if the player cant make a move from the activeTile. If no move is possible the game ends.
     */
    private void validatePossibleMove() {
        if (!canMove()) {
            System.out.println("You are stuck!");
            PopUp pop = new PopUp(this, "Game Over", "You lose");
        }
    }

    /**
     * Checks if the activeTile is the located on the winning tile. If true the player wins the game.
     */
    private void validateWin() {
        if (Coordinate.isEqual(activeTile.boardPlacement, winningTile.boardPlacement)) {
            System.out.println("Game won");
            PopUp pop = new PopUp(this, "Game Over");
            if (pop.isSelected) {
                this.dispose();
                new Board(boardSize, totalDestinationTiles, totalImpassableTiles);
            }
        }
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

        boolean isMovePossible = hasClickedOnUnvisitedAdj(
                isWithinBoard(clickedTilePosition),
                isClickedOnAdjacentTile(clickedTilePosition),
                clickedTilePosition
        );

        if (selectedTile == null) {
            if (isMovePossible) markTileWithQuestionMark(clickedTilePosition);
        } else changeActiveTile(clickedTilePosition);

        repaint();

        validatePossibleMove();
        validateWin();
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
