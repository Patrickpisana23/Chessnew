package business.pieces;

import business.service.moves.pieces.CreateMoveService;
import business.service.moves.pieces.PieceMove;
import gui.board.ChessGameBoard;
import util.ColorOfPiece;

import javax.swing.*;

 
public class King extends ChessGamePiece{


    public King( ChessGameBoard board, int row, int col, int color ){
        super( board, row, col, color, false );
        pieceMove = new PieceMove(CreateMoveService.kingOrQeenMove(row,col,new ColorOfPiece(color)));
        if (!this.skipMoveGeneration) {
            possibleMoves = pieceMove.calculateCardinalMoves(board,1);
        }
    }

    @Override
    public void calculatePossibleMoves(ChessGameBoard board) {
        pieceMove = new PieceMove(CreateMoveService.kingOrQeenMove(pieceRow,pieceColumn,colorOfPiece));
        possibleMoves = pieceMove.calculateCardinalMoves(board,1);
    }

    
    @Override
    public ImageIcon createImageByPieceType(){
        return new ImageIcon(
                getClass().getResource(resourceOfPiece.resourceByType("King"))
        );
    }
}
