/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meadowgame;

//import meadowgame.Game;
import static meadowgame.DefineConst.*;
import meadowgame.Gui.*;

/*public class Main implements Runnable{

    GUI gui = new GUI();
    
    public static void main(String[] args) {
       new Thread(new Main()).start();
    }

    @Override
    public void run() {
       while(true)
       {
           gui.repaint();
       }
    }
    
}*/
public class Main {

    public static void main(String[] args) {

        int width = 1, height = 1;
        GameStart game_start = new GameStart();
        int result = game_start.getResult();
        switch (result) {
            case QUIT:
                System.exit(0);
            case NEW_GAME:
                SizeSlider slider = new SizeSlider();
                width = slider.getWidth();
                height = slider.getHeight();
            case LOAD_GAME:
                World world = new World(width, height, RECTANGLE);
                world.initWorld(result);
                GUI gui = new GUI(width, height, RECTANGLE);
                gui.setVisible(true);
                Game game = new Game(gui, world);
                if (result == LOAD_GAME) {
                    game.LoadGame();
                }
                break;
        }
    }

}
