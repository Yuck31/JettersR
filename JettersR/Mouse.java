package JettersR;
/**
 * This is the Class that manages user input via mouse (their isn't that much of a use for it right now...).
 *
 * author: Luke Sullivan
 * Last Edit: 12/31/2019
 */
import JettersRLevelEditor.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Rectangle;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener
{
    public Screen screen;
    
    private double mouseX = -1;
    private double mouseY = -1;
    private int mouseB = -1;
    private int mouseCB = -1;
    private boolean pressed = false;

    private int mouseR = 0;
    
    public static int lClick = 1, mClick = 2, rClick = 3;
    
    public Mouse(Screen screen)
    {
        this.screen = screen;
    }
    
    public void update()
    {
        mouseR = 0;
    }

    public double getX()
    {
        return ((mouseX) / Game.frame.getWidth()) * screen.width;
    }

    public double getY()
    {
        int offset = 0;
        if(LevelEditorMain.menuBar != null){offset = 16;}
        return ((mouseY) / (Game.frame.getHeight()-32-offset)) * screen.height;
    }

    public int getButton()
    {
        return mouseB;
    }
    
    public int getWheelRot()
    {
        return mouseR;
    }
    
    public boolean buttonPressed(int button)
    {
        if(mouseCB == button && !pressed)
        {
            pressed = true;
            return true;
        }
        else{return false;}
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
        // if(mouseCB != e.getButton())
        // {
            // mouseCB = e.getButton();
        // }
        // else{pressed = true;}
        mouseCB = -1;
        pressed = false;
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
        if(mouseCB != e.getButton() && !pressed)
        {
            mouseCB = e.getButton();
        }
        else{pressed = true;}
    }

    public void mouseReleased(MouseEvent e)
    {
        mouseB = -1;
        mouseCB = -1;
        pressed = false;
    }
    
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        mouseR = e.getWheelRotation();
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle((int)getX(), (int)getY(), 1, 1);
    }
}
