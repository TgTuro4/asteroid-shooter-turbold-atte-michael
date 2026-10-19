package game;

import java.awt.*;

/**
 * Represents an asteroid entity in the game environment.
 * Handles rendering of polygonal the astoeroids.
 */
public class Asteroid extends Sprite implements Drawable {
    
    /**
     * Constructs a new Asteroid instance with specified component shapes and initial coordinates.
     *
     * @param components Array of {@link Polygon} shapes that compose the physical form of the asteroid.
     * @param xPos       Initial X coordinate position in world space.
     * @param yPos       Initial Y coordinate position in world space.
     */
    public Asteroid(Polygon[] components, double xPos, double yPos) {
        super(components, xPos, yPos);
    }

    /**
     * Renders each component polygon of the asteroid at its current position and rotation angle onto the screen.
     *
     * @param g {@link Graphics} context used for drawing frame visual elements.
     */
    public void paint(Graphics g) {
        for (Polygon component : getComponents()) {
            component.position = new Point(super.position.x, super.position.y);
            component.rotation = heading;
            Point[] points = component.getPoints();
            int[] xPoints = new int[points.length];
            int[] yPoints = new int[points.length];
            for (int i = 0; i < points.length; i++) {
                xPoints[i] = (int) points[i].x;
                yPoints[i] = (int) points[i].y;
            }
            g.setColor(Color.gray);
            g.fillPolygon(xPoints, yPoints, points.length);
        }
    }
}
