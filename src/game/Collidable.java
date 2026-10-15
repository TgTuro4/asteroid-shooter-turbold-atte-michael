package game;

/*
This abstract class represents any game object that can collide with another game object.
It contains a polygon named "bounds", representing the rectangular hitbox of the object.
*/
public abstract class Collidable {
    public Polygon bounds;

    /*
    Returns the hitbox of the collidable object.
    @return Object hitbox polygon
    */
    public Polygon getBounds() {
        return bounds;
    }

    /*
    Checks if this object and another specified object are colliding.
    @param Other collidable object
    @return True if the two objects are colliding, false otherwise
    */
    public boolean checkCollision(Collidable other) {
        if (this.bounds == null || other.bounds == null) {
            return false;
        }
        for (Point p : other.bounds.getPoints()) {
            if (this.bounds.contains(p)) {
                return true;
            }
        }
        return false;
    }

    public abstract void onCollision();
}
