# Loading and Displaying Images in Processing (Java Mode)

This guide explains how to load and display image files in a Processing sketch running in Java mode. All examples are based on code in `src/Sketch.java` and assets in the `images/` folder of this repo.

---

## Where to Put Your Images

Place all image files in the `images/` folder at the root of your project:

```
Processing-Interactive-Demo/
├── images/
│   ├── fish-left.gif
│   ├── fish-right.gif
│   └── screenshot.png
├── src/
│   └── Sketch.java
└── lib/
    └── core.jar
```

Processing resolves image paths **relative to the project root**, not relative to `src/`. So the path `"images/fish-right.gif"` works correctly from within `Sketch.java`.

> **Supported formats:** PNG, JPG, GIF (including animated GIFs), and BMP.

---

## Step 1: Declare a PImage Variable

`PImage` is Processing's image type. Declare it as an instance variable alongside your other fields, so it is accessible from any method.

```java
PImage fishRight;
PImage fishLeft;
```

From `Sketch.java` (lines 21–22):
```java
// Fish images for each direction
PImage fishLeft;
PImage fishRight;
```

---

## Step 2: Load the Image in setup()

Use `loadImage()` to read the file from disk. Always do this in `setup()` — loading images is slow and should only happen once, not inside `draw()`.

```java
@Override
public void setup() {
    fishRight = loadImage("images/fish-right.gif");
    fishLeft  = loadImage("images/fish-left.gif");
}
```

From `Sketch.java` (lines 71–72):
```java
// Load fish images from the images/ folder
fishRight = loadImage("images/fish-right.gif");
fishLeft  = loadImage("images/fish-left.gif");
```

If the file path is wrong or the file is missing, `loadImage()` will return `null` and your sketch will crash with a `NullPointerException` the moment you try to draw the image. Double-check your path and filename (including capitalization) if this happens.

---

## Step 3: Display the Image in draw()

Use the `image()` function to draw a `PImage` onto the canvas.

### Basic syntax

```java
image(img, x, y);
```

- `img` — the `PImage` variable to draw
- `x`, `y` — the position to draw it at (see imageMode below for what "position" means)

### With explicit dimensions

```java
image(img, x, y, width, height);
```

This stretches or shrinks the image to fit the given dimensions. Useful for scaling sprites to a consistent size regardless of the original file resolution.

---

## Understanding imageMode

`imageMode` controls what the `(x, y)` coordinates passed to `image()` refer to.

| Mode | What (x, y) means |
|---|---|
| `CORNER` (default) | Top-left corner of the image |
| `CENTER` | Centre of the image |

This sketch uses `CENTER` mode (set once in `setup()`):

```java
imageMode(CENTER);
```

From `Sketch.java` (line 88):
```java
// Draw images relative to their centre point
imageMode(CENTER);
```

With `CENTER` mode, placing an image at `(fishX, fishY)` centres the image on that point. This makes positioning logic much simpler — `fishX` and `fishY` always refer to the middle of the fish, not a corner.

With the default `CORNER` mode, `(x, y)` is the top-left corner. You would need to subtract half the image dimensions to centre it manually:

```java
// CORNER mode — manual centering
image(fishRight, fishX - fishRight.width / 2, fishY - fishRight.height / 2);

// CENTER mode — cleaner
image(fishRight, fishX, fishY);
```

---

## Reading Image Dimensions

Once loaded, a `PImage` exposes its dimensions as `.width` and `.height`.

```java
fishRight.width   // pixel width of the loaded image
fishRight.height  // pixel height of the loaded image
```

This sketch uses image dimensions to keep the fish from clipping off the edge of the screen. With `CENTER` mode, the fish's centre must stay at least half its width away from the edge:

From `Sketch.java` (lines 122–124):
```java
// Right boundary check (accounting for image width)
if (fishX > width - fishRight.width / 2) {
    fishX = width - fishRight.width / 2;
```

---

## Switching Images at Runtime

You can display different images based on program state. This sketch stores two images — one facing left, one facing right — and chooses which to draw based on the fish's current direction:

From `Sketch.java` (lines 143–148):
```java
public void displayFish() {
    if (isMovingRight) {
        image(fishRight, fishX, fishY);
    } else {
        image(fishLeft, fishX, fishY);
    }
}
```

This is a common pattern for sprite-based animation: load multiple images and swap between them based on state.

---

## Animated GIFs

Processing supports animated GIFs natively. When you call `loadImage()` on a `.gif` file, Processing loads all frames and plays them automatically. No extra code is needed — just call `image()` on the loaded `PImage` and the animation plays on its own.

The fish sprites in this repo (`fish-left.gif`, `fish-right.gif`) are animated GIFs. Their fins and details animate automatically.

---

## Full Example

Here is the complete image-related code from this sketch, assembled in one place:

```java
import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {

    PImage fishLeft;
    PImage fishRight;
    float fishX;
    float fishY;

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
        fishLeft  = loadImage("images/fish-left.gif");

        fishX = width / 2;
        fishY = height / 2;

        imageMode(CENTER);
    }

    @Override
    public void draw() {
        background(0, 150, 255);
        image(fishRight, fishX, fishY);
    }
}
```

---

## Common Mistakes

**Image not loading / NullPointerException**  
The file path passed to `loadImage()` is probably wrong. Remember:
- The path is relative to the project root, not to `src/`
- The filename is case-sensitive on Mac and Linux (`Fish.png` ≠ `fish.png`)
- The file must actually exist in the `images/` folder

**Image appears in the wrong position**  
Check whether you set `imageMode(CENTER)` or are using the default `CORNER` mode. Forgetting this is a very common source of off-by-half-width positioning bugs.

**Image is blurry or the wrong size**  
You may have accidentally used the four-argument form `image(img, x, y, w, h)` with wrong dimensions, which stretches the image. Use the three-argument form `image(img, x, y)` to draw at the original size.

**Loading images inside draw()**  
Never call `loadImage()` inside `draw()`. It reads from disk every frame (~60 times per second), which will cause severe lag. Always load once in `setup()`.
