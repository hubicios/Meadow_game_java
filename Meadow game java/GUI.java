package meadowgame;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;
import static meadowgame.DefineConst.*;

/**
 *
 * @author Hubert
 */
public class GUI extends JFrame {

    private int widthN = 0, heightN = 0;
    private Color[][] fields;
    private JButton nextTurnButton;
    private JButton saveGameButton;
    private JButton loadGameButton;
    private JButton changeBoardTypeButton;
    private JTextArea turnInfo;
    private Board board;
    private char boardType_;
    private int hexHeight = 0;
    private int width_border = 0, height_border = 0;

    public GUI(int width, int height, char boardType) {
        this.setFocusable(true);
        this.setSize(WINDOW_WIDTH + 2 * WINDOW_SIDE_BORDER, WINDOW_HEIGHT + WINDOW_SIDE_BORDER + WINDOW_TOP_BORDER);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2) - (this.getHeight() / 2);
        this.setLocation(xPos, yPos);
        this.setTitle("Hubert Solecki s17823 PO_2");
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        reload(width, height, boardType);

        board = new Board();
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nextTurnButton = new JButton("New turn");
        changeBoardTypeButton = new JButton("Change board type");
        saveGameButton = new JButton("Save game");
        loadGameButton = new JButton("Load game");

        nextTurnButton.setBackground(HUMAN_COLOR_ACTIVE);
        nextTurnButton.setForeground(BUTTON_FOREGROUND);
        changeBoardTypeButton.setBackground(BUTTON_BACKGROUND);
        changeBoardTypeButton.setForeground(BUTTON_FOREGROUND);
        saveGameButton.setBackground(BUTTON_BACKGROUND);
        saveGameButton.setForeground(BUTTON_FOREGROUND);
        loadGameButton.setBackground(BUTTON_BACKGROUND);
        loadGameButton.setForeground(BUTTON_FOREGROUND);

        nextTurnButton.setFocusable(false);
        changeBoardTypeButton.setFocusable(false);
        saveGameButton.setFocusable(false);
        loadGameButton.setFocusable(false);

        downPanel.add(nextTurnButton);
        downPanel.add(saveGameButton);
        downPanel.add(loadGameButton);
        downPanel.add(changeBoardTypeButton);

        turnInfo = new JTextArea(5, 75);
        turnInfo.setLineWrap(true);
        turnInfo.setEditable(false);
        turnInfo.setFocusable(false);
        JScrollPane scroll = new JScrollPane(turnInfo);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        downPanel.add(scroll);
        downPanel.setBackground(DEFAULT_BOARD_COLOR);
        mainPanel.setBackground(DEFAULT_BOARD_COLOR);

        mainPanel.add(downPanel, BorderLayout.SOUTH);
        mainPanel.add(board);


        JRootPane rootPane = this.getRootPane();
        rootPane.setDefaultButton(nextTurnButton);

        this.add(mainPanel);
    }

    public void reload(int width, int height, char boardType) {
        boardType_ = boardType;
        widthN = width;
        heightN = height;
        fields = new Color[heightN][widthN];
    }

    public void clearFields() {
        for (int i = 0; i < widthN; i++) {
            for (int j = 0; j < heightN; j++) {
                fields[j][i] = DEFAULT_FIELD_COLOR;
            }
        }
    }

    public void getFieldsColor() {
        for (int i = 0; i < widthN; i++) {
            for (int j = 0; j < heightN; j++) {
                fields[j][i] = DEFAULT_FIELD_COLOR;
            }
        }
    }

    public void setField(Color[][] colors) {
        for (int i = 0; i < widthN; i++) {
            for (int j = 0; j < heightN; j++) {
                fields[j][i] = colors[j][i];
            }
        }
    }

    public void setField(int x, int y, Color color) {

        fields[y][x] = color;
    }

    public int getWidthBorder() {
        return width_border;
    }

    public int getHeightBorder() {
        return height_border;
    }

    public int getHexHeight() {
        return hexHeight;
    }

    void addNextTurnListener(Game.NextTurnListener nextTurnListener) {
        nextTurnButton.addActionListener(nextTurnListener);
    }

    void addSaveGameListener(Game.SaveGameListener saveGameListener) {
        saveGameButton.addActionListener(saveGameListener);
    }

    void addLoadGameListener(Game.LoadGameListener loadGameListener) {
        loadGameButton.addActionListener(loadGameListener);
    }

    void addFieldPressedListener(Game.FieldPressedListener fieldPressedListener) {
        this.addMouseListener(fieldPressedListener);
    }

    void addKeyReleasedListener(Game.KeyReleasedListener keyReleasedListener) {
        this.addKeyListener(keyReleasedListener);
    }

    void addToTextField(String desctiption) {
        turnInfo.append(desctiption);

    }

    void clearTextField() {
        turnInfo.setText("");

    }

    void addChangeBoardTypeListener(Game.ChangeBoardTypeListener changeBoardTypeListener) {
        changeBoardTypeButton.addActionListener(changeBoardTypeListener);
    }

    public void changeBoardType() {
        switch (boardType_) {

            case RECTANGLE:
                boardType_ = HEXAGON;
                break;

            case HEXAGON:
                boardType_ = RECTANGLE;
                break;
        }
    }

    public char getBoardType() {
        return boardType_;
    }

    public class Board extends JPanel {

        private int fieldWidth, fieldHeight;

        public Board() {
        }

        @Override
        public void paintComponent(Graphics g) {
            int col, row;
            row = WINDOW_WIDTH; //width of the panel
            col = WINDOW_HEIGHT - MENU_HEIGHT; //height of the panel

            //drwa background
            g.setColor(DEFAULT_BOARD_COLOR);
            g.fillRect(0, 0, row, col);
            g.setColor(DEFAULT_FIELD_COLOR);

            //draw grid of rectangles
            if (boardType_ == RECTANGLE) {
                fieldWidth = row / widthN;
                fieldHeight = col / heightN;

                width_border = (row - fieldWidth * widthN) / 2;
                height_border = (col - fieldHeight * heightN) / 2;
                drawRectangles(g);
            } else {//HEXAGON
                /*width_border = 0;
                height_border = 0;*/

                hexHeight = (int) (2 * min((col / (2.0 * heightN + 1)), (row * sqrt(3) / (3 * widthN + widthN % 2)))) - 1;
                setHeight(hexHeight);

                height_border = (2 * col - hexHeight * (2 * heightN + 1)) / 4;
                width_border = (int) ((2 * row - hexHeight * (3 * widthN + widthN % 2) / sqrt(3)) / 4);

                drawHexagons(g);
            }
        }

        private void drawRectangles(Graphics g) {
            for (int i = 0; i < widthN; i++) {
                for (int j = 0; j < heightN; j++) {
                    g.setColor(fields[j][i]);
                    g.fillRect(SPACING + width_border + i * fieldWidth, SPACING + height_border + j * fieldHeight, fieldWidth - 2 * SPACING, fieldHeight - 2 * SPACING);
                }
            }
        }

        private void drawHexagons(Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //draw hexagons
            for (int i = 0; i < widthN; i++) {
                for (int j = 0; j < heightN; j++) {
                    // drawHex(i, j, g2);
                    drawHex(i, j, g2, fields[j][i]);
                }
            }
        }

        private int h, r, R;

        //hexagon methods
        private void setHeight(int height) {
            h = height;			// h = basic dimension: height (distance between two adj centresr aka size)
            r = h / 2;			// r = radius of inscribed circle
            R = (int) (h / sqrt(3));	// s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
        }

        private Polygon hex(int x0, int y0) {

            int y = y0 + height_border;
            int x = x0 + width_border;

            if (R == 0 || h == 0) {
               // System.out.println("Size is equal to 0");
                return new Polygon();
            }

            int[] cx, cy;

            cx = new int[]{x + R / 2, x + R * 3 / 2, x + R * 2, x + R * 3 / 2, x + R / 2, x};	//this is for the whole hexagon to be below and to the right of this point
            cy = new int[]{y, y, y + r, y + r + r, y + r + r, y + r};
            return new Polygon(cx, cy, 6);
        }

        private void drawHex(int i, int j, Graphics2D g2, Color color) {

            int x = i * (R * 3 / 2);
            int y = j * h + (i % 2) * h / 2;
            Polygon poly = hex(x, y);
            g2.setColor(color);
            g2.fillPolygon(hex(x, y));
            g2.setColor(Color.BLACK);
            g2.drawPolygon(poly);
        }

    }
}
