package business.pieces;

import business.service.moves.pieces.PieceMove;
import util.ColorOfPiece;
import gui.board.ChessGameBoard;
import util.ResourceOfPiece;

import javax.swing.*;
import java.util.List;
 
public abstract class ChessGamePiece {
    protected boolean skipMoveGeneration;
    protected ColorOfPiece colorOfPiece;
    protected ImageIcon pieceImage;
    protected PieceMove pieceMove;
    protected ResourceOfPiece resourceOfPiece;
   
    public List<String> possibleMoves;
     
    protected int pieceRow;
    
    protected int pieceColumn;

  
    public ChessGamePiece(ChessGameBoard board, int row, int col, int pieceColor) {
        skipMoveGeneration = false;
        this.colorOfPiece = new ColorOfPiece(pieceColor);
        this.resourceOfPiece = new ResourceOfPiece(pieceColor);
        pieceImage = createImageByPieceType();
        pieceRow = row;
        pieceColumn = col;
        if (board.getCell(row, col) != null) {
            board.getCell(row, col).setPieceOnSquare(this);
        }
    }

   
    public ChessGamePiece(ChessGameBoard board, int row, int col, int pieceColor, boolean skipMoveGeneration) {
        this.skipMoveGeneration = skipMoveGeneration;
        this.colorOfPiece = new ColorOfPiece(pieceColor);
        this.resourceOfPiece = new ResourceOfPiece(pieceColor);
        pieceImage = this.createImageByPieceType();
        pieceRow = row;
        pieceColumn = col;
        if (board.getCell(row, col) != null) {
            board.getCell(row, col).setPieceOnSquare(this);
        }
    }

    public abstract void calculatePossibleMoves(ChessGameBoard board);

   
    public abstract ImageIcon createImageByPieceType();

   
    public ImageIcon getImage() {
        return pieceImage;
    }

    public ColorOfPiece getColorOfPiece(){
        return colorOfPiece;
    }

    
    public void setPieceLocation(int row, int col) {
        pieceRow = row;
        pieceColumn = col;
    }

   
    public int getRow() {
        return pieceRow;
    }
 
    public int getColumn() {
        return pieceColumn;
    }


    public boolean isSkipMoveGeneration() {
        return skipMoveGeneration;
    }

    public void setSkipMoveGeneration(boolean skipMoveGeneration) {
        this.skipMoveGeneration = skipMoveGeneration;
    }

    public List<String> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<String> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    
    @Override
    public String toString() {
        return this.getClass().toString().substring(6) + " @ (" + pieceRow
                + ", " + pieceColumn + ")";
    }
}
