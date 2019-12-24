package JettersR.Mode7Entities;

import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Mode7Entities.Mobs.*;
import JettersR.Entity.Mob.*;
import JettersR.Mode7Entities.Items.*;
import java.util.Random;
import java.awt.Rectangle;

public abstract class Mode7Entity//An Mode7Entity in this project is ANYTHING that isn't a tile
{
    public enum Direction
    {
        UP, DOWN, LEFT, RIGHT, NONE;
    }
    protected Mode7Level mode7Level;
    
    AudioPlayer audioPlayer;
    public double x, y;
    public int z;
    public double nx, ny;
    public int width, height;
    public Sprite sprite;
    private boolean removed = false;
    
    public Direction dir;
    public int dirX;
    public int dirY;
    public boolean hopping;
    public boolean rolling;
    public int hopTime;
    public boolean forced;
    public int forceMulti;
    
    public boolean destroyFlag = false;
    public boolean collectFlag = false;
    protected double speed, range, damage;

    public Rectangle bounds;

    public Mode7Entity(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;

        bounds = new Rectangle(0, 0, width, height);//Default Rectangle bounds
    }

    public Rectangle getCollisionBounds(double xOffset, double yOffset)
    {
        return new Rectangle((int)(x + bounds.x + xOffset), (int)(y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public void update(){}

    public void render(Screen screen){}

    public void remove()//Remove entity from level
    {
        removed = true;//Mode7Entity list in Level class specifically checks for this boolean
    }

    public int getX()
    {
        return (int)x;
    }

    public int getY()
    {
        return (int)y;
    }

    public int getZ()
    {
        return z;
    }

    public int getBoundsX()
    {
        return bounds.x;
    }

    public int getBoundsY()
    {
        return bounds.y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Sprite getSprite()
    {
        return sprite;
    }
    
    public boolean solid()
    {
        return false;
    }

    public boolean breakable()
    {
        return false;
    }

    public boolean hazard()
    {
        return false;
    }

    public void updateVelocity(int xa, int ya){}

    public void move(){};
    public void move(int xa, int ya){}

    public void die(){}

    public void Destroy(){}

    public void collect(){}

    public boolean isRemoved()
    {
        return removed;
    }
    
    public void init(Mode7Level mode7Level)
    {
        this.mode7Level = mode7Level;
    }
}