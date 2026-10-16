package game;

public abstract class Sprite extends Collidable {
    private Polygon[] components;
    private double heading;
    double xPos;
    double yPos;
    double xVelocity;
    double yVelocity;

    public Polygon[] getComponents() {
        return components;
    }

    public abstract void rotate(double deg);

    public abstract void move();

    public void setVelocity(double xVel, double yVel) {
        this.xVelocity = xVel;
        this.yVelocity = yVel;
    }

    public double getHeading(){
        return heading;
    }
}
