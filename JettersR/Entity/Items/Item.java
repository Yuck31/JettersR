package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.Entity.*;
import JettersR.Entity.Mob.*;
public class Item extends Entity
{
    public AudioContents itemCollect = AudioManager.sounds_itemCollect;
    public byte spawnTime = 0;
    public byte time = 0;
    public boolean breakable = true;
    boolean spawning = true;

    public AnimatedSprite spawn = new AnimatedSprite(SpriteSheet.itemSpawnAnim, 36, 36, 3, 2, false);
    public AnimatedSprite itemAnim = new AnimatedSprite(SpriteSheet.bombUpAnim, 32,32,12, 6);
    public AnimatedSprite collect = new AnimatedSprite(SpriteSheet.itemCollectAnim, 36, 36, 3, 4);
    public AnimatedSprite burn = new AnimatedSprite(SpriteSheet.itemBurnAnim, 40, 40, 6, 3);
    public AnimatedSprite crush = new AnimatedSprite(SpriteSheet.itemCrushAnim, 64, 64, 19, 2);
    public Item(int x, int y, int z)
    {
        super(x,y,z);
        spawn();
    }

    @Override
    public String getClassName(){return "Item";}

    public static String className(){return "Item";}

    public void spawn()
    {
        bounds.x = 1;
        bounds.y = 2;
        bounds.width = 29;
        bounds.height = 29;

        collected = false;
        breakable = true;
        spawning = true;
        spawnTime = 0;
        itemAnim.resetAnim();
        spawn.resetAnim();
    }

    public void Destroy()
    {
        if(spawning){return;}
        if(destroyFlag == false && !collected && !crushFlag)
        {
            x = x - 5;
            y = y - 4;
            bounds.width = 0;
            bounds.height = 0;
        }
        breakable = false;
        destroyFlag = true;
    }

    public void Crush()
    {
        if(spawning){return;}
        if(crushFlag == false && !destroyFlag && !collected)
        {
            x = x - 1;
            y = y - 30;
            bounds.width = 0;
            bounds.height = 0;
        }
        breakable = false;
        crushFlag = true;
    }

    public void collect()
    {
        if(spawning){return;}
        if(collected == false)
        {
            Game.am.add(itemCollect);
            x = x - 2;
            y = y - 4;
            bounds.width = 0;
            bounds.height = 0;
        }
        collected = true;
    }

    public void update()
    {
        if(!spawning)
        {
            if(collected == true)
            {
                collect.update();
                time++;
                if(time >= 11)
                {
                    remove();
                }
            }
            else if(destroyFlag == true)
            {
                burn.update();
                time++;
                if(time >= 17)
                {
                    remove();
                }
            }
            else if(crushFlag == true)
            {
                crush.update();
                time++;
                if(time >= 37)
                {
                    remove();
                }
            }
            else{itemAnim.update();}
        }
        if(spawnTime < 6)
        {
            spawn.update();
            spawnTime++;
            if(spawnTime >= 2){spawning = false;}
        }
    }

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
        if(spawning){return;}
        hopping = true;
        this.dirX = dirX;
        this.dirY  = dirY;
        this.forced = forced;
        this.forceMulti = forceMulti;

        x += dirX*2*forceMulti;
        y += dirY*2*forceMulti;

        if(forced && hopTime <= 0)
        {
            this.dirX = (this.dirX);
            this.dirY  = (this.dirY);
            this.forced = false;
            this.forceMulti = 1;
        }
    }

    public void setSprite()
    {
        if(!spawning)
        {
            if(collected == true)
            {
                sprite = collect.getSprite();
            }
            else if(destroyFlag == true)
            {
                sprite = burn.getSprite();
            }
            else if(crushFlag == true)
            {
                sprite = crush.getSprite();
            }
            else{sprite = itemAnim.getSprite();}
        }
    }

    public void render(Screen screen)
    {
        setSprite();
        if(spawnTime >= 2){screen.renderSprite((int)x, (int)((y - (z*16))-bounceHeight[bounceNum]), sprite, true);}
        if(spawnTime < 6){screen.renderSprite((int)(x-2), (int)(((y-2) - (z*16))-bounceHeight[bounceNum]), spawn.getSprite(), true);}
    }

    public void renderScale(Screen screen, float scale)
    {
        setSprite();
        if(spawnTime >= 2){screen.renderSprite((int)(x*scale), (int)((y - (z*16))-bounceHeight[bounceNum]), Sprite.scale(sprite,scale), true);}
        if(spawnTime < 6){screen.renderSprite((int)((x-2)*scale), (int)((((y-2)- (z*16))-bounceHeight[bounceNum])*scale), Sprite.scale(spawn.getSprite(),scale), true);}
    }
}