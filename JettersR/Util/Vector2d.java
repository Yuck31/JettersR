package JettersR.Util;
/**
 * 
 *
 * @author: Luke Sullivan
 * @1/3/20
 */
public class Vector2d//2d Double vector
{
    public double x,y;
    
    public Vector2d()
    {
        set(0,0);
    }
    
    public Vector2d(Vector2d vector)
    {
        set(vector.x, vector.y);
    }
    
    public Vector2d(double x, double y)
    {
        set(x,y);
    }
    
    public void set(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    
    public Vector2d add(Vector2i vector)
    {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }
    
    public Vector2d subtract(Vector2i vector)
    {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }
    
    public Vector2d SetX(int x)
    {
        this.x = x;
        return this;
    }
    public Vector2d SetY(int y)
    {
        this.y = y;
        return this;
    }
}
