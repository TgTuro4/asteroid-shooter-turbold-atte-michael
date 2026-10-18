package game;
import java.util.ArrayList;

public class RocketShip extends Sprite {
    private int health;
    private int maxHealth;
    private ArrayList<Laser> laserList;

    public RocketShip(Polygon[] components, double xPos, double yPos, int maxHealth) {
        super(components, xPos, yPos);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.laserList = new ArrayList<>();
    }

    public void shoot() {
        Laser newLaser = new Laser(10, 25, 60); 
        laserList.add(newLaser);
    }

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

    public int getHealth() {
        return health;
    }

    public int takeDamage(int amount) {
        this.health -= amount;
        if (this.health < 0) {
            this.health = 0;
        }
        return this.health;
    }

    public Laser[] getActiveLasers() {
        ArrayList<Laser> active = new ArrayList<>();
        for (Laser l : laserList) {
            if (l.isActive()) {
                active.add(l);
            }
        }
        return active.toArray(new Laser[0]);
    }
    
    public class Laser {
        private int speed;
        private int damage;
        private boolean isShot;
        private int lifetime;
        private double laserX;
        private double laserY;
        private double laserHeading;

        public Laser(int speed, int damage, int lifetime) {
            this.speed = speed;
            this.damage = damage;
            this.lifetime = lifetime;
            this.isShot = true;
            
            this.laserX = RocketShip.this.xPos;
            this.laserY = RocketShip.this.yPos;
            this.laserHeading = RocketShip.this.heading;
        }

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

        public boolean isActive() {
            return isShot && !hasExpired();
        }

        public boolean hasHit() {
            return false;
        }

        public boolean hasExpired() {
            return lifetime <= 0;
        }

        public void deactivate() {
            this.isShot = false;
        }

        public int getDamage() {
            return damage;
        }

        public double getLaserX() {
            return laserX;
        }

        public double getLaserY() {
            return laserY;
        }
    }

    @Override 
    public void moveForward() {
        super.xPos += Math.cos(Math.toRadians(heading)) * 1; // move forward by 5 units
        super.yPos += Math.sin(Math.toRadians(heading)) * 1; // move forward by 5 units
        updateLasers();
    }

    @Override 
    public void moveBackward() {
        super.xPos -= Math.cos(Math.toRadians(heading)) * 1; // move backward by 5 units
        super.yPos -= Math.sin(Math.toRadians(heading)) * 1; // move backward by 5 units
        updateLasers();
    }

    @Override
    public void rotate(double deg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotate'");
    }

    @Override
    public void onCollision() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }
}
