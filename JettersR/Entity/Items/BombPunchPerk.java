package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class BombPunchPerk extends Item
{
    public BombPunchPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.bombPunchAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.bombPunch = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new BombPunchPerk((int)x, (int)y, (int)z);
    // }
}
