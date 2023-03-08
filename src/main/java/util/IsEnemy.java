package util;

import business.pieces.ChessGamePiece;
import gui.board.ChessGameBoard;

public class IsEnemy {

   
    public static boolean invoke(ChessGameBoard board, int row, int col, int pieceColor) {
        if (row > 7 || col > 7 || row < 0 || col < 0) {
            return false;
        }
        ChessGamePiece enemyPiece =
                board.getCell(row, col).getPieceOnSquare() == null
                        ? null
                        : board.getCell(row, col).getPieceOnSquare();
        if (enemyPiece == null
                || pieceColor == ColorOfPiece.UNASSIGNED
                || enemyPiece.getColorOfPiece().getColor() == ColorOfPiece.UNASSIGNED) {
            return false;
        }
        if (pieceColor == ColorOfPiece.WHITE) {
            if (enemyPiece.getColorOfPiece().getColor() == ColorOfPiece.BLACK) {
                return true;
            } else {
                return false;
            }
        } else {
            if (enemyPiece.getColorOfPiece().getColor() == ColorOfPiece.WHITE) {
                return true;
            } else {
                return false;
            }
        }
    }
}
