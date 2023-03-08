package business.pieces;

import business.service.moves.pieces.PawnMove;
import gui.board.ChessGameBoard;

import javax.swing.*;

 
public class Pawn extends ChessGamePiece {

    private boolean notMoved;
    private PawnMove pawnMove;

    
    public Pawn(ChessGameBoard board, int row, int col, int color) {
        super(board, row, col, color, true);
        notMoved = true;
        pawnMove = new PawnMove();
    }
 

    public void calculatePossibleMoves(ChessGameBoard board) {
        possibleMoves = pawnMove.calculatePossibleMoves(board,pieceRow,pieceColumn,colorOfPiece.getColor(),isNotMoved());
    }

    public boolean isNotMoved() {
        return notMoved;
    }

    public void setNotMoved(boolean notMoved) {
        this.notMoved = notMoved;
    }

    
    @Override
    public ImageIcon createImageByPieceType(){
        return new ImageIcon(
                getClass().getResource(resourceOfPiece.resourceByType("Pawn"))
        );
    }
}
