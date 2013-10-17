/*
 * assignment 4
 * Robbins Jeffrey
 * Robbinsj
 * Guo jinhua
 * guoj
 */
import tester.*;
import javalib.worldimages.Posn;
class BoundedPt extends Posn {
    int width = 600; // the width of the canvas
    int height = 400; // the height of the canvas
    
    BoundedPt(int x, int y) {
        super(x, y);
        if (x > this.width) {
            throw new RuntimeException("The given x coordinate was" +
                    (x - this.width) + "points beyond the right edge");
        }
        if (x < 0) {
            throw new RuntimeException("The given x coordinate was" +
                    (Math.abs(x)) + "points beyond the left edge");
        }
        if (y < 0) {
            throw new RuntimeException("The given y coordinate was" +
                    (Math.abs(y)) + "points beyond the top edge");
        }
        if (y > this.height) {
            throw new RuntimeException("The given y coordinate was" +
                    (y - this.height) + "points beyond the bottom edge");
        }
    }
}


