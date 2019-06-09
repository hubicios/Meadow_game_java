package meadowgame.Gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import static meadowgame.DefineConst.DEFAULT_BOARD_COLOR;

public class GameStart {

    private int result;

    public GameStart() {
        /* // JFrame frame = new JFrame();
        int result = JOptionPane.showConfirmDialog(null,"Choose one","Stat game",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        // JOptionPane.showInternalConfirmDialog(desktop, "Continue printing?");

        System.out.println(result);*/

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] options = new String[2];
        options[0] = new String("New Game");
        options[1] = new String("Load Game");

        String message = "<html><body><div width='200px' align='center' style=\"color:black;text-align: center\">Choose one of the options</div></body></html>";
        JLabel messageLabel = new JLabel(message);

        //UIManager.put("Panel.background", DEFAULT_BOARD_COLOR);
       // UIManager.put("OptionPane.background", DEFAULT_BOARD_COLOR);
        UIManager.put("Button.background", Color.WHITE);
        result = JOptionPane.showOptionDialog(frame.getContentPane(), messageLabel, "Meadow game", 0, JOptionPane.PLAIN_MESSAGE, null, options, null);
        //-1 exit
        //0 new game
        //1 load game
        // System.out.println(result);
    }

    public int getResult() {
        return result;
    }
}
