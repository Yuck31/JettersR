package JettersR.Entity.Statics;

import JettersR.*;
import JettersR.Entity.*;
import JettersR.Entity.Items.*;
import java.awt.Rectangle;
import java.util.Random;

public class SoftBlock extends StaticEntity
{
    public boolean flag = false;
    public boolean itemDropped = false;
    public byte time = 0;
    public byte animTime = 0;
    public AnimatedSprite destroy = new AnimatedSprite(SpriteSheet.softBlockDestroyAnim,40,40,6, 3, false);
    public SoftBlock(int x, int y, int z)
    {
        super(x,y,z, 31, 31);
        //super(x,y,z);
        this.sprite = Sprite.softBlock;

        bounds.x = -1;
        bounds.y = 0;
        bounds.width = 32;
        bounds.height = 31;
    }
    
    @Override
    public String getClassName(){return "SoftBlock";}
    public static String className(){return "SoftBlock";}

    public void Destroy()
    {
        flag = true;
    }

    public void update()
    {
        if(flag == true){time++;}
        if(flag && animTime <= 15)
        {
            destroy.update();
            animTime++;
        }
        dropItem();
    }
    
    public void dropItem()
    {
        if(time >= 29 && itemDropped == false)
        {
            int dropAnItem = random.nextInt(2);
            if(dropAnItem == 1)
            {
                int dropWhatItem = random.nextInt(58);
                if(dropWhatItem <= 5)
                {
                    level.add(new BombUp((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 10)
                {
                    level.add(new FireUp((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 14)
                {
                    level.add(new SpeedUp((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 16)
                {
                    level.add(new FullFire((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 20)
                {
                    level.add(new Heart((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 24)
                {
                    level.add(new BombKickPerk((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 28)
                {
                    level.add(new BombThrowPerk((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 30)
                {
                    level.add(new SuperPowerGlove((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 34)
                {
                    level.add(new BombPunchPerk((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 38)
                {
                    level.add(new PowerBombPerk((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 40)
                {
                    level.add(new RemoteControl((int)x, (int) y, z));
                }
                else if(dropWhatItem <= 43)
                {
                    level.add(new PierceBombPerk((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 45)
                {
                    level.add(new ClusterBombPerk((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 47)
                {
                    level.add(new DangerousBombPerk((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 49)
                {
                    level.add(new BomberShootPerk((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 51)
                {
                    level.add(new TriBombUp((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 53)
                {
                    level.add(new TriFireUp((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 55)
                {
                    level.add(new TriSpeedUp((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 56)
                {
                    level.add(new SoftBlockPass((int)x,(int)y, z));
                }
                else if(dropWhatItem <= 57)
                {
                    level.add(new BombPass((int)x,(int)y, z));
                }
            }
            else
            {
                //Insert Test code here
                //level.add(new SkullItem((int)x, (int) y, z, Disease.OADS));
            }
            itemDropped = true;
        }
        else if(itemDropped){remove();}
    }

    public void render(Screen screen)
    {
        if(flag)
        {
            sprite = destroy.getSprite();
            screen.renderSprite((int)x-4, (int)((y-3)-(z*16)), sprite, true);
        }
        else{
            screen.renderSprite((int)x, (int)(y+1-(z*16)), sprite, true);
        }
        //screen.drawRect((int)(x+bounds.x), (int)(y+bounds.y), bounds.width, bounds.height, 0xFF00FFE6, true);
    }
    
    public void renderScale(Screen screen, float scale)
    {
        if(flag)
        {
            sprite = destroy.getSprite();
            screen.renderSprite((int)((x-4)*scale), (int)(((y-3)-(z*16))*scale), Sprite.scale(sprite, scale), true);
        }
        else{
            screen.renderSprite((int)(x*scale), (int)((y+1-(z*16))*scale), Sprite.scale(sprite, scale), true);
        }
        //screen.drawRect((int)(x+bounds.x), (int)(y+bounds.y), bounds.width, bounds.height, 0xFF00FFE6, true);
    }

    public boolean solid()
    {
        return true;
    }

    public boolean breakable()
    {
        return true;
    }
}