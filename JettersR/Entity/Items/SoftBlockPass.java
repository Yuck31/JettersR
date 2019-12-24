package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class SoftBlockPass extends Item
{
    public SoftBlockPass(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.softBlockPassAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.softBlockPass = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new SoftBlockPass((int)x, (int)y, (int)z);
    // }
}
