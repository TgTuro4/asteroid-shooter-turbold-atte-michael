package game;

import java.awt.*;

public class Asteroid extends Sprite {
    private boolean alive;

    public Asteroid(Polygon[] components, double xPos, double yPos) {
        super(components, xPos, yPos);
        alive = true;
    }
    
    public void paint(Graphics g) {
        for (Polygon component : getComponents()) {
            component.position = new Point(xPos, yPos);
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
    
}
