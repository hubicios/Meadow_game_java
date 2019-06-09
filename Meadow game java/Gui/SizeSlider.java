/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meadowgame.Gui;

import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import static meadowgame.DefineConst.*;

/**
 *
 * @author Hubert
 */
public class SizeSlider {

    private int input[] = new int[2];//width and size form sliders

    public SizeSlider() {
        JFrame parent = new JFrame();

        JOptionPane optionPane = new JOptionPane();
        //input[] = new int[2];
        JSlider slider[] = new JSlider[2];
        ChangeListener changeListener[] = new ChangeListener[2];
        for (int i = 0; i < 2; i++) {
            slider[i] = new JSlider(MIN_BOARD_SIZE, MAX_BOARD_SIZE, 10 * (3 - i));
            slider[i].setPreferredSize(new Dimension(800, 50));
            slider[i].setMajorTickSpacing(1);
            slider[i].setPaintTicks(true);
            slider[i].setPaintLabels(true);
            input[i] = slider[i].getValue();
        }
        changeListener[0] = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider theSlider = (JSlider) changeEvent.getSource();
                if (!theSlider.getValueIsAdjusting()) {
                    input[0] = theSlider.getValue();
                }
            }
        };
        slider[0].addChangeListener(changeListener[0]);
        
        changeListener[1] = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider theSlider = (JSlider) changeEvent.getSource();
                if (!theSlider.getValueIsAdjusting()) {
                    input[1] = theSlider.getValue();
                }
            }
        };
        slider[1].addChangeListener(changeListener[1]);

        optionPane.setMessage(new Object[]{"Board width: ", slider[0], "Board height: ", slider[1]});
        optionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(parent, "New game, choose board size");
        dialog.setVisible(true);

       // System.out.println("Board size: " + input[0] + "," + input[1]);
    }

    public int getWidth() //width that were chosen in sldier
    {
        return input[0];
    }

    public int getHeight() //height that were chosen in sldier
    {
        return input[1];
    }
}
