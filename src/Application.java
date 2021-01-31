import board.Board;

public class Application {

    public static void main(String[] args) {
        int dimension = 8;
        int totalDestinationTiles = 8;
        int totalImpassableTiles = 5;
        Board board = new Board(dimension,totalDestinationTiles,totalImpassableTiles);
    }
}
