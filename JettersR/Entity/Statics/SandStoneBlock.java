package JettersR.Entity.Statics;

import JettersR.*;
import JettersR.Entity.Items.*;
import java.awt.Rectangle;
import java.util.Random;

public class SandStoneBlock extends SoftBlock
{
    public SandStoneBlock(int x, int y, int z)
    {
        super(x,y,z);
        this.sprite = Sprite.sandStoneBlock;
        destroy = new AnimatedSprite(SpriteSheet.sandStoneBlockDestroyAnim,40,40,6, 3, false);
    }

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

    public boolean solid()
    {
        return true;
    }

    public boolean breakable()
    {
        return true;
    }
}