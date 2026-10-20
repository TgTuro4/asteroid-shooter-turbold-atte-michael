package game;

import java.awt.*;

public interface Drawable {

    /**
     * Renders the object with the provided {@link Graphics} brush.
     * 
     * @param g the {@link Graphics} brush/context used for drawing the object.
     */
    public void paint(Graphics g);

}
