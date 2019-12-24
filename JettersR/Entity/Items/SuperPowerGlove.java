package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class SuperPowerGlove extends Item
{
    public SuperPowerGlove(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.superPowerGloveAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.bombThrow = 2;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new SuperPowerGlove((int)x, (int)y, (int)z);
    // }
}
