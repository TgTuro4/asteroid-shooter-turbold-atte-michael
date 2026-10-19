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
	private int score;
	private RocketShip rocket;
	private ArrayList<Asteroid> asteroids = new ArrayList<>();
	private boolean gameOver;
	private Round round;

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

	class Round {
		private int roundNumber;
		private final int timeLimit = 60; // seconds
		private int timeRemaining;
		private double elapsedMilliseconds;

		public Round() {
			roundNumber = 1;
			startRound();
		}

		private void startRound() {
			timeRemaining = timeLimit;
			elapsedMilliseconds = 0.0;
			asteroids.clear();
			// Start with three asteroids and add one more each round.
			for (int i = 0; i < roundNumber + 2; i++) {
				addAsteroid();
			}
		}

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

		public void nextRound() {
			if (!gameOver) {
				roundNumber++;
				startRound();
			}
		}

		public int getRoundNumber() {
			return roundNumber;
		}

		public int getTimeRemaining() {
			return timeRemaining;
		}
	}

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
				rocket.rotate(-5); // rotate left by 5 degrees
				break;
			case KeyEvent.VK_RIGHT:
				rocket.rotate(5); // rotate right by 5 degrees
				break;
			case KeyEvent.VK_SPACE:
				rocket.shoot();
				break;
		}
		wrapAround(rocket);
	}

	@Override // do not remove this method, it is required for KeyListener
	public void keyReleased(KeyEvent e) {}

	@Override // do not remove this method, it is required for KeyListener
	public void keyTyped(KeyEvent e) {}

	private void addAsteroid() {
		Polygon shape = new Polygon(
			new Point[] { new Point(0, 0), new Point(30, 0), new Point(15, 30) },
			new Point(0, 0), 0);
		// Spawn near an edge so a new wave does not start on the rocket.
		double x = Math.random() * (width - 30);
		double y = Math.random() < 0.5 ? 0 : height - 30;
		if (rocket != null && Math.abs(x - rocket.xPos) < 60
				&& Math.abs(y - rocket.yPos) < 60) {
			y = y == 0 ? height - 30 : 0;
		}
		Asteroid asteroid = new Asteroid(new Polygon[] {shape}, x, y);
		double direction = Math.random() * 2 * Math.PI;
		double speed = 0.5 + Math.random() * 0.5;
		asteroid.setVelocity(Math.cos(direction) * speed, Math.sin(direction) * speed);
		asteroids.add(asteroid);
	}

	private void endGame() {
		gameOver = true;
	}

	private void addScore() {
		score += 100;
	}

	private void wrapAround(Sprite sprite) {
		if (sprite.xPos < 0) {
			sprite.xPos += width;
		} else if (sprite.xPos >= width) {
			sprite.xPos -= width;
		}
		if (sprite.yPos < 0) {
			sprite.yPos += height;
		} else if (sprite.yPos >= height) {
			sprite.yPos -= height;
		}
	}

	private Rectangle getHitbox(Sprite sprite) {
		Rectangle hitbox = null;
		for (Polygon component : sprite.getComponents()) {
			component.position = new Point(sprite.xPos, sprite.yPos);
			component.rotation = sprite.getHeading();
			java.awt.Polygon shape = new java.awt.Polygon();
			for (Point point : component.getPoints()) {
				shape.addPoint((int) point.x, (int) point.y);
			}
			if (hitbox == null) {
				hitbox = shape.getBounds();
			} else {
				hitbox.add(shape.getBounds());
			}
		}
		return hitbox == null ? new Rectangle() : hitbox;
	}

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
		Rectangle rocketHitbox = getHitbox(rocket);
		for (int i = asteroids.size() - 1; i >= 0; i--) {
			Asteroid asteroid = asteroids.get(i);
			asteroid.xPos += asteroid.xVelocity;
			asteroid.yPos += asteroid.yVelocity;
			wrapAround(asteroid);
			Rectangle asteroidHitbox = getHitbox(asteroid);
			boolean destroyed = false;
			for (RocketShip.Laser laser : rocket.getLaserList()) {
				if (laser.isActive() && asteroidHitbox.intersects(
						laser.getLaserX(), laser.getLaserY(), 4, 4)) {
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

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);
		// Game's constructor can trigger painting before initialization finishes.
		if (round == null || rocket == null) {
			return;
		}
		updateGame();
		brush.setColor(Color.white);
		for (Asteroid asteroid : asteroids) {
			asteroid.paint(brush);
		}
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
