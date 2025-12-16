import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
    PImage fishLeft;
    PImage fishRight;

    float fishX;
    float fishY;
    float fishSpeed;

    boolean isMovingRight;

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

        imageMode(CENTER);
    }

    @Override
    public void draw() {
        background(0, 150, 255); // blue water

        moveFish();
        displayFish();
    }

    public void moveFish() {
        if (isMovingRight) {
            fishX += fishSpeed;

            if (fishX > width - fishRight.width / 2) {
                isMovingRight = false;
            }

        } else {
            fishX -= fishSpeed;

            if (fishX < fishLeft.width / 2) {
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
}
