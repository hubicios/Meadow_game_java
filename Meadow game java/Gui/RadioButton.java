package meadowgame.Gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import static meadowgame.DefineConst.ALL_SIGNS;

public class RadioButton {

    /* public RadioButton() {
        super("Choose one organism");

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2) - (this.getHeight() / 2);
        this.setLocation(xPos, yPos);

        int n = 10, q = 0;
        JRadioButton options[] = new JRadioButton[n];
        options[q] = new JRadioButton("Antelope");
        q++;
        options[q] = new JRadioButton("Fox");
        q++;
        options[q] = new JRadioButton("Sheep");
        q++;
        options[q] = new JRadioButton("Tortoise");
        q++;
        options[q] = new JRadioButton("Wolf");
        q++;
        options[q] = new JRadioButton("Belladonna");
        q++;
        options[q] = new JRadioButton("Dandelion");
        q++;
        options[q] = new JRadioButton("Grass");
        q++;
        options[q] = new JRadioButton("Guarana");
        q++;
        options[q] = new JRadioButton("Sosnowski's Hogweed");

        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < n; i++) {
            group.add(options[i]);
        }

        setLayout(new FlowLayout());

        for (int i = 0; i < n; i++) {
            add(options[i]);
        }

        for (int i = 0; i < n; i++) {
            if(options[i].isSelected())
            {
                System.out.println(DefineConst.ALL_SIGNS[i+1]);
            }
        }
        
        
        
            pack();
        }*/
    private char result=0;

    public RadioButton(int x, int y) {
        /* JDialog.setDefaultLookAndFeelDecorated(true);
        Object[] selectionValues = {"Antelope","Fox","Sheep","Tortoise","Wolf","Belladonna","Dandelion","Grass","Guarana","Sosnowski's Hogweed"};
        String initialSelection = "Antelope";
        Object selection = JOptionPane.showInputDialog(null, "Choose one organism",
                "Adding new organism", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        System.out.println(selection);*/
        String[] choices = {"Antelope", "Fox", "Sheep", "Tortoise", "Wolf", "Belladonna", "Dandelion", "Grass", "Guarana", "Sosnowski's Hogweed"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose one organism",
                "Field (" + (x+1) + "," + (y+1) + ") selected",
                JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);

        if (input != null) {
            System.out.println(input);
        
        result=ALL_SIGNS[Arrays.asList(choices).indexOf(input)+1];
        }
    }

    public char getResult() {
        return result;
    }
}
