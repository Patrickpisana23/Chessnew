package business.service.moves.impl;

import business.pieces.Pawn;
import business.pieces.Queen;
import business.service.moves.IPieceMoveService;
import business.game.ChessGameEngine;
import business.pieces.ChessGamePiece;
import gui.ChessGraveyard;
import gui.ChessPanel;
import gui.board.ChessGameBoard;
import util.ColorOfPiece;
import util.IsEnemy;
import util.IsOnScreen;

import java.awt.*;

public class PieceMoveServiceImpl implements IPieceMoveService {

    
    @Override
    public boolean move(ChessGameBoard board, ChessGamePiece piece, int row, int col) {
        if (canMove(board,piece, row, col)) {
            String moveLog = this.toString() + " -> ";
            board.clearCell(piece.getRow(), piece.getColumn());
            if (IsEnemy.invoke(board, row, col,piece.getColorOfPiece().getColor())) {
                ChessGraveyard graveyard;
                ChessGameEngine gameEngine =
                        ((ChessPanel) board.getParent()).getGameEngine();
                if (gameEngine.getCurrentPlayer() == 1) {
                    graveyard =
                            ((ChessPanel) board.getParent()).getGraveyard(2);
                } else {
                    graveyard =
                            ((ChessPanel) board.getParent()).getGraveyard(1);
                }
                graveyard.addPiece(
                        board.getCell(row, col).getPieceOnSquare());
            }
            piece.setPieceLocation(row, col);
            moveLog += " (" + row + ", " + col + ")";
            ((ChessPanel) board.getParent()).getGameLog().addToLog(moveLog);
            board.getCell(row, col).setPieceOnSquare(piece);
            if (!piece.isSkipMoveGeneration()) {
                piece.calculatePossibleMoves(board);
            }
            return true;
        } else {
            return false;
        }
    }

     
    @Override
    public boolean canMove(ChessGameBoard board,ChessGamePiece piece, int row, int col) {
        piece.calculatePossibleMoves(board);
        if (piece.getPossibleMoves().indexOf(row + "," + col) > -1) {
            return testMoveForKingSafety(board,piece, row, col);
        }
        return false;
    }

     

    @Override
    public boolean testMoveForKingSafety(ChessGameBoard board, ChessGamePiece piece, int row, int col) {
        piece.calculatePossibleMoves(board);
        ChessGamePiece oldPieceOnOtherSquare =
                board.getCell(row, col).getPieceOnSquare();
        ChessGameEngine engine =
                ((ChessPanel) board.getParent()).getGameEngine();
        int oldRow = piece.getRow();
        int oldColumn = piece.getColumn();
        board.clearCell(piece.getRow(), piece.getColumn()); // move us off
        piece.setPieceLocation(row, col); // move us to the new location
        board.getCell(row, col).setPieceOnSquare(piece);
        boolean retVal = !engine.isKingInCheck(true); // is the current
        // king still in check?
        piece.setPieceLocation(oldRow, oldColumn); // move us back
        board.getCell(oldRow, oldColumn).setPieceOnSquare(piece);
        board.clearCell(row, col); // ^ move the other piece back
        // to where it was
        board.getCell(row, col).setPieceOnSquare(oldPieceOnOtherSquare);
        return retVal;
    }

    
    @Override
    public void showLegalMoves(ChessGameBoard board, ChessGamePiece piece) {
        piece.calculatePossibleMoves(board);
        if (IsOnScreen.invoke(piece.getRow(), piece.getColumn())) {
            for (String locStr : piece.getPossibleMoves()) {
                String[] currCoords = locStr.split(",");
                int row = Integer.parseInt(currCoords[0]);
                int col = Integer.parseInt(currCoords[1]);
                if (canMove(board,piece, row, col)){ // only show legal moves
                    if (IsEnemy.invoke(board, row, col,piece.getColorOfPiece().getColor())) {
                        board.getCell(row, col).setBackground(
                                Color.YELLOW);
                    } else {
                        board.getCell(row, col).setBackground(Color.PINK);
                    }
                }
            }
        }
    }


    
    @Override
    public boolean hasLegalMoves(ChessGameBoard board, ChessGamePiece piece) {
        piece.calculatePossibleMoves(board);
        if (IsOnScreen.invoke(piece.getRow(),piece.getColumn())) {
            for (String locStr : piece.getPossibleMoves()) {
                String[] currCoords = locStr.split(",");
                int row = Integer.parseInt(currCoords[0]);
                int col = Integer.parseInt(currCoords[1]);
                if (canMove(board,piece, row, col)){ // only show legal moves
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
