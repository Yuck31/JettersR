package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class MergerPerk extends Item
{
    public MergerPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.mergerAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.canMerge = true;
        player.mergeTimer = 1500;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new MergerPerk((int)x, (int)y, (int)z);
    // }
}
