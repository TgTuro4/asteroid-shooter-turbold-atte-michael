package game;

import java.awt.*;

public abstract class Sprite extends Collidable {
    private Polygon[] components;
    double heading;
    double xPos;
    double yPos;
    double xVelocity;
    double yVelocity;

    public Sprite(Polygon[] components, double xPos, double yPos) {
        this.components = components;
        this.xPos = xPos;
        this.yPos = yPos;
        this.heading = 0.0;
        this.xVelocity = 0.0;
        this.yVelocity = 0.0;
    }

    public void rotate(double deg) {
        heading += deg;
    }

    // Sets the sprite's velocity in the x and y directions
    // @param xVel The velocity in the x direction
    // @param yVel The velocity in the y direction
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

    // Gets the component polygons of the sprite
    // @return An array of polygons representing the sprite's components
    public Polygon[] getComponents() {
        return components;
    }

    // Gets the direction the sprite is currently facing in degrees, where 0 degrees is due east
    // @return The heading of the sprite in degrees
    public double getHeading(){
        return heading;
    }
}
