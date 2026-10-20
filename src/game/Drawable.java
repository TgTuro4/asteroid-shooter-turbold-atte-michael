package game;

import java.awt.*;

/**
 * The Drawable interface contains a paint method that requires implementing classes
 * to define how they will be rendered on screen using graphics. This interface
 * should be used for all classes for elements that need to appear on screen.
 */
public interface Drawable {

    /**
     * Renders the object with the provided {@link Graphics} brush.
     * 
     * @param g the {@link Graphics} brush/context used for drawing the object.
     */
    public void paint(Graphics g);

}
