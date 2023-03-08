package gui;

import javax.swing.*;
import java.util.Date;
 
 
public class ChessGameLog extends JScrollPane {

    private JTextArea textArea;

     
    public ChessGameLog() {
        super(
                new JTextArea("", 5, 30),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        textArea = ((JTextArea) this.getViewport().getView());
    }

   
    public void addToLog(String s) {
        if (textArea.getText().length() > 0) {
            textArea.setText(textArea.getText() + "\n" + new Date() + " - "
                    + s);
        } else {
            textArea.setText(new Date() + " - " + s);
        }
    }

    
    public void clearLog() {
        textArea.setText("");
    }

 
    public String getLastLog() {
        int indexOfLastNewLine = textArea.getText().lastIndexOf("\n");
        if (indexOfLastNewLine < 0) {
            return textArea.getText();
        }
        return textArea.getText().substring(indexOfLastNewLine + 1);
    }
}
