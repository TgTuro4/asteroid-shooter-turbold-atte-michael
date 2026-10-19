package game;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Represents a controllable player or entity rocket ship in the game.
 * Manages health, movement, rendering, and active lasers fired by the ship.
 */
public class RocketShip extends Sprite {

    /** Current health value of the rocket ship. */
    private int health;

    /** Maximum possible health value of the rocket ship. */
    // TODO: Consider using in future to reset health per round or for power-ups
    @SuppressWarnings("unused")
    private int maxHealth;

    /** List of all lasers fired by this rocket ship. */
    private ArrayList<Laser> laserList;

    /**
     * Constructs a new RocketShip with specified polygonal components, initial position, and maximum health.
     *
     * @param components Array of {@link Polygon} shapes that visually compose the rocket ship.
     * @param xPos       Initial X coordinate position in the world space.
     * @param yPos       Initial Y coordinate position in the world space.
     * @param maxHealth  Maximum starting health capacity for the ship.
     */
    public RocketShip(Polygon[] components, double xPos, double yPos, int maxHealth) {
        super(components, xPos, yPos);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.laserList = new ArrayList<>();
    }

    /**
     * Instantiates and fires a new {@link Laser} from the rocket ship's current position and heading.
     */
    public void shoot() {
        Laser newLaser = new Laser(10, 25, 60); 
        laserList.add(newLaser);
    }

    /**
     * Updates positions and lifetimes of all managed lasers, removing inactive or expired lasers.
     */
    public void updateLasers() {
        for (int i = laserList.size() - 1; i >= 0; i--) {
            Laser laser = laserList.get(i);
            if (laser.isActive()) {
                laser.update();
            } else {
                laserList.remove(i);
            }
        }
    }

    /**
     * Gets the current remaining health of the rocket ship.
     *
     * @return Current health value.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Decreases the rocket ship's health by a specified amount, ensuring it does not drop below zero.
     *
     * @param amount Damage amount to subtract from current health.
     * @return Updated health value after damage application.
     */
    public int takeDamage(int amount) {
        this.health -= amount;
        if (this.health < 0) {
            this.health = 0;
        }
        return this.health;
    }

    /**
     * Filters and returns an array containing only active lasers currently in play.
     *
     * @return An array of active {@link Laser} instances.
     */
    public Laser[] getActiveLasers() {
        ArrayList<Laser> active = new ArrayList<>();
        for (Laser l : laserList) {
            if (l.isActive()) {
                active.add(l);
            }
        }
        return active.toArray(new Laser[0]);
    }

    /**
     * Inner class representing a projectile laser fired by a {@link RocketShip}.
     */
    public class Laser {

        /** Speed unit per frame travel distance. */
        private int speed;

        /** Damage value inflicted upon hit. */
        private int damage;

        /** State flag indicating if the laser is active. */
        private boolean isShot;

        /** Remaining duration in frames before the laser automatically expires. */
        private int lifetime;

        /** Current X coordinate position of the laser. */
        private double laserX;

        /** Current Y coordinate position of the laser. */
        private double laserY;

        /** Direction angle in degrees at which the laser travels. */
        private double laserHeading;

        /**
         * Constructs a new Laser bound to the firing RocketShip's position and orientation.
         *
         * @param speed    Movement speed magnitude per frame update.
         * @param damage   Damage inflicted on collision.
         * @param lifetime Total frame lifetime before expiration.
         */
        public Laser(int speed, int damage, int lifetime) {
            this.speed = speed;
            this.damage = damage;
            this.lifetime = lifetime;
            this.isShot = true;
            
            this.laserX = RocketShip.this.xPos;
            this.laserY = RocketShip.this.yPos;
            this.laserHeading = RocketShip.this.heading;
        }

        /**
         * Renders the laser onto the graphics context if active.
         *
         * @param g {@link Graphics} rendering context.
         */
        public void paint(Graphics g) {
            if (isActive()) {
                g.fillOval((int) laserX, (int) laserY, 4, 4);
            }
        }

        /**
         * Updates position based on current trajectory and decrements lifetime counter.
         */
        public void update() {
            if (isShot && lifetime > 0) {
                double rad = Math.toRadians(laserHeading);
                laserX += speed * Math.cos(rad);
                laserY += speed * Math.sin(rad);
                lifetime--;

                if (lifetime <= 0) {
                    deactivate();
                }
            }
        }

        /**
         * Checks whether the laser is active and unexpired.
         *
         * @return {@code true} if active and unexpired; {@code false} otherwise.
         */
        public boolean isActive() {
            return isShot && !hasExpired();
        }

        /**
         * Checks if the laser lifetime duration has ended.
         *
         * @return {@code true} if lifetime is zero or less; {@code false} otherwise.
         */
        public boolean hasExpired() {
            return lifetime <= 0;
        }

        /**
         * Deactivates the laser project.
         */
        public void deactivate() {
            this.isShot = false;
        }

        /**
         * Gets the damage output of this laser projectile.
         *
         * @return Damage value.
         */
        public int getDamage() {
            return damage;
        }

        /**
         * Gets current X coordinate position of this laser projectile.
         *
         * @return X coordinate position.
         */
        public double getLaserX() {
            return laserX;
        }

        /**
         * Gets current Y coordinate position of this laser projectile.
         *
         * @return Y coordinate position.
         */
        public double getLaserY() {
            return laserY;
        }
    }

    /**
     * Moves the rocket ship forward relative to its current heading direction.
     */
    @Override 
    public void moveForward() {
        super.xPos += Math.cos(Math.toRadians(heading)) * 1; // move forward by 1 unit
        super.yPos += Math.sin(Math.toRadians(heading)) * 1; // move forward by 1 unit
    }

    /**
     * Moves the rocket ship backward relative to its current heading direction.
     */
    @Override 
    public void moveBackward() {
        super.xPos -= Math.cos(Math.toRadians(heading)) * 1; // move backward by 1 unit
        super.yPos -= Math.sin(Math.toRadians(heading)) * 1; // move backward by 1 unit
    }

    /**
     * Handles collision events for this ship.
     *
     * 
     */
    @Override
    public void onCollision() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }

    /**
     * Retrieves the internal list containing all active and inactive laser instances.
     *
     * @return {@link ArrayList} of {@link Laser} objects.
     */
    public ArrayList<Laser> getLaserList() {
        return laserList;
    }

    /**
     * Draws all components of the rocket ship .
     *
     * @param g {@link Graphics} context to draw upon.
     */
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
            g.drawPolygon(xPoints, yPoints, points.length);
        }
    }
}