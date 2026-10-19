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
	static int counter = 0;
    private int score;
    private RocketShip rocket;
    // private Obstacle[] obstacles;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
	private boolean gameOver;
	private Round round;

	public AsteroidShooter() {
		super("Asteroid Shooter", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);
		round = new Round();
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
		if (gameOver) {
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
				rocket.rotate(5); // rotate left by 5 degrees
				break;
			case KeyEvent.VK_RIGHT:
				rocket.rotate(-5); // rotate right by 5 degrees
				break;
			case KeyEvent.VK_SPACE:
				rocket.shoot();
				break;
		}
	}

	@Override // do not remove this method, it is required for KeyListener
	public void keyReleased(KeyEvent e) {}

	@Override // do not remove this method, it is required for KeyListener
	public void keyTyped(KeyEvent e) {}

	private void addAsteroid() {
		Polygon shape = new Polygon(
			new Point[] { new Point(0, 0), new Point(10, 0), new Point(5, 10) },
			new Point(0, 0), 0);
		asteroids.add(new Asteroid(new Polygon[] {shape}, Math.random() * width, Math.random() * height));
    }

    // private void addObstacle() {
    //     // implementation
    // }

    private void endGame() {
        // Implementation
    }

    private void addScore() {
        // Implementation
    }

	public static void main(String[] args) {
		AsteroidShooter a = new AsteroidShooter();
		a.repaint();
	}

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);
		// Game's constructor can trigger painting before initialization finishes.
		if (round == null) {
			return;
		}
		round.update();
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
	}

}
