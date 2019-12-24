package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class BombThrowPerk extends Item
{
    public BombThrowPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.bombThrowAnim,32,32,12, 6);
    }

    public void collect(Player player)
    {
        if(player.bombThrow < 2)
        {
            player.bombThrow = 1;
        }
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new BombThrowPerk((int)x, (int)y, (int)z);
    // }
}
