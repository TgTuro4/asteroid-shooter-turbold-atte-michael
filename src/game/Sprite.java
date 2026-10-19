package game;

import java.awt.*;

/**
 * Abstract base class representing a 2D game object composed of polygon components.
 * Manages positioning, heading, velocities, and rotation behavior.
 * Extends {@link Collidable} to support collision interaction logic.
 */
public abstract class Sprite extends Collidable {
    /** Polygon shapes that visually and structurally compose this sprite. */
    private Polygon[] components;

    /** Current facing direction angle in degrees (0 degrees is due East). */
    double heading;

    /** Current position along the X coordinate axis in world. */
    double xPos;

    /** Current position along the Y coordinate axis in world. */
    double yPos;

    /** Movement velocity vector magnitude along the X axis. */
    double xVelocity;

    /** Movement velocity vector magnitude along the Y axis. */
    double yVelocity;

    /**
     * Constructs a new Sprite instance with specified polygon components and initial coordinates.
     * Initial heading angle and directional velocities default to 0.0.
     *
     * @param components Array of {@link Polygon} shapes that composes the sprite.
     * @param xPos       Initial X coordinate position in world space.
     * @param yPos       Initial Y coordinate position in world space.
     */
    public Sprite(Polygon[] components, double xPos, double yPos) {
        this.components = components;
        this.xPos = xPos;
        this.yPos = yPos;
        this.heading = 0.0;
        this.xVelocity = 0.0;
        this.yVelocity = 0.0;
    }

    /**
     * Rotates the sprite's heading by a certain degree.
     *
     * @param deg Angle adjustment in degrees to add to the current heading.
     */
    public void rotate(double deg) {
        heading += deg;
    }

    /**
     * Sets the sprite's movement velocity for both the X and Y axis direction vectors.
     *
     * @param xVel Velocity vector component along the X axis.
     * @param yVel Velocity vector component along the Y axis.
     */
    public void setVelocity(double xVel, double yVel) {
        this.xVelocity = xVel;
        this.yVelocity = yVel;
    }

    /**
     * Calculates bounding rectangle hitboxes for a sprite based on its constituent polygons.
     *
     * @return {@link Rectangle} representing overall collision boundary box.
     */
    public Rectangle getHitbox() {
		Rectangle hitbox = null;
		for (Polygon component : this.getComponents()) {
			component.position = new Point(this.xPos, this.yPos);
			component.rotation = this.getHeading();
			java.awt.Polygon shape = new java.awt.Polygon();
			for (Point point : component.getPoints()) {
				shape.addPoint((int) point.x, (int) point.y);
			}
			if (hitbox == null) {
				hitbox = shape.getBounds();
			} else {
				hitbox.add(shape.getBounds());
			}
		}
		return hitbox == null ? new Rectangle() : hitbox;
	}

    /**
     * Gets the polygons of the sprite.
     *
     * @return Array of {@link Polygon} shapes forming the sprite.
     */
    public Polygon[] getComponents() {
        return components;
    }

    /**
     * Gets the direction the sprite is currently facing in degrees, where 0 degrees is due east.
     *
     * @return The heading of the sprite in degrees.
     */
    public double getHeading(){
        return heading;
    }
}
