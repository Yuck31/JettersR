package JettersR.Entity;

import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Entity.Mob.*;

public class Projectile extends Entity
{
    protected final double xOrigin, yOrigin;
    protected double angle;
    public boolean shot = false;
    
    public Projectile(int x, int y, int z, double dir)
    {
        super(x,y,z);
        this.x = x;
        this.y = y;
        this.z = z;
        xOrigin = x;
        yOrigin = y;
        angle = dir;
    }
    
    public Projectile(int x, int y, int z)
    {
        super(x,y,z);
        this.x = x;
        this.y = y;
        this.z = z;
        xOrigin = x;
        yOrigin = y;
    }
    
    @Override
    public String getClassName(){return "Projectile";}
    public static String className(){return "Projectile";}
    
    public Sprite getSprite()
    {
        return sprite;
    }
    
    public int getSpriteSize()
    {
        return sprite.SIZE;
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
}