package JettersR.Mode7Entities;

import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Mode7Entities.Mobs.*;
import JettersR.Entity.Mob.*;

public abstract class Mode7Projectile extends Mode7Entity
{
    protected final double xOrigin, yOrigin;
    protected double angle;
    public Sprite sprite;
    public int hopTime;
    public boolean hopping = false;
    public boolean rolling = false;
    public boolean shot = false;
    //protected double nx, ny;
    protected double speed, range, damage;
    
    public Mode7Projectile(int x, int y, int z, double dir)
    {
        super(x,y,z);
        this.x = x;
        this.y = y;
        this.z = z;
        xOrigin = x;
        yOrigin = y;
        angle = dir;
    }
    
    public Mode7Projectile(int x, int y, int z)
    {
        super(x,y,z);
        this.x = x;
        this.y = y;
        this.z = z;
        xOrigin = x;
        yOrigin = y;
    }
    
    public void update(){}
    
    public Sprite getSprite()
    {
        return sprite;
    }
    
    public int getSpriteSize()
    {
        return sprite.SIZE;
    }
    
    public void updateVelocity(int xa, int ya){}
    
    public void move(int xa, int ya){}
    
    public void pickUp(Player player){}
    
    public void roll(int dirX, int dirY){}
    
    public void hop(int dirX, int dirY, boolean forced, int forceMulti){}
    
    public void Throw(int dirX, int dirY, int throwMulti){}

    public void Destroy(){}
    
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
}