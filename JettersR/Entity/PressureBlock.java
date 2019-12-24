package JettersR.Entity;
/**
 * This is the class that represents the pressure blocks that appear during sudden death.
 * 
 * Author: Luke Sullivan
 * 12/21/19
 */
import JettersR.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.Items.*;

public class PressureBlock extends Entity
{
    private boolean falling = false;

    private int spawnTime = 0;
    private int landTime = 0;
    private int hazardTime = 0;

    public int targetY = 0;

    public AnimatedSprite spawning = new AnimatedSprite(SpriteSheet.SDBspawningAnim ,58,58,3, 2);
    public AnimatedSprite landing = new AnimatedSprite(SpriteSheet.SDBlandingAnim ,58,58,3, 2);

    public PressureBlock(int x, int y, int z)
    {
        super(x,y,z);
        falling = true;
        this.y = y - 300;
        this.z = z;
        this.targetY = y;

        bounds.x = 14;
        bounds.y = 19;
        bounds.width = 29;//If this was 31, the player would be able to clip through th block.
        bounds.height = 29;
    }

    public static String className(){return "PressureBlock";}

    public void update()
    {
        if(falling && spawnTime < 6){spawnTime++; spawning.update();}
        else if(!falling && landTime < 8){landTime++; landing.update();}

        if(falling){y = y + 5;}
        if(y >= targetY && falling){y = targetY; falling = false; z = z - 1;}

        if(!falling && hazardTime < 4){hazardTime++; checkCollisions();}
        else if(!falling)
        {
            bounds.width = 0;
            bounds.height = 0;
        }
    }

    public void checkCollisions()
    {
        int tx = (((int)(x)+bounds.x+(bounds.width/2)) / 32);
        int ty = (((int)(y)+bounds.y+(bounds.height/2)) / 32);
        
        level.setTile(tx, ty, z, level.wallTiles, 0xFF000001);
        level.destroyTile(tx, ty, z, level.floorTiles);
        
        if(entityCollision(0,0,z) != null)
        {
            if(entityCollision(0,0,z) instanceof Projectile)
            {
                entityCollision(0, 0, z).Destroy();
            }
            else if(!(entityCollision(0,0,z) instanceof Player))
            {
                entityCollision(0, 0, z).remove();
            }
        }
        for(Player i : level.players)
        {
            if(i.equals(this))
            {
                continue;
            }
            if(i.getCollisionBounds(0,0).intersects(getCollisionBounds(0, 0)) && i.z == this.z)
            {
                if(!i.imMobile && !i.dead){i.die();}
                //break;
            }
        }
        if(itemDetect(0,0,z) != null)
        {
            itemDetect(0,0,z).Crush();
        }
    }

    public boolean hazard()
    {
        if(!falling && hazardTime < 4){return true;}
        else{return false;}
    }

    public boolean solid()
    {
        if(!falling){return true;}
        else{return false;}
    }

    public void setSprite()
    {
        if(falling)
        {
            if(spawnTime < 6){sprite = spawning.getSprite();}
            else{sprite = Sprite.pressureBlock0;}
        }
        else if(!falling)
        {
            if(landTime < 8){sprite = landing.getSprite();}
            if(landTime == 8){remove();}
        }
    }

    public void render(Screen screen)
    {
        setSprite();
        if(falling){screen.renderSprite((int)x,(int)(y) - (z*16) + 16, sprite, true);}
        else{screen.renderSprite((int)x,(int)(y) - (z*16), sprite, true);}
        //screen.drawRect((int)(x+bounds.x), (int)(y+bounds.y), bounds.width, bounds.height, 0xFFFF1111, true);
    }

    public void renderScale(Screen screen, float scale)
    {
        setSprite();
        if(falling){screen.renderSprite((int)(x*scale),(int)((y-(z*16)+16)*scale), Sprite.scale(sprite, scale), true);}
        else{screen.renderSprite((int)(x*scale),(int)((y-(z*16))*scale), Sprite.scale(sprite, scale), true);}
    }
}