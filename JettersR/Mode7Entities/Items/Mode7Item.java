package JettersR.Mode7Entities.Items;

import JettersR.*;
import JettersR.Mode7Entities.*;

public class Mode7Item extends Mode7Entity
{
    public boolean destroyFlag = false;
    public boolean crushFlag = false;
    public boolean collectFlag = false;
    public boolean breakable = true;
    public AnimatedSprite collect = new AnimatedSprite(SpriteSheet.itemCollectAnim,36,36,3, 4);
    public AnimatedSprite burn = new AnimatedSprite(SpriteSheet.itemBurnAnim,40,40,6, 3);
    public AnimatedSprite crush = new AnimatedSprite(SpriteSheet.itemCrushAnim,64,64,19, 2);
    public Mode7Item(int x, int y, int z)
    {
        super(x,y,z);
        
        bounds.x = 0;
        bounds.y = 1;
        bounds.width = 31;
        bounds.height = 31;
    }

    public void Destroy(){}
    
    public void Crush(){}
    
    public void collect(){}
    
    public void update(){}
    
    public boolean breakable()
    {
        return breakable;
    }
    
    public boolean solid()
    {
        return false;
    }
    
    public void hop(int dirX, int dirY, boolean forced, int forceMulti)
    {
        hopping = true;
        this.dirX = dirX;
        this.dirY  = dirY;
        this.forced = forced;
        this.forceMulti = forceMulti;

        x += dirX*2*forceMulti;
        y += dirY*2*forceMulti;
        bounds.x += dirX*2*forceMulti;
        bounds.y += dirY*2*forceMulti;

        if(forced && hopTime <= 0)
        {
            this.dirX = (this.dirX);
            this.dirY  = (this.dirY);
            this.forced = false;
            this.forceMulti = 1;
        }
    }

    public void render(Screen screen)
    {
        screen.renderSprite((int)x, (int)y, sprite, true);
        screen.drawRect((int)(x+bounds.x), (int)(y+bounds.y), bounds.width, bounds.height, 0xFF00FFE6, true);
    }
}