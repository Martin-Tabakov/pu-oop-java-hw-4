package tiles;

public class Coordinate {
    private int row;
    private  int col;
    public Coordinate(int row,int col){
        this.row = row;
        this.col = col;
    }
    public int getRow(){ return row;}
    public int getCol(){ return col;}
    public void setRow(int row){ this.row = row;}
    public void setCol(int col){ this.col = col;}
    public static boolean isEqual(Coordinate a, Coordinate b)
    {
        return a.getCol() ==b.getCol() && a.getRow() ==b.getRow();
    }
}
