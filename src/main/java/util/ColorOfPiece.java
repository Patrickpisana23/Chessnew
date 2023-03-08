package util;

public  class ColorOfPiece {

  
    public static final int BLACK = 0;
   
    public static final int WHITE = 1;
   
    public static final int UNASSIGNED = -1;

    private int color;

    public ColorOfPiece(int pieceColor) {
        this.color = pieceColor;
    }

    public int getColor() {
        return color;
    }
}
