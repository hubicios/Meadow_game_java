package meadowgame;

import java.awt.Color;

final public class DefineConst {

    //colors taken from https://materialuicolors.co
    //human
    public static final Color HUMAN_COLOR_AVAILABLE = new Color(56, 142, 60,200); //skill available //green 700
    public static final Color HUMAN_COLOR_ACTIVE = new Color(0, 230, 118,200); //skill active //green A400
    public static final Color HUMAN_COLOR_UNACTIVE = new Color(27, 94, 32,200); //skill cooldown //green 900
    public static final char HUMAN_SIGN = 'C';
    public static final int HUMAN_STRENGTH = 5;
    public static final int HUMAN_PRIORITY = 4;
    public static final int HUMAN_SKILL_TIME = 5;
    //wolf
    public static final Color WOLF_COLOR = new Color(244, 67, 54,200); //red 500
    public static final char WOLF_SIGN = 'W';
    public static final int WOLF_STRENGTH = 9;
    public static final int WOLF_PRIORITY = 5;
    //sheep
    public static final Color SHEEP_COLOR=new Color(224, 224, 224,200);//white 300
    public static final char SHEEP_SIGN = 'O';
    public static final int SHEEP_STRENGTH = 4;
    public static final int SHEEP_PRIORITY = 4;
    //fox
    public static final Color FOX_COLOR = new Color(255, 152, 0,200);//orange 500
    public static final char FOX_SIGN = 'L';
    public static final int FOX_STRENGTH = 3;
    public static final int FOX_PRIORITY = 7;
    //tortoise
    public static final Color TORTOISE_COLOR = new Color(0, 150, 136,200);//teal 500
    public static final char TORTOISE_SIGN = 'Z';
    public static final int TORTOISE_STRENGTH = 2;
    public static final int TORTOISE_PRIORITY = 1;
    //antelope
    public static final Color ANTELOPE_COLOR = new Color(121, 85, 72,200);//brown 500
    public static final char ANTELOPE_SIGN = 'A';
    public static final int ANTELOPE_STRENGTH = 4;
    public static final int ANTELOPE_PRIORITY = 4;
    public static final int ANTELOPE_JUMP = 2;
    //grass
    public static final Color GRASS_COLOR = new Color(174, 213, 129,200);//lightgreen 300
    public static final char GRASS_SIGN = 't';
    public static final int GRASS_STRENGTH = 0;
    public static final double GRASS_SPREAD = 0.02;
    //dandelion
    public static final Color DANDELION_COLOR = new Color(255, 241, 118,200);//yellow 300
    public static final char DANDELION_SIGN = 'm';
    public static final int DANDELION_STRENGTH = 0;
    public static final double DANDELION_SPREAD = 0.02;
    public static final int DANDELION_SPREAD_TIMES = 3;
    //guarana
    public static final Color GUARANA_COLOR = new Color(149, 117, 205,200);//deep purple 300
    public static final char GUARANA_SIGN = 'g';
    public static final int GUARANA_STRENGTH = 0;
    public static final double GUARANA_SPREAD = 0.02;
    public static final int GUARANA_BONUS_GIVEN = 3;
    //belladonna
    public static final Color BELLADONNA_COLOR = new Color(240, 98, 146,200);//pink 300
    public static final char BELLADONNA_SIGN = 'j';
    public static final int BELLADONNA_STRENGTH = 99;
    public static final double BELLADONNA_SPREAD = 0.03;
    //sosnowski's hogweed
    public static final Color SOSNOWSKIS_HOGWEED_COLOR = new Color(183, 28, 28,200);//red 9100 
    public static final char SOSNOWSKIS_HOGWEED_SIGN = 'b';
    public static final int SOSNOWSKIS_HOGWEED_STRENGTH = 10;
    public static final double SOSNOWSKIS_HOGWEED_SPREAD = 0.01;

    public static final char[] ALL_SIGNS={HUMAN_SIGN,ANTELOPE_SIGN,FOX_SIGN,SHEEP_SIGN,TORTOISE_SIGN,WOLF_SIGN,BELLADONNA_SIGN,DANDELION_SIGN,GRASS_SIGN,GUARANA_SIGN,SOSNOWSKIS_HOGWEED_SIGN};
    public static final double PLANT_SPREAD = 0.02;

    public static final int FERTILITY_AGE = 4; //min age to make new organizm

    public static final int NONE_INPUT = -1;
    public static final int MAX_BOARD_SIZE = 50;
    public static final int MIN_BOARD_SIZE = 10;
    public static final char RECTANGLE = 'R';
    public static final char HEXAGON = 'H';
    //public static final int POPULTION_DENISTY = 2; // 1-low 2-medium 3-high

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 1000;
    public static final int MENU_HEIGHT = 95;
    public static final int SPACING = 2;
    public static final int WINDOW_SIDE_BORDER = 8;
    public static final int WINDOW_TOP_BORDER = 31;
    public static final Color DEFAULT_FIELD_COLOR = new Color(117, 117, 117,200);//grey 600 
    public static final Color DEFAULT_FIELD_HEX_COLOR = new Color(80, 80, 80,200);//custom
    public static final Color DEFAULT_BOARD_COLOR = new Color(66, 66, 66);//gray 800 
    public static final Color BUTTON_FOREGROUND=Color.BLACK;
    public static final Color BUTTON_BACKGROUND=Color.WHITE;
    public final static int BORDERS = 5;
    
    public static final int QUIT =-1;
    public static final int NEW_GAME =0;
    public static final int LOAD_GAME =1;
}
