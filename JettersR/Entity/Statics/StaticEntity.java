package JettersR.Entity.Statics;

import JettersR.*;
import JettersR.Entity.*;

public class StaticEntity extends Entity
{
    public StaticEntity(int x, int y, int z, int width, int height)
    {
        super(x, y, z);
        this.width = width;
        this.height = height;
    }
    
    public void Destroy(){}

    public void update(){}

    public void render(Screen screen)
    {
        //screen.drawRect((int)(x+bounds.x)-16, (int)(y+bounds.y), bounds.width, bounds.height, 0xFF00FFE6, true);
    }
    
    public boolean solid()
    {
        return false;
    }
    
    public boolean breakable()
    {
        return false;
    }
}