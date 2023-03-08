package gui;

import business.pieces.ChessGamePiece;

import javax.swing.*;
import java.awt.*;

 
public class ChessGraveyard extends JPanel {
    private String title;

  
    public ChessGraveyard(String title) {
        this.title = title;
        this.add(new JLabel(title));
        this.setLayout(new GridLayout(8, 0));
    }

   
    public void addPiece(ChessGamePiece piece) {
        piece.setPieceLocation(-1, -1);
        JLabel pieceLabel = new JLabel();
        pieceLabel.setIcon(piece.getImage());
        this.add(pieceLabel);
    }
 
    public void clearGraveyard() {
        this.removeAll();
        this.add(new JLabel(title));
    }
}
