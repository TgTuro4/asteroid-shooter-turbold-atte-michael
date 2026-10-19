package game;

/*
CLASS: AsteroidShooter
DESCRIPTION: Extending Game, AsteroidShooter is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class AsteroidShooter extends Game implements KeyListener {
	/** Current accumulated player score. */
    private int score;

    /** Player controlled rocket ship entity. */
    private RocketShip rocket;

    /** Collection of active asteroids currently present on screen. */
    private ArrayList<Asteroid> asteroids = new ArrayList<>();

    /** Flag indicating whether the game has ended. */
    private boolean gameOver;

    /** Active round manager instance handling time limits and round advancement. */
    private Round round;

	/**
     * Constructs and initializes the Asteroid Shooter game environment, creating the player rocket,
     * starting round parameters, and setting up input listeners.
     */
	public AsteroidShooter() {
		super("Asteroid Shooter", 800, 600);
		Polygon shape = new Polygon(
			new Point[] { new Point(0, 0), new Point(30, 15), new Point(0, 30) },
			new Point(0, 0), 0);
		rocket = new RocketShip(new Polygon[] {shape}, width / 2.0, height / 2.0, 100);
		round = new Round();
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);
	}

	/**
     * Inner class managing individual game rounds, time limits, elapsed timer tracking,
     * and spawning asteroid scaling rules per round.
     */
	class Round {

		/** Current round number, starting at 1. */
        private int roundNumber;

        /** Total duration limit for each round in seconds. */
        private final int timeLimit = 60; // seconds

        /** Remaining time left in the current round in seconds. */
        private int timeRemaining;

        /** Accumulated elapsed time in milliseconds for calculating active round progression. */
        private double elapsedMilliseconds;

		/**
         * Constructs a new Round instance starting at round 1.
         */
		public Round() {
			roundNumber = 1;
			startRound();
		}

		/**
         * Resets timers, clears existing asteroids, and spawns a new set scaled to the current round.
         */
		private void startRound() {
			timeRemaining = timeLimit;
			elapsedMilliseconds = 0.0;
			asteroids.clear();
			// Start with three asteroids and add one more each round.
			for (int i = 0; i < roundNumber + 2; i++) {
				addAsteroid();
			}
		}

		/**
         * Updates elapsed round time and handles transitions when the time limit expires.
         */
		public void update() {
			if (gameOver) {
				return;
			}
			// Approximate each update as 10 milliseconds, matching Game's delay.
			elapsedMilliseconds += 10.0;
			int elapsedSeconds = (int) (elapsedMilliseconds / 1000.0);
			timeRemaining = Math.max(0, timeLimit - elapsedSeconds);
			if (timeRemaining == 0) {
				nextRound();
			}
		}

		/**
         * Advances the game to the next round increment and spawns corresponding asteroids if the game is active.
         */
		public void nextRound() {
			if (!gameOver) {
				roundNumber++;
				startRound();
			}
		}

		/**
         * Gets the current round number.
         *
         * @return Current round number.
         */
		public int getRoundNumber() {
			return roundNumber;
		}

		/**
		 * Gets the remaining time in seconds for the current round.
		 *
		 * @return Remaining time in seconds.
		 */
		public int getTimeRemaining() {
			return timeRemaining;
		}
	}

	/**
     * Handles keyboard press events for controlling movement, rotation, and shooting actions of the rocket.
     *
     * @param e {@link KeyEvent} containing detail about key pressed.
     */
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (gameOver || rocket == null) {
			return;
		}
		switch (key) {
			case KeyEvent.VK_UP:
				rocket.moveForward();
				break;
			case KeyEvent.VK_DOWN:
				rocket.moveBackward();
				break;
			case KeyEvent.VK_LEFT:
				rocket.rotate(-12); // rotate left by 5 degrees
				break;
			case KeyEvent.VK_RIGHT:
				rocket.rotate(12); // rotate right by 5 degrees
				break;
			case KeyEvent.VK_SPACE:
				rocket.shoot();
				System.out.println(rocket.getLaserList().size());
				break;
		}
		wrapAround(rocket);
	}

	/**
     * Required by {@link KeyListener}. Invoked when a key is released.
     *
     * @param e {@link KeyEvent} containing key release information.
     */
	@Override // do not remove this method, it is required for KeyListener
	public void keyReleased(KeyEvent e) {}

	/**
     * Required by {@link KeyListener}. Invoked when a key is typed.
     *
     * @param e {@link KeyEvent} containing key typing information.
     */
	@Override // do not remove this method, it is required for KeyListener
	public void keyTyped(KeyEvent e) {}

	/**
     * Spawns a new asteroid near the screen edges with random trajectory vector and velocity.
     */
	private void addAsteroid() {
		Polygon shape = new Polygon(
			new Point[] { new Point(0, 0), new Point(30, 0), new Point(15, 30) },
			new Point(0, 0), 0);
		// Spawn near an edge so a new wave does not start on the rocket.
		double x = Math.random() * (width - 30);
		double y = Math.random() < 0.5 ? 0 : height - 30;
		if (rocket != null && Math.abs(x - rocket.position.x) < 60
				&& Math.abs(y - rocket.position.y) < 60) {
			y = y == 0 ? height - 30 : 0;
		}
		Asteroid asteroid = new Asteroid(new Polygon[] {shape}, x, y);
		double direction = Math.random() * 2 * Math.PI;
		double speed = 0.5 + Math.random() * 0.5;
		asteroid.setVelocity(Math.cos(direction) * speed, Math.sin(direction) * speed);
		asteroids.add(asteroid);
	}

	/**
     * Sets the internal state flag indicating that the game has ended.
     */
	private void endGame() {
		gameOver = true;
	}

	/**
     * Increments score counter by 100 points.
     */
	private void addScore() {
		score += 100;
	}

	/**
     * Wraps a sprite's position around screen boundaries when passing edge thresholds.
     *
     * @param sprite Target {@link Sprite} to wrap within visible screen dimensions.
     */
	private void wrapAround(Sprite sprite) {
		if (sprite.position.x < 0) {
			sprite.position.x += width;
		} else if (sprite.position.x >= width) {
			sprite.position.x -= width;
		}
		if (sprite.position.y < 0) {
			sprite.position.y += height;
		} else if (sprite.position.y >= height) {
			sprite.position.y -= height;
		}
	}

	/**
     * Updates positions, lifetimes, active round timers, collision states 
	 * between lasers/asteroids/rocket, and game over triggers.
     */
	private void updateGame() {
		if (gameOver) {
			return;
		}
		if (rocket.getHealth() <= 0) {
			endGame();
			return;
		}
		round.update();
		rocket.updateLasers();
		Rectangle rocketHitbox = rocket.getHitbox();
		for (int i = asteroids.size() - 1; i >= 0; i--) {
			Asteroid asteroid = asteroids.get(i);
			asteroid.rotate(5);
			asteroid.position.x += asteroid.xVelocity;
			asteroid.position.y += asteroid.yVelocity;
			wrapAround(asteroid);
			Rectangle asteroidHitbox = asteroid.getHitbox();
			boolean destroyed = false;
			for (RocketShip.Laser laser : rocket.getLaserList()) {
				if (laser.isActive() && asteroidHitbox.intersects(
						laser.getPosition().x, laser.getPosition().y, 4, 4)) {
					laser.deactivate();
					asteroids.remove(i);
					addScore();
					destroyed = true;
					break;
				}
			}
			if (!destroyed && rocketHitbox.intersects(asteroidHitbox)) {
				asteroids.remove(i);
				rocket.takeDamage(25);
				if (rocket.getHealth() <= 0) {
					endGame();
					return;
				}
			}
		}
		if (asteroids.isEmpty()) {
			round.nextRound();
		}
	}

	public static void main(String[] args) {
		AsteroidShooter a = new AsteroidShooter();
		a.repaint();
	}

	/**
     * Paints game entities, update status, active lasers, HUD indicators, and game over overlays.
	 * This is also where a lambda expression is used for iterating over the asteroids collection
	 * to render each asteroid.
     *
     * @param brush {@link Graphics} context used for drawing frame visual elements.
     */
	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);
		// Game's constructor can trigger painting before initialization finishes.
		if (round == null || rocket == null) {
			return;
		}
		updateGame();
		brush.setColor(Color.white);
		asteroids.forEach(asteroid -> asteroid.paint(brush));
		rocket.paint(brush);
		for (RocketShip.Laser laser : rocket.getLaserList()) {
			if (laser.isActive()) {
				laser.paint(brush);
			}
		}

		brush.setColor(Color.white);
		brush.drawString("Score: " + score, 20, 30);
		brush.drawString("Round: " + round.getRoundNumber(), 20, 50);
		brush.drawString("Time: " + round.getTimeRemaining(), 20, 70);
		brush.drawString("Health: " + rocket.getHealth(), 20, 90);
		if (gameOver) {
			brush.drawString("Game Over! Final score: " + score, width / 2 - 80, height / 2);
		}
	}

}
