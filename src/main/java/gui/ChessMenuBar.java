package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 
public class ChessMenuBar extends JMenuBar {

     
    public ChessMenuBar() {
        String[] menuCategories = {"File", "Options", "Help"};
        String[] menuItemLists =
                {"New game/restart,Exit", "Toggle graveyard,Toggle game log",
                        "About"};
        for (int i = 0; i < menuCategories.length; i++) {
            JMenu currMenu = new JMenu(menuCategories[i]);
            String[] currMenuItemList = menuItemLists[i].split(",");
            for (int j = 0; j < currMenuItemList.length; j++) {
                JMenuItem currItem = new JMenuItem(currMenuItemList[j]);
                currItem.addActionListener(new MenuListener());
                currMenu.add(currItem);
            }
            this.add(currMenu);
        }
    }

   
    private class MenuListener implements ActionListener {

       
        @Override
        public void actionPerformed(ActionEvent event) {
            String buttonName = ((JMenuItem) event.getSource()).getText();
            if (buttonName.equals("About")) {
                aboutHandler();
            } else if (buttonName.equals("New game/restart")) {
                restartHandler();
            } else if (buttonName.equals("Toggle game log")) {
                toggleGameLogHandler();
            } else if (buttonName.equals("Exit")) {
                exitHandler();
            } else {
                toggleGraveyardHandler();
            }
        }
    }

   
    private void aboutHandler() {
        JOptionPane.showMessageDialog(
                this.getParent(),
                "YetAnotherChessGame v1.0 by:\nBen Katz\nMyles David\n"
                        + "Danielle Bushrow\n\nFinal Project for CS2114 @ VT");
    }

  
    private void restartHandler() {
        ((ChessPanel) this.getParent()).getGameEngine().reset();
    }

    
    private void exitHandler() {
        JOptionPane.showMessageDialog(this.getParent(), "Thanks for leaving"
                + ", quitter! >:(");
        Component possibleFrame = this;
        while (possibleFrame != null && !(possibleFrame instanceof JFrame)) {
            possibleFrame = possibleFrame.getParent();
        }
        JFrame frame = (JFrame) possibleFrame;
        frame.setVisible(false);
        frame.dispose();
    }

    
    private void toggleGraveyardHandler() {
        ((ChessPanel) this.getParent()).getGraveyard(1).setVisible(
                !((ChessPanel) this.getParent()).getGraveyard(1).isVisible());
        ((ChessPanel) this.getParent()).getGraveyard(2).setVisible(
                !((ChessPanel) this.getParent()).getGraveyard(2).isVisible());
    }

  
    private void toggleGameLogHandler() {
        ((ChessPanel) this.getParent()).getGameLog().setVisible(
                !((ChessPanel) this.getParent()).getGameLog().isVisible());
        ((ChessPanel) this.getParent()).revalidate();
    }
}
