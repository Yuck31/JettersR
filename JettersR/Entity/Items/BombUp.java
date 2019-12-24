package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class BombUp extends Item
{    
    public BombUp(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.bombUpAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.bombs++;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new BombUp((int)x, (int)y, (int)z);
    // }
}
