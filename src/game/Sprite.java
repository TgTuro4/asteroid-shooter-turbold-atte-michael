package game;

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

    public abstract void rotate(double deg);

    public void moveForward() {
        xPos += Math.cos(Math.toRadians(heading)) * 1; // move forward by 5 units
        yPos += Math.sin(Math.toRadians(heading)) * 1; // move forward by 5 units
    }

    public void moveBackward() {
        xPos -= Math.cos(Math.toRadians(heading)) * 1; // move backward by 5 units
        yPos -= Math.sin(Math.toRadians(heading)) * 1; // move backward by 5 units
    }

    // Sets the sprite's velocity in the x and y directions
    // @param xVel The velocity in the x direction
    // @param yVel The velocity in the y direction
    public void setVelocity(double xVel, double yVel) {
        this.xVelocity = xVel;
        this.yVelocity = yVel;
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
