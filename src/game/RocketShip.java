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

    public void move() {
        super.xPos += 1;
        updateLasers();
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
        List<Laser> active = new ArrayList<>();
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
            
            this.laserX = super.xPos;
            this.laserY = super.yPos;
            this.laserHeading = super.heading;
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
}