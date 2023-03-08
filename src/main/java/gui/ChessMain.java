package gui;

import javax.swing.*;

 
public class ChessMain {

     
    public static void main(String[] args) {
        JFrame frame = new JFrame("YetAnotherChessGame 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChessPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
