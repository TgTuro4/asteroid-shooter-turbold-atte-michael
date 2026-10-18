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
	private int height;
    private int width;
    private int score;
    private RocketShip rocket;
    // private Obstacle[] obstacles;
    private ArrayList<Asteroid> asteroids;
	private boolean gameOver;

	public AsteroidShooter() {
		super("Asteroid Shooter", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);
	}

	class Round {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
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
		
    }

    private void addObstacle() {
        // implementation
    }

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

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter, 10, 10);
	}

}