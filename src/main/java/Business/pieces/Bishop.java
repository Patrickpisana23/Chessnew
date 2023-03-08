package business.pieces;

import business.service.moves.pieces.CreateMoveService;
import business.service.moves.pieces.PieceMove;
import gui.board.ChessGameBoard;
import util.ColorOfPiece;

import javax.swing.*;

 
public class Bishop extends ChessGamePiece {

    public Bishop(ChessGameBoard board, int row, int col, int color) {
        super(board, row, col, color);
        pieceMove = new PieceMove(CreateMoveService.bishopMove(row,col,new ColorOfPiece(color)));
        possibleMoves = pieceMove.calculateCardinalMoves(board,8);
    }

    public void calculatePossibleMoves(ChessGameBoard board){
        pieceMove = new PieceMove(CreateMoveService.bishopMove(pieceRow,pieceColumn,colorOfPiece));
        possibleMoves = pieceMove.calculateCardinalMoves(board,8);
    }
 
    @Override
    public ImageIcon createImageByPieceType(){
        return new ImageIcon(
                getClass().getResource(resourceOfPiece.resourceByType("Bishop"))
        );
    }


}
