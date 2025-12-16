import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class Sketch extends PApplet {

    PImage fishLeft;
    PImage fishRight;

    float fishX;
    float fishY;
    float fishSpeed;

    boolean isMovingRight;

    boolean debug = false;

    ArrayList<int[]> bubbles;
    int bubbleSpeed = 3;

    public static void main(String[] args) {
        PApplet.main("Sketch");
    }

    @Override
    public void settings() {
        size(600, 600);
    }

    @Override
    public void setup() {
        fishRight = loadImage("images/fish-right.gif");
        fishLeft = loadImage("images/fish-left.gif");

        fishX = width / 2;
        fishY = height / 2;
        fishSpeed = 5;

        isMovingRight = true;

        bubbles = new ArrayList<int[]>();

        imageMode(CENTER);
    }

    @Override
    public void draw() {
        background(0, 150, 255); // blue water

        moveFish();
        displayFish();

        updateBubbles();
        displayBubbles();
    }

    public void moveFish() {
        if (isMovingRight) {
            fishX += fishSpeed;

            if (fishX > width - fishRight.width / 2) {
                fishX = width - fishRight.width / 2;
                isMovingRight = false;
            }

        } else {
            fishX -= fishSpeed;

            if (fishX < fishLeft.width / 2) {
                fishX = fishLeft.width / 2;
                isMovingRight = true;
            }
        }
    }

    public void displayFish() {
        if (isMovingRight) {
            image(fishRight, fishX, fishY);
        } else {
            image(fishLeft, fishX, fishY);
        }
    }

    @Override
    public void mousePressed() {
        int bubbleX;

        if (isMovingRight) {
            bubbleX = (int) (fishX + fishRight.width / 2);
        } else {
            bubbleX = (int) (fishX - fishLeft.width / 2);
        }

        int bubbleY = (int) fishY;

        int[] bubble = { bubbleX, bubbleY };
        bubbles.add(bubble);

        if (debug) {
            debugBubbles();
        }
        
    }

    public void updateBubbles() {
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            int[] bubble = bubbles.get(i);

            bubble[1] -= bubbleSpeed;

            if (bubble[1] <= 0) {
                bubbles.remove(i);
            }
        }
    }

    public void displayBubbles() {
        noFill();
        stroke(255);
        strokeWeight(2);

        for (int[] bubble : bubbles) {
            ellipse(bubble[0], bubble[1], 10, 10);
        }
    }

    public void debugBubbles() {
        System.out.println("Number of bubbles: " + bubbles.size());

        for (int i = 0; i < bubbles.size(); i++) {
            int[] bubble = bubbles.get(i);
            System.out.println("Bubble " + i + " -> x: " + bubble[0] + ", y: " + bubble[1]);
        }

        System.out.println("----------------------------");
    }

}
