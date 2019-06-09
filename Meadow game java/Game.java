package meadowgame;

import meadowgame.Gui.RadioButton;
import java.awt.Color;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import static meadowgame.DefineConst.*;

public class Game {// implements java.io.Closeable {

    public Game() {
    }

    public Game(GUI gui, World world) {
        gui_ = gui;
        world_ = world;

        addListeners();
        prepareView();
    }

    public void prepareView() {
        gui_.clearTextField();
        world_.setDefaultColors();
        world_.drawOrganisms();
        gui_.setField(world_.getFieldsColor());
        gui_.repaint();
    }

    public void addListeners() {
        gui_.addNextTurnListener(new NextTurnListener());
        gui_.addChangeBoardTypeListener(new ChangeBoardTypeListener());
        gui_.addSaveGameListener(new SaveGameListener());
        gui_.addLoadGameListener(new LoadGameListener());
        gui_.addFieldPressedListener(new FieldPressedListener());
        gui_.addKeyReleasedListener(new KeyReleasedListener());
        world_.addTurnInfo(new MyTurnInfoListener());
    }

    public class SaveGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                world_.saveGame();
                JOptionPane.showMessageDialog(null, "Game has been saved");
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void LoadGame() {
        try (BufferedReader br = new BufferedReader(new FileReader("save.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            String[] parts = line.split(" ");
            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);
            int turnCount = Integer.parseInt(parts[2]);
            char boardType = parts[3].charAt(0);
            world_ = new World(width, height, turnCount, boardType);
            world_.addTurnInfo(new MyTurnInfoListener());
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                if (line != null) {
                    world_.importOrganism(line);
                }
            }
            gui_.reload(width, height, boardType);
            prepareView();
            
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class LoadGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LoadGame();
        }

    }

    public class NextTurnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui_.clearTextField();
            world_.makeTurn();
            gui_.setField(world_.getFieldsColor());
            gui_.repaint();
        }

    }

    public class ChangeBoardTypeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            world_.changeBoardType();
            gui_.changeBoardType();
            gui_.repaint();
            //System.out.println(world_.getBoardType() + " " + gui_.getBoardType());

        }

    }

    public class FieldPressedListener implements MouseListener {

        private int inBoxX(int mouseX) {
            int x = -1;
            int fieldWidth;
            fieldWidth = (WINDOW_WIDTH) / world_.getWidth();
            double xmod = ((double) mouseX / fieldWidth) % 1.0;
            if (1 - (double) SPACING / fieldWidth >= xmod && xmod >= (double) SPACING / fieldWidth) {
                if (mouseX / fieldWidth < world_.getWidth()) {
                    x = mouseX / fieldWidth;
                }
            }
            return x;
        }

        private int inBoxY(int mouseY) {
            int y = -1;
            int fieldHeight;
            fieldHeight = (WINDOW_HEIGHT - MENU_HEIGHT) / world_.getHeight();
            double ymod = ((double) mouseY / fieldHeight) % 1.0;
            if (1 - (double) SPACING / fieldHeight >= ymod && ymod >= (double) SPACING / fieldHeight) {
                if (mouseY / fieldHeight < world_.getHeight()) {
                    y = mouseY / fieldHeight;

                }
            }
            return y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            int mouseX = e.getX() - WINDOW_SIDE_BORDER - gui_.getWidthBorder();
            int mouseY = e.getY() - WINDOW_TOP_BORDER - gui_.getHeightBorder();
            int x, y;
            boolean good = false;
            if (world_.getBoardType() == RECTANGLE) {
                x = inBoxX(mouseX);
                y = inBoxY(mouseY);
                if (x >= 0 && y >= 0) {
                    good = true;
                }
            } else //HEXAGON
            {
                Point point = new Point();
                point.toHex(mouseX, mouseY, gui_.getHexHeight());
                x = point.getX();
                y = point.getY();
                if (x >= 0 && y >= 0 && x < world_.getWidth() && y < world_.getHeight()) {
                    good = true;
                }
            }
            if (good == true && world_.fieldFree(x, y)) {
                RadioButton radio_button = new RadioButton(x, y);
                char result = radio_button.getResult();
                if (result > 0) {
                    world_.generateOrganism(new Point(x, y), result);
                    //System.out.println("Clicked on[" + (x+1) + "," + (y+1) + "]");

                    world_.drawOrganisms();
                    gui_.setField(world_.getFieldsColor());
                    gui_.repaint();
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    public interface turnInfoListener {

        void addTurnInfo(String desctiption);
    }

    public class MyTurnInfoListener implements turnInfoListener {

        @Override
        public void addTurnInfo(String desctiption) {

            gui_.addToTextField(desctiption);
        }
    }

    /* public class KeyListener {
        //Code

        KeyListener() {
            //More code
            this.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), "Enter");
            this.getActionMap().put("Enter", new EnterAction());
        }

        private class EnterAction extends AbstractAction 

            (){
        @Override
            public void ActionPerformed(ActionEvent e) {
                //Acion
            }
        }
    }*/
    public class KeyReleasedListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int c = e.getKeyCode();

            if (((c == KeyEvent.VK_UP || c == KeyEvent.VK_DOWN || c == KeyEvent.VK_LEFT || c == KeyEvent.VK_RIGHT) && world_.getBoardType() == RECTANGLE)
                    || ((c == KeyEvent.VK_A || c == KeyEvent.VK_S || c == KeyEvent.VK_D || c == KeyEvent.VK_Z || c == KeyEvent.VK_X || c == KeyEvent.VK_C) && world_.getBoardType() == HEXAGON)
                    || c == KeyEvent.VK_Q) {
                world_.setInput(c);
            }

        }

    }

    /* public final void input(World world) {
        int c = 0;
        c = _getch();
        if (c == 0xE0) {
            int c2 = _getch();
            world.setInput(c2);
        } else {
            switch (c) {
                case 's':
                case 'S':
                    world.addTurnDescription("Game has been saved");
                    world.exportOrganisms();
                    break;
                case DefineConst.KEY_ESC:
                    System.exit(0);
                    break;
            }
            world.setInput(c);
        }

    }*/
    private GUI gui_;
    private World world_;
}
