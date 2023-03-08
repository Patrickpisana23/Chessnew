package business.game;

import business.service.king.IKingService;
import business.service.king.KingService;
import business.service.moves.IPieceMoveService;
import business.service.moves.impl.PieceMoveServiceImpl;
import util.ColorOfPiece;
import business.pieces.ChessGamePiece;
import business.pieces.King;
import gui.ChessPanel;
import gui.board.BoardSquare;
import gui.board.ChessGameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
 
public class ChessGameEngine {

    private ChessGamePiece currentPiece;
    private boolean firstClick;
    private int currentPlayer;
    private ChessGameBoard board;
    private IPieceMoveService pieceMoveService;
    private IKingService kingService;
    private King king1;
    private King king2;
 
    public ChessGameEngine(ChessGameBoard board) {
        firstClick = true;
        currentPlayer = 1;
        this.board = board;
        this.king1 = (King) board.getCell(7, 3).getPieceOnSquare();
        this.king2 = (King) board.getCell(0, 3).getPieceOnSquare();
        ((ChessPanel) board.getParent()).getGameLog().clearLog();
        ((ChessPanel) board.getParent()).getGameLog().addToLog(
                "A new chess "
                        + "game has been started. Player 1 (white) will play "
                        + "against Player 2 (black). BEGIN!");

        pieceMoveService = new PieceMoveServiceImpl();
        kingService = new KingService(board,pieceMoveService);
    }

  
    public boolean playerHasLegalMoves(int playerNum) {
        ArrayList<ChessGamePiece> pieces;
        if (playerNum == 1) {
            pieces = board.getAllWhitePieces();
        } else if (playerNum == 2) {
            pieces = board.getAllBlackPieces();
        } else {
            return false;
        }
        for (ChessGamePiece currPiece : pieces) {
            if (pieceMoveService.hasLegalMoves(board,currPiece)) {
                return true;
            }
        }
        return false;
    }
 
    public void reset() {
        firstClick = true;
        currentPlayer = 1;
        ((ChessPanel) board.getParent()).getGraveyard(1).clearGraveyard();
        ((ChessPanel) board.getParent()).getGraveyard(2).clearGraveyard();
        ((ChessPanel) board.getParent()).getGameBoard().initializeBoard();
        ((ChessPanel) board.getParent()).revalidate();
        this.king1 = (King) board.getCell(7, 3).getPieceOnSquare();
        this.king2 = (King) board.getCell(0, 3).getPieceOnSquare();
        ((ChessPanel) board.getParent()).getGameLog().clearLog();
        ((ChessPanel) board.getParent()).getGameLog().addToLog(
                "A new chess "
                        + "game has been started. Player 1 (white) will play "
                        + "against Player 2 (black). BEGIN!");
    }

 
    public int getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * Switches the turn to be the next player's turn.
     */
    private void nextTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        ((ChessPanel) board.getParent()).getGameLog().addToLog(
                "It is now Player " + currentPlayer + "'s turn.");
    }

  
    private boolean selectedPieceIsValid() {
        if (currentPiece == null) // user tried to select an empty square
        {
            return false;
        }
        if (currentPlayer == 2) // black player
        {
            if (currentPiece.getColorOfPiece().getColor() == ColorOfPiece.BLACK) {
                return true;
            }
            return false;
        } else
        // white player
        {
            if (currentPiece.getColorOfPiece().getColor() == ColorOfPiece.WHITE) {
                return true;
            }
            return false;
        }
    }
 
    public boolean isKingInCheck(boolean checkCurrent) {
        if (checkCurrent) {
            if (currentPlayer == 1) {
                return kingService.isChecked(king1);
            }
            return kingService.isChecked(king2);
        } else {
            if (currentPlayer == 2) {
                return kingService.isChecked(king1);
            }
            return kingService.isChecked(king2);
        }
    }

 
    private void askUserToPlayAgain(String endGameStr) {
        int resp =
                JOptionPane.showConfirmDialog(board.getParent(), endGameStr
                        + " Do you want to play again?");
        if (resp == JOptionPane.YES_OPTION) {
            reset();
        } else {
            board.resetBoard(false);
            // System.exit(0);
        }
    }

 
    private void checkGameConditions() {
        int origPlayer = currentPlayer;
        for (int i = 0; i < 2; i++) {
            int gameLostRetVal = determineGameLost();
            if (gameLostRetVal < 0) {
                askUserToPlayAgain("Game over - STALEMATE. You should both go"
                        + " cry in a corner!");
                return;
            } else if (gameLostRetVal > 0) {
                askUserToPlayAgain("Game over - CHECKMATE. " + "Player "
                        + gameLostRetVal + " loses and should go"
                        + " cry in a corner!");
                return;
            } else if (isKingInCheck(true)) {
                JOptionPane.showMessageDialog(
                        board.getParent(),
                        "Be careful player " + currentPlayer + ", " +
                                "your king is in check! Your next move must get " +
                                "him out of check or you're screwed.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            // check the next player's conditions as well.
        }
        currentPlayer = origPlayer;
        nextTurn();
    }

 
    public int determineGameLost() {
        if (kingService.isChecked(king1) && !playerHasLegalMoves(1)) // player 1
        // loss
        {
            return 1;
        }
        if (kingService.isChecked(king2) && !playerHasLegalMoves(2)) // player 2
        // loss
        {
            return 2;
        }
        if ((!kingService.isChecked(king1) && !playerHasLegalMoves(1))
                || (!kingService.isChecked(king2) && !playerHasLegalMoves(2))
                || (board.getAllWhitePieces().size() == 1 &&
                board.getAllBlackPieces().size() == 1)) // stalemate
        {
            return -1;
        }
        return 0; // game is still in play
    }
  
 
    public void determineActionFromSquareClick(MouseEvent e) {
        BoardSquare squareClicked = (BoardSquare) e.getSource();
        ChessGamePiece pieceOnSquare = squareClicked.getPieceOnSquare();
        board.clearColorsOnBoard();
        if (firstClick) {
            currentPiece = squareClicked.getPieceOnSquare();
            if (selectedPieceIsValid()) {
                pieceMoveService.showLegalMoves(board,currentPiece);
                squareClicked.setBackground(Color.GREEN);
                firstClick = false;
            } else {
                if (currentPiece != null) {
                    JOptionPane.showMessageDialog(
                            squareClicked,
                            "You tried to pick up the other player's piece! "
                                    + "Get some glasses and pick a valid square.",
                            "Illegal move",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            squareClicked,
                            "You tried to pick up an empty square! "
                                    + "Get some glasses and pick a valid square.",
                            "Illegal move",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            if (pieceOnSquare == null ||
                    !pieceOnSquare.equals(currentPiece)) // moving
            {
                boolean moveSuccessful =
                        pieceMoveService.move(
                                board,currentPiece,squareClicked.getRow(), squareClicked.getColumn());
                if (moveSuccessful) {
                    checkGameConditions();
                } else {
                    int row = squareClicked.getRow();
                    int col = squareClicked.getColumn();
                    JOptionPane.showMessageDialog(
                            squareClicked,
                            "The move to row " + (row + 1) + " and column "
                                    + (col + 1)
                                    + " is either not valid or not legal "
                                    + "for this piece. Choose another move location, "
                                    + "and try using your brain this time!",
                            "Invalid move",
                            JOptionPane.ERROR_MESSAGE);
                }
                firstClick = true;
            } else
            // user is just unselecting the current piece
            {
                firstClick = true;
            }
        }
    }
}
