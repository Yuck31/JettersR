package JettersR;

/**
 * This is the Class that manages user input via mouse (their isn't that much of a use for it right now...).
 *
 * author: Luke Sullivan
 * Last Edit: 9/21/2019
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener
{
    private static int mouseX = -1;
    private static int mouseY = -1;
    private static int mouseB = -1;
    
    public static int pressed = 0;
    
    public static int getX()
    {
        return mouseX;
    }
    public static int getY()
    {
        return mouseY;
    }
    public static int getButton()
    {
        return mouseB;
    }
    
    public void mouseDragged(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public void mouseClicked(MouseEvent e)
    {
        //Click
    }
    
    public void mouseEntered(MouseEvent e)
    {
    
    }

    
    public void mouseExited(MouseEvent e)
    {
    
    }
    
    public void mousePressed(MouseEvent e)
    {
        mouseB = e.getButton();
        pressed++;
    }
    
    public void mouseReleased(MouseEvent e)
    {
        mouseB = -1;
        pressed = 0;
    }
}
