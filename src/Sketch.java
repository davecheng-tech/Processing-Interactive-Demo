import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

/**
 * Sketch
 *
 * This Processing program demonstrates:
 *  - Basic animation using draw()
 *  - Conditional logic to control movement and direction
 *  - Use of an ArrayList to manage a dynamic collection of objects (bubbles)
 *  - Simple console-based debugging
 */
public class Sketch extends PApplet {

    /* =====================
     * Instance Variables
     * ===================== */

    // Fish images for each direction
    PImage fishLeft;
    PImage fishRight;

    // Fish position and speed
    float fishX;
    float fishY;
    float fishSpeed;

    // True if fish is currently moving to the right
    boolean isMovingRight;

    // Enables or disables console debug output
    boolean debug = true;

    /*
     * Stores all bubbles currently on screen.
     * Each bubble is represented by an int array:
     *   bubble[0] -> x position
     *   bubble[1] -> y position
     */
    ArrayList<int[]> bubbles;

    // Speed at which bubbles rise vertically
    int bubbleSpeed = 3;

    // Required entry point when running a Processing sketch as a Java application
    public static void main(String[] args) {
        PApplet.main("Sketch");
    }

    /**
     * settings()
     *
     * Called before setup().
     * Used to configure the size of the drawing canvas.
     */
    @Override
    public void settings() {
        size(600, 600);
    }

    /**
     * setup()
     *
     * Runs once at program start.
     * Used for loading assets and initializing state.
     */
    @Override
    public void setup() {
        // Load fish images from the images/ folder
        fishRight = loadImage("images/fish-right.gif");
        fishLeft  = loadImage("images/fish-left.gif");

        // Start fish near the centre of the screen
        fishX = width / 2;
        fishY = height / 3 * 2;

        // Set horizontal movement speed
        fishSpeed = 5;

        // Fish initially moves to the right
        isMovingRight = true;

        // Create the ArrayList that will store all bubbles
        bubbles = new ArrayList<int[]>();

        // Draw images relative to their centre point
        imageMode(CENTER);
    }

    /**
     * draw()
     *
     * Runs repeatedly (about 60 times per second).
     * Acts as the main animation loop.
     */
    @Override
    public void draw() {
        // Clear the screen with a blue background
        background(0, 150, 255);

        // Update and draw the fish
        moveFish();
        displayFish();

        // Update and draw all bubbles
        updateBubbles();
        displayBubbles();
    }

    /**
     * moveFish()
     *
     * Updates the fish's horizontal position.
     * Reverses direction when the fish reaches the edge of the screen.
     */
    public void moveFish() {
        if (isMovingRight) {
            fishX += fishSpeed;

            // Right boundary check (accounting for image width)
            if (fishX > width - fishRight.width / 2) {
                fishX = width - fishRight.width / 2;
                isMovingRight = false;
            }
        } else {
            fishX -= fishSpeed;

            // Left boundary check (accounting for image width)
            if (fishX < fishLeft.width / 2) {
                fishX = fishLeft.width / 2;
                isMovingRight = true;
            }
        }
    }

    /**
     * displayFish()
     *
     * Draws the appropriate fish image based on
     * the current movement direction.
     */
    public void displayFish() {
        if (isMovingRight) {
            image(fishRight, fishX, fishY);
        } else {
            image(fishLeft, fishX, fishY);
        }
    }

    /**
     * mousePressed()
     *
     * Automatically called when the mouse is clicked.
     * Creates a new bubble at the fish's mouth.
     */
    @Override
    public void mousePressed() {
        int bubbleX;

        // Determine bubble x-position based on fish direction
        if (isMovingRight) {
            bubbleX = (int)(fishX + fishRight.width / 2);
        } else {
            bubbleX = (int)(fishX - fishLeft.width / 2);
        }

        // Bubble starts vertically centred on the fish
        int bubbleY = (int)fishY;

        // Store bubble position and add to ArrayList
        int[] bubble = { bubbleX, bubbleY };
        bubbles.add(bubble);

        // Debug output, show array of tracked bubble positions
        if (debug) {
            debugBubbles();
        }
    }

    /**
     * updateBubbles()
     *
     * Moves all bubbles upward.
     * Removes bubbles once they leave the top of the screen.
     *
     * NOTE:
     * Looping backwards prevents index errors when removing items.
     */
    public void updateBubbles() {
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            int[] bubble = bubbles.get(i);

            // Move bubble upward
            bubble[1] -= bubbleSpeed;

            // Remove bubble if it is off-screen
            if (bubble[1] <= 0) {
                bubbles.remove(i);
            }
        }
    }

    /**
     * displayBubbles()
     *
     * Draws all bubbles currently stored in the ArrayList.
     */
    public void displayBubbles() {
        noFill();
        stroke(255);
        strokeWeight(2);

        for (int[] bubble : bubbles) {
            ellipse(bubble[0], bubble[1], 10, 10);
        }
    }

    /**
     * debugBubbles()
     *
     * Prints the contents of the bubbles ArrayList
     * to the console in a readable format.
     */
    public void debugBubbles() {
        System.out.println("Number of bubbles: " + bubbles.size());

        for (int i = 0; i < bubbles.size(); i++) {
            int[] bubble = bubbles.get(i);
            System.out.println(
                "Bubble " + i + " -> x: " + bubble[0] + ", y: " + bubble[1]
            );
        }

        System.out.println("----------------------------");
    }
}
