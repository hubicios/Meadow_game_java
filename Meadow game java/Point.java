package meadowgame;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static meadowgame.DefineConst.*;

public class Point implements java.io.Closeable {

    protected int x_;
    protected int y_;

    public Point() {
        x_ = 0;
        y_ = 0;
    }

    public Point(int x, int y) {
        x_ = x;
        y_ = y;
    }

    public Point(int x, int y, Direction direction) {
        this(x, y);
        move(direction);
    }

    public void move(Direction direction) {
        move(direction, 1);
    }

    public void move(Direction direction, int n)//number of fields
    {
        switch (direction) {
            case TOP:
                y_ = y_ - n;
                break;
            case RIGHT_TOP:
                x_ = x_ + n;
                y_ = y_ - n;
                break;
            case RIGHT:
                x_ = x_ + n;
                break;
            case RIGHT_DOWN:
                x_ = x_ + n;
                y_ = y_ + n;
                break;
            case DOWN:
                y_ = y_ + n;
                break;
            case LEFT_DOWN:
                x_ = x_ - n;
                y_ = y_ + n;
                break;
            case LEFT:
                x_ = x_ - n;
                break;
            case LEFT_TOP:
                x_ = x_ - n;
                y_ = y_ - n;
                break;
            case DIRECTION_NONE:
                break;
        }
    }

    public final void close() {
    }

    public final int getX() {
        return x_;
    }

    public final int getY() {
        return y_;
    }

    public final void setXY(int x, int y) {
        x_ = x;
        y_ = y;
    }

    public final void setX(int x) {
        x_ = x;
    }

    public final void setY(int y) {
        y_ = y;
    }

    public final void addToX(int liczba) {
        x_ += liczba;
    }

    public final void addToY(int liczba) {
        y_ += liczba;
    }

    public final boolean equals(int x, int y) {
        return x_ == x && y_ == y;
    }

    public final boolean aroundPoint(int x_neighbour, int y_neighbour, char boardType) { //check if x/y_neighbour is around point x/y
        boolean good = false;
        if (abs(x_ - x_neighbour) <= 1 && abs(y_ - y_neighbour) <= 1) {
            good = true;
        }
        if (boardType == HEXAGON) {//HEXAGON
            if (x_ % 2 == 1) { //without left-top and right-top
                if ((y_== y_neighbour+1 && x_ != x_neighbour)) {
                    good = false;
                }
            } else {// x is even without left-down and right-down
                if ((y_== y_neighbour-1 && x_ != x_neighbour)) {
                    good = false;
                }
            }
        }
        return good;
    }

    protected boolean equalsTo(Point point) {
        return this.getX() == point.getX() && this.getY() == point.getY();
    }

    public void toHex(int mx, int my, int height) { //from mouse pixels coordinates to hex representation in 2d array
        int h = height;			// h = basic dimension: height (distance between two adj centresr aka size)
        int r = h / 2;			// r = radius of inscribed circle
        int R = (int) (h / sqrt(3));	// s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)


        int x = (int) (mx / (3*R/2)); //this gives a quick value for x. It works only on odd cols and doesn't handle the triangle sections. It assumes that the hexagon is a rectangle with width s+t (=1.5*s).
        int y = (int) ((my - (x % 2) * r) / h); //this gives the row easily. It needs to be offset by h/2 (=r)if it is in an even column
        //dx,dy are the number of pixels from the hex boundary. (ie. relative to the hex clicked in)
        int dx = mx - x * (3*R/2);
        int dy = my - y * h;

        if (my - (x % 2) * r < 0) {
            
            x_=-1;// prevent clicking in the open halfhexes at the top of the screen
            y_=-1;
            return;
        }

        //even columns
        if (x % 2 == 0) {
            if (dy > r) {	//bottom half of hexes
                if (dx * r / (R/2) < dy - r) {
                    x--;
                }
            }
            if (dy < r) {	//top half of hexes
                if (((R/2) - dx) * r / (R/2) > dy) {
                    x--;
                    y--;
                }
            }
        } else {  // odd columns
            if (dy > h) {	//bottom half of hexes
                if (dx * r / (R/2) < dy - h) {
                    x--;
                    y++;
                }
            }
            if (dy < h) {	//top half of hexes
                if (((R/2) - dx) * r / (R/2) > dy - r) {
                    x--;
                }
            }
        }
       x_=x;
       y_=y;
    }
}
