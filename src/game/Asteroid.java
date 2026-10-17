package game;

public class Asteroid extends Sprite {

    private boolean alive;

    public Asteroid(Polygon[] components, double xPos, double yPos) {
        super(components, xPos, yPos);
        alive = true;
    }

    private void destroy() {
        alive = false;
    }

    @Override
    public boolean checkCollision(Collidable other) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkCollision'");
    }

    @Override
    public void onCollision() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }

    @Override
    public void rotate(double deg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotate'");
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }
    
}
