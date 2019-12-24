package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class BombKickPerk extends Item
{
    public BombKickPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.bombKickAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.bombKick = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new BombKickPerk((int)x, (int)y, (int)z);
    // }
}
