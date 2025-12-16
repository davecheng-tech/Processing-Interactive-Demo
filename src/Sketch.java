import processing.core.PApplet;

/**
 * Template for programs with Processing graphics output.
 * @author Your Name
 */
public class Sketch extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Sketch");
    }

    /** Set up canvas size. */
    @Override
    public void settings() {
        size(600, 600);  // Canvas size
    }

    /** Runes once at start. */
    @Override
    public void setup() {
        background(120, 197, 227);  // Cyan background
    }

    /** Loops continuously after setup(). */
    @Override
    public void draw() {
        fill(242, 19, 224);     // Magenta fill
        circle(300, 300, 200);  // Draw a circle at the centre
    }

    /** Additional helper methods below (optional) */

}
