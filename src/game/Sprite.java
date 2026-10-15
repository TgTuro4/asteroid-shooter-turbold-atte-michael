package game;

public abstract class Sprite extends Collidable {
    private Polygon[] components;
    private double heading;
    private double xPos;
    private double yPos;
    private double xVelocity;
    private double yVelocity;
    private Polygon bounds;

    public Polygon[] getComponents() {
        return components;
    }

    public abstract void rotate(double deg);

    public abstract void move();

    public void setVelocity(double xVel, double yVel) {
        this.xVelocity = xVel;
        this.yVelocity = yVel;
    }

    @Override
    public Polygon getBounds() {
        return bounds;
    }
}
