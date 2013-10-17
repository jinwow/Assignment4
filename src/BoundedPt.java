/*
 * assignment 4
 * Robbins Jeffrey
 * Robbinsj
 * Guo jinhua
 * guoj
 */
import tester.*;

abstract class Posn {
    int x;
    int y;
    Posn(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class BoundedPt extends Posn {
    int width = 600; // the width of the canvas
    int height = 400; // the height of the canvas
    
    BoundedPt(int x, int y) {
        super(x, y);
        if (x > this.width) {
            throw new IllegalArgumentException("The given x coordinate was " +
                    (x - this.width) + " points beyond the right edge");
        }
        if (x < 0) {
            throw new IllegalArgumentException("The given x coordinate was " +
                    (Math.abs(x)) + " points beyond the left edge");
        }
        if (y < 0) {
            throw new IllegalArgumentException("The given y coordinate was " +
                    (Math.abs(y)) + " points beyond the top edge");
        }
        if (y > this.height) {
            throw new IllegalArgumentException("The given y coordinate was " +
                    (y - this.height) + " points beyond the bottom edge");
        }
    }
}

class ExamplesExceptions {
    boolean testExceptions(Tester t) {
        return
                t.checkConstructorException(new IllegalArgumentException("The given x coordinate was 100 points beyond the right edge"),
                        "BoundedPt", 700, 10) &&
                t.checkConstructorException(new IllegalArgumentException("The given x coordinate was 100 points beyond the left edge"),
                        "BoundedPt", -100, 10) &&
                t.checkConstructorException(new IllegalArgumentException("The given y coordinate was 100 points beyond the top edge"),
                        "BoundedPt", 100, -100) &&
                t.checkConstructorException(new IllegalArgumentException("The given y coordinate was 100 points beyond the bottom edge"),
                         "BoundedPt",100, 500);
    }
}


